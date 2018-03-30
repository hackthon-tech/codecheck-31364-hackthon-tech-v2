
# firebase
import firebase_admin
from firebase_admin import credentials
import time
import datetime
import re


import requests
import json

from urllib.parse import urlparse
import mysql.connector
import sys


__author__ = 'manabu'

import json
import requests

fcm_header = {
    'Content-type': 'application/json; charset=UTF-8',
    'Authorization': '{firebase_apikey}'
}


base_ekisupo_url = "http://api.ekispert.jp/v1/json"
rosen_url = base_ekisupo_url + "/operationLine?key={apikey}"
tien_url = base_ekisupo_url + "/rail/service/information?key={apikey}"

# mysqlUrl
mysql_url = urlparse('mysql://localhost:3306/okiteyoboy?useSSL=false')

def rosen_info_json(offset):
    url = rosen_url + "&offset=" + str(offset)
    print(url)
    headers = {"content-type": "application/json"}
    res = requests.get(url, headers=headers)
    return res.json()


def rosen_max():
    url = rosen_url + "&limit=100&offset=" + str(1)
    print(url)
    headers = {"content-type": "application/json"}
    res = requests.get(url, headers=headers)
    return res.json()['ResultSet']['max']

def tien_info_json():
    pass 

# 路線の遅延ユーザをセレクトする
def searchUser(cur, now, company):
    cur.execute('SELECT * FROM userMaster')

    print(cur.fetchall() )

def updateNotificationUser(cur):
    print(updateNotificationUser)


# 遅延情報のユーザを検索する
def tienUserNotification():
    url = tien_url
    # print(url)
    headers = {"content-type": "application/json"}
    res = requests.get(url, headers=headers)
    

    tien_info_json = res.json()['ResultSet']


    if not 'Information' in tien_info_json:
        return

    lineListKari = []
    # 遅延情報の取得
    for tien_data in tien_info_json['Information']:
        if tien_data['status'] == '平常運転':
            continue
        if tien_data['status'] == 'その他':
            continue

        if type(tien_data['Line']) == list:
            for data in tien_data['Line']:
                print(data['Name'])
                lineListKari.append(tien_data['Line']['Name'])

        else :
            print(tien_data['Line']['Name'])
            lineListKari.append(tien_data['Line']['Name'])


    print("#################################### \n JRの文字を削除する \n####################################")
    lineList = []
    for line in lineListKari:
        if not re.search("^.*ＪＲ.*", line):
            lineList.append(line)
        else:
            if line == "ＪＲ":
                continue
            else :
                lineList.append(line.split("ＪＲ")[1])

    print("#################################### \n DBから値を取得 \n####################################")
    conn = mysql.connector.connect(
        host = mysql_url.hostname or 'localhost',
        port = mysql_url.port or 3306,
        user = mysql_url.username or 'root',
        password = mysql_url.password or '',
        database = mysql_url.path[1:],
    )

    if not conn.is_connected():
        print("not db connection")
        sys.exit(0)


    # 現在時間
    now = datetime.datetime.now()
    # 現在時間 + 1h
    now_1hour = now + datetime.timedelta(hours=1)
    # 1990 1/1 (現在時間 + 1h[h]):(現在時間 [mm])
    now1990 = datetime.datetime(1990, 1, 1, int(now_1hour.hour), int(now_1hour.minute), 0)


    # 路線ごとにプッシュ通知を送信する
    for line in lineList:
        print(line + "を設定しているユーザの情報にプッシュ通知を送る")
        cur = conn.cursor()

        # 遅延情報に合わせて
        cur.execute('SELECT objectId, deviceToken FROM userMaster WHERE lastPushDate  < %s and wakeUpTime < %s and company LIKE %s and alarm = 1', (now.strftime('%Y-%m-%d') ,now1990.strftime('%Y-%m-%d %H:%M:%S'), line))

        uidList = []
        deviceTokenList = []
        for curData in cur.fetchall():
            uidList.append(curData[0])
            if not curData[1] == None:
                deviceTokenList.append(curData[1])


        # ここでプッシュ通知の作成

        # プッシュ通知の送信
        for token in deviceTokenList:        
            payload = {'to': token, 'priority': 'high', 'data': {}}
            r = requests.post('https://fcm.googleapis.com/fcm/send', headers=fcm_header, data = json.dumps(payload))
            print(r)
            print(payload)

        # データの更新
        updateNowDate = datetime.datetime(int(now.year), int(now.month), int(now.day), 23, 59, 59)
        print(updateNowDate)
        for uid in uidList:
            print('uid : ' + uid)
            print('updateNowDate : ' + updateNowDate.strftime('%Y-%m-%d%H:%M:00'))
            cur.execute('UPDATE userMaster SET lastPushDate = %s WHERE objectId = %s and alarm = 1', (updateNowDate.strftime('%Y-%m-%d %H:%M:00'), uid, ))
            conn.commit()


