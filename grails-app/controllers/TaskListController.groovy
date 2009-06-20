import org.cyberneko.html.parsers.SAXParser
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

class TaskListController {

  def list = {
	def user = grailsApplication.config.fixdapfeed.user
	def password = grailsApplication.config.fixdapfeed.password
	def taskListDom = getDom("http://fixdap.com/p/jggug/", user, password)
	render(view:"list", model:[src:taskListDom], contentType:"text/xml")
  }

  def list2 = {
	def user = grailsApplication.config.fixdapfeed.user
	def password = grailsApplication.config.fixdapfeed.password
	def taskListDom = getDom("http://fixdap.com/p/jggug/", user, password)
	render(view:"list2", model:[src:taskListDom], contentType:"text/html")
  }

  def rss = {
	def user = grailsApplication.config.fixdapfeed.user
	def password = grailsApplication.config.fixdapfeed.password
	def taskListDom = getDom("http://fixdap.com/p/jggug/rss", user, password)
	taskListDom.'**'.findAll{it.name()=='link'}.each {
	  def url = it.text()
	  def cachedUrlData = servletContext.getAttribute(url)
	  if (cachedUrlData == null) {
		def html = getDomByNekoParser(url, user, password)
		def title = html.'**'.find { it.name() == 'TABLE' }.TR[1].TD[1]
		def desc = html.'**'.find { it.name()=='TD' && it.@class=='tasktitle' }
		servletContext.setAttribute(url, [title, desc])
	  }
	}
	render(view:"rss", model:[src:taskListDom], contentType:"text/xml")
  }

  def getDom(url, user, password) {
	def stream = getStreamOfUrl(url, user, password);
	if (stream == null) {
	  render "Error"
	  return null
	}
	def dom;
	try {
	  dom = new XmlSlurper().parse(new InputStreamReader(stream, "UTF-8"))
	}
	catch (org.xml.sax.SAXParseException e) {
	  render("Parser Error, fixdapfeed.user and fixdapfeed.password properties in Config.groovy are OK?")
	  throw e
	}
	finally {
	  stream.close()
	}
	return dom
  }

  def getDomByNekoParser(url, user, password) {
	def stream = getStreamOfUrl(url, user, password)
	if (stream == null) {
	  render "Error"
	  return null
	}
	def parser = new XmlSlurper(new SAXParser()) // NekoHTMLパーサラッピング
	def dom
	try {
	  dom = parser.parse(stream)
	}
	catch (Exception e) {
	  render("Parser Error")
	  throw e
	}
	finally {
	  stream.close()
	}
	return dom
  }

  def getStreamOfUrl(targetUrl, user, password) {
	def cookie;
	try {
	  URL url = new URL("http://fixdap.com/login/");
	  HttpURLConnection connection = url.openConnection();
	  connection.instanceFollowRedirects = false;
	  connection.doOutput = true;
	  connection.requestMethod = "POST";
	  connection.setRequestProperty('Referer', 'http://fixdap.com/login/?.next=http%3A%2F%2Ffixdap.com%2Fp%2Fjggug%2F');
	  connection.setRequestProperty('Content-Type', "application/x-www-form-urlencoded");
	  OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
	  writer.write("fixdap_id=${user}&password=${password}");
	  writer.close();

	  connection.connect()
	  def status = connection.getResponseCode()
	  cookie = connection.getHeaderField('Set-Cookie').split(';')[0]
	} catch (MalformedURLException e) {
	  e.printStackTrace()
	} catch (IOException e) {
	  e.printStackTrace()
	}
	try {
	  URL url = new URL(targetUrl);
	  HttpURLConnection connection = url.openConnection();
	  connection.instanceFollowRedirects = true;
	  connection.requestMethod = "GET";
	  connection.setRequestProperty('Referer', 'http://fixdap.com/login/?.next=http%3A%2F%2Ffixdap.com%2Fp%2Fjggug%2F');
	  connection.setRequestProperty('Cookie', cookie);

	  connection.connect()
	  def status = connection.getResponseCode()
	  if (status == 200) {
		return connection.getInputStream()
	  }
	} catch (MalformedURLException e) {
	  e.printStackTrace()
	} catch (IOException e) {
	  e.printStackTrace()
	}
	return null
  }

  def beforeInterceptor = {
	println "Tracing action ${actionUri}"
	System.out.println "1Tracing action ${actionUri}"
  }

}
