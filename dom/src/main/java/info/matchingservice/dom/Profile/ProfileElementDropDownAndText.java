package info.matchingservice.dom.Profile;

import info.matchingservice.dom.Dropdown.DropDownForProfileElement;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.Optional;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
public class ProfileElementDropDownAndText extends ProfileElement {
    
    //REPRESENTATIONS /////////////////////////////////////////////////////////////////////////////////////
    
    private DropDownForProfileElement optionalDropDownValue;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Optional
    public DropDownForProfileElement getOptionalDropDownValue(){
        return optionalDropDownValue;
    }
    
    public void setOptionalDropDownValue(final DropDownForProfileElement value){
        this.optionalDropDownValue = value;
    }
    
    private String text;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Optional
    public String getText(){
        return text;
    }
    
    public void setText(final String text){
        this.text = text;
    }

}
