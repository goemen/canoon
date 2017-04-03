package canoon

import grails.converters.JSON

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class HomeController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        render new Home(status: true, message: "Canoon API is running.") as JSON
    }

    def show(Home home) {
        respond home
    }

    def create() {
        respond new Home(params)
    }

    @Transactional
    def save(Home home) {
        if (home == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (home.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond home.errors, view:'create'
            return
        }

        home.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'home.label', default: 'Home'), home.id])
                redirect home
            }
            '*' { respond home, [status: CREATED] }
        }
    }

    def edit(Home home) {
        respond home
    }

    @Transactional
    def update(Home home) {
        if (home == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (home.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond home.errors, view:'edit'
            return
        }

        home.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'home.label', default: 'Home'), home.id])
                redirect home
            }
            '*'{ respond home, [status: OK] }
        }
    }

    @Transactional
    def delete(Home home) {

        if (home == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        home.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'home.label', default: 'Home'), home.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'home.label', default: 'Home'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
