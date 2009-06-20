import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

class AppEngineTagLib {
  def ifLoggedIn = { attrs, body ->
					def userService = UserServiceFactory.getUserService()
					if (userService.isUserLoggedIn()) {
					  out << body()
					}
  }

  def ifNotLoggedIn = { attrs, body ->
					def userService = UserServiceFactory.getUserService()
					if (!userService.isUserLoggedIn()) {
					  out << body()
					}
  }

  def ifAdminLoggedIn = { attrs, body ->
					def userService = UserServiceFactory.getUserService()
					if (userService.isUserAdmin()) {
					  out << body()
					}
  }

  def ifAdminNotLoggedIn = { attrs, body ->
					def userService = UserServiceFactory.getUserService()
					if (!userService.isUserAdmin()) {
					  out << body()
					}
  }

  def loginUrl = { attrs, body ->
				   def userService = UserServiceFactory.getUserService()
				   out << userService.createLoginURL(request.getRequestURI())
  }

  def logoutUrl = { attrs, body ->
					def userService = UserServiceFactory.getUserService()
					out << userService.createLogoutURL(request.getRequestURI())
  }

  def userNickname = { attrs, body ->
				   def userService = UserServiceFactory.getUserService()
				   def user = userService.getCurrentUser()
				   out << user.getNickname()
  }

  def userAuthDomain = { attrs, body ->
				   def userService = UserServiceFactory.getUserService()
				   def user = userService.getCurrentUser()
				   out << user.getAuthDomain()
  }

  def userEmail = { attrs, body ->
				   def userService = UserServiceFactory.getUserService()
				   def user = userService.getCurrentUser()
				   out << user.getEmail()
  }

  
}
