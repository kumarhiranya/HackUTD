import requests
from bs4 import BeautifulSoup as bs
import pandas as pd
import re
import sys

import warnings
warnings.filterwarnings('ignore')

url = "https://coursebook.utdallas.edu/"
login_url = "https://coursebook.utdallas.edu/login/coursebook"
course_url = "https://coursebook.utdallas.edu/myclasses"


head = {"User-Agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36",
           "Referer":login_url,"Origin":url}


def getResponse(data):
    session = requests.session()
    result = session.get(login_url)
    result = session.post(login_url, data = data, headers = head)
    _cookies = result.cookies
    response = session.get(course_url, cookies = _cookies)
    return response


def getTabularData(response):
    table = pd.read_html(response.content)
    df = table[0]
    df = df[['Class SectionClass Number','Class Title', 'Instructor(s)', 'Schedule &Location']]
    regex = r'.*[ap]m([A-Z].*)$'
    df['Location'] = df['Schedule &Location'].map(lambda x: re.search(regex,str(x))[1])
    regex = r'(.*m).*'
    df['Schedule'] = df['Schedule &Location'].map(lambda x: re.search(regex,str(x))[1])
    del df['Schedule &Location']
    regex = r'(.*) \(.*\)$'
    df['Class Title'] = df['Class Title'].map(lambda x: re.search(regex, str(x))[1])
    df['Class'] = df['Class SectionClass Number']
    del df['Class SectionClass Number']
    return df

def saveToCSV(df, string):
    df.to_csv(string, sep=',')

from tkinter import *
import tkinter.messagebox as tm


class LoginFrame(Frame):
    def __init__(self, master):
        super().__init__(master)

        self.label_1 = Label(self, text="Username")
        self.label_2 = Label(self, text="Password")

        self.entry_1 = Entry(self)
        self.entry_2 = Entry(self, show="*")

        self.label_1.grid(row=0, sticky=E)
        self.label_2.grid(row=1, sticky=E)
        self.entry_1.grid(row=0, column=1)
        self.entry_2.grid(row=1, column=1)

        self.checkbox = Checkbutton(self, text="Keep me logged in")
        self.checkbox.grid(columnspan=2)

        self.logbtn = Button(self, text="Login", command = self._login_btn_clicked)
        self.logbtn.grid(columnspan=2)

        self.pack()

    def _login_btn_clicked(self):
        #print("Clicked")
        username = self.entry_1.get()
        password = self.entry_2.get()

        data = {"netid":username,
        "password":password,
        "action":"login"
        }
        response = getResponse(data)
        dataFrame = getTabularData(response)
        print(dataFrame)
        saveToCSV(dataFrame, 'data.csv')

def getData():
    netid = user
    passwd = pwd
    data = {"netid":netid,
        "password":passwd,
        "action":"login"
        }
    return data

def main():
    root = Tk()
    lf = LoginFrame(root)
    root.mainloop()
    
    
if __name__=='__main__':
    main()
