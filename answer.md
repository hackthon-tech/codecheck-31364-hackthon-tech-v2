# アプリケーションについて

## 「起こすよBOY」

### アプリケーションの概要説明

「起こすよBOY」は目的地に遅れないようにGoogleHomeで起こしてくれるシステムです。駅すぽあとAPIで遅延情報を把握し、適切な起床時間に起こしてくれます。利用ユーザは出社時間に遅れることなく、生活リズムを崩れないようにサポートしてくれるサービスです。

>参考動画

https://www.youtube.com/watch?v=RwReCkxNP6w

[![IMAGE ALT TEXT HERE](http://img.youtube.com/vi/RwReCkxNP6w/0.jpg)](https://www.youtube.com/watch?v=RwReCkxNP6w)

### 苦労した点・自慢したい点

>苦労した点

GoogleHomeとのやり取りの技術面の調査が1番苦労しました。まずGoogleHomeに接続するためにローカルネットワークの機器を探すmDNSを使用するのですが、Androidでは標準ライブラリーがないため、jmDNSを利用して探しています。また、GoogleHomeとはソケット通信でChromecastと同じpayloadを送りやりとりを行うところを1から解析してJavaで作成したところは苦労しました。

>自慢したい点

メンバー2人で技術調査、環境作成、サーバ・アプリ作成、デザイン、動画作成、等の幅広いジャンルを1人1人がこなし2週間でサービスを作れたことは自慢したいです。

### 実装した機能、利用した技術スペック

> Androidアプリ

##### ■ サーバ連携
###### HTTP通信
##### ■ GoogleHome連携
###### jmDNS
###### socket通信
##### ■ サーバ通知受取
###### Firebase Notifications

> サーバサイド

##### ■ ユーザ登録、ユーザ設定管理API
###### springフレームワーク
##### ■ データベース
###### mysql
##### ■ バッチ処理
###### python

> 駅、遅延情報取得

##### ■ 駅すぱあとWebサービス

### 開発したアプリケーションの使い方・ビルド手順

> 必要機器

- Android端末(OS7.0以降)
- GoogleHome

> 準備

Android端末にアプリをダウンロードします

- ダウンロードURL

http://54.250.45.248:8080/app_download/

![システム図](https://s3-ap-northeast-1.amazonaws.com/hack.tech/app_download_qr.png "QRコード")

> 設定方法

1. スマホとGoogleHomeを同じネットワーク内に接続します

2. インストールしたアプリを起動します

3. 登録ボタンを押します→登録成功のダイアログがでます

4. 設定ボタンを押します→設定画面に移ります

5. 各設定項目を入力します
 
6. 設定更新ボタンを押します→設定更新成功のダイアログがでます

7. GoogleHome接続テストボタンを押します→接続成功ダイアログがでてGoogleHomeがしゃべります

 **[注意]** *同じローカルネットワークにGoogleHomeとスマホが接続していても稀に失敗すること場合があります。その際には一度、スマホのWiFiをOFF→ONにして再度試してください。*

> 利用方法

#### 設定で登録した起床時刻前に路線で遅延が発生しているかサーバで検知します

1. 寝る前にスマホとGoogleHomeを同じネットワーク内にしておきます

2. 遅延が発生すると、サーバが検知して起床時刻より早い時間にGoogleHomeが起こしてくれます


