package canoon

import grails.transaction.Transactional

@Transactional
class UserService {

    def createUser(User user) {
        user.save()
        return user
    }

    def addUsersToRole(List<User> users, Role role) {

        if (!role.id) {
            throw new Exception(String.format(canoon.Constants.ROLE_DOES_NOT_EXIST, role.authority))
        }

        users.each {
            if (!it.id)
                throw new Exception(String.format(canoon.Constants.USER_DOES_NOT_EXIST, it.username))

            UserRole.create(it, role)
        }
    }

    def addRole(Role role) {

        if (Role.findByAuthority(role.authority)) {
            throw new Exception(String.format(canoon.Constants.ROLE_ALREADY_EXIST, role.authority))
        }

        return role.save()
    }

    def deleteRole(Long id, String authority) {

        def inStoreRole = Role.findById(id)
        if (!inStoreRole) {
            throw new Exception(String.format(canoon.Constants.ROLE_DOES_NOT_EXIST, authority))
        }

        // delete all user roles
        inStoreRole.userRoles.each {
            it.delete()
        }

        inStoreRole.delete()
    }
}
