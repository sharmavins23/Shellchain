from argparse import ArgumentParser
from flask import Flask, abort, jsonify, request
import routes  # Register API routes
from shellchain import Shellchain  # Load our shellchain class

app = Flask(__name__)

# Create a Blockchain Application
shellchain = Shellchain()


def startServer():
    parser = ArgumentParser()

    # Usage: python main.py --port <any port number>
    parser.add_argument("-p", "--port", default=5000,
                        type=int, help="port to listen on")

    args = parser.parse_args()
    port = args.port  # Save the port value, default 3000

    app.run(host="0.0.0.0", port=port)
