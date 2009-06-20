[ソースコード取得方法]

git clone git@github.com:uehaj/fixdapfeed.git

[準備]

Java SDK, Grails, app-engine Pluginがインストール済みであること。


[デプロイ方法]

(1)準備

登録済みのGAEアプリのアプリケーション名を以下の２カ所に指定する。

- application.propertiesのapp.name
- grails-app/conf/Config.groovyのgoogle.appengine.application

(2)GAEへのデプロイ

以下を実行する。

grails app-engine package
appcfg.sh update target/war
(2回目以降はキャッシュが切れるまで以下で良い)
grails app-engine deploy

[利用方法]

(1)ログインする
(2)[登録情報を見る]
(3)New Projectでプロジェクト登録を行い、Fixdapの
  -Project: プロジェクト名
  -User: データ取得に使用するユーザ名
  -Password: データ取得に使用するユーザ名のパスワード
を入力する。(urlは通常入力不要)








