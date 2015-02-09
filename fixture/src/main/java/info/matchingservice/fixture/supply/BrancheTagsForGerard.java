package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.fixture.actor.PersonForGerard;
import info.matchingservice.fixture.tag.TagsAbstract;
import info.matchingservice.fixture.tag.TagCategoriesFixture;

import javax.inject.Inject;

public class BrancheTagsForGerard extends TagsAbstract {

	@Override
	protected void execute(ExecutionContext executionContext) {
		
		//preqs
		executeChild(new PersonForGerard(), executionContext);
        executeChild(new SuppliesForGerard(), executionContext);
        executeChild(new SupplyProfileElementsForGerard(), executionContext);
		executeChild(new TagCategoriesFixture(), executionContext);
		
		createBranchTagHolder(
				persons.findPersons("Dou").get(0).getCollectSupplies().last().getCollectSupplyProfiles().first().getProfileElement().first(), 
				"schilderkunst", 
				executionContext
				);
		createBranchTagHolder(
				persons.findPersons("Dou").get(0).getCollectSupplies().last().getCollectSupplyProfiles().first().getProfileElement().first(), 
				"portretteerkunst", 
				executionContext
				);
		createBranchTagHolder(
				persons.findPersons("Dou").get(0).getCollectSupplies().last().getCollectSupplyProfiles().first().getProfileElement().first(), 
				"kunst", 
				executionContext
				);
		createBranchTagHolder(
				persons.findPersons("Dou").get(0).getCollectSupplies().last().getCollectSupplyProfiles().first().getProfileElement().first(), 
				"zelfstandig", 
				executionContext
				);
	}
	
    @Inject
    private Persons persons;

}
