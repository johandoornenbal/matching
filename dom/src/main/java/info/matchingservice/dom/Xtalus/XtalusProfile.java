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
                name = "findXtalusProfileByPerson", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Xtalus.XtalusProfile "
                        + "WHERE person == :person")
})
@DomainObject
public class XtalusProfile {

    //region > person (property)
    private Person person;

    @MemberOrder(sequence = "1")
    public Person getPerson() {
        return person;
    }

    public void setPerson(final Person person) {
        this.person = person;
    }
    //endregion


    //region > story (property)
    private String story;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "true")
    public String getStory() {
        return story;
    }

    public void setStory(final String story) {
        this.story = story;
    }
    //endregion

    //region > backgroundImage (property)
    private String backgroundImage;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "true")
    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(final String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
    //endregion







}
