package nl.xtalus.dom.user;

public enum Sex {
    MALE("man"),
    FEMALE("vrouw"),
    NEUTRAL("Onzijdig"),
    HERMAPHRODITE("Dubbelzijdig");
    
    private String title;
    
    Sex(String title) {
        this.title = title;
    }    

    public String title() {
        return title;
    }
}
