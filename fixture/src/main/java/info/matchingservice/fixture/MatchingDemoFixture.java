package info.matchingservice.fixture;

import info.matchingservice.fixture.need.NeedForFrans;
import info.matchingservice.fixture.need.NeedForRembrandt;
import info.matchingservice.fixture.need.VacanciesForFrans;
import info.matchingservice.fixture.need.VacanciesForRembrandt;
import info.matchingservice.fixture.need.VacancyProfilesForFrans;
import info.matchingservice.fixture.need.VacancyProfilesForRembrandt;
import info.matchingservice.fixture.party.PersonForAntoni;
import info.matchingservice.fixture.party.PersonForFrans;
import info.matchingservice.fixture.party.PersonForGerard;
import info.matchingservice.fixture.party.PersonForMichiel;
import info.matchingservice.fixture.party.PersonForRembrandt;
import info.matchingservice.fixture.party.ProfileForFrans;
import info.matchingservice.fixture.party.ProfileForGerard;
import info.matchingservice.fixture.party.ProfileForRembrandt;
import info.matchingservice.fixture.party.RolesForAntoni;
import info.matchingservice.fixture.party.RolesForFrans;
import info.matchingservice.fixture.party.RolesForGerard;
import info.matchingservice.fixture.party.RolesForMichiel;
import info.matchingservice.fixture.party.RolesForRembrandt;

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
        executeChild(new ProfileForFrans(), executionContext);
        executeChild(new ProfileForRembrandt(), executionContext);
        executeChild(new ProfileForGerard(), executionContext);
        executeChild(new NeedForFrans(), executionContext);
        executeChild(new NeedForRembrandt(), executionContext);
        executeChild(new VacanciesForFrans(), executionContext);
        executeChild(new VacanciesForRembrandt(), executionContext);
        executeChild(new VacancyProfilesForFrans(), executionContext);
        executeChild(new VacancyProfilesForRembrandt(), executionContext);
    }

}
