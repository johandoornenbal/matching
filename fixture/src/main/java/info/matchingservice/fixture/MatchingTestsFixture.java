package info.matchingservice.fixture;

import info.matchingservice.fixture.actor.PersonForAntoni;
import info.matchingservice.fixture.actor.PersonForFrans;
import info.matchingservice.fixture.actor.PersonForGerard;
import info.matchingservice.fixture.actor.PersonForMichiel;
import info.matchingservice.fixture.actor.PersonForRembrandt;
import info.matchingservice.fixture.actor.ProfileForFrans;
import info.matchingservice.fixture.actor.ProfileForGerard;
import info.matchingservice.fixture.actor.ProfileForRembrandt;
import info.matchingservice.fixture.actor.RolesForAntoni;
import info.matchingservice.fixture.actor.RolesForFrans;
import info.matchingservice.fixture.actor.RolesForGerard;
import info.matchingservice.fixture.actor.RolesForMichiel;
import info.matchingservice.fixture.actor.RolesForRembrandt;
import info.matchingservice.fixture.need.NeedForFrans;
import info.matchingservice.fixture.need.NeedForRembrandt;
import info.matchingservice.fixture.need.VacanciesForFrans;
import info.matchingservice.fixture.need.VacanciesForRembrandt;
import info.matchingservice.fixture.need.VacancyProfileElementsForFrans;
import info.matchingservice.fixture.need.VacancyProfileElementsForRembrandt;

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
        executeChild(new VacancyProfileElementsForFrans(), executionContext);
        executeChild(new VacancyProfileElementsForRembrandt(), executionContext);
    }

}
