package info.matchingservice.dom.Dropdown;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.AutoComplete;

import info.matchingservice.dom.MatchingDomainObject;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@AutoComplete(repository=Quality.class,  action="autoComplete")
public class Quality extends MatchingDomainObject<Quality> {

    public Quality() {
        super("keyword");
    }
    
    private String keyword;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getKeyword(){
        return keyword;
    }

    public void setKeyword(final String keyword){
        this.keyword=keyword;
    }
    
    public String toString(){
        return getKeyword();
    }
}
