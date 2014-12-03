package info.matchingservice.dom.DemandSupply;

import info.matchingservice.dom.TitledEnum;

public enum DemandSupplyType implements TitledEnum {

    GENERIC_DEMANDSUPPLY("Algemeen"),
    EVENT_DEMANDSUPPLY("Evenement");

    private String title;
    
    DemandSupplyType (String title) {
        this.title = title;
    }
    
    public String title() {
        return title;
    }

}
