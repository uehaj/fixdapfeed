package org.jggug.fixdapfeed;

import javax.jdo.annotations.*;
import java.security.MessageDigest;
// import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
class Project {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	Long id

	@Persistent
	String user

	@Persistent
	String password

	@Persistent
	String projectName

	@Persistent
	String url = "http://fixdap.com/p/"

	@Persistent
    String ownerMail

	@Persistent
    String randomPart

    def setRandomPart() {
	  try {
		  def baos = new ByteArrayOutputStream()
		  def dos = new DataOutputStream(baos)
		  dos.writeUTF(user)
		  dos.writeUTF(password)
		  dos.writeUTF(projectName)
		  randomPart = baos.toByteArray().encodeAsSHA256()
      }
	  catch (Exception e) {
		e.printStackTrace();
	  }
	}

    def getProjectUrl() {
	  url + projectName
	}

    def getRssUrl() {
	  "/p/" + randomPart + '/rss'
	}

    def getGadgetUrl() {
	  "/p/" + randomPart + '/gadget'
	}

    def getHtmlUrl() {
	  "/p/" + randomPart + '/html'
	}

    static constraints = {
    	id(visible:false)
		projectName()
        user()
        password(password:true)
        url()
	}
}
