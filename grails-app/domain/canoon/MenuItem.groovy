package canoon

class MenuItem {
    String name
    String details
    Double price
    Double quantity

    static belongsTo = [Menu]
    static constraints = {
    }
}
