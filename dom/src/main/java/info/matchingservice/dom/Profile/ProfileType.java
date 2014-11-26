package info.matchingservice.dom.Profile;

import info.matchingservice.dom.TitledEnum;

public enum ProfileType implements TitledEnum {

    SUPPLY_PERSON_PROFILE("CV"),
    SUPPLY_GENERIC_PROFILE("Algemeen aanbod"),
    DEMAND_PERSON_PROFILE("Gezocht CV"),
    DEMAND_GENERIC_PROFILE("Algemeen gezocht");

    private String title;
    
    ProfileType (String title) {
        this.title = title;
    }
    
    public String title() {
        return title;
    }

}
