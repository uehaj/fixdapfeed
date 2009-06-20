<?xml version="1.0" encoding="UTF-8" ?>
<Module>
<ModulePrefs title="<%= src.head.title.text() %>"
 author="uehaj" author_email="uehaj@jggug.com" width="640" height="480"
 scrolling="true"
 />
<Content type="html">
<![CDATA[
<base href="http://fixdap.com/">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Style-Type" content="text/css" />
<meta http-equiv="Content-Script-Type" content="text/javascript" />
<style type="text/css">
<!--
@import url("http://fixdap.com/css/0.01/cmn.css");
@import url("http://fixdap.com/css/0.03/design_template/layaout.css");
@import url("http://fixdap.com/css/0.01/module.css");
@import url("http://fixdap.com/css/0.02/form.css");
@import url("http://fixdap.com/css/0.02/design_template/001/project.css");
.tasklist { font-size: 80%; }
th { font-style: bold; text-align: center; }
-->
</style>
<script type='text/javascript' charset='utf-8' src='http://fixdap.com/js/0.01/jquery/jquery.js'></script>
<script type='text/javascript' charset='utf-8' src='http://fixdap.com/js/0.04/fixdap.js'></script>

<table class="tasklist">
<tr onClick="window.open('http://fixdap.com/p/jggug/')">
<th>No</th>
<th>タイトル</th>
<th>ステータス</th>
<th>重要度</th>
<th>担当</th>
<th>完了予定日</th>
<th>更新日時</th>
<th>記事数</th>
<th>編集</th>
</tr>

<%
  src.'**'.find{it.name()=='table'}.tr.eachWithIndex { it, idx ->
%>
<tr class="<%= idx % 2 == 0 ? "zebra-even" : "zebra-odd" %>">
<%
	if (it.td.size() != 0) {
%>
<td class="partition"><%= it.td[0].text() %></td>
<td class="tasktitle partition"><a href="<%=
  it.td[1].a.@href
%>" target="_top"><%= it.td[1].text() %></a></td>
<td class="status1 partition"><span><%= it.td[2].text() %></span></td>
<td class="partition"><span class="priority4"><%= it.td[3].text() %></span></td>
<td class="partition"><%= it.td[4].text() %></td>
<td class="partition"><%= it.td[5].text().replaceAll(/ \d\d:\d\d$/,"") %></td>
<td class="partition"><%= it.td[6].text().replaceAll(/ \d\d:\d\d$/,"") %></td>
<td class="partition"><%= it.td[7].text() %></td>
<td><a href="<%=
  it.td[8].a.@href
%>"><%= it.td[8].text() %></a></td>
<%
	}
  }
%>

</table>

]]>
</Content>
</Module>
