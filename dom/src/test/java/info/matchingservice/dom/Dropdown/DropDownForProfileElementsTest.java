package info.matchingservice.dom.Dropdown;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.query.Query;

import info.matchingservice.dom.FinderInteraction;
import info.matchingservice.dom.Profile.ProfileElementType;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DropDownForProfileElementsTest {

    info.matchingservice.dom.FinderInteraction finderInteraction;

    DropDownForProfileElements dropDownForProfileElements;

    @Before
    public void setup() {
        dropDownForProfileElements = new DropDownForProfileElements() {

            @Override
            protected <T> T firstMatch(Query<T> query) {
                finderInteraction = new FinderInteraction(query, FinderInteraction.FinderMethod.FIRST_MATCH);
                return null;
            }

            @Override
            protected List<DropDownForProfileElement> allInstances() {
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

    public static class findDropDowns extends DropDownForProfileElementsTest {

        @Test
        public void happyCase() {

            final ProfileElementType profileElementType = ProfileElementType.EDUCATION_LEVEL;

            dropDownForProfileElements.findDropDowns("somestring", profileElementType);

            assertThat(finderInteraction.getFinderMethod(), is(FinderInteraction.FinderMethod.ALL_MATCHES));
            //            assertThat(finderInteraction.getResultType(), IsisMatchers.classEqualTo(Profile.class));
            assertThat(finderInteraction.getQueryName(), is("matchDropDownByKeyWord"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("value"), is((Object) "somestring"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("type"), is((Object) profileElementType));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(2));
        }

    }


} 
