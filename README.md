# Shellchain

A small Blockchain-based enterprise resource planner for a bank, created for my
Blockchain Deep Dive course.

## Overview

The project is made using Java's swing GUI library on the front end. The backend
Python Flask server is run through Java's Process functionality, built into
runtime. Managing the server is done automatically on startup and stop events
from the Swing GUI, through emulating console commands.

## Scenario

The god of the seas, Poseidon, is having trouble keeping track of the individual
rivers running off from his mighty ocean. He has found that the mermaid owners
of his individual rivers (and their corresponding river**bank**s) are holding a
very lucrative side business selling fish as food. Swans and geese come to the
riverbanks and take away trash/litter with their beaks. For their efforts, they
are rewarded with fish. This sustainable business model has piqued Poseidon’s
interest, as it encourages recycling and beautifies his rivers, and indirectly,
his oceans as well.

However, this system is imperfect. The mermaids are complaining about a lack of
transparency and organization in the individual riverbanks, and their **resource
planning**. The swans and geese are too often turned away at some rivers due to
a lack of fish, and these rivers begin to pile up with trash. Other rivers are
pristine and clean but have too many fish as a result. The mermaids feel that
Poseidon is becoming picky with which mermaids he likes and allowing fish to
flourish in those areas as a result. To add to the issue, thieving crabs from
international crab mafias come and steal stray fish. They leave around trash and
use thrown away plastic soda can rings, rope, and anything else they can find to
snatch up fish and steal them for their own nefarious biddings.

Poseidon has chosen to solve these dilemmas by creating an information network
for all of his mermaids to interact with. Using enchanted seashells that he digs
up from beneath the sand at the beach, Poseidon can create copies of these
shells and teleport them around to the individual mermaids. Mermaids can also
write on these shells in order to keep better records, but only Poseidon can dig
up a new shell and distribute the information. These shells will help Poseidon
and the mermaids keep track of their booming river business. It also lets
Poseidon be more transparent and regain his employees’ trust in his leadership
and expertise.

## Features of Poseidon's Enterprise Resource Planner

Poseidon’s rivers’ main business utilizes fish as a primary product. Fish travel
through the different rivers, and swans and geese are capable of coming to the
rivers and taking fish. Poseidon needs to manage these rivers and be able to
redistribute fish to and from different rivers.

1. The primary feature of Poseidon’s resource planner is his ability to
   distribute seashells. This feature allows Poseidon to dig up a new seashell,
   which will publish all the information from individual mermaids that happened
   from the previous seashell.
2. Poseidon, known by the Greeks as the Earth-shaker, is constantly creating new
   rivers with his might. There are also a lot of rivers that Poseidon has yet
   to document. This feature will register a new mermaid owner of a river as a
   new node in the Blockchain, allowing them to be open to any transactions or
   view shared information.
3. Certain rivers may have a far larger stock of fish than others. Swans and
   geese were having issues with certain rivers not having the proper amount of
   fish. In order to solve this, Poseidon needs a way to transfer fish from one
   river to another.
4. Swans and geese need to be able to take fish out of the river. The river
   mermaids need to be able to document this sale of fish in a transparent and
   simple way. This feature will track a sale of fish for a specific amount of
   trash taken out.
5. The river mermaids spend a lot of time catching thieving crabs, but it is
   useful to know which crab mafia the crabs belong to. Currently, the crabs
   have formed into three mafias – the Beta Cancri, the Karka Rashi, and the
   Shiny Kinglers. This feature allows a mermaid to report when they catch a
   crab, and after the mermaids torture the crab into spilling what faction it
   comes from, the mermaids can further report which mafia it comes from as
   well.
6. Poseidon wants to know how the individual rivers are performing, and so this
   feature allows him to view the information about an individual river. This
   feature will display things like the total amount of fish sold, the current
   amount of fish in the river, the current amount of trash in the river, how
   many crabs were caught, which crab mafia is the most active in the area
   around each river, and other useful information.

## User Experience

The application is written in Java Swing graphical user interfaces. On launch,
the user is met with individual tabs for the different functions.

