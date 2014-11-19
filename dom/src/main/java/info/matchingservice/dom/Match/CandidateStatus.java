package info.matchingservice.dom.Match;

import info.matchingservice.dom.TitledEnum;

public enum CandidateStatus implements TitledEnum {

    CANDIDATE("kandidaat"),
    RESERVED("gereserveerd"),
    CHOSEN("gekozen"),
    DISMISSED("afgewezen");

    private String title;
    
    CandidateStatus (String title) {
        this.title = title;
    }
    
    public String title() {
        return title;
    }

}
