package info.matchingservice.dom.Party;

import javax.jdo.annotations.InheritanceStrategy;

import info.matchingservice.dom.MatchingDomainObject;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findMyRoles", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Party.Role "
                    + "WHERE ownedBy == :ownedBy"),
    @javax.jdo.annotations.Query(
            name = "findSpecificRole", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Party.Role "
                    + "WHERE ownedBy == :ownedBy && role == :role"),
})
public class Role extends MatchingDomainObject<Role> {

    public Role() {
        super("role");
    }
    
    private String ownedBy;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getOwnedBy() {
        return ownedBy;
    }
    
    public void setOwnedBy(final String owner) {
        this.ownedBy=owner;
    }
    
    private RoleType role;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public RoleType getRole(){
        return role;
    }
    
    public void setRole(final RoleType role) {
        this.role=role;
    }

}
