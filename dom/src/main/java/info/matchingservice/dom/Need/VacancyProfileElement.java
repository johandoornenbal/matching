package info.matchingservice.dom.Need;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.ProfileElementNature;
import info.matchingservice.dom.ProfileElementType;

import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Immutable;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Where;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findVacancyProfileElementByOwnerVacancy", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Need.VacancyProfileElement "
                    + "WHERE vacancyProfileElementOwner == :vacancyProfileElementOwner"),
    @javax.jdo.annotations.Query(
            name = "findVacancyProfileElementByOwnerVacancyAndNature", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Need.VacancyProfileElement "
                    + "WHERE vacancyProfileElementOwner == :vacancyProfileElementOwner && profileElementNature == :profileElementNature")
})
@Immutable
public class VacancyProfileElement extends MatchingSecureMutableObject<VacancyProfileElement> {

    public VacancyProfileElement() {
        super("ownedBy, vacancyProfileElementDescription, weight, vacancyProfileElementOwner, profileElementId");
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
    
    // immutables /////////////////////////////////////////////////////////////////////////////////////
    
    private VacancyProfile vacancyProfileElementOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    @Named("'Stoel'")
    @Hidden(where=Where.PARENTED_TABLES)
    @MemberOrder(sequence = "120")
    public VacancyProfile getVacancyProfileElementOwner() {
        return vacancyProfileElementOwner;
    }
    
    public void setVacancyProfileElementOwner(final VacancyProfile vacancyProfileOwner) {
        this.vacancyProfileElementOwner = vacancyProfileOwner;
    }
    
    private ProfileElementNature profileElementNature;
    
    @Disabled
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "100")
    public ProfileElementNature getProfileElementNature() {
        return profileElementNature;
    }
    
    public void setProfileElementNature(final ProfileElementNature nature) {
        this.profileElementNature = nature;
    }
    
    private ProfileElementType profileElementType;
    
    @Disabled
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "110")
    public ProfileElementType getProfileElementType() {
        return profileElementType;
    }
    
    public void setProfileElementType(final ProfileElementType type) {
        this.profileElementType = type;
    }
    
    // weight /////////////////////////////////////////////////////////////////////////////////////
    
    private Integer weight;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @MemberOrder(sequence = "20")
    public Integer getWeight() {
        return weight;
    }
    
    public void setWeight(final Integer weight) {
        this.weight = weight;
    }
    
    @Named("Bewerk gewicht")
    public VacancyProfileElement EditWeight(
            @Named("gewicht")
            Integer newWeight
            ){
        this.setWeight(newWeight);
        return this;
    }
    
    public Integer default0EditWeight() {
        return getWeight();
    }
    
    //element description /////////////////////////////////////////////////////////////////////////////////////
    
    private String vacancyProfileElementDescription;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MultiLine
    @Named("Profiel element beschrijving")
    @MemberOrder(sequence = "30")
    public String getVacancyProfileElementDescription(){
        return vacancyProfileElementDescription;
    }
    
    public void setVacancyProfileElementDescription(final String description) {
        this.vacancyProfileElementDescription = description;
    }
    
    @Named("Bewerk beschrijving")
    public VacancyProfileElement EditVacancyProfileDescription(
            @Named("Beschrijving")
            @MultiLine
            String newDescr
            ){
        this.setVacancyProfileElementDescription(newDescr);
        return this;
    }
    
    public String default0EditVacancyProfileDescription() {
        return getVacancyProfileElementDescription();
    }
    
    //delete action /////////////////////////////////////////////////////////////////////////////////////
    
    @Named("Verwijder element")
    public VacancyProfile DeleteVacancyProfileElement(
            @Optional @Named("Verwijderen OK?") boolean areYouSure
            ){
        container.removeIfNotAlready(this);
        container.informUser("Element verwijderd");
        return getVacancyProfileElementOwner();
    }
    
    public String validateDeleteVacancyProfileElement(boolean areYouSure) {
        return areYouSure? null:"Geef aan of je wilt verwijderen";
    }
    
    /// Helpers
    
    public String toString(){
        return this.vacancyProfileElementDescription;
    }
    
    // Used in case owner chooses identical description and weight
    @SuppressWarnings("unused")
    private String profileElementId;

    @Hidden
    public String getProfileElementId() {
        if (this.getId() != null) {
            return this.getId();
        }
        return "";
    }
    
    public void setProfileElementId() {
        this.profileElementId = this.getId();
    }

    // Injects
    
    @javax.inject.Inject
    private DomainObjectContainer container;
    
    

}
