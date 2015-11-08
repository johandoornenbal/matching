package info.matchingservice.dom.TrustedCircles;

import info.matchingservice.dom.MatchingSecureMutableObject;
import org.apache.isis.applib.annotation.*;

import javax.jdo.annotations.*;
import java.util.SortedSet;
import java.util.TreeSet;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findTrustedCircleConfigByOwner", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.TrustedCircles.TrustedCircleConfig "
                        + "WHERE ownedBy == :ownedBy")
})
public class TrustedCircleConfig extends MatchingSecureMutableObject<TrustedCircleConfig> {

    public TrustedCircleConfig() {
        super("ownedBy");
    }

    private String ownedBy;

    @Override
    @Column(allowsNull = "false")
    @PropertyLayout(hidden= Where.ALL_TABLES)
    @Property(editing= Editing.DISABLED)
    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(final String owner) {
        this.ownedBy = owner;
    }

    //region > elements (collection)
    private SortedSet<TrustedCircleElement> elements = new TreeSet<TrustedCircleElement>();

    @MemberOrder(sequence = "1")
    @CollectionLayout(render=RenderType.EAGERLY)
    @Persistent(mappedBy = "trustedCircleConfig", dependentElement = "true")
    public SortedSet<TrustedCircleElement> getElements() {
        return elements;
    }

    public void setElements(final SortedSet<TrustedCircleElement> elements) {
        this.elements = elements;
    }
    //endregion

}
