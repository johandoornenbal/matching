package info.matchingservice.dom.Profile;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
public class ProfileElementText extends ProfileElement {
    
    //REPRESENTATIONS /////////////////////////////////////////////////////////////////////////////////////
    
    private String textValue;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    public String getTextValue(){
        return textValue;
    }
    
    public void setTextValue(final String value){
        this.textValue = value;
    }

}