There are four different tabs for the four main types of functions, and these
correspond to the backend API routes as well. Furthermore, there is a combo box
with an account selection in the top above the tabs.

Upon selecting an account, the actions performed by the user of the application
will hinge upon that account selected. By default, the only account present will
be Poseidon. The Poseidon account has different permissions and can utilize
different pages compared to other account holders. The first two tabs are
limited to the account holder Poseidon, and the third tab is limited to other
account holders. The fourth tab is available for all users to utilize.

The first tab allows Poseidon to dig up a shell by pressing a button. In doing
so, the information from transactions is instantly updated, and can be seen in
the status tab.

The second tab allows Poseidon to register another river. The user can input the
name of the river, as well as the starting integer amount values of fish and
garbage in the river. Upon pressing the submit button, the river will be created
if the values are properly parsed, or an error message will show up if the
values are improperly entered. The requisite combo boxes for account holders are
updated when a new river is added.

The third tab has three subtabs nested within it. The third tab is predominantly
for any form of transaction, which is recorded on a shell.

The first subtab allows the user to document a fish sale. The sender of this
fish sale is automatically scraped as the account holder from the topmost combo
box. The values of amounts and garbage amounts are entered as well.

The second subtab allows the user to document a trade of fish between different
rivers. The sender is automatically scraped, but the user may enter a recipient
and an amount of fish. The recipients are selected through a combo box dropdown
that is automatically updated.

The third subtab allows the user to document a crab being caught. The values for
fish amount stolen and garbage amount left behind can be input as integers. The
user can also select a mafia out of the possible set.

Finally, the fourth tab allows any user regardless of account currently logged
in to view the status of any other user. They can select an account to view
status of from the combo box, and once they do so, press the submit button to
view information about the status. If Poseidon is chosen, the total amount of
shells dug up will be shown. For every other river, the current values of fish,
garbage, as well as the amount of crabs caught sorted by mafia are all
displayed.

## (Technical) Overview

The minimum viable product runs a blockchain server through Python, using a
Flask app. This server can then be sent HTTP requests, which is sent through a
Java query class designed to interact with the server. JSON values for body
information are sent through Java HttpURLConnection HTTP POST requests, and
information is retrieved through JSON as well. As such, a Java library for JSON
parsing (JSON-Simple, distributed by Google) is utilized for both JSON parsing
and converting into objects. Finally, a Java Buffered Reader is utilized to read
the output stream from the HTTP requests. This appends the values to a line
using a String Builder, and is capable of concatenating the JSON strings to
further be parsed in to JSON objects and JSON arrays.

Once the requisite information from the backend is converted into the requisite
set of nested objects, the front-end code can utilize this to display to the
user using Swing graphical user interfaces and components like JFrames. Due to
the way the backend is run through Java, the individual nodes actually connect
through one connection, and individual transactions are handled through a single
querying class.

In this way, the Blockchain backend is used more as a database to store
information about transactions in an immutable and easily accessible way. Any
connection can theoretically connect and scrape the information for any form of
use, but the Blockchain is set up such that the singular boss (in this case,
Poseidon) can add events. This allows him to keep control of the planner, while
still allowing for transparency for his employees.

### Software End-Points

In this scenario, the different mermaids would use similar copies of the same
UI, which represents a minimum viable product for a connection server.

The current Java product connects to the Python server by emulating a powershell
command line run task to run the shellchain.py file. When the server is
terminated, a powershell command line interface CTRL+C (program execution halt)
task is emulated. The individual client applications would connect in these
ways.

Furthermore, a different address can be generated for every consecutive user,
and this address would be automatically updated in the adjacency lists for the
other users.

### Distribute seashells

This feature would, on demand, distribute a seashell to the individual mermaid
employees. In practice, it is the equivalent of mining a new block in the
blockchain application. It would require no inputs other than the user’s press
of a button and would output a message to the user confirming that the specific
action was performed properly.

The process of designing this feature in a way that’s user friendly and quickly
accessible is a bit more complicated than just the creation of a new block.
However, the creation of the new block is done and stored on the Python backend
server. A new block is created, and the relevant calculations for a hash value
for the new block are calculated. The previous hash value is stored, as well as
the index, timestamp, and finally, any pending transactions that need to be
displayed on the block will be put onto the block. Doing so will make all
transactions immutable.

