package nl.yodo.dom.Party;

import java.util.List;

import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.AutoComplete;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.query.QueryDefault;

import nl.yodo.dom.TrustLevel;
import nl.yodo.dom.pruts.Secobject;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findYodoPersonUnique", language = "JDOQL",
            value = "SELECT "
                    + "FROM nl.yodo.dom.Party.YodoPerson "
                    + "WHERE ownedBy == :ownedBy"),
    @javax.jdo.annotations.Query(
            name = "matchPersonByLastName", language = "JDOQL",
            value = "SELECT "
                    + "FROM nl.yodo.dom.Party.YodoPerson "
                    + "WHERE lastName.matches(:lastName)")                    
})
@AutoComplete(repository=YodoPersons.class,  action="autoComplete")
public class YodoPerson extends YodoParty {
    
    public String title() {
        return this.getLastName() + ", " + this.getFirstName() + " " + this.getMiddleName();
    }
    
    private String firstName;
    
    @MemberOrder(sequence = "10")
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(final String fn) {
        this.firstName = fn;
    }
    
    private String middleName;
    
    @MemberOrder(sequence = "20")
    @javax.jdo.annotations.Column(allowsNull = "true")
    public String getMiddleName() {
        return middleName;
    }
    
    public void setMiddleName(final String mn) {
        this.middleName = mn;
    }
    
    private String lastName;
    
    @MemberOrder(sequence = "30")
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(final String ln) {
        this.lastName = ln;
    }
    
    public void modifyLastName(final String ln) {
        setLastName(ln);
        container.informUser("Bijgewerkt nog niet persisted!");
    }

    private String forOuterContactEyes;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @MultiLine
    @Hidden(where=Where.ALL_TABLES)
    @MemberOrder(sequence = "40")
    public String getForOuterContactEyes() {
        return forOuterContactEyes;
    }
    
    public void setForOuterContactEyes(final String string) {
        this.forOuterContactEyes = string;
    }
    
    public boolean hideForOuterContactEyes() {
        return super.allowedTrustLevel(TrustLevel.OUTER_CIRCLE);
    }
       
    private String forNormalContactEyes;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @MultiLine
    @Hidden(where=Where.ALL_TABLES)
    @MemberOrder(sequence = "50")
    public String getForNormalContactEyes() {
        return forNormalContactEyes;
    }
    
    public void setForNormalContactEyes(final String string) {
        this.forNormalContactEyes = string;
    }
    
    public boolean hideForNormalContactEyes() {
        return super.allowedTrustLevel(TrustLevel.ENTRY_LEVEL);
    }
    
    
    private String notForAllEyes;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @MultiLine
    @Hidden(where=Where.ALL_TABLES)
    @MemberOrder(sequence = "60")
    public String getNotForAllEyes() {
        return notForAllEyes;
    }
    
    public void setNotForAllEyes(final String string) {
        this.notForAllEyes = string;
    }

    public boolean hideNotForAllEyes() {
        return super.allowedTrustLevel(TrustLevel.INNER_CIRCLE);
    }
    
    // SecObjects test 
    
    @MemberOrder(sequence = "100")
    @Render(Type.EAGERLY)
    @Named("Test met 'veilige' objecten")
    public List<Secobject> getMyObjects() {
        QueryDefault<Secobject> query =
                QueryDefault.create(
                        Secobject.class,
                        "myObjects",
                        "ownedBy", this.getOwnedBy());
        return container.allMatches(query);
    }
    
    
    // TESTS /////////////////////////////////////////////////////    
    public void updating() {
        container.informUser("Nog niet persisted - TEST!");
    }
    
    public void updated() {
        container.informUser("Persisted - TEST!");
    }
    
    @javax.inject.Inject
    private DomainObjectContainer container;

}
