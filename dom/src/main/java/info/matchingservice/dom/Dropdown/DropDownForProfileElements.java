package info.matchingservice.dom.Dropdown;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Profile.ProfileElementType;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Where;

@DomainService(repositoryFor = DropDownForProfileElement.class)
@DomainServiceLayout(
        named="Beheer",
        menuBar = DomainServiceLayout.MenuBar.PRIMARY,
        menuOrder = "80"
)
public class DropDownForProfileElements extends MatchingDomainService<DropDownForProfileElement> {

    public DropDownForProfileElements() {
        super(DropDownForProfileElements.class, DropDownForProfileElement.class);
    }
    
    public List<DropDownForProfileElement> allProfileElementDropDowns(){
        return allInstances(DropDownForProfileElement.class);
    }
    
    public List<DropDownForProfileElement> findDropDowns(
            @ParameterLayout(named="value")
            final String value
            ) {
        return allMatches("matchDropDownByKeyWord", "value", value);
    }
    
    @ActionLayout(hidden=Where.EVERYWHERE)
    public DropDownForProfileElement newProfileElementDropDown(
            final ProfileElementType category,
            final String value
            ){
        final DropDownForProfileElement newProfileElementDropDown = newTransientInstance(DropDownForProfileElement.class);
        newProfileElementDropDown.setType(category);
        newProfileElementDropDown.setValue(value.toLowerCase());
        persist(newProfileElementDropDown);
        return newProfileElementDropDown;
    }
    
    @ActionLayout(named="Nieuwe kwaliteit")
    public DropDownForProfileElement newQualityDropDown(
            @ParameterLayout(named="value")
            final String value
            ){
        final DropDownForProfileElement newProfileElementDropDown = newTransientInstance(DropDownForProfileElement.class);
        newProfileElementDropDown.setType(ProfileElementType.QUALITY);
        newProfileElementDropDown.setValue(value.toLowerCase());
        persist(newProfileElementDropDown);
        return newProfileElementDropDown;
    }
    
    public List<DropDownForProfileElement> autoComplete0NewQualityDropDown(final String search) {
        return dropDownForProfileElements.findDropDowns(search);
    }
    
    public String validateNewQualityDropDown(final String value){
        if (!this.findDropDowns(value.toLowerCase()).isEmpty()){
            return "Deze kwaliteit is al eerder ingevoerd";
        }
        return null;
    }
    
    @Inject
    DropDownForProfileElements dropDownForProfileElements;
}
