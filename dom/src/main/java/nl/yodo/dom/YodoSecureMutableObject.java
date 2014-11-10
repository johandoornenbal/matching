package nl.yodo.dom;

import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.base.Objects;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;

/**
 * A Domain object that is secure checks who is the owner of an instance. Only
 * the owner and an admin of the app can edit the instance. Only an admin can
 * change the owner field. A domain object that is mutable and can be changed by
 * multiple users over time, and should therefore have optimistic locking
 * controls in place.
 * 
 * <p>
 * Subclasses must be annotated with:
 * 
 * <pre>
 * @javax.jdo.annotations.DatastoreIdentity(
 *     strategy = IdGeneratorStrategy.NATIVE,
 *     column = "id")
 * BUT NOT WITH (See reason..)    
 * @javax.jdo.annotations.Version(
 *     strategy=VersionStrategy.VERSION_NUMBER, 
 *     column="version")
 * public class MyDomainObject extends YodoMutableObject {
 *   ...
 * }
 * </pre>
 * 
 * <p>
 * Note however that if a subclass that has a supertype which is annotated with
 * {@link javax.jdo.annotations.Version} (eg <tt>CommunicationChannel</tt>) then
 * the subtype must not also have a <tt>Version</tt> annotation (otherwise JDO
 * will end up putting a <tt>version</tt> column in both tables, and they are
 * not kept in sync).
 */
//@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
//@javax.jdo.annotations.DatastoreIdentity(
//        strategy = IdGeneratorStrategy.NATIVE,
//        column = "id")
//@javax.jdo.annotations.Version(
//        strategy = VersionStrategy.VERSION_NUMBER,
//        column = "version")
//@javax.jdo.annotations.Discriminator(
//        strategy = DiscriminatorStrategy.CLASS_NAME,
//        column = "discriminator")
public abstract class YodoSecureMutableObject<T extends YodoDomainObject<T>>
        extends YodoMutableObject<T> {

    public YodoSecureMutableObject(final String keyProperties) {
        super(keyProperties);
    }

    // /////// Security ////////////////////////////////////////////////

    /**
     * Methods should only be used by the owner or app admin
     * 
     * @param type
     * @return
     */
    public String disabled(Identifier.Type type) {
        // user is owner
        if (Objects.equal(getOwnedBy(), container.getUser().getName())) {
            return null;
        }
        // user is admin of socrates app
        // if
        // (container.getUser().hasRole("isisModuleSecurityRealm:socrates-admin"))
        // {
        if (container.getUser().hasRole(".*socrates-admin")) {
            return null;
        }
        // user is neither owner nor admin
        return "Not allowed to modify / Niet toegestaan te wijzigen";
    }

    private String ownedBy;

    @Hidden
//    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(final String owner) {
        this.ownedBy = owner;
    }

    public void changeOwner(final String owner) {
        this.setOwnedBy(owner);
    }

    public boolean hideChangeOwner(final String owner) {
        // user is admin of socrates app
        if (container.getUser().hasRole(".*socrates-admin")) {
            return false;
        }
        return true;
    }

    /**
     * Viewerrights are derived from YodoTrustedContact
     * 
     */
    @Hidden
    public TrustLevel getViewerRights() {
        return trustedContacts.trustLevel(this.getOwnedBy(), container.getUser().getName());
    }

    /**
     * This method can be used in combination with hideXxx() or disableXxx()
     * method of a child e.g. hideXxx(){ return
     * super.allowedTrustLevel(TrustLevel.ENTRY_LEVEL); } Hides the Xxx field or
     * method for all levels under ENTRY_LEVEL (OUTER_CIRCLE and BANNED) and
     * show it for levels ENTRY_LEVEL and up (INNER_CIRCLE and INTIMATE)
     * 
     * Exceptions are when called by the owner of the instance or admin of the
     * app
     * 
     * @param trustlevel
     * @return
     */
    @Hidden
    public boolean allowedTrustLevel(final TrustLevel trustlevel) {
        // user is owner
        if (Objects.equal(getOwnedBy(), container.getUser().getName())) {
            return false;
        }
        // user is admin of app
        if (container.getUser().hasRole(".*socrates-admin")) {
            return false;
        }
        if (getViewerRights() != null && trustlevel.compareTo(getViewerRights()) <= 0) {
            return false;
        }
        return true;
    }

    // /////// Injects ////////////////////////////////////////////////

    @javax.inject.Inject
    DomainObjectContainer container;

    @javax.inject.Inject
    private YodoTrustedContacts trustedContacts;

}
