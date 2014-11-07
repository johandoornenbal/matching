package nl.yodo.dom;

import java.util.List;

import javax.jdo.annotations.InheritanceStrategy;

import com.google.common.base.Objects;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Bookmarkable;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.query.QueryDefault;

/**
 * Establishes a trust relationship between the user creating an instance (owner) and the chosen contact.
 * The level of Trust is expressed by enum TrustLevel level.
 * 
 * The idea is: the higher the level of trust, the more information of contained in instances (of classes 
 * extending YodoSecureMutableObject) created by the owner will be available.
 * 
 * There should be at most 1 instance for each owner - contact combination.
 *
 * @version $Rev$ $Date$
 */
@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findYodoTrustedContact", language = "JDOQL",
            value = "SELECT "
                    + "FROM nl.yodo.dom.YodoTrustedContact "
                    + "WHERE ownedBy == :ownedBy"),
    @javax.jdo.annotations.Query(
            name = "findYodoTrustedUniqueContact", language = "JDOQL",
            value = "SELECT "
                    + "FROM nl.yodo.dom.YodoTrustedContact "
                    + "WHERE ownedBy == :ownedBy && contact == :contact")
})
@Bookmarkable
public class YodoTrustedContact extends YodoSecureMutableObject<YodoTrustedContact> {
    public YodoTrustedContact() {
        super("ownedBy");
    }
    
    /**
     * Hides instances that are not owned except for admin
     * NOTE: does not hide instances in Lists; only the forms
     */
    public boolean hidden() {
        // user is owner
        if (Objects.equal(getOwnedBy(), container.getUser().getName())){
            return false;
        }
        // user is admin of app
        if (container.getUser().hasRole(".*socrates-admin")) {
            return false;
        }
        return true;
    }
    
    /**
     * Temp method to display the owner
     */
    @Hidden(where=Where.OBJECT_FORMS)
    public String getEigenaar() {
        return getOwnedBy();
    }
    
    /**
     * contact contains username of the contact
     */
    private String contact;
    @Hidden
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getContact() {
        return contact;
    }
    
    public void setContact(final String contact) {
        this.contact=contact;
    }
    
    /**
     * level contains an enum TrustLevel, indicating the circle of trust
     */
    private TrustLevel level;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "30")
    @Named("Vertrouwens niveau")
    public TrustLevel getLevel() {
        return level;
    }
    
    public void setLevel(final TrustLevel level) {
        this.level = level;
    }
    
    public TrustLevel defaultLevel() {
        return TrustLevel.ENTRY_LEVEL;
    }
    
    // Region //// Delete action //////////////////////////////
    @Named("Dit contact verwijderen")
    public List<YodoTrustedContact> delete(@Optional @Named("Verwijderen OK?") boolean areYouSure) { 
        container.removeIfNotAlready(this);
        
        container.informUser("Contact verwijderd");
        
        QueryDefault<YodoTrustedContact> query = 
                QueryDefault.create(
                        YodoTrustedContact.class, 
                    "findYodoTrustedContact", 
                    "ownedBy", getOwnedBy());
        
        return (List<YodoTrustedContact>) container.allMatches(query);   
    }
    
    public String validateDelete(boolean areYouSure) {
        return areYouSure? null:"Geef aan of je wilt verwijderen";
    }
    
    /**
     * Hides the Delete action unless for owner / admin
     * NOTE: since the instance is hidden unless owner / admin it is a bit superfluous
     */
    public boolean hideDelete() {
        // user is owner
        if (Objects.equal(getOwnedBy(), currentUserName())){
            return false;
        }
        // user is admin of app
        if (container.getUser().hasRole(".*socrates-admin")) {
            return false;
        }
        return true;
    }
    
    
    // Region //// helpers
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    // Region //// injections ///////////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
    
}
