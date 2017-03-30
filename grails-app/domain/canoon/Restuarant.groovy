package canoon

class Restuarant {

    String name
    String alias
    String address

    static hasMany = [members: User]

    static belongsTo = [manager: User]

    static constraints = {
        name blank: false, nullable: false
        alias blank: true, nullable: true
        address blank: true, nullable: true
        manager nullable: false
    }

    static mapping = {
        table 'Restuarants'
    }
}
