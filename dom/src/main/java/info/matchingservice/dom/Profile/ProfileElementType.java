package info.matchingservice.dom.Profile;

import info.matchingservice.dom.TitledEnum;

public enum ProfileElementType implements TitledEnum {

    MATCHABLE_KEYWORDS("Matchbare steekwoorden"),
    MATCHABLE_FIGURE("Matchbaar getal"),
    MATCHABLE_DROPDOWN("Matchbare keuzelijst");

    private String title;
    
    ProfileElementType (String title) {
        this.title = title;
    }
    
    public String title() {
        return title;
    }

}
