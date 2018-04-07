from flask import Flask, abort, request 
from pymongo import MongoClient
import json

app = Flask(__name__)
client = MongoClient("mongodb://developer:qwerty123@ds243325.mlab.com:43325/findme")
db = client['findme']

def emailPresent(em):
	cursor = db.login_info.find({"email": em})
	if cursor.count()==0:
		return False
	else:
		return True

@app.route("/login", methods=['POST'])
def login():
	email=request.json['email']
	password=request.json['pass']
	cursor = db.login_info.find({"email": email})
	if cursor.count()==0:
		return "Email not present"
	else:
		pwd=cursor[0]['pass']
		if password==pwd:
			return "True"
		else:
			return "False"

@app.route("/register", methods=['POST'])
def register():
	email=request.json['email']
	password=request.json['pass']
	if password=="":
		return "Invalid Password"
	emailPreStatus = emailPresent(email)
	if emailPreStatus==False:
		db.login_info.insert_one(
			{
				"email":email,
				"pass":password
			}
		)
		return "Congrats! You are registered"
	else:
		return "Email already registered. Try another one."


if __name__ == '__main__':
    app.run()