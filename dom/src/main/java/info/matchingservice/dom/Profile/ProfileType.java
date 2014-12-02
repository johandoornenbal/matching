package info.matchingservice.dom.Profile;

import info.matchingservice.dom.TitledEnum;

public enum ProfileType implements TitledEnum {

    PERSON_PROFILE("CV"),
    GENERIC_PROFILE("Algemeen");

    private String title;
    
    ProfileType (String title) {
        this.title = title;
    }
    
    public String title() {
        return title;
    }

}
