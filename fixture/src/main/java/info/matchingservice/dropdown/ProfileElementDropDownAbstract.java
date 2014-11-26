package info.matchingservice.dropdown;

import info.matchingservice.dom.Dropdown.ProfileElementDropDown;
import info.matchingservice.dom.Dropdown.ProfileElementDropDowns;
import info.matchingservice.dom.Profile.ProfileElementCategory;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class ProfileElementDropDownAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected ProfileElementDropDown createDropdown (
            ProfileElementCategory category,
            String value,
            ExecutionContext executionContext
            ) {
        ProfileElementDropDown newDropdown = profileElementDropDowns.newProfileElementDropDown(category, value);
        
        return executionContext.add(this, newDropdown);
    }

    //region > injected services
    
    @javax.inject.Inject
    private ProfileElementDropDowns profileElementDropDowns;
    

}