package info.matchingservice.dom.Profile;

import info.matchingservice.dom.AbstractBeanPropertiesTest;
import info.matchingservice.dom.PojoTester;
import org.junit.Test;

/**
 * Created by jonathan on 22-4-15.
 */
public class ProfileElementBooleanTest {


    public static class BeanProperties extends AbstractBeanPropertiesTest {

        @Test
        public void test() {
            final ProfileElementBoolean pojo = new ProfileElementBoolean();
            newPojoTester()
                    .withFixture(pojos(Profile.class, ProfileForTesting.class))
                    .exercise(pojo, PojoTester.FilterSet.excluding("uniqueItemId"));


            pojo.setBooleanValue(true);
            pojo.setBooleanValue(false);


            assert(!pojo.getBooleanValue());


        }

    }
}
