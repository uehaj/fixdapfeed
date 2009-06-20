package org.jggug.fixdapfeed;

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

  def persistenceManager

  def account(pm, key) {
	def query = pm.newQuery(Project)
	query.setFilter("randomPart == keyParam")
    query.declareParameters("String keyParam");
	def projectInstanceList = query.execute(key)
	if (projectInstanceList && projectInstanceList.size() > 0) {
	  def project = projectInstanceList[0]
	  return [project.user, project.password, project.url, project.projectName]
	}
	render("Project ${key} not found.")
  }

  def gadget = {
	def (user, password, url, projectName) = account(persistenceManager, params.key);
println " (user, password, url, projectName) = ($user, $password, $url, $projectName)"
	def taskListDom = getDom(url+projectName+'/', user, password)
	render(view:"gadget", model:[src:taskListDom], contentType:"text/xml")
  }

  def html = {
	def (user, password, url, projectName) = account(persistenceManager, params.key);
	def taskListDom = getDom(url+projectName+'/', user, password)
	render(view:"html", model:[src:taskListDom], contentType:"text/html")
  }

  def rss = {
	def (user, password, url, projectName) = account(persistenceManager, params.key);
	def taskListDom = getDom(url+projectName+"/rss", user, password)
println "taskListDom = getDom($url+/rss, $user, $password)"
	taskListDom.'**'.findAll{it.name()=='link'}.each {
	  def urlKey = it.text()
	  def cachedUrlData = servletContext.getAttribute(urlKey)
	  if (cachedUrlData == null) {
		def html = getDomByNekoParser(urlKey, user, password)
		def title = html.'**'.find { it.name() == 'TABLE' }.TR[1].TD[1]
		def desc = html.'**'.find { it.name()=='TD' && it.@class=='tasktitle' }
		servletContext.setAttribute(urlKey, [title, desc])
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
	  render("Login Error, please check user and password.")
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

	  connection.setRequestProperty('Referer', "http://fixdap.com/login/?.next=${targetUrl.encodeAsURL()}");
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
	  connection.setRequestProperty('Referer', "http://fixdap.com/login/?.next=${targetUrl.encodeAsURL()}");
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

}
