package info.matchingservice.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import info.matchingservice.assessment.AssessmentsForFransFixture;
import info.matchingservice.assessment.AssessmentsForRembrandtFixture;
import info.matchingservice.dropdown.ProfileElementDropDownsFixture;
import info.matchingservice.fixture.actor.TestOrganisations;
import info.matchingservice.fixture.actor.TestPersonalContacts;
import info.matchingservice.fixture.actor.TestPersons;
import info.matchingservice.fixture.actor.TestRoles;
import info.matchingservice.fixture.communicationChannels.TestAddress;
import info.matchingservice.fixture.communicationChannels.TestEmails;
import info.matchingservice.fixture.communicationChannels.TestPhone;
import info.matchingservice.fixture.demand.DemandProfileElementsForFrans;
import info.matchingservice.fixture.demand.DemandProfileElementsForMichiel;
import info.matchingservice.fixture.demand.DemandProfileElementsForRembrandt;
import info.matchingservice.fixture.demand.TestDemandProfiles;
import info.matchingservice.fixture.demand.TestDemands;
import info.matchingservice.fixture.supply.TestSupplies;
import info.matchingservice.fixture.supply.TestSupplyProfileElementsPersonProfiles;
import info.matchingservice.fixture.supply.TestSupplyProfiles;
import info.matchingservice.fixture.tag.TagCategoriesFixture;
import info.matchingservice.fixture.tag.TagsFixture;
import info.matchingservice.fixture.tag.TagsForAntoni;
import info.matchingservice.fixture.tag.TagsForFrans;
import info.matchingservice.fixture.tag.TagsForGerard;
import info.matchingservice.fixture.tag.TagsForMichiel;
import info.matchingservice.fixture.tag.TagsForRembrandt;

public class MatchingDemoFixture extends FixtureScript {
    
    public MatchingDemoFixture() {
        super(null, "matchingdemo-fixture");
    }

    @Override
    protected void execute(ExecutionContext executionContext) {
        // prereqs
//        executeChild(new TeardownFixture(), executionContext);
        
        // create
    	executeChild(new TestMatchingProfileTypeRules(), executionContext);
    	executeChild(new TestConfig(), executionContext);
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
        executeChild(new TestSupplyProfileElementsPersonProfiles(), executionContext);
        executeChild(new TestOrganisations(), executionContext);
        executeChild(new AssessmentsForRembrandtFixture(), executionContext);
        executeChild(new AssessmentsForFransFixture(), executionContext);
        executeChild(new TestPersonalContacts(), executionContext);
        executeChild(new TagCategoriesFixture(), executionContext);
        executeChild(new TagsFixture(), executionContext);
        executeChild(new TagsForFrans(), executionContext);
        executeChild(new TagsForGerard(), executionContext);
        executeChild(new TagsForRembrandt(), executionContext);
        executeChild(new TagsForMichiel(), executionContext);
        executeChild(new TagsForAntoni(), executionContext);
        executeChild(new TestEmails(), executionContext);
        executeChild(new TestPhone(), executionContext);
        executeChild(new TestAddress(), executionContext);
        
    }

}