def normalUserNotification():

    conn = mysql.connector.connect(
        host = mysql_url.hostname or 'localhost',
        port = mysql_url.port or 3306,
        user = mysql_url.username or 'root',
        password = mysql_url.password or '',
        database = mysql_url.path[1:],
    )

    if not conn.is_connected():
        print("not db connection")
        sys.exit(0)

    cur = conn.cursor()
    now = datetime.datetime.now()
    now1990 = datetime.datetime(1990, 1, 1, int(now.hour), int(now.minute), 0)


    print(now.strftime('%Y-%m-%d'))
    print(now.strftime('%Y-%m-%d %H:%M:%S'))
    print(now1990.strftime('%Y-%m-%d %H:%M:00'))

    cur.execute('SELECT objectId, deviceToken FROM userMaster WHERE lastPushDate  < %s and wakeUpTime < %s', (now.strftime('%Y-%m-%d'), now1990.strftime('%Y-%m-%d %H:%M:00'), ))


    uidList = []
    deviceTokenList = []
    for curData in cur.fetchall():
        uidList.append(curData[0])
        if not curData[1] == None:
            deviceTokenList.append(curData[1])


    # ここでプッシュ通知の作成
    
    # デバイストークンのリストだと失敗する。
    # payload = {'to': deviceTokenList, 'priority': 'high', 'data': {}}

    # プッシュ通知の送信
    for token in deviceTokenList:        
        payload = {'to': token, 'priority': 'high', 'data': {}}
        r = requests.post('https://fcm.googleapis.com/fcm/send', headers=fcm_header, data = json.dumps(payload))
        print(r)
        print(payload)

    # データの更新
    updateNowDate = datetime.datetime(int(now.year), int(now.month), int(now.day), 23, 59, 59)
    print(updateNowDate)
    for uid in uidList:
        print('uid : ' + uid)
        print('updateNowDate : ' + updateNowDate.strftime('%Y-%m-%d%H:%M:00'))
        cur.execute('UPDATE userMaster SET lastPushDate = %s WHERE objectId = %s', (updateNowDate.strftime('%Y-%m-%d %H:%M:00'), uid, ))
        conn.commit()



if __name__ == '__main__':
    

    # 遅延ユーザにメッセージを送る。
    tienUserNotification()

    # 遅延ユーザ以外で時間をすぎたユーザに送る。
    normalUserNotification()


    # dbにアクセスして遅延情報と合致する路線のユーザを取得
    # 1. 現在時間を取得する。
    # 2. 路線を取得




    # cur.execute('SELECT * FROM userMaster WHERE wakeUpTime < %s and lastPushDate  > %s ', (now1990.strftime('%Y-%m-%d %H:%M:%S'), (now.strftime('%Y-%m-%d')),))
    # cur.execute('SELECT * FROM userMaster WHERE  lastPushDate  < %s ', (now.strftime('%Y-%m-%d'),)) #今日より古い情報
    # cur.execute('SELECT * FROM userMaster WHERE  wakeUpTime < %s ', (now1990.strftime('%Y-%m-%d %H:%M:%S'),)) #タイマーの一時間後




    # print(cur.fetchone())

    # col_name LIKE pattern


    ############################
    # カレンダー情報
    ############################
    # print(date_aaa)
    # t2 = datetime.datetime(1990, 2, 2, 10,1)
    # print(t2)

    # now.year # => 2017
    # now.month # => 7
    # now.day # => 1
    # now.hour # => 23
    # now.minute # => 15
    # now.second # => 34



    ############################
    # 路線情報の取得
    ############################
    # tien_max = int(rosen_max())
    # offset = 1
    # tienInfoList = []

    # while (tien_max > offset):
    #     json_data = rosen_info_json(offset)
    #     tienInfoList.append(json_data)
    #     offset = offset + 100


    # print(len(tienInfoList))
    # for tien_info_list in tienInfoList:
    #     for tien_info in tien_info_list['ResultSet']['Line']:
    #         print(tien_info['Name'])