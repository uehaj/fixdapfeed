package org.jggug.fixdapfeed

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.datastore.*

class ProjectController {

	def persistenceManager
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def currentUserMail() {
	  def userService = UserServiceFactory.getUserService()
	  def user = userService.getCurrentUser()
	  user.getEmail()
	}

    def list = {
        params.max = Math.min(params.max ? params.max.toInteger() : 10,  100)
		def query
		try {
		  query = persistenceManager.newQuery(Project)
		  query.setFilter("ownerMail == ownerMailParam")
		  query.declareParameters("String ownerMailParam");
		  def projectInstanceList = query.execute(currentUserMail())
		  def total = 0
		  if (projectInstanceList && projectInstanceList.size() > 0) {
			total =  projectInstanceList.size()
		  }
		  return [projectInstanceList:projectInstanceList,
				  projectInstanceTotal:total]
		}
		finally {
		  query.closeAll()
		}
    }

    def show = {
	    def projectInstance = persistenceManager.getObjectById( Project.class, Long.parseLong( params.id )  )
		if (projectInstance.ownerMail != currentUserMail()) {
            flash.message = "Permission denied with id ${params.id}"
            redirect(action:list)
		}
        if(!projectInstance) {
            flash.message = "Project not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ projectInstance : projectInstance ] }
    }

    def delete = {
	    def projectInstance = persistenceManager.getObjectById( Project.class, Long.parseLong( params.id )  )
        if (projectInstance) {
		    if (projectInstance.ownerMail != currentUserMail()) {
                flash.message = "Permission denied with id ${params.id}"
                redirect(action:list)
				return
		    }
            try {
                persistenceManager.deletePersistent(projectInstance)
                flash.message = "Project ${params.id} deleted"
                redirect(action:list)
            }
            catch(Exception e) {
                flash.message = "Project ${params.id} could not be deleted"
                redirect(action:show, id:params.id)
            }
        }
        else {
            flash.message = "Project not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
	    def projectInstance = persistenceManager.getObjectById( Project.class, Long.parseLong( params.id )  )
		if(!projectInstance) {
            flash.message = "Project not found with id ${params.id}"
            redirect(action:list)
        }
		else if (projectInstance.ownerMail != currentUserMail()) {
            flash.message = "Permission denied with id ${params.id}"
            redirect(action:list)
		}
        else {
			projectInstance = persistenceManager.detachCopy( projectInstance )    
        	return [ projectInstance : projectInstance ]
        }
    }

    def update = {
	 	def projectInstance = persistenceManager.getObjectById( Project.class, Long.parseLong( params.id )  )
    
    	if (projectInstance) {
		    if (projectInstance.ownerMail != currentUserMail()) {
                flash.message = "Permission denied with id ${params.id}"
                redirect(action:list)
				return
		    }

		     projectInstance.properties["projectName", "user", "password", "url"] = params
            if(!projectInstance.hasErrors()){
				try{
					projectInstance.setRandomPart()
					projectInstance.ownerMail = currentUserMail()
					persistenceManager.makePersistent(projectInstance)
				} catch( Exception e ){
				   	render(view:'edit',model:[projectInstance:projectInstance])
				}finally{
					flash.message = "Project ${params.id} updated"
	                redirect(action:show,id:projectInstance.id)
				}        
 			}
            else {
                render(view:'edit',model:[projectInstance:projectInstance])
            }
        }
        else {
            flash.message = "Project not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def create = {
        def projectInstance = new Project()
        projectInstance.properties["projectName", "user", "password", "url"] = params
        return ['projectInstance':projectInstance]
    }

    def save = {
        def projectInstance = new Project(params)
		if (!projectInstance.hasErrors() ) {
			try{
				projectInstance.setRandomPart()
				projectInstance.ownerMail = currentUserMail()
				persistenceManager.makePersistent(projectInstance)
			} catch (Exception e) {
			  e.printStackTrace()
			} finally{
				flash.message = "Project ${projectInstance.id} created"
				redirect(action:show,id:projectInstance.id)	
			}
		}
		render(view:'create',model:[projectInstance:projectInstance])
    }

}
