package canoon

class UrlMappings {

    static mappings = {
        "/api/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: "Status", action: "index")
        "/api"(controller: "Status", action: "index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
