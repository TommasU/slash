import json
import pytest

from src.modules.app import app


@pytest.fixture
def client():
    app.config['TESTING'] = True
    with app.test_client() as client:
        yield client

def test_landingpage(client):
    response = client.get('/')
    assert response.status_code == 200

def test_signup(client):
    response = client.get('/signup')
    assert response.status_code == 200

def test_product_search(client):
    response = client.get('/search')
    assert response.status_code == 200

def test_product_wishlist(client):
    response = client.get('/wishlist')
    assert response.status_code == 200

def test_search_endpoint(client):
    response = client.get('/search?product_name=milk')
    assert response.status_code == 200

def test_wishlist_endpoint(client):
    response = client.get('/wishlist')
    assert response.status_code == 200

def test_product_search_filtered(client):
    response = client.get('/filter?product_name=milk&sort=default&currency=usd&num=default')
    assert response.status_code == 400