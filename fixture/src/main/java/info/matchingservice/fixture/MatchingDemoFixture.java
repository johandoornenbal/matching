package info.matchingservice.fixture;

import info.matchingservice.fixture.Dropdowns.DropDownHbo;
import info.matchingservice.fixture.Dropdowns.DropDownMbo;
import info.matchingservice.fixture.Dropdowns.DropDownWo;
import info.matchingservice.fixture.ProfileElementConfig.ProfileElementChoicesForDemand;
import info.matchingservice.fixture.ProfileElementConfig.ProfileElementChoicesForSupply;
import info.matchingservice.fixture.ProvidedServices.ServicesForTesting;
import info.matchingservice.fixture.ProvidedServices.ServicesOccurrencesForTesting;
import info.matchingservice.fixture.actor.TestOrganisations;
import info.matchingservice.fixture.actor.TestPersonalContacts;
import info.matchingservice.fixture.actor.TestPersons;
import info.matchingservice.fixture.actor.TestRoles;
import info.matchingservice.fixture.assessment.AssessmentsForFransFixture;
import info.matchingservice.fixture.assessment.AssessmentsForRembrandtFixture;
import info.matchingservice.fixture.communicationChannels.TestAddress;
import info.matchingservice.fixture.communicationChannels.TestEmails;
import info.matchingservice.fixture.communicationChannels.TestPhone;
import info.matchingservice.fixture.demand.*;
import info.matchingservice.fixture.howdoido.*;
import info.matchingservice.fixture.match.ProfileMatchesForTesting;
import info.matchingservice.fixture.security.*;
import info.matchingservice.fixture.supply.TestSupplies;
import info.matchingservice.fixture.supply.TestSupplyProfileElementsPersonProfiles;
import info.matchingservice.fixture.supply.TestSupplyProfiles;
import info.matchingservice.fixture.tag.*;
import org.apache.isis.applib.fixturescripts.FixtureScript;

public class MatchingDemoFixture extends FixtureScript {
    
    public MatchingDemoFixture() {
        super(null, "matchingdemo-fixture");
    }

    @Override
    protected void execute(ExecutionContext executionContext) {
        // prereqs
//        executeChild(new TeardownFixture(), executionContext);
        
        // create
        executionContext.executeChild(this, new BasicTopCategoryFixtures());
        executionContext.executeChild(this, new BasicSubCategoryFixtures());
        executionContext.executeChild(this, new MoreBasicSubCategoryFixtures());
        executionContext.executeChild(this, new EvenMoreBasicSubCategoryFixtures());
        executionContext.executeChild(this, new BasicUserFixtures());
        executionContext.executeChild(this, new BasicTemplateFixtures());
        executionContext.executeChild(this, new BasicQuestionFixtures());
        executionContext.executeChild(this, new BasicRequestFixtures());
        executionContext.executeChild(this, new BasicFormFixtures());
        executionContext.executeChild(this, new BasicRatingFixtures());

        executionContext.executeChild(this, new fransUser());
        executionContext.executeChild(this, new rembrandtUser());
        executionContext.executeChild(this, new gerardUser());
        executionContext.executeChild(this, new michielUser());
        executionContext.executeChild(this, new antoniUser());
        executionContext.executeChild(this, new jeanneUser());
        executionContext.executeChild(this, new xtalusAdminUser());
        executionContext.executeChild(this, new MatchingRegularRoleAndPermissions());
        executionContext.executeChild(this, new MatchingAdminRoleAndPermissions());
        executionContext.executeChild(this, new fransUser_Has_MatchingRegularRole());
        executionContext.executeChild(this, new gerardUser_Has_MatchingRegularRole());
        executionContext.executeChild(this, new rembrandtUser_Has_MatchingRegularRole());
        executionContext.executeChild(this, new michielUser_Has_MatchingRegularRole());
        executionContext.executeChild(this, new antoniUser_Has_MatchingRegularRole());
        executionContext.executeChild(this, new jeanneUser_Has_MatchingRegularRole());
        executionContext.executeChild(this, new xtalusAdminUser_Has_MatchingAdminRole());

        executionContext.executeChild(this, new howdoidoUsers());
        executionContext.executeChild(this, new howdoidoUser1_Has_MatchingRegularRole());
        executionContext.executeChild(this, new howdoidoUser2_Has_MatchingRegularRole());
        executionContext.executeChild(this, new howdoidoUser3_Has_MatchingRegularRole());
        executionContext.executeChild(this, new howdoidoUser4_Has_MatchingRegularRole());
        executionContext.executeChild(this, new howdoidoUser5_Has_MatchingRegularRole());

    	executeChild(new TestMatchingProfileTypeRules(), executionContext);
    	executeChild(new TestConfig(), executionContext);
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
        executeChild(new TestEmails(), executionContext);
        executeChild(new TestPhone(), executionContext);
        executeChild(new TestAddress(), executionContext);
        executeChild(new DropDownHbo(), executionContext);
        executeChild(new DropDownMbo(), executionContext);
        executeChild(new DropDownWo(), executionContext);
        executeChild(new ProfileElementChoicesForDemand(), executionContext);
        executeChild(new ProfileElementChoicesForSupply(), executionContext);
        executeChild(new ServicesForTesting(), executionContext);
        executeChild(new ServicesOccurrencesForTesting(), executionContext);

    }

}
