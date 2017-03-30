package canoon

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(UserService)
@Mock([User, Role, UserRole])
class UserServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "Test create user"() {
        def username =  "username"
        def password = "password"
        service.createUser(new User(username: username, password: password))
        def savedUser = User.findByUsername(username)
        expect:"user found"
            savedUser != null

        and: "user id to be 1"
            savedUser.id == 1
    }

    void "Test add user roles"() {
        def username =  "username"
        def password = "password"
        def user =  new User(username: username, password: password).save()
        def ROLE_ADMIN = "ROLE_ADMIN"
        def role = new Role(ROLE_ADMIN).save()

        service.addUsersToRole([user].toList(), role)

        def userRole = UserRole.findByUser(user)

        expect: "user role is found"
            userRole != null
        and: "user has one role"
            userRole.role.authority == ROLE_ADMIN
    }

    void "Test add user roles - role does not exist"() {
        setup:
            def username =  "username"
            def password = "password"
            def user =  new User(username: username, password: password).save()
            def ROLE_ADMIN = "ROLE_ADMIN"
            def role = new Role(ROLE_ADMIN)

        when:
            service.addUsersToRole([user].toList(), role)

        then:
            Exception ex = thrown()
            ex.message == String.format(canoon.Constants.ROLE_DOES_NOT_EXIST, ROLE_ADMIN)
    }

    void "Test add user roles - user does not exist"() {
        setup:
            def username =  "username"
            def password = "password"
            def user =  new User(username: username, password: password)
            def ROLE_ADMIN = "ROLE_ADMIN"
            def role = new Role(ROLE_ADMIN).save()

        when:
            service.addUsersToRole([user].toList(), role)

        then:
            Exception ex = thrown()
            ex.message == String.format(canoon.Constants.USER_DOES_NOT_EXIST, username)
    }

    void "Test add role"() {
        setup:
            def ROLE_ADMIN = "ROLE_ADMIN"
            def role = new Role(ROLE_ADMIN)

        when:
            service.addRole(role)

        then:
            Role.findByAuthority(ROLE_ADMIN) != null
    }

    void "Test add role - Authority  already exist"() {
        setup:
        def ROLE_ADMIN = "ROLE_ADMIN"
            service.addRole(new Role(ROLE_ADMIN))

        when:
            service.addRole(new Role(ROLE_ADMIN))

        then:
            Exception ex = thrown()
            ex.message == String.format(canoon.Constants.ROLE_ALREADY_EXIST, ROLE_ADMIN)
    }

    void "Test delete role"() {
        setup:
            def username =  "username"
            def password = "password"
            def user =  new User(username: username, password: password).save()
            def ROLE_ADMIN = "ROLE_ADMIN"
            def role = service.addRole(new Role(ROLE_ADMIN))
            service.addUsersToRole([user].toList(), role)

        when:
            service.deleteRole(role.id, role.authority)

        then:
            Role.findById(role.id) == null
            UserRole.findByUserAndRole(user, role) == null
    }

    void "Test delete role -  authority does not exist"() {
        setup:
            def ROLE_ADMIN = "ROLE_ADMIN"

        when:

            service.deleteRole(1, ROLE_ADMIN)

        then:
            Exception ex = thrown()
            ex.message == String.format(canoon.Constants.ROLE_DOES_NOT_EXIST, ROLE_ADMIN)
    }
}
