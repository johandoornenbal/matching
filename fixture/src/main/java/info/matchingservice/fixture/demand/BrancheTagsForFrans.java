package info.matchingservice.fixture.demand;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.fixture.actor.TestPersons;
import info.matchingservice.fixture.tag.TagCategoriesFixture;
import info.matchingservice.fixture.tag.TagsAbstract;

import javax.inject.Inject;

public class BrancheTagsForFrans extends TagsAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
    	executeChild(new TestPersons(), executionContext);
        executeChild(new TestDemands(), executionContext);
        executeChild(new DemandProfileElementsForFrans(), executionContext);
        executeChild(new TagCategoriesFixture(), executionContext);
        
        
        createBranchTagHolder(
                persons.findPersons("Hals").get(0).getCollectDemands().last().getCollectDemandProfiles().first().getCollectProfileElements().first(), 
                "kunst", 
                executionContext
                );
        createBranchTagHolder(
                persons.findPersons("Hals").get(0).getCollectDemands().last().getCollectDemandProfiles().first().getCollectProfileElements().first(), 
                "schilderkunst", 
                executionContext
                );
        createBranchTagHolder(
                persons.findPersons("Hals").get(0).getCollectDemands().last().getCollectDemandProfiles().first().getCollectProfileElements().first(), 
                "iets_anders", 
                executionContext
                );
    }
    
    @Inject
    private Persons persons;    
}
