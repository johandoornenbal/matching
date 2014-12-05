package info.matchingservice.fixture;

import info.matchingservice.dropdown.ProfileElementDropDownsFixture;
import info.matchingservice.fixture.actor.PersonForAntoni;
import info.matchingservice.fixture.actor.PersonForFrans;
import info.matchingservice.fixture.actor.PersonForGerard;
import info.matchingservice.fixture.actor.PersonForMichiel;
import info.matchingservice.fixture.actor.PersonForRembrandt;
import info.matchingservice.fixture.actor.RolesForAntoni;
import info.matchingservice.fixture.actor.RolesForFrans;
import info.matchingservice.fixture.actor.RolesForGerard;
import info.matchingservice.fixture.actor.RolesForMichiel;
import info.matchingservice.fixture.actor.RolesForRembrandt;
import info.matchingservice.fixture.demand.DemandProfilesForRembrandt;
import info.matchingservice.fixture.demand.DemandProfilesForFrans;
import info.matchingservice.fixture.demand.DemandsForFrans;
import info.matchingservice.fixture.demand.DemandsForRembrandt;
import info.matchingservice.fixture.supply.SuppliesForAntoni;
import info.matchingservice.fixture.supply.SuppliesForFrans;
import info.matchingservice.fixture.supply.SuppliesForGerard;
import info.matchingservice.fixture.supply.SuppliesForMichiel;
import info.matchingservice.fixture.supply.SuppliesForRembrandt;
import info.matchingservice.fixture.supply.SupplyProfileDropDownsForAntoni;
import info.matchingservice.fixture.supply.SupplyProfilesForAntoni;
import info.matchingservice.fixture.supply.SupplyProfilesForGerard;

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
        executeChild(new PersonForFrans(), executionContext);
        executeChild(new PersonForRembrandt(), executionContext);
        executeChild(new PersonForGerard(), executionContext);
        executeChild(new PersonForMichiel(), executionContext);
        executeChild(new PersonForAntoni(), executionContext);
        executeChild(new RolesForFrans(), executionContext);
        executeChild(new RolesForGerard(), executionContext);
        executeChild(new RolesForAntoni(), executionContext);
        executeChild(new RolesForMichiel(), executionContext);
        executeChild(new RolesForRembrandt(), executionContext);
        executeChild(new DemandsForFrans(), executionContext);
        executeChild(new DemandProfilesForRembrandt(), executionContext);
        executeChild(new DemandsForRembrandt(), executionContext);
        executeChild(new DemandProfilesForFrans(), executionContext);
        executeChild(new SuppliesForGerard(), executionContext);
        executeChild(new SupplyProfilesForGerard(), executionContext);
        executeChild(new SuppliesForAntoni(), executionContext);
        executeChild(new SupplyProfilesForAntoni(), executionContext);
        executeChild(new SupplyProfileDropDownsForAntoni(), executionContext);
        executeChild(new SuppliesForFrans(), executionContext);
        executeChild(new SuppliesForMichiel(), executionContext);
        executeChild(new SuppliesForRembrandt(), executionContext);
    }

}
