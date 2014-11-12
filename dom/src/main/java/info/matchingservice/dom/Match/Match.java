package info.matchingservice.dom.Match;

import info.matchingservice.dom.Need.Vacancy;
import info.matchingservice.dom.Party.Person;
import info.matchingservice.dom.Profile.Profile;

import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.ViewModel;


@ViewModel
public class Match {

    public Match(Vacancy matchInitiator, Profile matchingProfile, Integer value) {
        this.matchInitiator = matchInitiator;
        this.matchingProfile = matchingProfile;
        this.calculatedMatchingValue = value;
    }
    
    private Vacancy matchInitiator;
    
    @Hidden
    public Vacancy getMatchInitiator() {
        return matchInitiator;
    }
    
    public void setMatchInitiator(final Vacancy vac) {
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
    
    //helpers
    
    public String toString() {
        return getMatchInitiator().toString() + " vs. " + getMatchingProfile().toString();
    }
    
    public Person getProposedPerson() {
        return getMatchingProfile().getProfileOwner();
    }

}
