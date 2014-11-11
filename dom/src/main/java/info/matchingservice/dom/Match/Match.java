package info.matchingservice.dom.Match;

import info.matchingservice.dom.MatchingDomainObject;
import info.matchingservice.dom.Need.Vacancy;
import info.matchingservice.dom.Party.Person;
import info.matchingservice.dom.Profile.Profile;

import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;

import org.apache.isis.applib.annotation.Disabled;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
public class Match extends MatchingDomainObject<Match> {

    public Match() {
        super("calculatedMatchingValue, matchingProfile, matchInitiator");
    }
    
    private Vacancy matchInitiator;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    public Vacancy getMatchInitiator() {
        return matchInitiator;
    }
    
    public void setMatchInitiator(final Vacancy vac) {
        this.matchInitiator = vac;
    }
    
    private Profile matchingProfile;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    public Profile getMatchingProfile() {
        return matchingProfile;
    }
    
    public void setMatchingProfile(final Profile prof) {
        this.matchingProfile = prof;
    }
    
    private Integer calculatedMatchingValue;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
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
