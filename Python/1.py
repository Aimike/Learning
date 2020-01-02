import requests
from bs4 import BeautifulSoup
link = "https://www.daibei.ink/"
headers = {"User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:71.0) Gecko/20100101 Firefox/71.0"}
r = requests.get(link,headers=headers)

with open("test.txt","a+") as f:
    f.write(r.text)
    f.close()
