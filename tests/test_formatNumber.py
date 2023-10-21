"""
Copyright (C) 2021 SE Slash - All Rights Reserved
You may use, distribute and modify this code under the
terms of the MIT license.
You should have received a copy of the MIT license with
this file. If not, please write to: secheaper@gmail.com

"""
import sys
import os

# Get the absolute path to the project's root directory
project_root = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))

# Add the project's root directory to the Python path
sys.path.insert(0, project_root)
# from src.modules import formatter
from src.modules.formatter import getNumbers


def test_getNumbers():
    """
    Checks the getNumbers function
    """
    assert getNumbers("some chars and $10.00") == 10.0
    assert getNumbers("some chars and $10.99 some other chars") == 10.99

# def test_formatTitle():
#     """
#     Checks the formatting of titles
#     """
#     assert len(formatTitle("abc"*40)) == 43

# def test_currency():
#     """
#     Checks the currency calculations
#     """
#     usd = 10
#     inr = formatter.EXCHANGES["rates"]["INR"]
#     eur = formatter.EXCHANGES["rates"]["EUR"]
#     aud = formatter.EXCHANGES["rates"]["AUD"]
#     yen = formatter.EXCHANGES["rates"]["JPY"]
#     pound = formatter.EXCHANGES["rates"]["GBP"]

#     assert formatter.getCurrency("inr", "$10.00") == "INR " + str(usd*inr)
#     assert formatter.getCurrency("euro", "$10.00") == "EURO " + str(usd*eur)
#     assert formatter.getCurrency("aud", "$10.00") == "AUD " + str(usd*aud)
#     assert formatter.getCurrency("yen", "$10.00") == "YEN " + str(usd*yen)
#     assert formatter.getCurrency("pound", "$10.00") == "POUND " + str(usd*pound)
