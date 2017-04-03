package canoon

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

@Transactional(readOnly = true)
class StatusController {

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def index() {
        render new Status() as JSON
    }
}
