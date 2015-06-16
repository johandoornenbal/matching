package info.matchingservice.dom.DemandSupply;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.query.Query;

import info.matchingservice.dom.FinderInteraction;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DemandsTest {


    info.matchingservice.dom.FinderInteraction finderInteraction;

    Demands demands;

    @Before
    public void setup() {
        demands = new Demands() {

            @Override
            protected <T> T firstMatch(Query<T> query) {
                finderInteraction = new FinderInteraction(query, FinderInteraction.FinderMethod.FIRST_MATCH);
                return null;
            }

            @Override
            protected List<Demand> allInstances() {
                finderInteraction = new FinderInteraction(null, FinderInteraction.FinderMethod.ALL_INSTANCES);
                return null;
            }

            @Override
            protected <T> List<T> allMatches(Query<T> query) {
                finderInteraction = new FinderInteraction(query, FinderInteraction.FinderMethod.ALL_MATCHES);
                return null;
            }
        };
    }

    public static class FindDemandByDescription extends DemandsTest {

        @Test
        public void happyCase() {

            String ownedBy = new String();
            String demandDescription = new String();

            demands.findDemandByDescription(demandDescription, ownedBy);

            assertThat(finderInteraction.getFinderMethod(), is(FinderInteraction.FinderMethod.ALL_MATCHES));
            //            assertThat(finderInteraction.getResultType(), IsisMatchers.classEqualTo(Profile.class));
            assertThat(finderInteraction.getQueryName(), is("findDemandByDescription"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("demandDescription"), is((Object) demandDescription));
            assertThat(finderInteraction.getArgumentsByParameterName().get("ownedBy"), is((Object) ownedBy));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(2));


            demands.findDemandByDescription(demandDescription);

            assertThat(finderInteraction.getFinderMethod(), is(FinderInteraction.FinderMethod.ALL_MATCHES));
            //            assertThat(finderInteraction.getResultType(), IsisMatchers.classEqualTo(Profile.class));
            assertThat(finderInteraction.getQueryName(), is("findDemandByDescriptionOnly"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("demandDescription"), is((Object) demandDescription));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }

    }

    public static class FindDemandByUniqueItemId extends DemandsTest {

        @Test
        public void happyCase() {

            UUID uuid = UUID.randomUUID();;
            demands.findDemandByUniqueItemId(uuid);

            assertThat(finderInteraction.getFinderMethod(), is(FinderInteraction.FinderMethod.ALL_MATCHES));
            //            assertThat(finderInteraction.getResultType(), IsisMatchers.classEqualTo(Demand.class));
            assertThat(finderInteraction.getQueryName(), is("findDemandByUniqueItemId"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("uniqueItemId"), is((Object) uuid));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }
    }




}
