package info.matchingservice.dom.Match;

import info.matchingservice.dom.AbstractBeanPropertiesTest;
import info.matchingservice.dom.PojoTester.FilterSet;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileForTesting;

import org.junit.Test;

public class ProfileComparisonTest {
	
	public static class BeanProperties extends AbstractBeanPropertiesTest {
		
        @Test
        public void test() {
            final ProfileComparison pojo = new ProfileComparison();
            newPojoTester()
                    .withFixture(pojos(Profile.class, ProfileForTesting.class))
                    .exercise(pojo, FilterSet.excluding("uniqueItemId"));
        }

    }

}
