package info.matchingservice.fixture.tag;

import info.matchingservice.dom.Profile.ProfileElement;
import info.matchingservice.dom.Tags.TagHolders;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class TagsAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected ProfileElement createBrancheTagHolder(
            ProfileElement ownerElement,
            String tagProposal,
            String ownedBy,
            ExecutionContext executionContext 
            ){
        ProfileElement newHolder = tagHolders.createTagHolder(ownerElement, tagProposal, "branche", ownedBy);
        return newHolder;
    }
    
    protected ProfileElement createPassionTagHolder(
            ProfileElement ownerElement,
            String tagProposal,
            String ownedBy,
            ExecutionContext executionContext 
            ){
        ProfileElement newHolder = tagHolders.createTagHolder(ownerElement, tagProposal, "passie", ownedBy);
        return newHolder;
    }
    
    protected ProfileElement createQualityTagHolder(
            ProfileElement ownerElement,
            String tagProposal,
            String ownedBy,
            ExecutionContext executionContext 
            ){
        ProfileElement newHolder = tagHolders.createTagHolder(ownerElement, tagProposal, "kwaliteit", ownedBy);
        return newHolder;
    }
    
    protected ProfileElement createWeekDayTagHolder(
            ProfileElement ownerElement,
            String tagProposal,
            String ownedBy,
            ExecutionContext executionContext 
            ){
        ProfileElement newHolder = tagHolders.createTagHolder(ownerElement, tagProposal, "weekdagen", ownedBy);
        return newHolder;
    }
    
    //region > injected services
    
    @javax.inject.Inject
    TagHolders tagHolders;
}
