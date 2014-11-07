package nl.yodo.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import nl.yodo.fixture.party.PersonForTest1;
import nl.yodo.fixture.party.PersonForTest2;
import nl.yodo.fixture.party.PersonForTest3;
import nl.yodo.fixture.party.PersonForTest4;
import nl.yodo.fixture.party.YodoPersonsTeardown;
import nl.yodo.fixture.pruts.SecObjectsForTest;

public class YodoDemoFixture extends FixtureScript {
    
    public YodoDemoFixture() {
        super(null, "yododemo-fixture");
    }

    @Override
    protected void execute(ExecutionContext executionContext) {
        // prereqs
        executeChild(new YodoPersonsTeardown(), executionContext);
        
        // create
        executeChild(new PersonForTest1(), executionContext);
        executeChild(new PersonForTest2(), executionContext);
        executeChild(new PersonForTest3(), executionContext);
        executeChild(new PersonForTest4(), executionContext);
        executeChild(new SecObjectsForTest(), executionContext);
    }

}