Using Flask, an API route is created which will allow for the HTTP requests to
ping this backend. These HTTP routes form the pathway between the frontend and
backend. The next set of processing is done in Java. An event handler on the
button that a user clicks will activate when the button is pressed, which will
firstly check the current account selected. Since only Poseidon can dig a new
shell up, this check will ensure that only Poseidon is utilizing the command and
return or throw an error message up when someone else tries to utilize it. From
there on a query class will handle the interconnect between the Java graphical
user interface and the Python backend. This query class sets up an
HttpURLConnection, which is a connection utilized by the query class to send GET
and POST requests. From this point, a GET request is sent to the Python server,
which then tells it to dig a shell up.

### Register rivers

This feature allows Poseidon to register a new river on demand. A river in this
case would be a specific node, which is capable of acting as the recipient or
sender of certain transactions. The individual rivers act as data models for the
individual business points in the network, so they need to not only store a name
and potential address, but the amount of resources they have. In particular, the
information that will need to be input will be the river’s starting amount of
fish, amount of garbage, and the name of the mermaid residing over the river.

This section will not output anything in specific, as output will be handled by
a separate feature. However, this feature can output a small message confirming
the status of the user’s request. From the moment the user submits this
information, processing is then done through the Java query class, designed to
act as an intermediary between the backend and user interface.

The information is made into a requisite Java object, which is then sent to the
query class to be picked apart and converted into a JSON string. This
information is then sent to the backend for processing. Using a flask app route,
this information is converted into an adjacency list for the existing node. If
there are multiple nodes, each individual node’s adjacency list of neighbors has
to be updated in order to include the added river.

### Sell fish

The major portion of the business is selling fish to swans and geese coming to
the rivers. In exchange for them taking away garbage and cleaning up the
surrounding area, they are rewarded with fish. In this stead, it is important to
keep track of the change of resources – that is, the amount of fish traded for
the amount of garbage collected. Thus, the input for this feature would directly
be the amount of fish sold, and the amount of garbage collected. It’s important
to note that another key piece of information is required for this – the
information of the mermaid/node owner conducting the sale.

The information for the individual sale is sent through to become a sale object
in Java. This allows certain information about the sale to be stored and
utilized later. The object is then passed to the query class, where it is then
created into a JSON for an HTTP POST request. The request is then sent to the
backend using an HTTP URL connection.

On the backend, the flask app route accepts the information and calls the
requisite function in the blockchain code. This code takes the JSON file and
strips it down to its requisite parts, creating an object that goes into the
list of pending transactions. When the next shell is dug up, the transaction
will get appended to this shell and will become an immutable record.

### Trade fish

A key feature that the mermaids can utilize in this network setup is the ability
to redistribute resources amongst rivers. By monitoring the status of other
rivers, and comparing with others, one can select a recipient that requires
certain resources and send a certain amount of fish to that place. This feature
records that event, such that no double-allocation is performed, and there are
as few inefficiencies in the sale process as possible.

As such, the inputs for such a function hinge around the sender, which is a
mermaid/river owner. They can select a recipient, who is another mermaid/river
owner, to trade to. Finally, they can input an amount of fish to send. The
information for the individual sale is sent through to become a trade object in
Java. This allows certain information about the sale to be stored and utilized
later. The object is then passed to the query class, where it is then created
into a JSON for an HTTP POST request. The request is then sent to the backend
using an HTTP URL connection.

On the backend, the flask app route accepts the information and calls the
requisite function in the blockchain code. This code takes the JSON file and
strips it down to its requisite parts, creating an object that goes into the
list of pending transactions. When the next shell is dug up, the transaction
will get appended to this shell and will become an immutable record.

### Catching a crab

One of the major issues with the current system is that thieving crabs come and
steal fish and leave around a significant amount of trash and waste. The crabs
have seemed to so far to follow the trend of ganging up in small groups, and by
sending reports of these crabs and their specific mafia, one is capable of
tracking down the specific area that certain mafias operate. By looking at two
location-wise positionally close nodes and seeing patterns in the amount of
crabs they caught belonging to a certain faction, one can deduce which faction
is active in that area.

