package info.matchingservice.dom.Actor;

import info.matchingservice.dom.TitledEnum;

public enum SystemRoleType implements TitledEnum {

    PRINCIPAL("opdrachtgever");

    private String title;
    
    SystemRoleType (String title) {
        this.title = title;
    }
    
    public String title() {
        return title;
    }

}
