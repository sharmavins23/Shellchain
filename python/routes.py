from flask import Flask, jsonify, request, abort
from uuid import uuid4
import os
from win32api import GenerateConsoleCtrlEvent
from app import app, shellchain, startServer


# Save my address to identify myself as a node
node_identifier = str(uuid4()).replace('-', '')


# Create a new shell (mining)
@app.route('/dig_shell', methods=['GET'])
def dig_shell():
    last_shell = shellchain.last_shell
    proof = shellchain.proof_of_work(last_shell)

    previous_hash = shellchain.hash(last_shell)
    shell = shellchain.dig_shell(proof, previous_hash)

    response = {
        'message': "New shell dug up!",
        'index': shell['index'],
        'transactions': shell['transactions'],
        'proof': shell['proof'],
        'previous_hash': shell['previous_hash']
    }

    return jsonify(response), 200  # success code 200


# Sell fish out of the network (transactions)
@app.route('/transactions/sell_fish', methods=['POST'])
def sell_fish():
    values = request.get_json()

    required = ['sender', 'amount', 'garbageAmount']

    if not all(k in values for k in required):  # if some field is missing
        return 'Missing values', 400

    index = shellchain.fish_sale(
        values['sender'], values['amount'], values['garbageAmount'])

    response = {
        'message': f'Transactions will be added to shell {index}'
    }

    return jsonify(response), 201


# Trade fish within the network (transactions)
@app.route('/transactions/trade_fish', methods=['POST'])
def trade_fish():
    values = request.get_json()

    required = ['sender', 'recipient', 'amount']

    if not all(k in values for k in required):  # if some field is missing
        return 'Missing values', 400

    index = shellchain.fish_trade(
        values['sender'], values['recipient'], values['amount'])

    response = {
        'message': f'Transactions will be added to shell {index}'
    }

    return jsonify(response), 201


# Catch a crab
@app.route('/transactions/catch_crab', methods=['POST'])
def catch_crab():
    values = request.get_json()

    required = ['sender', 'mafia', 'amount', 'garbageAmount']

    if not all(k in values for k in required):  # if some field is missing
        return 'Missing values', 400

    index = shellchain.crab_catch(
        values['sender'], values['mafia'], values['amount'], values['garbageAmount'])

    response = {
        'message': f'Transactions will be added to shell {index}'
    }

    return jsonify(response), 201


# Post the entire chain
@app.route('/chain', methods=['POST'])
def full_chain():
    response = {
        'chain': shellchain.chain,
        'length': len(shellchain.chain)
    }

    return jsonify(response), 200


# Generate a CTRL+C event to stop the server process
@app.route('/terminate', methods=['POST'])
def shutdown_server():
    try:
        CTRL_C_EVENT = 0
        GenerateConsoleCtrlEvent(CTRL_C_EVENT, 0)
        return "Worked"
    except Exception as e:
        # force quit
        os._exit(0)
        return e


startServer()  # After all routes are loaded, run the flask application
