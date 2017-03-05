import requests
from bs4 import BeautifulSoup as bs
import time
import xml.etree.ElementTree as ET
import urllib.parse as ulib

start_url = "https://elearning.utdallas.edu/webapps/portal/execute/tabs/tabAction?tab_tab_group_id=_1_1"
base_url = "https://elearning.utdallas.edu"
login_url = "https://elearning.utdallas.edu/webapps/login/"

payload = { "user_id": "*********", "password":"*******", "login":"Login","action":"login" }
head = {"User-Agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36",
           "Referer":start_url,"Origin":"https://elearning.utdallas.edu"}

session_requests = requests.session()

result = session_requests.get(start_url)
result = session_requests.post(login_url, data=payload, headers = head)

cookie = result.cookies


post_result = session_requests.get(start_url, cookies = cookie)


page = post_result.content

soup = bs(page,'html.parser')

print(soup.title)

courses_api = "https://elearning.utdallas.edu/webapps/portal/execute/tabs/tabAction"

data = {
'action':'refreshAjaxModule',
'modId':'_25_1',
'tabId':'_1_1',
'tab_tab_group_id':'_1_1'
}


response = requests.post(courses_api, data =data, cookies = cookie)


soup = bs(response.content, 'html.parser')

#print(soup.prettify())

cont = response.content.decode('utf-8')

soup = bs(cont, 'lxml')

#soup.findAll('div',{'class':'courseInformation'})

course_urls= []

for block in soup.findAll('a'):
    link = block['href']
    abs_link = ulib.urljoin(base_url, link.strip())
    course_urls.append(abs_link)

soup.findAll('span')[1].text.split(';')[0] # prof name

for block in soup.findAll('a'):
    courseName = block.text
    print(courseName)

#print(abs_link)

res = requests.get(course_urls[1], cookies = cookie)

soup = bs(res.content, 'html.parser')

print(soup.title)

divs = soup.findAll('li',{"class":"clearfix"})

for div in divs:
    if div.get_text() == 'Assignments':
        link = div.find('a')
        print(link['href'])
        

