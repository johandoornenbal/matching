package info.matchingservice.dom.Profile;

import info.matchingservice.dom.TitledEnum;

public enum ProfileElementNature implements TitledEnum {

    SINGLE_ELEMENT("Enkelvoudig"),
    MULTI_ELEMENT("Meervoudig");

    private String title;
    
    ProfileElementNature (String title) {
        this.title = title;
    }
    
    public String title() {
        return title;
    }

}
