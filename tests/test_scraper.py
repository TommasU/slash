from bs4 import BeautifulSoup

from src.modules.scraper import httpsGet, searchAmazon, prepare_html_document, prepare_xml_document, driver


def test_httpsGet():
    URL = "https://www.amazon.com"  # Replace with a valid URL
    response = httpsGet(URL)
    assert response is not None


def test_searchAmazon():
    # Create a BeautifulSoup object or use a mock to simulate the web page
    # Replace the arguments with appropriate values
    page = BeautifulSoup('<html><body><div data-asin="B08KWLDGMY" data-index="3" data-uuid="c177a9bf-6ccb-4982-be1c-973d05018272" data-component-type="s-search-result" class="sg-col-4-of-24 sg-col-4-of-12 s-result-item s-asin sg-col-4-of-16 AdHolder sg-col s-widget-spacing-small sg-col-4-of-20" data-component-id="4" data-cel-widget="search_result_2"></body></html>', "html.parser")
    df_flag = 0  # Set appropriate value
    currency = "USD"  # Set appropriate value
    products = searchAmazon(page, df_flag, currency)
    assert products is not None


def test_prepare_html_document():
    html_text = "<html>Mocked HTML content</html>"
    soup = prepare_html_document(html_text)
    assert isinstance(soup, BeautifulSoup)


def test_prepare_xml_document():
    xml_text = "<root>Mocked XML content</root>"
    soup = prepare_xml_document(xml_text)
    assert isinstance(soup, BeautifulSoup)


def test_driver():
    product = "milk"
    currency = "USD"
    num = 10
    df_flag = 0
    csv = False
    cd = None
    ui = False
    sort = "rades"
    isRestApi = False
    result = driver(product, currency, num, df_flag, csv, cd, ui, sort, isRestApi)
    assert result is not None