In this way it becomes crucial to input the information about the sender of the
crab catch event, as well as the amount of fish the crab stole and the amount of
garbage the crab left behind for numerical purposes. Finally, the specific mafia
that the crab belongs to can be selected as well as an important piece of input
information.

The information for the individual sale is sent through to become a catch crab
event object in Java. This allows certain information about the sale to be
stored and utilized later. The object is then passed to the query class, where
it is then created into a JSON for an HTTP POST request. The request is then
sent to the backend using an HTTP URL connection.

On the backend, the flask app route accepts the information and calls the
requisite function in the blockchain code. This code takes the JSON file and
strips it down to its requisite parts, creating an object that goes into the
list of pending transactions. When the next shell is dug up, the transaction
will get appended to this shell and will become an immutable record.

### Displaying the status of a river

This feature allows anyone to view the status of another river. By constantly
monitoring surrounding rivers’ information on sales, trades, and crab catches,
rivers can better plan for the redistribution of their resources or keep more
vigilant in the case of repetitive crab attacks.

The input of this feature would be the river in question, of which the status
can be read. The output for this feature would be the river’s information on the
amount of fish remaining in the river, the amount of garbage still in the river,
and the amount of crabs caught, distributed by their requisite mafia.

The processing for this feature would start in the backend, as a chain of blocks
is slowly built up upon every single block addition event. This chain is
information stored through nested JSON objects and is sent through the flask app
route. From there a simple HTTP POST request is capable of returning this
information. The information is then handled by a Java querying class. Using an
HTTP URL connection, and a Buffered Reader, the information is read out and
stored in a Java string. One of the first things the Java string undergoes is
its creation into a JSON object. This JSON object is then iterated through as a
JSON array, iterating through each individual shell in the chain. Each
individual shell in the chain is treated as a JSON object, which allows us to
access its individual objects – in particular, its transactions.

The transactions subarray is converted into a JSON array and iterated through
and casted into Transaction objects. The Transaction objects class is a super
class for the three individual types of transactions. As each transaction is run
through, the type (listed in the JSON parameters) is read and the transaction
object is downcasted into its corresponding transaction sub-class object. This
allows the more specific information to be stored in the individual classes.
Once the transaction object is properly downcasted and created, it is then
stored in an ArrayList for later usage. The rest of the information is also
stored in a shell object along with the ArrayList for transactions, and these
shell objects make up an ArrayList representing the entire chain.

For calculating a single river’s status, the entire chain ArrayList is iterated
through. Every single shell individually has its transactions iterated through,
now as Java objects. Depending on their specific casting and type, they modify
running sums of fish amounts, garbage amounts, and crab catch amounts. The
rivers store the initial values of fish and garbage, and the transactions store
the changes in fish and garbage amounts, so the current amounts are recalculated
based on the entire pulled blockchain each individual time. This ensures that
none of the data is wrongfully manipulated or inaccurate from the immutable
record. Once these running total values are summed and calculated, they are
displayed to the user in the graphical interface.

# License TL;DR

This project is distributed under the MIT license. This is a paraphrasing of a
[short summary](https://tldrlegal.com/license/mit-license).

This license is a short, permissive software license. Basically, you can do
whatever you want with this software, as long as you include the original
copyright and license notice in any copy of this software/source.

## What you CAN do:

-   You may commercially use this project in any way, and profit off it or the
    code included in any way;
-   You may modify or make changes to this project in any way;
-   You may distribute this project, the compiled code, or its source in any
    way;
-   You may incorporate this work into something that has a more restrictive
    license in any way;
-   And you may use the work for private use.

## What you CANNOT do:

-   You may not hold me (the author) liable for anything that happens to this
    code as well as anything that this code accomplishes. The work is provided
    as-is.

## What you MUST do:

-   You must include the copyright notice in all copies or substantial uses of
    the work;
-   You must include the license notice in all copies or substantial uses of the
    work.

If you're feeling generous, give credit to me somewhere in your projects.
