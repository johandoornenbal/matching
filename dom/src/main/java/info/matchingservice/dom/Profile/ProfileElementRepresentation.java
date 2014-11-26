package info.matchingservice.dom.Profile;

import info.matchingservice.dom.TitledEnum;

public enum ProfileElementRepresentation implements TitledEnum {
    
    PROFILE_ELEMENT_DROPDOWN("Dropdown");

    private String title;
    
    ProfileElementRepresentation (String title) {
        this.title = title;
    }
    
    public String title() {
        return title;
    }

}
