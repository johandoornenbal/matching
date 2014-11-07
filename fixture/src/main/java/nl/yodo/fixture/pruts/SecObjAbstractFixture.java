package nl.yodo.fixture.pruts;

import javax.inject.Inject;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import nl.yodo.dom.pruts.Secobject;
import nl.yodo.dom.pruts.Secobjects;

public abstract class SecObjAbstractFixture extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected Secobject createSecObject(
            String name,
            String ownedBy,
            ExecutionContext executionContext
            ) {
        Secobject secobj = secobjects.newSecobject(name, ownedBy);
        return secobj;
    }
    
    @Inject
    protected Secobjects secobjects;
    
}
