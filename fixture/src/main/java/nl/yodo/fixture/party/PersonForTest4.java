package nl.yodo.fixture.party;

import nl.yodo.dom.TrustLevel;

public class PersonForTest4 extends YodoPersonAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        createYodoPerson(
                "444", 
                "Tester", 
                "de", 
                "Test4", 
                "test4",
                "test3",
                TrustLevel.INNER_CIRCLE, //user test3 takes user test4 as a personalcontact at INNER_CIRCLE
                "test1",
                TrustLevel.OUTER_CIRCLE, //user test1 takes user test4 as a personalcontact at OUTER_CIRCLE
                executionContext);
    }

}
