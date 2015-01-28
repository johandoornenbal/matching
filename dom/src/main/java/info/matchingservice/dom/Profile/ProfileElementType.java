package info.matchingservice.dom.Profile;

import info.matchingservice.dom.TitledEnum;

public enum ProfileElementType implements TitledEnum {
    
    QUALITY("Kwaliteit"),
    TEXT("Tekst"),
    PASSION("Passie"),
    NUMERIC("Getal");

    private String title;
    
    ProfileElementType (String title) {
        this.title = title;
    }
    
    public String title() {
        return title;
    }
}
