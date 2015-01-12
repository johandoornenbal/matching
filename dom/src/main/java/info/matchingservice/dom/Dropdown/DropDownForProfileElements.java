package info.matchingservice.dom.Dropdown;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Named;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Profile.ProfileElementType;

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
            @Named("Keyword of gedeelte ervan")
            final String value
            ) {
        return allMatches("matchDropDownByKeyWord", "value", value);
    }
    
    @Hidden
    public DropDownForProfileElement newProfileElementDropDown(
            final ProfileElementType category,
            final String value
            ){
        final DropDownForProfileElement newProfileElementDropDown = newTransientInstance(DropDownForProfileElement.class);
        newProfileElementDropDown.setType(category);
        newProfileElementDropDown.setValue(value);
        persist(newProfileElementDropDown);
        return newProfileElementDropDown;
    }
    
    @Named("Nieuwe kwaliteit")
    public DropDownForProfileElement newQualityDropDown(
            @Named("Kwaliteit")
            final String value
            ){
        final DropDownForProfileElement newProfileElementDropDown = newTransientInstance(DropDownForProfileElement.class);
        newProfileElementDropDown.setType(ProfileElementType.QUALITY);
        newProfileElementDropDown.setValue(value);
        persist(newProfileElementDropDown);
        return newProfileElementDropDown;
    } 
}
