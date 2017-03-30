package canoon

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(RestuarantService)
@Mock([User, Restuarant])
class RestuarantServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "Test create restuarant"() {
        def username = "somename"
        setup:
            def user = new User(username: username, password: "pass").save()
            def restuarantName = "Earls"
        when:
            def restuarant = service.create(new Restuarant(name: restuarantName, manager: user))
        then:
            restuarant.id != null
            Restuarant.findByName(restuarantName) != null
    }
}
