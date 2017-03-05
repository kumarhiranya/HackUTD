
# coding: utf-8

# In[210]:

import pandas as pd
import math


# In[13]:

df = pd.read_csv('data.csv', sep=',', encoding='utf-8')


# In[14]:

df


# In[126]:

for day in ["Tues"]:
    line = df[df['Schedule'].map(lambda x: day in x)]['Schedule']
    time_regex = r'^.*\s:\s(.*-.*)'
    time = re.search(time_regex,line.to_string())[1]
    print(day+" "+time)


# In[127]:

time_regex = r'^.*\s:\s(.*-.*)'


# In[124]:

import re


# In[145]:

time = re.search(time_regex,line.to_string())[1]


# In[146]:

print(time)


# In[148]:

start, end = time.split('-')


# In[153]:

start[0]


# In[163]:

end[0]
from datetime import datetime


# In[181]:

assgnmments = 0
time_left = []
due_dates = []
with open('deadlines.txt', 'r') as f:
    count=0
    for line in f.readlines():
        if(count%2==0):
            print("Course: "+line)
        else:
            row = line.split('\t')
            time = row[-1].strip()
            date = row[-3].strip()
            due = time+' '+date
            
            due = datetime.strptime(due,'%I:%M %p %A, %B %d, %Y')
            now = datetime.now()
            
            print(due-now)
            due_dates.append(due)
            time_left.append(due-now)
        count+=1
        assgnmments = count/2;


# In[182]:

expected_time_taken = []
for i in range(int(assgnmments)):
    inp = input('Expected time to complete assignment '+str(i+1)+': (in hrs)')
    expected_time_taken.append(inp)


# In[183]:

# expected time taken - from user
expected_time_taken #list
#due dates
due_dates # list
#time left
time_left # list


# In[205]:

time_left[0]


# In[211]:

rev_priorities = []
for i in range(len(time_left)):
    left_hrs = time_left[i].days*24+ time_left[i].seconds//3600
    rev_priorities.append(math.ceil(left_hrs/int(expected_time_taken[i])))
    print(rev_priorities[i])


# In[ ]:



