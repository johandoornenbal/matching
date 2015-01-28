package info.matchingservice.dom.Profile;

import info.matchingservice.dom.TitledEnum;

public enum ProfileType implements TitledEnum {

    PERSON_PROFILE("CV"),
    ORGANISATION_PROFILE("Organisatie profiel"),
    SERVICE_PROFILE("Dienst"),
    DEVICE_PROFILE("Voorwerp"),
    EVENT_PROFILE("Evenement"),
    COURSE_PROFILE("Cursus / Opleiding"),
    GENERIC_PROFILE("Algemeen");

    private String title;
    
    ProfileType (String title) {
        this.title = title;
    }
    
    public String title() {
        return title;
    }

}
