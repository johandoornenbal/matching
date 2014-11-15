package info.matchingservice.dom.Actor;

import info.matchingservice.dom.TitledEnum;

public enum OrganisationRoleType implements TitledEnum {

    PRINCIPAL("opdrachtgever");

    private String title;
    
    OrganisationRoleType (String title) {
        this.title = title;
    }
    
    public String title() {
        return title;
    }

}
