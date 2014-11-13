package info.matchingservice.dom.Match;

import info.matchingservice.dom.Need.VacancyProfile;
import info.matchingservice.dom.Party.Person;
import info.matchingservice.dom.Profile.Profile;

import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.ViewModel;


@ViewModel
public class Match {

    public Match(VacancyProfile matchInitiator, Profile matchingProfile, Integer value) {
        this.matchInitiator = matchInitiator;
        this.matchingProfile = matchingProfile;
        this.calculatedMatchingValue = value;
    }
    
    private VacancyProfile matchInitiator;
    
    @Hidden
    public VacancyProfile getMatchInitiator() {
        return matchInitiator;
    }
    
    public void setMatchInitiator(final VacancyProfile vac) {
        this.matchInitiator = vac;
    }
    
    private Profile matchingProfile;
    
    public Profile getMatchingProfile() {
        return matchingProfile;
    }
    
    public void setMatchingProfile(final Profile prof) {
        this.matchingProfile = prof;
    }
    
    private Integer calculatedMatchingValue;
    
    public Integer getCalculatedMatchingValue() {
        return calculatedMatchingValue;
    }
    
    public void setCalculatedMatchingValue(final Integer matchingvalue) {
        this.calculatedMatchingValue = matchingvalue;
    }
    
    private String matchedTextDiffs;
    
    @Hidden
    public String getMatchedTextDiffs() {
        return matchedTextDiffs;
    }
    
    public void setMatchedTextDiffs(final String difs) {
        this.matchedTextDiffs = difs;
    }

    private Integer matchedTextMeasure;
    
    public Integer getmatchedTextMeasure() {
        return matchedTextMeasure;
    }
    
    public void setmatchedTextMeasure(final Integer difs) {
        this.matchedTextMeasure = difs;
    }   
    
    //helpers
    
    public String toString() {
        return getMatchInitiator().toString() + " vs. " + getMatchingProfile().toString();
    }
    
    public Person getProposedPerson() {
        return getMatchingProfile().getProfileOwner();
    }

}
