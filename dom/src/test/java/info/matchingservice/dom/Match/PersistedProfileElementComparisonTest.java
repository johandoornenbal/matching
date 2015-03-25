package info.matchingservice.dom.Match;

import info.matchingservice.dom.AbstractBeanPropertiesTest;
import info.matchingservice.dom.PojoTester.FilterSet;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Actor.ActorForTesting;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElement;
import info.matchingservice.dom.Profile.ProfileElementForTesting;
import info.matchingservice.dom.Profile.ProfileForTesting;

import org.junit.Test;

public class PersistedProfileElementComparisonTest {
	
	public static class BeanProperties extends AbstractBeanPropertiesTest {
		
        @Test
        public void test() {
            final PersistedProfileElementComparison pojo = new PersistedProfileElementComparison();
            newPojoTester()
                    .withFixture(pojos(Profile.class, ProfileForTesting.class))
                    .withFixture(pojos(ProfileElement.class, ProfileElementForTesting.class))
                    .withFixture(pojos(Actor.class, ActorForTesting.class))
                    .exercise(pojo, FilterSet.excluding("uniqueItemId"));
        }

    }

}
