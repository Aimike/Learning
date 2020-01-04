#
# 爬虫学习
import requests,os,sys
from bs4 import BeautifulSoup

def write_file(file,desfile):
    """写入文件"""
    with open(desfile,"w+",encoding='utf-8') as f:
        f.write(file)
        f.close()

def send_request(link,desf,headers = {"User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:71.0) Gecko/20100101 Firefox/71.0"}):
    r = requests.get(link,headers=headers)
    r.encoding = 'utf-8'
    soup = BeautifulSoup(r.text,'lxml')
    write_file(r.text,desf)
    a_list = soup.find_all('a')
    global exists_a,exists_dir
    for a_demo in a_list:
        if  (a_demo.attrs['href'].strip() not in exists_a) and (a_demo.attrs['href'].strip() != '') and (a_demo.attrs['href'][0] == '/') :
            exists_a.append(a_demo.attrs['href'].strip())
            index_r = a_demo.attrs['href'].strip().rfind('/')
            if (len(a_demo.attrs['href'].strip()) > 1) and (not os.path.exists('./text'+a_demo.attrs['href'].strip().split('/')[-1])) and ('./text' + a_demo.attrs['href'].strip()[:index_r]not in exists_dir):
                print('索引' + str(index_r))
                print('原文' + a_demo.attrs['href'].strip())
                os.makedirs('./text' + a_demo.attrs['href'].strip()[:index_r])
                exists_dir.append('./text' + a_demo.attrs['href'].strip()[:index_r])
            if index_r == len(a_demo.attrs['href'].strip()) - 1:
                send_request(link[:link.rfind('/')] + a_demo.attrs['href'].strip(),'./text/'+a_demo.text.strip()+'.html')
            else:
                send_request(link[:link.rfind('/')] + a_demo.attrs['href'].strip(),'./text'+a_demo.attrs['href'].strip())
            print(a_demo.attrs['href'])
    return r,soup

link = "https://www.daibei.ink/"
exists_a=[]
exists_dir=[]
r,soup = send_request(link,"./text/index.html")#发送请求爬取主页面

# link = "https://www.daibei.ink/skin/db/js/index.js"
# os.makedirs("./text/skin/db/css/")
# send_request(link,"./text/skin/db/js/index.js")

# r = requests.get(link,headers={"User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:71.0) Gecko/20100101 Firefox/71.0"})
# r.encoding = 'utf-8'
# soup = BeautifulSoup(r.text,'lxml')
# #write_file(r.text,desf)
# img_list = soup.find_all('img')
# for ea in img_list:
#     r = requests.get(link + ea.attrs['src'].strip(),headers={"User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:71.0) Gecko/20100101 Firefox/71.0"})
#     r.encoding = 'utf-8'
#     soup = BeautifulSoup(r.text,'lxml')
#     index_r = ea.attrs['src'].rfind('/')
#     if (not os.path.exists('./text' + ea.attrs['src'].strip()[:index_r])) and (ea.attrs['src'].strip() not in exists_dir):
#         print('原文' + ea.attrs['src'].strip())
#         os.makedirs('./text' + ea.attrs['src'].strip()[:index_r])
#         exists_dir.append(ea.attrs['src'].strip())
#     write_file(r.text,'./text'+ ea.attrs['src'].strip())
