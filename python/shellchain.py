from time import time
import hashlib
import json
from urllib.parse import urlparse
import requests


# Class definition of our shellchain (Blockchain-like) object
class Shellchain:
    def __init__(self):  # constructor
        self.current_transactions = []
        self.chain = []
        self.rivers = set()

        # the first seashell has to be created
        self.dig_shell(previous_hash='1', proof=100)  # first seashell in chain

    # mine a new block
    def dig_shell(self, proof, previous_hash):
        shell = {
            # point in chain that shell occurs at
            'index': len(self.chain) + 1,
            'timestamp': time(),  # current timestamp using date/time library
            'transactions': self.current_transactions,
            'proof': proof,
            'previous_hash': previous_hash or self.hash(self.chain[-1])
        }

        # reset current list of transactions
        self.current_transactions = []

        # add new shell to shellchain
        self.chain.append(shell)

        return shell

    # sale of fish based on certain garbage amount removed
    def fish_sale(self, sender, amount, garbageAmount):
        self.current_transactions.append({
            'type': 'fish_sale',
            'sender': sender,
            'amount': amount,
            'garbageAmount': garbageAmount
        })

        # return index of new transaction
        return self.last_shell['index'] + 1

    # trades a number of fish between rivers
    def fish_trade(self, sender, recipient, amount):
        self.current_transactions.append({
            'type': 'fish_trade',
            'sender': sender,
            'recipient': recipient,
            'amount': amount
        })

        # return index of new transaction
        return self.last_shell['index'] + 1

    # a crab is caught thieving
    def crab_catch(self, sender, mafia, amount, garbageAmount):
        self.current_transactions.append({
            'type': 'crab_catch',
            'sender': sender,
            'mafia': mafia,
            'amount': amount,
            'garbageAmount': garbageAmount
        })

        # return index of new transaction
        return self.last_shell['index'] + 1

    @property
    def last_shell(self):
        return self.chain[-1]

    @staticmethod
    def hash(shell):
        shell_string = json.dumps(shell, sort_keys=True).encode()
        return hashlib.sha256(shell_string).hexdigest()

    def proof_of_work(self, last_shell):
        # find a number p' such that hash(pp') contains 4 leading zeroes
        # p is previous proof, p' is new proof

        last_proof = last_shell['proof']
        last_hash = self.hash(last_shell)  # hashes last shell's proof of work

        proof = 0
        # checks every proof value until true
        while self.valid_proof(last_proof, proof, last_hash) is False:
            proof += 1

        return proof

    @staticmethod
    def valid_proof(last_proof, proof, last_hash):
        guess = f'{last_proof}{proof}{last_hash}'.encode()
        guess_hash = hashlib.sha256(guess).hexdigest()

        return guess_hash[:4] == "0000"
