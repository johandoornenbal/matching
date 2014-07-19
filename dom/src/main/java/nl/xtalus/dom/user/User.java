package nl.xtalus.dom.user;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.collect.ComparisonChain;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import org.apache.isis.applib.AbstractDomainObject;
import org.apache.isis.applib.annotation.Bookmarkable;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;

import nl.xtalus.dom.profile.Profile;
import nl.xtalus.dom.profile.ProfileType;

@javax.jdo.annotations.PersistenceCapable(
        identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id")
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER, column = "version")
@Bookmarkable
public class User extends AbstractDomainObject implements Comparable<User>{

    public String title() {
        return String.format("%s (%s)", getFirstName(), getDateOfBirth().toString("dd-MM-yyyy"));
    }

    private String firstName;

    @Named("Naam")
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "1")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }
    
    private String middleName;
    
    @Optional
    @Named("tussen")
    @javax.jdo.annotations.Column(allowsNull = "true")
    @MemberOrder(sequence = "2")
    public String getMiddleName() {
        return middleName;
    }
    
    public void setMiddleName(String name) {
        this.middleName = name;
    }    

    private String lastName;
    
    @Named("Achternaam")
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "3")
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String name) {
        this.lastName = name;
    }    
    
    private LocalDate dateOfBirth;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "4")
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    private LocalDateTime joinedOn;

    @Hidden
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Persistent
    public LocalDateTime getJoinedOn() {
        return joinedOn;
    }

    public void setJoinedOn(LocalDateTime created) {
        this.joinedOn = created;
    }

    @MemberOrder(sequence = "5")
    @Named("Aangemeld op")
    public LocalDate getDateJoined() {
        return getJoinedOn().toLocalDate();
    }
    
    // {{ Profiles (Collection)
    @Persistent(mappedBy = "owner", dependentElement = "false")
    private SortedSet<Profile> profiles = new TreeSet<Profile>();

    @MemberOrder(sequence = "1")
    public SortedSet<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(final SortedSet<Profile> collectionName) {
        this.profiles = collectionName;
    }
    // }}

    @Override
    public int compareTo(User o) {
        return ComparisonChain.start()
        .compare(this.getFirstName(), o.getFirstName())
        .compare(this.getDateOfBirth(), o.getDateOfBirth())
        .result();
    }
    
    @Named("Voeg profiel toe")
    public Profile addProfile(ProfileType profileType){
        Profile profile = newTransientInstance(profileType.cls());
        profile.setOwner(this);
        return profile;
    }


}
