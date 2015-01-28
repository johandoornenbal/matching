package info.matchingservice.dom.Actor;

import java.util.List;

import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.query.QueryDefault;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findMyRoles", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Actor.SystemRole "
                    + "WHERE ownedBy == :ownedBy"),
    @javax.jdo.annotations.Query(
            name = "findSpecificRole", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Actor.SystemRole "
                    + "WHERE ownedBy == :ownedBy && role == :role"),
})
public class SystemRole extends Role {
    
//    private String ownedBy;
//    
//    @javax.jdo.annotations.Column(allowsNull = "false")
//    public String getOwnedBy() {
//        return ownedBy;
//    }
//    
//    public void setOwnedBy(final String owner) {
//        this.ownedBy=owner;
//    }
    
    private SystemRoleType role;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public SystemRoleType getRole(){
        return role;
    }
    
    public void setRole(final SystemRoleType role) {
        this.role=role;
    }
    
    // Region //// Delete action //////////////////////////////
    public List<SystemRole> delete(
            @ParameterLayout(named="areYouSure")
            @Parameter(optional=Optionality.TRUE)
            boolean areYouSure) {
        container.removeIfNotAlready(this);
        container.informUser("Rol verwijderd");
        QueryDefault<SystemRole> query =
                QueryDefault.create(
                        SystemRole.class,
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
