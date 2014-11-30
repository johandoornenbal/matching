package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Dropdown.DropDownForProfileElement;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElementCategory;
import info.matchingservice.dom.Profile.ProfileElementDropDown;
import info.matchingservice.dom.Profile.ProfileElementDropDowns;
import info.matchingservice.dom.Profile.ProfileElementNature;
import info.matchingservice.dom.Supply.SupplyProfiles;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class SupplyProfileDropDownAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
        
    protected ProfileElementDropDown createDropDownElement(
            String description,
            Integer weight,
            DropDownForProfileElement dropdownValue,
            ProfileElementCategory profileElementCategory,
            Profile profileOwner,
            ProfileElementNature nature,
            String ownedBy,
            ExecutionContext executionContext
            ){
        ProfileElementDropDown newDropDown = profileElementDropDowns.newProfileElementDropDown(description, weight, dropdownValue, profileElementCategory, profileOwner, nature, ownedBy);
        return executionContext.add(this,newDropDown);
    }
    
    //region > injected services
    @javax.inject.Inject
    SupplyProfiles profiles;
    
    @javax.inject.Inject
    ProfileElementDropDowns profileElementDropDowns;
}
