package info.matchingservice.dom.Profile;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.TrustLevel;
import info.matchingservice.dom.Assessment.ProfileAssessment;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Persistent;

import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
public abstract class SuperProfile extends MatchingSecureMutableObject<SuperProfile> {
    
    public SuperProfile() {
        super("profileName, ownedBy");
    }
    
    //Override for secure object /////////////////////////////////////////////////////////////////////////////////////
    private String ownedBy;

    @Override
    @Hidden
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(final String owner) {
        this.ownedBy = owner;
    }
    
    
    //ProfileName /////////////////////////////////////////////////////////////////////////////////////
    private String profileName;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Named("Profiel naam")
    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(final String test) {
        this.profileName = test;
    }

    //Profile Elements ///////////////////////////////////////////////////////////////////////////////
    private SortedSet<ProfileElement> profileElement = new TreeSet<ProfileElement>();

    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "profileElementOwner", dependentElement = "true")
    @Named("Profiel elementen")
    public SortedSet<ProfileElement> getProfileElement() {
        return profileElement;
    }
    
    public void setProfileElement(final SortedSet<ProfileElement> vac) {
        this.profileElement = vac;
    }


    //Assessments ///////////////////////////////////////////////////////////////////////////////
    private SortedSet<ProfileAssessment> assessments = new TreeSet<ProfileAssessment>();

    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "target", dependentElement = "true")
    @Named("Assessments")
    public SortedSet<ProfileAssessment> getAssessments() {
        return assessments;
    }

    public void setAssessments(final SortedSet<ProfileAssessment> assessment) {
        this.assessments = assessment;
    }

    public boolean hideAssessments() {
        return this.allowedTrustLevel(TrustLevel.INNER_CIRCLE);
    }
   
    //HELPERS
    
    public String toString() {
        return "Profiel: " + this.profileName;
    }
    
}