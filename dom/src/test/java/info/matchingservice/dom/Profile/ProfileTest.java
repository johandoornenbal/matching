package info.matchingservice.dom.Profile;


import info.matchingservice.dom.AbstractBeanPropertiesTest;
import info.matchingservice.dom.PojoTester.FilterSet;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.DemandForTesting;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.DemandSupply.SupplyForTesting;
import info.matchingservice.dom.Rules.ProfileTypeMatchingRule;
import info.matchingservice.dom.Rules.ProfileTypeMatchingRuleForTesting;


import org.junit.Test;


public class ProfileTest {
	
	public static class BeanProperties extends AbstractBeanPropertiesTest {

        @Test
        public void test() {
            final Profile pojo = new Profile();
            newPojoTester()
                    .withFixture(pojos(Demand.class, DemandForTesting.class))
                    .withFixture(pojos(Supply.class, SupplyForTesting.class))
                    .withFixture(pojos(ProfileTypeMatchingRule.class, ProfileTypeMatchingRuleForTesting.class))
                    .exercise(pojo, FilterSet.excluding("uniqueItemId"));
        }

    }

}
