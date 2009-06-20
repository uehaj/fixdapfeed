<html>
    <head>
        <title>Fixdap feed</title>
		<meta name="layout" content="main" />
    </head>
    <body>
        <h1 style="margin-left:20px;">Welcome to Fixdap frontend for JGGUG support</h1>
        <p style="margin-left:20px;width:80%"></p>
        <div class="dialog" style="margin-left:20px;width:60%;">
			<g:ifNotLoggedIn><a href="<g:loginUrl />">ログイン</a>してください</g:ifNotLoggedIn>
			<g:ifLoggedIn>ようこそ<g:userNickname />さん<p /></g:ifLoggedIn>
			<g:ifLoggedIn>
            <ul>
			  <li><a href="/project/list">登録情報を見る</a>
			  <li><a href="<g:logoutUrl />">ログアウト</a>
            </ul>
			<br>
			</g:ifLoggedIn>
        </div>
    </body>
</html>
