package info.matchingservice.dom.TrustedCircles;

import info.matchingservice.dom.MatchingDomainObject;
import info.matchingservice.dom.TrustLevel;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.MemberOrder;

import javax.jdo.annotations.*;

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
                name = "findTrustedCircleElementByConfigAndNameAndTypeAndClassName", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.TrustedCircles.TrustedCircleElement "
                        + "WHERE trustedCircleConfig == :trustedCircleConfig && name == :name && type == :type && className == :className")
})
public class TrustedCircleElement extends MatchingDomainObject<TrustedCircleElement> {

    public TrustedCircleElement(){
        super("trustedCircleConfig, name, type, className, hideFor");
    }

    //region > owner (property)
    private TrustedCircleConfig trustedCircleConfig;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "false", name = "trustedCircleConfigId")
    public TrustedCircleConfig getTrustedCircleConfig() {
        return trustedCircleConfig;
    }

    public void setTrustedCircleConfig(final TrustedCircleConfig trustedCircleConfig) {
        this.trustedCircleConfig = trustedCircleConfig;
    }
    //endregion

    //region > name (property)
    private String name;

    @MemberOrder(sequence = "2")
    @Column(allowsNull = "false")
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
    //endregion

    //region > type (property)
    private Identifier.Type type;

    @MemberOrder(sequence = "3")
    @Column(allowsNull = "false")
    public Identifier.Type getType() {
        return type;
    }

    public void setType(final Identifier.Type type) {
        this.type = type;
    }
    //endregion

    //region > className (property)
    private String className;

    @MemberOrder(sequence = "4")
    @Column(allowsNull = "false")
    public String getClassName() {
        return className;
    }

    public void setClassName(final String className) {
        this.className = className;
    }
    //endregion

    //region > hideFor (property)
    private TrustLevel hideFor;

    @MemberOrder(sequence = "5")
    @Column(allowsNull = "false")
    public TrustLevel getHideFor() {
        return hideFor;
    }

    public void setHideFor(final TrustLevel hideFor) {
        this.hideFor = hideFor;
    }
    //endregion

}
