package info.matchingservice.dom.Match;

import javax.inject.Inject;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;

import com.google.common.collect.ComparisonChain;

import info.matchingservice.dom.MatchingDomainObject;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Need.VacancyProfile;
import info.matchingservice.dom.Profile.Profile;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.ViewModel;


@ViewModel
@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
public class ProfileComparison extends MatchingDomainObject<ProfileComparison> {
    
    public ProfileComparison() {
        super("matchInitiator");
    }

    public ProfileComparison(VacancyProfile matchInitiator, Profile matchingProfile, Integer value) {
        super("matchInitiator");
        this.matchInitiator = matchInitiator;
        this.matchingProfile = matchingProfile;
        this.calculatedMatchingValue = value;   
    }
    
    private VacancyProfile matchInitiator;
    
    @Hidden
    @javax.jdo.annotations.Column(allowsNull = "false")
    public VacancyProfile getMatchInitiator() {
        return matchInitiator;
    }
    
    public void setMatchInitiator(final VacancyProfile vac) {
        this.matchInitiator = vac;
    }
    
    private Profile matchingProfile;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public Profile getMatchingProfile() {
        return matchingProfile;
    }
    
    public void setMatchingProfile(final Profile prof) {
        this.matchingProfile = prof;
    }
    
    private Integer calculatedMatchingValue;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public Integer getCalculatedMatchingValue() {
        return calculatedMatchingValue;
    }
    
    public void setCalculatedMatchingValue(final Integer matchingvalue) {
        this.calculatedMatchingValue = matchingvalue;
    }
    
    //Region>Actions ////////////////////////////////////////////////////////
    
    public ProfileMatch SaveMatch(){
        return profileMatches.newProfileMatch(getMatchInitiator().getVacancyOwner().getNeedOwner(), getMatchingProfile().getProfileOwner(), getMatchInitiator());
    }
    
    //TODO: uitbreiden met controle of er al een save is gemaakt met deze kenmerken...
    public boolean hideSaveMatch() {
        return !getMatchInitiator().getVacancyOwner().getNeedOwner().getOwnedBy().equals(currentUserName());
    }
    
    
    //Region>Helpers ////////////////////////////////////////////////////////
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    public String toString() {
        return getMatchInitiator().toString() + " vs. " + getMatchingProfile().toString();
    }
    
    public Actor getProposedPerson() {
        return getMatchingProfile().getProfileOwner();
    }    
    
    public int compareTo(ProfileComparison that) {
        return ComparisonChain.start()
            .compare(this.calculatedMatchingValue, that.calculatedMatchingValue)
            .compare(this.matchingProfile, that.matchingProfile)
            .result();
    }
    
    // Region>injections ////////////////////////////    
    @Inject
    private ProfileMatches profileMatches;
    
    @javax.inject.Inject
    private DomainObjectContainer container;

}
