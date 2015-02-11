package info.matchingservice.fixture;

import info.matchingservice.assessment.AssessmentsForFransFixture;
import info.matchingservice.assessment.AssessmentsForRembrandtFixture;
import info.matchingservice.dropdown.ProfileElementDropDownsFixture;
import info.matchingservice.fixture.actor.TestOrganisations;
import info.matchingservice.fixture.actor.TestPersonalContacts;
import info.matchingservice.fixture.actor.TestPersons;
import info.matchingservice.fixture.actor.TestRoles;
import info.matchingservice.fixture.demand.DemandProfileElementsForFrans;
import info.matchingservice.fixture.demand.DemandProfileElementsForMichiel;
import info.matchingservice.fixture.demand.DemandProfileElementsForRembrandt;
import info.matchingservice.fixture.demand.TestDemandProfiles;
import info.matchingservice.fixture.demand.TestDemands;
import info.matchingservice.fixture.supply.SupplyProfileElementsForAntoni;
import info.matchingservice.fixture.supply.SupplyProfileElementsForFrans;
import info.matchingservice.fixture.supply.SupplyProfileElementsForGerard;
import info.matchingservice.fixture.supply.SupplyProfileElementsForMichiel;
import info.matchingservice.fixture.supply.SupplyProfileElementsForRembrandt;
import info.matchingservice.fixture.supply.TestSupplies;
import info.matchingservice.fixture.supply.TestSupplyProfiles;
import info.matchingservice.fixture.tag.BrancheTagsForFrans;
import info.matchingservice.fixture.tag.BrancheTagsForGerard;
import info.matchingservice.fixture.tag.TagCategoriesFixture;
import info.matchingservice.fixture.tag.TagsFixture;
import info.matchingservice.fixture.tag.TagsForFrans;
import info.matchingservice.fixture.tag.TagsForMichiel;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class MatchingTestsFixture extends FixtureScript {
    
    public MatchingTestsFixture() {
        super(null, "matchingtests-fixture");
    }

    @Override
    protected void execute(ExecutionContext executionContext) {
        // prereqs
        executeChild(new TeardownFixture(), executionContext);
   
        // create
        executeChild(new ProfileElementDropDownsFixture(), executionContext);
        executeChild(new TestPersons(), executionContext);
        executeChild(new TestRoles(), executionContext);
        executeChild(new TestDemands(), executionContext);
        executeChild(new TestDemandProfiles(), executionContext);
        executeChild(new DemandProfileElementsForFrans(), executionContext);
        executeChild(new DemandProfileElementsForRembrandt(), executionContext);
        executeChild(new DemandProfileElementsForMichiel(), executionContext);
        executeChild(new TestSupplies(), executionContext);
        executeChild(new TestSupplyProfiles(), executionContext);
        executeChild(new SupplyProfileElementsForFrans(), executionContext);
        executeChild(new SupplyProfileElementsForAntoni(), executionContext);        
        executeChild(new SupplyProfileElementsForGerard(), executionContext); 
        executeChild(new SupplyProfileElementsForMichiel(), executionContext); 
        executeChild(new SupplyProfileElementsForRembrandt(), executionContext); 
        executeChild(new TestOrganisations(), executionContext);
        executeChild(new AssessmentsForRembrandtFixture(), executionContext);
        executeChild(new AssessmentsForFransFixture(), executionContext);
        executeChild(new TestPersonalContacts(), executionContext);
        executeChild(new TagCategoriesFixture(), executionContext);
        executeChild(new TagsFixture(), executionContext);
        executeChild(new TagsForFrans(), executionContext);
        executeChild(new TagsForMichiel(), executionContext);
        executeChild(new BrancheTagsForGerard(), executionContext);
        executeChild(new BrancheTagsForFrans(), executionContext);
    }

}
