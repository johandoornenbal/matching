package info.matchingservice.integtest.dom;

import info.matchingservice.dom.TrustedCircles.TrustedCircleConfig;
import info.matchingservice.dom.TrustedCircles.TrustedCircleConfigRepo;
import info.matchingservice.dom.TrustedCircles.TrustedCircleElement;
import info.matchingservice.dom.TrustedCircles.TrustedCircleElementRepo;
import info.matchingservice.fixture.TeardownFixture;
import info.matchingservice.fixture.actor.TestPersons;
import info.matchingservice.integtest.MatchingIntegrationTest;
import org.apache.isis.applib.Identifier;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import static org.junit.Assert.assertTrue;

/**
 * Created by jodo on 08/11/15.
 */
public class TrustedCirclesTest extends MatchingIntegrationTest {

    @Before
    public void setupData() {
        scenarioExecution().install(new TeardownFixture());
        scenarioExecution().install(new TestPersons());
    }

    @Test
    public void findTrustedCircleElementUnique() throws Exception {

        // given
        TrustedCircleConfig config = trustedCircleConfigRepo.findOrCreateConfig("frans");

        // when
        TrustedCircleElement element = trustedCircleElementRepo.findTrustedCircleElementUnique(
                config, "demands", Identifier.Type.PROPERTY_OR_COLLECTION, "Person");

        // then
        assertTrue(element.getName().equals("demands"));
        assertTrue(element.getTrustedCircleConfig().equals(config));

    }

    @Inject
    private TrustedCircleConfigRepo trustedCircleConfigRepo;

    @Inject
    private TrustedCircleElementRepo trustedCircleElementRepo;

}
