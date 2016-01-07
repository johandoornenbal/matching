package info.matchingservice.dom.Xtalus;

import info.matchingservice.dom.Actor.Person;
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
                name = "findEducationByPerson", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Xtalus.Education "
                        + "WHERE person == :person")
})
@DomainObject
public class Education {

    private String title, institute, location, honoursProgramm;


    //region > person (property)
    private Person person;

    @MemberOrder(sequence = "1")

    @Column(allowsNull = "false")
    public Person getPerson() {
        return person;
    }

    public void setPerson(final Person person) {
        this.person = person;
    }
    //endregion

    @Column(allowsNull = "false")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(allowsNull = "false")
    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }


    @Column(allowsNull = "true")
    public String getHonoursProgramm() {
        return honoursProgramm;
    }

    public void setHonoursProgramm(String honoursProgramm) {
        this.honoursProgramm = honoursProgramm;
    }
}
