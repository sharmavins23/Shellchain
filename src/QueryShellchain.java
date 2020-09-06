import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author vinst
 */
public class QueryShellchain {

    private String url;
    private HttpURLConnection httpClient;
    private boolean serverStatus;

    public QueryShellchain() throws Exception {
        this.url = "http://localhost:5000";

        serverStatus = runShellchain();
        if (!serverStatus) {
            throw new Exception("ERROR: Was unable to connect to the shellchain.");
        }
    }

    private boolean runShellchain() {
        try {
            String runShellchainCommand = "powershell.exe  python python/app.py -p 5000";

            Process powerShellProcess = Runtime.getRuntime().exec(runShellchainCommand);
            powerShellProcess.getOutputStream().close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private HttpURLConnection establishConnection(String route) throws Exception {
        return (HttpURLConnection) new URL(url + route).openConnection();
    }

    public void digShells() throws Exception {
        httpClient = establishConnection("/dig_shell");

        httpClient.setRequestMethod("GET");

        int responseCode = httpClient.getResponseCode();
    }

    public void sellFish(FishSale sale) throws Exception {
        httpClient = establishConnection("/transactions/sell_fish");

        httpClient.setRequestMethod("POST");
        httpClient.setRequestProperty("Content-Type", "application/json; utf-8");
        httpClient.setDoOutput(true);

        String jsonInput = "{\"sender\":\"" + sale.getSender().toString()
                + "\",\"amount\":\"" + sale.getAmount()
                + "\",\"garbageAmount\":\"" + sale.getGarbageAmount() + "\"}";

        try (OutputStream os = httpClient.getOutputStream()) {
            byte[] input = jsonInput.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        final BufferedReader in = new BufferedReader(new InputStreamReader(httpClient.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        } // line now has response info
    }

    public void tradeFish(FishTrade trade) throws Exception {
        httpClient = establishConnection("/transactions/trade_fish");

        httpClient.setDoOutput(true);
        httpClient.setRequestMethod("POST");
        httpClient.setRequestProperty("User-Agent", "Java client");
        httpClient.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        httpClient.connect();

        String jsonInput = "{\"sender\":\"" + trade.getSender().toString()
                + "\",\"recipient\":\"" + trade.getRecipient().toString()
                + "\",\"amount\":\"" + trade.getAmount() + "\"}";
        byte[] out = jsonInput.getBytes(StandardCharsets.UTF_8);

        try (OutputStream os = httpClient.getOutputStream()) {
            os.write(out);
        }

        final BufferedReader in = new BufferedReader(new InputStreamReader(httpClient.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        } // line now has response info
    }

    public void catchCrab(CrabCatch crab) throws Exception {
        httpClient = establishConnection("/transactions/catch_crab");

        httpClient.setRequestMethod("POST");
        httpClient.setRequestProperty("Content-Type", "application/json; utf-8");
        httpClient.setDoOutput(true);

        String jsonInput = "{\"sender\":\"" + crab.getSender().toString()
                + "\",\"mafia\":\"" + crab.getMafia()
                + "\",\"amount\":\"" + crab.getAmount()
                + "\",\"garbageAmount\":\"" + crab.getGarbageAmount() + "\"}";

        try (OutputStream os = httpClient.getOutputStream()) {
            byte[] input = jsonInput.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        final BufferedReader in = new BufferedReader(new InputStreamReader(httpClient.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        } // line now has response info
    }

    public ArrayList<Shell> getChain() throws Exception {
        httpClient = establishConnection("/chain");
        httpClient.setRequestMethod("POST");
        int responseCode = httpClient.getResponseCode(); // Unhandled

        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(httpClient.getInputStream()))) {

            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
        }

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(response.toString());

        JSONArray jsonChain = (JSONArray) json.get("chain");

        ArrayList<Shell> chain = new ArrayList<Shell>();
        Iterator chainIter = jsonChain.iterator();
        while (chainIter.hasNext()) {
            JSONObject jsonShell = (JSONObject) chainIter.next();

            int index = Integer.parseInt(jsonShell.get("index").toString());
            String previousHash = jsonShell.get("previous_hash").toString();
            int proof = Integer.parseInt(jsonShell.get("proof").toString());
            double timestamp = Double.parseDouble(jsonShell.get("timestamp").toString());
            JSONArray jsonTransactions = (JSONArray) jsonShell.get("transactions");

            ArrayList<Transaction> transactions = new ArrayList<Transaction>();
            Iterator transactionIter = jsonTransactions.iterator();
            while (transactionIter.hasNext()) {
                JSONObject currentTransaction = (JSONObject) transactionIter.next();
                River sender = new River(currentTransaction.get("sender").toString(), 0, 0);
                int amount = Integer.parseInt(currentTransaction.get("amount").toString());

                // Check for the different object types and add to arraylist
                String currentTransactionType = currentTransaction.get("type").toString();
                if (currentTransactionType.equals("fish_sale")) {
                    int garbageAmount = Integer.parseInt(currentTransaction.get("garbageAmount").toString());

                    FishSale transaction = new FishSale(sender, amount, garbageAmount);
                    transactions.add(transaction);
                } else if (currentTransactionType.equals("fish_trade")) {
                    River recipient = new River(currentTransaction.get("recipient").toString(), 0, 0);

                    FishTrade transaction = new FishTrade(sender, amount, recipient);
                    transactions.add(transaction);
                } else if (currentTransactionType.equals("crab_catch")) {
                    String mafia = currentTransaction.get("mafia").toString();
                    int garbageAmount = Integer.parseInt(currentTransaction.get("garbageAmount").toString());

                    CrabCatch transaction = new CrabCatch(sender, amount, mafia, garbageAmount);
                }
            }

            Shell currentShell = new Shell(index, previousHash, proof, timestamp, transactions);
            chain.add(currentShell);
        }

        return chain;
    }

    public void shutDown() throws Exception {
        HttpURLConnection httpClient = establishConnection("/terminate");

        httpClient.setRequestMethod("POST");
        int responseCode = httpClient.getResponseCode();
        return;
    }
}
