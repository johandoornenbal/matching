package info.matchingservice.fixture.tag;

import info.matchingservice.dom.Profile.ProfileElement;
import info.matchingservice.dom.Tags.TagHolders;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class TagsAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected ProfileElement createBranchTagHolder(
            ProfileElement ownerElement,
            String tagProposal,
            ExecutionContext executionContext 
            ){
        ProfileElement newHolder = tagHolders.newBrancheTagHolder(ownerElement, tagProposal);
        return newHolder;
    }
    
    protected ProfileElement createPassionTagHolder(
            ProfileElement ownerElement,
            String tagProposal,
            ExecutionContext executionContext 
            ){
        ProfileElement newHolder = tagHolders.createPassionTagHolder(ownerElement, tagProposal);
        return newHolder;
    }
    
    protected ProfileElement createQualityTagHolder(
            ProfileElement ownerElement,
            String tagProposal,
            ExecutionContext executionContext 
            ){
        ProfileElement newHolder = tagHolders.newQualityTagHolder(ownerElement, tagProposal);
        return newHolder;
    }
    
    //region > injected services
    
    @javax.inject.Inject
    TagHolders tagHolders;
}
