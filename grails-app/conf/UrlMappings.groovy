class UrlMappings {
    static mappings = {
      "/p/$key/rss"(controller:"taskList", action:"rss")
      "/p/$key/gadget"(controller:"taskList", action:"gadget")
      "/p/$key/html"(controller:"taskList", action:"html")
	  "/taskList/rss"(controller:"taskList", key:"", action:"rss")

      "/$controller/$action?/$id?"{
	      constraints {
			 // apply constraints here
		  }
	  }
      "/"(view:"/index")
	  "500"(view:'/error')
	}
}
