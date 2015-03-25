package info.matchingservice.dom.Profile;

import info.matchingservice.dom.AbstractBeanPropertiesTest;
import info.matchingservice.dom.PojoTester.FilterSet;


import org.junit.Test;

public class ProfileElementTagTest {
	
	public static class BeanProperties extends AbstractBeanPropertiesTest {
		
        @Test
        public void test() {
            final ProfileElementTag pojo = new ProfileElementTag();
            newPojoTester()
                    .withFixture(pojos(Profile.class, ProfileForTesting.class))
                    .exercise(pojo, FilterSet.excluding("uniqueItemId"));
        }

    }

}
