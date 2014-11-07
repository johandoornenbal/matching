package nl.socrates.dom.party;

import nl.yodo.dom.TitledEnum;

public enum PersonContactStatus implements TitledEnum {
    UNCONFIRMED("niet bevestigd"),
    CONFIRMED("bevestigd"),
    DENIED("afgewezen");
    
    private String title;
    
    PersonContactStatus (String title) {
        this.title = title;
    }
    
    public String title() {
        return title;
    }    
}
