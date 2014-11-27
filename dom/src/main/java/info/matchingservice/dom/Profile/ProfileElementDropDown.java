package info.matchingservice.dom.Profile;

import info.matchingservice.dom.Dropdown.DropDownForProfileElement;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
public class ProfileElementDropDown extends ProfileElement {
    
    //REPRESENTATIONS /////////////////////////////////////////////////////////////////////////////////////
    
    private DropDownForProfileElement dropDownValue;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    public DropDownForProfileElement getDropDownValue(){
        return dropDownValue;
    }
    
    public void setDropDownValue(final DropDownForProfileElement value){
        this.dropDownValue = value;
    }

}
