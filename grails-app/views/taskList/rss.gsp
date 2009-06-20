<?xml version="1.0" encoding="UTF-8" ?>
<rdf:RDF
 xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
 xmlns:admin="http://webns.net/mvcb/"
 xmlns:content="http://purl.org/rss/1.0/modules/content/"
 xmlns:dc="http://purl.org/dc/elements/1.1/"
 xmlns="http://purl.org/rss/1.0/"
>
<channel rdf:about="http://fixdap.com/p/jggug/">
 <title><%= src.channel.title.text() %></title>
 <link>http://fixdap.com/p/jggug/</link>
 <items>
  <rdf:Seq>
<% src.channel.items.Seq.li.each { %>
<rdf:li rdf:resource="${it.@resource}" />
<% } %>
  </rdf:Seq>
 </items>
</channel>

<% src.item.each { item -> %>
<% def (title, desc) = application.getAttribute(item.link.text()) %>
<item rdf:about="${ item.about.text() }">
 <title>${title}</title>
 <link>${ item.link.text() }</link>
 <description><![CDATA[ ${desc}]]></description>
 <dc:creator>${ item.creator.text() }</dc:creator>
 <dc:date>${ item.date.text() }</dc:date>
 <content:encoded><![CDATA[ ${desc}]]></content:encoded>
</item>
<% } %>

</rdf:RDF>
