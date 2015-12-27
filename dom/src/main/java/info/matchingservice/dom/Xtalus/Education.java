package info.matchingservice.dom.Xtalus;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberOrder;

import javax.jdo.annotations.*;

/**
 * Created by jonathan on 27-12-15.
 */
@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@javax.jdo.annotations.Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findEducationByXtalusProfile", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Xtalus.Education "
                        + "WHERE xtalusProfile == :xtalusProfile")
})
@DomainObject
public class Education {

    private String title, institute, location, honoursProgramm;


    //region > xtalusProfile (property)
    private XtalusProfile xtalusProfile;

    @MemberOrder(sequence = "1")
    public XtalusProfile getXtalusProfile() {
        return xtalusProfile;
    }

    public void setXtalusProfile(final XtalusProfile xtalusProfile) {
        this.xtalusProfile = xtalusProfile;
    }
    //endregion


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(allowsNull = "true")
    public String getHonoursProgramm() {
        return honoursProgramm;
    }

    public void setHonoursProgramm(String honoursProgramm) {
        this.honoursProgramm = honoursProgramm;
    }
}
