from flask import Flask, request, render_template, session, jsonify
from src.modules.scraper import driver

import json
from src.firebase_config import auth

app = Flask(__name__, template_folder=".")
app.secret_key="Slash"


@app.route("/", methods=["POST", "GET"])
def landingpage():
    if request.method == "POST":
        print("post")
        email = request.form.get("email")
        password = request.form.get("password")
        try:
            user = auth.sign_in_with_email_and_password(email, password)
            session['user'] = email
            return render_template("./static/landing.html")
        except:
            error = "Invalid username or password. Try again"
            return render_template("./static/signin.html", error=error)
    else:
        return render_template("./static/signin.html")





# @app.route("/",methods=["POST","GET"])
# def landingpage():
#     if request.method == "POST" :
#         email = request.form.get("email")
#         password = request.form.get("password")
#
#         try:
#             user = auth.sign_in_with_email_and_password(email,password)
#             session['user'] = email
#         except:
#             return "Failed to Login"
#
#     return render_template("./static/landing.html")
#
#
@app.route("/signup", methods=["POST", "GET"])
def signup():
    error = None
    if request.method == "POST":
        email = request.form.get("email")
        password = request.form.get("password")
        confirm_password = request.form.get('confirmpassword')
        if email and password and confirm_password:
            print("valid")
            if password != confirm_password:
                error = 'Invalid username or password. Please try again!'
                return render_template("./static/signup.html", error=error)
            try:
                auth.create_user_with_email_and_password(email, password)
                return render_template("./static/landing.html")
            except:
                error = "Invalid username or password. Try again"
                return render_template("./static/signup.html", error=error)
        else:
            error = "Invalid email/password"
            return render_template("./static/signin.html", error = error)
    else:
        return render_template("./static/signup.html")


@app.route("/search", methods=["POST", "GET"])
def product_search(new_product="", sort=None, currency=None, num=None):
    product = request.args.get("product_name")
    if product == None:
        product = new_product
    isRestApi = request.headers.get('Content-Type', '') == 'application/json'
    data = driver(product, currency, num, 0, False, None, True, sort, isRestApi)
    if isRestApi:
        return jsonify(data)

    return render_template("./static/result.html", data=data, prod=product)

@app.route("/wishlist", methods=["POST", "GET"])
def product_wishlist(new_product="", sort=None, currency=None, num=None):
    # product = request.args.get("milk")
    product = "milk"
    if product == None:
        product = new_product
    isRestApi = request.headers.get('Content-Type','') == 'application/json'
    data = driver(product, currency, num, 0, False, None, True, sort, isRestApi)
    if isRestApi:
        return jsonify(data)

    return render_template("./static/wishlist.html", data=data, prod=product)

# @app.route('/wishlist')
# def wishlist():
#     return render_template("./static/wishlist.html")

@app.route("/filter", methods=["POST", "GET"])
def product_search_filtered():
    product = request.args.get("product_name")
    sort = request.form["sort"]
    currency = request.form["currency"]
    num = request.form["num"]

    if sort == "default":
        sort = None
    if currency == "usd":
        currency = None
    if num == "default":
        num = None
    return product_search(product, sort, currency, num)
