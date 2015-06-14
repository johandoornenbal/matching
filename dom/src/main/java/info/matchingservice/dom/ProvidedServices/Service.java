package info.matchingservice.dom.ProvidedServices;

import java.math.BigDecimal;
import java.util.UUID;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.Where;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.MatchingSecureMutableObject;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
@Queries({
        @Query(
                name = "findServiceByOwnedBy", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.ProvidedServices.Service "
                        + "WHERE ownedBy == :ownedBy"),
        @Query(
                name = "findServiceByType", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.ProvidedServices.Service "
                        + "WHERE serviceType == :serviceType"),
        @Query(
                name = "findServiceByOwner", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.ProvidedServices.Service "
                        + "WHERE serviceOwner == :serviceOwner"),
        @Query(
                name = "findServiceByUniqueItemId", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.ProvidedServices.Service "
                        + "WHERE uniqueItemId.matches(:uniqueItemId)"),
        @Query(
                name = "findServiceByDescriptionContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.ProvidedServices.Service "
                        + "WHERE serviceDescription.toLowerCase().indexOf(:serviceDescription) >= 0"),
        @Query(
                name = "findServiceByStatus", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.ProvidedServices.Service "
                        + "WHERE serviceStatus == :serviceStatus"),
        @Query(
                name = "findServiceByDescription", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.ProvidedServices.Service "
                        + "WHERE serviceDescription == :serviceDescription"),
})
@DomainObject(editing= Editing.DISABLED)
public class Service extends MatchingSecureMutableObject<Service> {

    public Service() {
        super("uniqueItemId");
    }


    //*****PROPERTIES****//


    //region > serviceDescription (property)
    private String serviceDescription;

    @Column(allowsNull = "false")
    @Title
    public String getServiceDescription() {
        return serviceDescription;
    }


    public void setServiceDescription(final String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }
    //endregion


    //region > serviceOwner (property)
    private Person serviceOwner;

    @Column(allowsNull = "false")
    public Person getServiceOwner() {
        return serviceOwner;
    }


    public void setServiceOwner(final Person serviceTemplateOwner) {
        this.serviceOwner = serviceTemplateOwner;
    }
    //endregion


    //region > serviceSummary (property)
    private String serviceSummary;

    @Column(allowsNull = "false")
    public String getServiceSummary() {
        return serviceSummary;
    }

    public void setServiceSummary(final String serviceSummary) {
        this.serviceSummary = serviceSummary;
    }
    //endregion


    //region > serviceType (property)
    private ServiceType serviceType;

    @Column(allowsNull = "false")
    public ServiceType getServiceType() {
        return serviceType;
    }


    public void setServiceType(final ServiceType serviceType) {
        this.serviceType = serviceType;
    }
    //endregion




    //region > priceEuro (property)
    private BigDecimal priceEuro;

    @Column(allowsNull = "true")
    public BigDecimal getPriceEuro() {
        return priceEuro;
    }

    public void setPriceEuro(final BigDecimal priceEuro) {
        this.priceEuro = priceEuro;
    }
    //endregion


    //region > postalCode (property)
    private String postalCode;

    @Column(allowsNull = "true")
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }
    //endregion



    //region > priceCredits (property)
    private BigDecimal priceCredits;

    @Column(allowsNull = "true")
    public BigDecimal getPriceCredits() {
        return priceCredits;
    }

    public void setPriceCredits(final BigDecimal priceCredits) {
        this.priceCredits = priceCredits;
    }
    //endregion


    //region > priceStreet (property)
    private BigDecimal priceStreet;

    @Column(allowsNull = "true")
    public BigDecimal getPriceStreet() {
        return priceStreet;
    }

    public void setPriceStreet(final BigDecimal priceStreet) {
        this.priceStreet = priceStreet;
    }
    //endregion


    //region > uniqueItemId (property)
    private UUID uniqueItemId;

    @Column(allowsNull = "false")
    public UUID getUniqueItemId() {
        return uniqueItemId;
    }


    public void setUniqueItemId(final UUID uniqueItemId) {
        this.uniqueItemId = uniqueItemId;
    }
    //endregion


    //region > serviceStatus (property)
    private ServiceStatus serviceStatus;

    @Column(allowsNull = "true")
    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(final ServiceStatus serviceStatus) {
        this.serviceStatus = serviceStatus;
    }
    //endregion



    //** ownedBy - Override for secure object **//
    private String ownedBy;

    @Override
    @Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    @PropertyLayout(hidden= Where.ALL_TABLES)
    public String getOwnedBy() {
        return ownedBy;
    }


    public void setOwnedBy(final String owner) {
        this.ownedBy = owner;
    }

    //--ownedBy--//





    //----PROPERTIES---//



    //**** UPDATE SERVICESUMMARY ****//

    //region > updateSummary (action)
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout
    public Service updateServiceSummary(final String serviceSummary) {

        setServiceSummary(serviceSummary);
        return this;

    }
    //endregion

    public String default0UpdateServiceSummary() {
        return getServiceSummary();
    }

    public boolean hideUpdateServiceSummary() {
        return notOwner();
    }


    //---- UPDATE SERVICESUMMARY ----//


    //**** UPDATE SERViCE TYPE ****//

    public Service updateServiceType(final ServiceType serviceType){

        setServiceType(serviceType);
        return this;

    }

    public ServiceType default0UpdateServiceType() {
        return getServiceType();
    }

    public boolean hideUpdateServiceType() {
        return notOwner();

    }



    //-- UPDATE SERVICE TYPE ----//













    //*** HELPERS ***//






    /**returns true if the current user is not the owner of this object
     *
     * @return
     */
    @Programmatic
    private boolean notOwner(){
        return !this.getOwnedBy().equals(container.getUser().getName());

    }


    //-- HELPERS ---//
    @javax.inject.Inject
    DomainObjectContainer container;


//    @Inject
//    ServiceStakeholders stakeholders;


    @Inject
    Persons persons;

}