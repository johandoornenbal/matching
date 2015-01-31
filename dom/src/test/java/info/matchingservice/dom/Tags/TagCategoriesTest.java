/*
 *
 *  Copyright 2015 Yodo Int. Projects and Consultancy
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package info.matchingservice.dom.Tags;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import info.matchingservice.dom.FinderInteraction;
import info.matchingservice.dom.FinderInteraction.FinderMethod;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.query.Query;
//import org.apache.isis.core.commons.matchers.IsisMatchers;

public class TagCategoriesTest {

    FinderInteraction finderInteraction;
    
    TagCategories tagCategories;
    
    @Before
    public void setup() {
        
        tagCategories = new TagCategories(){
            @Override
            protected <T> T firstMatch(Query<T> query) {
                finderInteraction = new FinderInteraction(query, FinderMethod.FIRST_MATCH);
                return null;
            }
            @Override
            protected List<TagCategory> allInstances() {
                finderInteraction = new FinderInteraction(null, FinderMethod.ALL_INSTANCES);
                return null;
            }
            @Override
            protected <T> List<T> allMatches(Query<T> query) {
                finderInteraction = new FinderInteraction(query, FinderMethod.ALL_MATCHES);
                return null;
            }  
        };
        
    }
    
    public static class findTagCategoryMatches extends TagCategoriesTest {
        @Test
        public void exactMatchUsesLowerCase() {
            tagCategories.findTagCategoryMatches("tEsT123");
            
            assertThat(finderInteraction.getFinderMethod(), is(FinderMethod.ALL_MATCHES));
//            assertThat(finderInteraction.getResultType(), IsisMatchers.classEqualTo(TagCategory.class));
            assertThat(finderInteraction.getQueryName(), is("tagCategoryMatches"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("tagCategoryDescription"), is((Object)"test123"));
            assertTrue(finderInteraction.getArgumentsByParameterName().get("tagCategoryDescription").equals((Object)"test123"));
            assertFalse(finderInteraction.getArgumentsByParameterName().get("tagCategoryDescription").equals((Object)"tEsT123"));

            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));

        }
    }
    
    public static class findTagCategoryContains extends TagCategoriesTest {
        @Test
        public void matchContainsUsesLowerCase() {
            tagCategories.findTagCategoryContains("test123");
            
            assertThat(finderInteraction.getFinderMethod(), is(FinderMethod.ALL_MATCHES));
//            assertThat(finderInteraction.getResultType(), IsisMatchers.classEqualTo(TagCategory.class));
            assertThat(finderInteraction.getQueryName(), is("tagCategoryContains"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("tagCategoryDescription"), is((Object)"test123"));
            assertTrue(finderInteraction.getArgumentsByParameterName().get("tagCategoryDescription").equals((Object)"test123"));
            assertFalse(finderInteraction.getArgumentsByParameterName().get("tagCategoryDescription").equals((Object)"tEsT123"));
            
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }
    }
    
}
