package info.matchingservice.dom.Dropdown;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Named;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Profile.ProfileElementCategory;

@DomainService(menuOrder = "150", repositoryFor = ProfileElementDropDown.class)
public class ProfileElementDropDowns extends MatchingDomainService<ProfileElementDropDown> {

    public ProfileElementDropDowns() {
        super(ProfileElementDropDowns.class, ProfileElementDropDown.class);
    }
    
    public List<ProfileElementDropDown> allProfileElementDropDowns(){
        return allInstances(ProfileElementDropDown.class);
    }
    
    public List<ProfileElementDropDown> findDropDowns(
            @Named("Keyword of gedeelte ervan")
            final String value
            ) {
        return allMatches("matchDropDownByKeyWord", "value", value);
    }
    
    public ProfileElementDropDown newProfileElementDropDown(
            final ProfileElementCategory category,
            final String value
            ){
        final ProfileElementDropDown newProfileElementDropDown = newTransientInstance(ProfileElementDropDown.class);
        newProfileElementDropDown.setCategory(category);
        newProfileElementDropDown.setValue(value);
        persist(newProfileElementDropDown);
        return newProfileElementDropDown;
    }
}
