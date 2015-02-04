package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.fixture.actor.PersonForGerard;
import info.matchingservice.fixture.tag.BrancheTagsAbstract;
import info.matchingservice.fixture.tag.TagCategoriesFixture;

import javax.inject.Inject;

public class BrancheTagsForGerard extends BrancheTagsAbstract {

	@Override
	protected void execute(ExecutionContext executionContext) {
		
		//preqs
		executeChild(new PersonForGerard(), executionContext);
        executeChild(new SuppliesForGerard(), executionContext);
        executeChild(new SupplyProfileElementsForGerard(), executionContext);
		executeChild(new TagCategoriesFixture(), executionContext);
		
		createTagHolder(
				persons.findPersons("Dou").get(0).getMySupplies().last().getSupplyProfiles().first().getProfileElement().first(), 
				"schilderkunst", 
				executionContext
				);
		createTagHolder(
				persons.findPersons("Dou").get(0).getMySupplies().last().getSupplyProfiles().first().getProfileElement().first(), 
				"portretteerkunst", 
				executionContext
				);
		createTagHolder(
				persons.findPersons("Dou").get(0).getMySupplies().last().getSupplyProfiles().first().getProfileElement().first(), 
				"kunst", 
				executionContext
				);
		createTagHolder(
				persons.findPersons("Dou").get(0).getMySupplies().last().getSupplyProfiles().first().getProfileElement().first(), 
				"zelfstandig", 
				executionContext
				);
	}
	
    @Inject
    private Persons persons;

}
