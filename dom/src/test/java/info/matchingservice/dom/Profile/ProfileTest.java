package info.matchingservice.dom.Profile;


import org.junit.Test;

import info.matchingservice.dom.AbstractBeanPropertiesTest;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.DemandForTesting;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.DemandSupply.SupplyForTesting;
import info.matchingservice.dom.PojoTester.FilterSet;
import info.matchingservice.dom.Rules.ProfileTypeMatchingRule;
import info.matchingservice.dom.Rules.ProfileTypeMatchingRuleForTesting;
import static org.junit.Assert.*;

public class ProfileTest {
	
	public static class BeanProperties extends AbstractBeanPropertiesTest {

        @Test
        public void test() {
            final Profile pojo = new Profile();
            newPojoTester()
                    .withFixture(pojos(Demand.class, DemandForTesting.class))
                    .withFixture(pojos(Supply.class, SupplyForTesting.class))
                    .withFixture(pojos(ProfileTypeMatchingRule.class, ProfileTypeMatchingRuleForTesting.class))
                    .exercise(pojo, FilterSet.excluding("timeStamp"));
        }

    }

    public static class ActionHideChosenProfileMatch {

        @Test
        public void hideChosenProfileMatchTest() {
            final Profile profile = new Profile();
            profile.setDemandOrSupply(DemandOrSupply.DEMAND);
            assertFalse(profile.hideChosenProfileMatch());
            profile.setDemandOrSupply(DemandOrSupply.SUPPLY);
            assertTrue(profile.hideChosenProfileMatch());
        }

    }

}
