package info.matchingservice.dom.Profile;

import info.matchingservice.dom.TitledEnum;

public enum ProfileNature implements TitledEnum {
    
    SINGLE_PROFILE("Enkelvoudig"),
    MULTI_PROFILE("Meervoudig");

    private String title;
    
    ProfileNature (String title) {
        this.title = title;
    }
    
    public String title() {
        return title;
    }
}
