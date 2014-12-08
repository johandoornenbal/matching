package info.matchingservice.dom.DemandSupply;

import info.matchingservice.dom.TitledEnum;

public enum DemandSupplyType implements TitledEnum {

    GENERIC_DEMANDSUPPLY("Algemeen type"),
    PERSONS_DEMANDSUPPLY("Personen type"),
    COURSE_DEMANDSUPPLY("Cursus type"),
    EVENT_DEMANDSUPPLY("Evenement type");

    private String title;
    
    DemandSupplyType (String title) {
        this.title = title;
    }
    
    public String title() {
        return title;
    }

}
