package info.matchingservice.dom;

public enum ProfileElementTextType implements TitledEnum {
    
    KEYWORDS("Trefwoorden"),
    STORY("Verhaal");
    
    private String title;
    
    ProfileElementTextType (String title) {
        this.title = title;
    }
    
    public String title() {
        return title;
    }

}
