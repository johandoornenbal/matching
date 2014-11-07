package nl.yodo.fixture.pruts;


public class SecObjectsForTest extends SecObjAbstractFixture {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        createSecObject("TestObject1 van test1", "test1", executionContext);
        createSecObject("Nog een TestObject van test1", "test1", executionContext);
        createSecObject("Test van test2", "test2", executionContext);
        createSecObject("Nog een test van test2", "test2", executionContext);
        createSecObject("Test van test3", "test3", executionContext);
        createSecObject("Nog een test van test3", "test3", executionContext);
        createSecObject("Test van test4", "test4", executionContext);
        createSecObject("Nog een test van test4", "test4", executionContext);
    }

}
