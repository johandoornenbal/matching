package nl.yodo.fixture;

import org.apache.isis.applib.fixtures.LogonFixture;
import org.apache.isis.applib.fixtures.SwitchUserFixture;

public class YodoSwitchToJohanLogon extends SwitchUserFixture {
    
    public YodoSwitchToJohanLogon() {
        super("Johan", "Socrates-User");
    }

}
