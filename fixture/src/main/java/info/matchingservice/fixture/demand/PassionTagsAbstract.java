package info.matchingservice.fixture.demand;

import info.matchingservice.dom.Profile.ProfileElement;
import info.matchingservice.dom.Tags.Tag;
import info.matchingservice.dom.Tags.TagHolders;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class PassionTagsAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected ProfileElement createTagHolder(
            ProfileElement ownerElement,
            Tag tag,
            ExecutionContext executionContext 
            ){
        ProfileElement newHolder = tagHolders.newPassionTagHolder(ownerElement, tag);
        return newHolder;
    }
    
    //region > injected services
    
    @javax.inject.Inject
    TagHolders tagHolders;
}
