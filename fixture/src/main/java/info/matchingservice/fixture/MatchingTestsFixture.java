package info.matchingservice.fixture;

import info.matchingservice.fixture.party.PersonForAntoni;
import info.matchingservice.fixture.party.PersonForFrans;
import info.matchingservice.fixture.party.PersonForGerard;
import info.matchingservice.fixture.party.PersonForMichiel;
import info.matchingservice.fixture.party.PersonForRembrandt;
import info.matchingservice.fixture.party.PersonsTeardown;
import info.matchingservice.fixture.party.RolesForAntoni;
import info.matchingservice.fixture.party.RolesForFrans;
import info.matchingservice.fixture.party.RolesForGerard;
import info.matchingservice.fixture.party.RolesForMichiel;
import info.matchingservice.fixture.party.RolesForRembrandt;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class MatchingTestsFixture extends FixtureScript {
    
    public MatchingTestsFixture() {
        super(null, "matchingtests-fixture");
    }

    @Override
    protected void execute(ExecutionContext executionContext) {
        // prereqs
        executeChild(new PersonsTeardown(), executionContext);
        
        // create
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
    }

}
