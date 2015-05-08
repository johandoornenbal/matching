package info.matchingservice.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import info.matchingservice.fixture.assessment.AssessmentsForFransFixture;
import info.matchingservice.fixture.assessment.AssessmentsForRembrandtFixture;
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
import info.matchingservice.fixture.match.ProfileMatchesForTesting;
import info.matchingservice.fixture.security.MatchingRegularRoleAndPermissions;
import info.matchingservice.fixture.security.antoniUser;
import info.matchingservice.fixture.security.antoniUser_Has_MatchingRegularRole;
import info.matchingservice.fixture.security.fransUser;
import info.matchingservice.fixture.security.fransUser_Has_MatchingRegularRole;
import info.matchingservice.fixture.security.gerardUser;
import info.matchingservice.fixture.security.gerardUser_Has_MatchingRegularRole;
import info.matchingservice.fixture.security.michielUser;
import info.matchingservice.fixture.security.michielUser_Has_MatchingRegularRole;
import info.matchingservice.fixture.security.rembrandtUser;
import info.matchingservice.fixture.security.rembrandtUser_Has_MatchingRegularRole;
import info.matchingservice.fixture.supply.TestSupplies;
import info.matchingservice.fixture.supply.TestSupplyProfileElementsPersonProfiles;
import info.matchingservice.fixture.supply.TestSupplyProfiles;
import info.matchingservice.fixture.tag.TagCategoriesFixture;
import info.matchingservice.fixture.tag.TagsFixture;
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
        executionContext.executeChild(this, new fransUser());
        executionContext.executeChild(this, new rembrandtUser());
        executionContext.executeChild(this, new gerardUser());
        executionContext.executeChild(this, new michielUser());
        executionContext.executeChild(this, new antoniUser());
        executionContext.executeChild(this, new MatchingRegularRoleAndPermissions());
        executionContext.executeChild(this, new fransUser_Has_MatchingRegularRole());
        executionContext.executeChild(this, new gerardUser_Has_MatchingRegularRole());
        executionContext.executeChild(this, new rembrandtUser_Has_MatchingRegularRole());
        executionContext.executeChild(this, new michielUser_Has_MatchingRegularRole());
        executionContext.executeChild(this, new antoniUser_Has_MatchingRegularRole());
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
        executeChild(new ProfileMatchesForTesting(), executionContext);

    }

}
