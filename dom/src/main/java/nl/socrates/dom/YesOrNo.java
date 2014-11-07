package nl.socrates.dom;

public enum YesOrNo {
    YES("Ja, verwijder"),
    NO("Nee, toch niet");
    
    private String title;
    
    private YesOrNo(String title) {
        this.title = title;
    }
    
    public String title() {
        return title;
    }
}
