package info.matchingservice.dom.Profile;

import info.matchingservice.dom.TitledEnum;

public enum ProfileElementCategory implements TitledEnum {
    
    QUALITY("Kwaliteit"),
    TEXT("Tekst"),
    NUMERIC("Getal");

    private String title;
    
    ProfileElementCategory (String title) {
        this.title = title;
    }
    
    public String title() {
        return title;
    }
}
