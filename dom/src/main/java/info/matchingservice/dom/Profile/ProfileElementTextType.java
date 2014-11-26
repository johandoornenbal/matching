package info.matchingservice.dom.Profile;

import info.matchingservice.dom.TitledEnum;

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
