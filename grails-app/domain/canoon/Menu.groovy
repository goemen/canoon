package canoon

class Menu {

    static belongsTo = [Restuarant]
    static hasMany = [MenuItem]
    static constraints = {
    }
}
