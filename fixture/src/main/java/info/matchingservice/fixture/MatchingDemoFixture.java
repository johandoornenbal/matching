package info.matchingservice.fixture;

import info.matchingservice.assessment.AssessmentsForFransFixture;
import info.matchingservice.assessment.AssessmentsForRembrandtFixture;
import info.matchingservice.dropdown.ProfileElementDropDownsFixture;
import info.matchingservice.fixture.actor.OrganisationsForFrans;
import info.matchingservice.fixture.actor.OrganisationsForMichel;
import info.matchingservice.fixture.actor.PersonalContactsForFrans;
import info.matchingservice.fixture.actor.PersonalContactsForMichiel;
import info.matchingservice.fixture.actor.PersonalContactsForRembrandt;
import info.matchingservice.fixture.actor.RolesForAntoni;
import info.matchingservice.fixture.actor.RolesForFrans;
import info.matchingservice.fixture.actor.RolesForGerard;
import info.matchingservice.fixture.actor.RolesForMichiel;
import info.matchingservice.fixture.actor.RolesForRembrandt;
import info.matchingservice.fixture.actor.TestPersons;
import info.matchingservice.fixture.demand.BrancheTagsForFrans;
import info.matchingservice.fixture.demand.DemandProfileElementsForFrans;
import info.matchingservice.fixture.demand.DemandProfileElementsForMichiel;
import info.matchingservice.fixture.demand.DemandProfileElementsForRembrandt;
import info.matchingservice.fixture.demand.DemandProfilesForFrans;
import info.matchingservice.fixture.demand.DemandProfilesForMichiel;
import info.matchingservice.fixture.demand.DemandProfilesForRembrandt;
import info.matchingservice.fixture.demand.DemandsForFrans;
import info.matchingservice.fixture.demand.DemandsForMichiel;
import info.matchingservice.fixture.demand.DemandsForRembrandt;
import info.matchingservice.fixture.supply.BrancheTagsForGerard;
import info.matchingservice.fixture.supply.SuppliesForAntoni;
import info.matchingservice.fixture.supply.SuppliesForFrans;
import info.matchingservice.fixture.supply.SuppliesForGerard;
import info.matchingservice.fixture.supply.SuppliesForMichiel;
import info.matchingservice.fixture.supply.SuppliesForRembrandt;
import info.matchingservice.fixture.supply.SupplyProfileElementsForAntoni;
import info.matchingservice.fixture.supply.SupplyProfileElementsForFrans;
import info.matchingservice.fixture.supply.SupplyProfileElementsForGerard;
import info.matchingservice.fixture.supply.SupplyProfileElementsForMichiel;
import info.matchingservice.fixture.supply.SupplyProfileElementsForRembrandt;
import info.matchingservice.fixture.supply.SupplyProfilesForAntoni;
import info.matchingservice.fixture.supply.SupplyProfilesForFrans;
import info.matchingservice.fixture.supply.SupplyProfilesForGerard;
import info.matchingservice.fixture.supply.SupplyProfilesForMichiel;
import info.matchingservice.fixture.supply.SupplyProfilesForRembrandt;
import info.matchingservice.fixture.tag.TagCategoriesFixture;
import info.matchingservice.fixture.tag.TagsFixture;
import info.matchingservice.fixture.tag.TagsForAntoni;
import info.matchingservice.fixture.tag.TagsForFrans;
import info.matchingservice.fixture.tag.TagsForMichiel;

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
        executeChild(new ProfileElementDropDownsFixture(), executionContext);
        executeChild(new TestPersons(), executionContext);
        executeChild(new RolesForFrans(), executionContext);
        executeChild(new RolesForGerard(), executionContext);
        executeChild(new RolesForAntoni(), executionContext);
        executeChild(new RolesForMichiel(), executionContext);
        executeChild(new RolesForRembrandt(), executionContext);
        executeChild(new DemandsForFrans(), executionContext);
        executeChild(new DemandsForRembrandt(), executionContext);
        executeChild(new DemandsForMichiel(), executionContext);
        executeChild(new DemandProfilesForRembrandt(), executionContext);
        executeChild(new DemandProfilesForFrans(), executionContext);
        executeChild(new DemandProfilesForMichiel(), executionContext);
        executeChild(new DemandProfileElementsForFrans(), executionContext);
        executeChild(new DemandProfileElementsForRembrandt(), executionContext);
        executeChild(new DemandProfileElementsForMichiel(), executionContext);
        executeChild(new SuppliesForGerard(), executionContext);
        executeChild(new SuppliesForFrans(), executionContext);
        executeChild(new SuppliesForRembrandt(), executionContext);
        executeChild(new SuppliesForAntoni(), executionContext);
        executeChild(new SuppliesForMichiel(), executionContext);
        executeChild(new SupplyProfilesForAntoni(), executionContext);
        executeChild(new SupplyProfilesForGerard(), executionContext);
        executeChild(new SupplyProfilesForFrans(), executionContext);
        executeChild(new SupplyProfilesForRembrandt(), executionContext);
        executeChild(new SupplyProfilesForMichiel(), executionContext);
        executeChild(new SupplyProfileElementsForFrans(), executionContext);
        executeChild(new SupplyProfileElementsForAntoni(), executionContext);        
        executeChild(new SupplyProfileElementsForGerard(), executionContext); 
        executeChild(new SupplyProfileElementsForMichiel(), executionContext); 
        executeChild(new SupplyProfileElementsForRembrandt(), executionContext); 
        executeChild(new OrganisationsForFrans(), executionContext);
        executeChild(new OrganisationsForMichel(), executionContext);
        executeChild(new AssessmentsForRembrandtFixture(), executionContext);
        executeChild(new AssessmentsForFransFixture(), executionContext);
        executeChild(new PersonalContactsForFrans(), executionContext);
        executeChild(new PersonalContactsForRembrandt(), executionContext);
        executeChild(new PersonalContactsForMichiel(), executionContext);
        executeChild(new TagCategoriesFixture(), executionContext);
        executeChild(new TagsFixture(), executionContext);
        executeChild(new TagsForFrans(), executionContext);
        executeChild(new TagsForAntoni(), executionContext);
        executeChild(new TagsForMichiel(), executionContext);
        executeChild(new BrancheTagsForGerard(), executionContext);
        executeChild(new BrancheTagsForFrans(), executionContext);
        
    }

}
