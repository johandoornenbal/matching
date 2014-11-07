package info.matchingservice.fixture;

import info.matchingservice.fixture.party.PersonForAntoni;
import info.matchingservice.fixture.party.PersonForFrans;
import info.matchingservice.fixture.party.PersonForGerard;
import info.matchingservice.fixture.party.PersonForMichiel;
import info.matchingservice.fixture.party.PersonForRembrandt;
import info.matchingservice.fixture.party.PersonsTeardown;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class MatchingDemoFixture extends FixtureScript {
    
    public MatchingDemoFixture() {
        super(null, "matchingdemo-fixture");
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
    }

}
