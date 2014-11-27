package info.matchingservice.dropdown;

import info.matchingservice.dom.Dropdown.DropDownForProfileElement;
import info.matchingservice.dom.Dropdown.DropDownForProfileElements;
import info.matchingservice.dom.Profile.ProfileElementCategory;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class ProfileElementDropDownAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected DropDownForProfileElement createDropdown (
            ProfileElementCategory category,
            String value,
            ExecutionContext executionContext
            ) {
        DropDownForProfileElement newDropdown = profileElementDropDowns.newProfileElementDropDown(category, value);
        
        return executionContext.add(this, newDropdown);
    }

    //region > injected services
    
    @javax.inject.Inject
    private DropDownForProfileElements profileElementDropDowns;
    

}