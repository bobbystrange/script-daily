#!/usr/bin/env python3
# -*- coding: utf-8 -*-
#
# Create by tuke on 2021/2/523

import requests
import logging
import http.client
import random
import uuid
import time
import json

# initialize logging
http.client.HTTPConnection.debuglevel = 1
logging.basicConfig()
logging.getLogger().setLevel(logging.DEBUG)
requests_log = logging.getLogger("requests.packages.urllib3")
requests_log.setLevel(logging.DEBUG)
requests_log.propagate = True

###

save_url  = "http://release.dev.newcoretech.com/api/metadata-app/data/save"
cookie = "BUSINESS=2; STAFF_ID=10000004; TENANT_ID=24852d91-5c43-4f67-99c5-e6e441337749; TOKEN=011BAF6AF6EA43A1DC2E9F4056857C11; SESSION=NDEwMzUyZDItOTQ1OC00OTM4LThiOWYtZmQxNTA5MGY5ODdj; zg_did=%7B%22did%22%3A%20%22176cc7a5651a58-0b0c95a944359b-163a6153-13c680-176cc7a5652e4d%22%7D; zg_386d7dc571e242ca9a0c778b2fffcfa3=%7B%22sid%22%3A%201614062375769%2C%22updated%22%3A%201614062609111%2C%22info%22%3A%201614052594282%2C%22superProperty%22%3A%20%22%7B%5C%22tenantId%5C%22%3A%20%5C%2224852d91-5c43-4f67-99c5-e6e441337749%5C%22%2C%5C%22%E4%BC%81%E4%B8%9A%E5%90%8D%E7%A7%B0%5C%22%3A%20%5C%22%E6%96%B0%E6%A0%B8%E4%BA%91%E6%B5%8B%E8%AF%95%5C%22%7D%22%2C%22platform%22%3A%20%22%7B%7D%22%2C%22utm%22%3A%20%22%7B%7D%22%2C%22referrerDomain%22%3A%20%22%22%2C%22landHref%22%3A%20%22http%3A%2F%2Frelease.dev.newcoretech.com%2Fhome%2F%22%2C%22cuid%22%3A%20%2224852d91-5c43-4f67-99c5-e6e441337749_10000004%22%2C%22zs%22%3A%200%2C%22sc%22%3A%200%2C%22firstScreen%22%3A%201614062375769%7D"
cookies={}
for line in cookie.split(';'):
    name,value=line.strip().split('=',1)
    cookies[name]=value


categories = [
    '''
    {
        "name": "B类",
        "field_2IweF__c": 1,
        "code": "122",
        "id": "10005122",
        "field_UQDns__c": "2"
    }
    '''
    ,
    '''
    {
        "name": "A类",
        "field_2IweF__c": 1,
        "code": "123",
        "id": "10005121",
        "field_UQDns__c": "2"
    }
    '''
]

body = '''
{left_bracket}
    "body": {left_bracket}
        "templateId": "2785",
        "info": {left_bracket}
            "name": "{text}",
            "code": "{text}",
            "address": "{text}",
            "zipcode": "{number}",
            "phone": "{phone}",
            "fax": "{text}",
            "contacts": "{text}",
            "trade": "{text}",
            "procurement_tax_rate": "{grace}",
            "calling_code": "86",
            "mobile": "{mobile}",
            "email": "{text}",
            "comments": "{text}",
            "bank_account_name": "{text}",
            "bank_name": "{text}",
            "bank_account": "{text}",
            "invoice": "{text}",
            "tax_num": "{text}",
            "category": {category},
            "currency": "人民币",
            "field_WwCYU__c": "{text}",
            "field_ULYw4__c": "{number}",
            "field_lEvRL__c": {timestamp},
            "field_Nz6Tu__c": "{select}"
        {right_bracket},
        "tables": []
    {right_bracket}
{right_bracket}
'''


def new_customer(seq):
    data = body.format(
            left_bracket='{',
            right_bracket='}',
            text=str(uuid.uuid4())[:8] + "-" + str(seq),
            number=random.randint(0, 2),
            grace=random.randint(0, 99),
            mobile=random.choice(['13098340163', '13905001034']),
            phone=random.choice(['13098340163', '13905001034']),
            timestamp=int(time.time_ns() / 1000_000),
            category=random.choice(categories),
            select=random.choice(['4', '6', '12'])
    )
    print("POST %s" % data)

    resp = requests.post(url=save_url, json=json.loads(data), cookies=cookies)
    print("Response: %s" % resp.text)


def main():
    for i in range(450, 10001):
        # time.sleep(0.01)
        new_customer(i)


if __name__ == '__main__':
    main()
