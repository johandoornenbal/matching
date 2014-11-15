package info.matchingservice.dom.Actor;

import java.util.List;

import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.query.QueryDefault;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findMyRoles", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Actor.OrganisationRole "
                    + "WHERE ownedBy == :ownedBy"),
    @javax.jdo.annotations.Query(
            name = "findSpecificRole", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Actor.OrganisationRole "
                    + "WHERE ownedBy == :ownedBy && role == :role"),
})
public class OrganisationRole extends Role {
    
    private String ownedBy;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getOwnedBy() {
        return ownedBy;
    }
    
    public void setOwnedBy(final String owner) {
        this.ownedBy=owner;
    }
    
    private OrganisationRoleType role;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public OrganisationRoleType getRole(){
        return role;
    }
    
    public void setRole(final OrganisationRoleType role) {
        this.role=role;
    }
    
    // Region //// Delete action //////////////////////////////
    public List<OrganisationRole> delete(@Optional @Named("Verwijderen OK?") boolean areYouSure) {
        container.removeIfNotAlready(this);
        container.informUser("Rol verwijderd");
        QueryDefault<OrganisationRole> query =
                QueryDefault.create(
                        OrganisationRole.class,
                        "findMyRoles",
                        "ownedBy", this.getOwnedBy());
        return container.allMatches(query);
    }
    
    public String validateDelete(boolean areYouSure) {
        return areYouSure? null:"Geef aan of je wilt verwijderen";
    }
    
    // Region //// injections ///////////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;

}
