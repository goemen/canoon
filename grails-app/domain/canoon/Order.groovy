package canoon

class Order {
    Integer index
    static belongsTo = [Seating]
    static hasMany = [MenuItem]
    static constraints = {
    }
}
