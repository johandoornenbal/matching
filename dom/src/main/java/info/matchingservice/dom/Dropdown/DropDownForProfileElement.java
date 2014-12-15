package info.matchingservice.dom.Dropdown;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.Immutable;

import info.matchingservice.dom.MatchingDomainObject;
import info.matchingservice.dom.Profile.ProfileElementType;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "matchDropDownByKeyWord", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Dropdown.DropDownForProfileElement "
                    + "WHERE type == 'QUALITY' && value.indexOf(:value) >= 0")             
})
@Immutable
public class DropDownForProfileElement extends MatchingDomainObject<DropDownForProfileElement>{
    
    public DropDownForProfileElement(){
        super("value, category");
    }
    
    public String title(){
        return getValue();
    }
    
    private ProfileElementType type;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public ProfileElementType getType(){
        return type;
    }
    
    public void setType(final ProfileElementType category){
        this.type = category;
    }
    
    private String value;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getValue(){
        return value;
    }
    
    public void setValue(final String value){
        this.value = value;
    }
}
