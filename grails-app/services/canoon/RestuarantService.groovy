package canoon

import grails.transaction.Transactional

@Transactional
class RestuarantService {

    def create(Restuarant restuarant) {
        restuarant.save()
        return restuarant
    }

    List<User> addMembers(Long restuarantId, List<Long> members) {
        def restuarant = Restuarant.findById(restuarantId)

        if (!restuarant) {
            throw new Exception(String.format(canoon.Constants.RESTUARANT_ID_DOES_NOT_EXIST, restuarantId))
        }

        List<User> users = User.list().collect { members.contains(it.id)}

        if (users.size() != members.size()) {
            def usersNotFound = members.collect {!users.collect { it.id}.contains(it)}
            throw new Exception(String.format(canoon.Constants.USER_IDS_DO_NOT_EXIST, usersNotFound.join(", ")))
        }

        restuarant.members = users
        restuarant.save()

        return restuarant
    }


}
