package nl.yodo.dom.pruts;

import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.Bookmarkable;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Where;

import nl.yodo.dom.TrustLevel;
import nl.yodo.dom.YodoSecureMutableObject;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "myObjects", language = "JDOQL",
            value = "SELECT "
                    + "FROM nl.yodo.dom.pruts.Secobject "
                    + "WHERE ownedBy == :ownedBy")
    })
@Bookmarkable
public class Secobject extends YodoSecureMutableObject<Secobject> {
    
    public Secobject(){
        super("name");
    }
    
    private String name;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getName() {
        return name;
    }
    
    public void setName(final String name) {
        this.name=name;
    }
    
    private String testLevelOuter;
    
    @Hidden(where=Where.ALL_TABLES)
    @javax.jdo.annotations.Column(allowsNull = "true")
    public String getTestLevelOuter() {
        return testLevelOuter;
    }
    
    public void setTestLevelOuter(final String string) {
        this.testLevelOuter=string;
    }
    
    /**
     * Show to level OUTER_CIRCLE or higher
     * @return
     */
    public boolean hideTestLevelOuter() {
        return super.allowedTrustLevel(TrustLevel.OUTER_CIRCLE);
    }    
    
    private String testLevelInstap;
    
    @Hidden(where=Where.ALL_TABLES)
    @javax.jdo.annotations.Column(allowsNull = "true")
    public String getTestLevelInstap() {
        return testLevelInstap;
    }
    
    public void setTestLevelInstap(final String string) {
        this.testLevelInstap=string;
    }
    
    /**
     * Show to level ENTRY_LEVEL or higher
     * @return
     */   
    public boolean hideTestLevelInstap() {
        return super.allowedTrustLevel(TrustLevel.ENTRY_LEVEL);
    }    
    
    private String testLevelInner;
    
    @Hidden(where=Where.ALL_TABLES)
    @javax.jdo.annotations.Column(allowsNull = "true")
    public String getTestLevelInner() {
        return testLevelInner;
    }
    
    public void setTestLevelInner(final String string) {
        this.testLevelInner=string;
    }

    /**
     * Show to level INNER_CIRCLE or higher
     * @return
     */
    public boolean hideTestLevelInner() {
        return super.allowedTrustLevel(TrustLevel.INNER_CIRCLE);
    }
    
    
    private String testLevelIntimate;
    
    @Hidden(where=Where.ALL_TABLES)
    @javax.jdo.annotations.Column(allowsNull = "true")
    public String getTestLevelIntimate() {
        return testLevelIntimate;
    }
    
    public void setTestLevelIntimate(final String string) {
        this.testLevelIntimate=string;
    }

    /**
     * Show only to level INTIMATE
     * @return
     */
    public boolean hideTestLevelIntimate() {
        return super.allowedTrustLevel(TrustLevel.INTIMATE);
    }

}
