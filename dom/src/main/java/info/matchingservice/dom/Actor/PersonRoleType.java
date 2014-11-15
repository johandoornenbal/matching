package info.matchingservice.dom.Actor;

import info.matchingservice.dom.TitledEnum;

public enum PersonRoleType implements TitledEnum {

    STUDENT("student"),
    PROFESSIONAL("zelfstandig professional"),
    PRINCIPAL("opdrachtgever");

    private String title;
    
    PersonRoleType (String title) {
        this.title = title;
    }
    
    public String title() {
        return title;
    }

}
