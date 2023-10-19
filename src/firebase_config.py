import pyrebase

firebase_config = {
    "apiKey": "AIzaSyDJAHCtz-K6SEYPyArm1W8uv28OtrKXm38",
    "authDomain": "slash-dabb0.firebaseapp.com",
    "projectId": "slash-dabb0",
    "storageBucket": "slash-dabb0.appspot.com",
    "messagingSenderId": "911245007818",
    "appId": "1:911245007818:web:c769ba05a0a6c2d9dacd33",
    "measurementId": "G-H9X5HCYPEK",
    "databaseURL": ""
}

firebase = pyrebase.initialize_app(firebase_config)
auth = firebase.auth()
db = firebase.database()
