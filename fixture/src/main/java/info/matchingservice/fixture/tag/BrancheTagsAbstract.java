package info.matchingservice.fixture.tag;

import info.matchingservice.dom.Profile.ProfileElement;
import info.matchingservice.dom.Tags.TagHolders;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class BrancheTagsAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected ProfileElement createTagHolder(
            ProfileElement ownerElement,
            String tagProposal,
            ExecutionContext executionContext 
            ){
        ProfileElement newHolder = tagHolders.newBrancheTagHolder(ownerElement, tagProposal);
        return newHolder;
    }
    
    //region > injected services
    
    @javax.inject.Inject
    TagHolders tagHolders;
}
