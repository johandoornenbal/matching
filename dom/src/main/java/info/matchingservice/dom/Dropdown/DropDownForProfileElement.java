package info.matchingservice.dom.Dropdown;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import info.matchingservice.dom.MatchingDomainObject;
import info.matchingservice.dom.Profile.ProfileElementCategory;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "matchDropDownByKeyWord", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Dropdown.ProfileElementDropDown "
                    + "WHERE category == 'QUALITY' && value.indexOf(:value) >= 0")             
})
public class DropDownForProfileElement extends MatchingDomainObject<DropDownForProfileElement>{
    
    public DropDownForProfileElement(){
        super("value, category");
    }
    
    public String title(){
        return getValue();
    }
    
    private ProfileElementCategory category;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public ProfileElementCategory getCategory(){
        return category;
    }
    
    public void setCategory(final ProfileElementCategory category){
        this.category = category;
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
