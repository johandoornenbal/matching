package info.matchingservice.dom.Profile;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.ParameterLayout;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findProfileElementOfType", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.ProfileElementText "
                    + "WHERE profileElementOwner == :profileElementOwner && profileElementType == :profileElementType")
})
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
    
    public ProfileElement EditTextValue(
    		@ParameterLayout(
    				named = "Passie",
    				multiLine = 8
    				)
            String textValue
            ){
        this.setTextValue(textValue);
        this.setDisplayValue(textValue);
        return this;
    }
    
    public String default0EditTextValue() {
        return getTextValue();
    }

}
