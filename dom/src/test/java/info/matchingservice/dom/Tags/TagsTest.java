/**
 *  Copyright 2015 Yodo Int. Projects and Consultancy
 *
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package info.matchingservice.dom.Tags;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.query.Query;

import info.matchingservice.dom.FinderInteraction;
import info.matchingservice.dom.FinderInteraction.FinderMethod;

public class TagsTest {
    
    FinderInteraction finderInteraction;
    
    Tags tags;
    
    @Before
    public void setup() {
        tags = new Tags(){
            @Override
            protected <T> T firstMatch(Query<T> query) {
                finderInteraction = new FinderInteraction(query, FinderMethod.FIRST_MATCH);
                return null;
            }
            @Override
            protected List<Tag> allInstances() {
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

    public static class findTagAndCategoryMatches extends TagsTest {
        @Test
        public void exactMatchUsesLowerCase() {
            tags.findTagMatches("tEsT123");
            
            assertThat(finderInteraction.getFinderMethod(), is(FinderMethod.ALL_MATCHES));
//            assertThat(finderInteraction.getResultType(), IsisMatchers.classEqualTo(TagCategory.class));
            assertThat(finderInteraction.getQueryName(), is("tagMatches"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("tagDescription"), is((Object)"test123"));
            assertTrue(finderInteraction.getArgumentsByParameterName().get("tagDescription").equals((Object)"test123"));
            assertFalse(finderInteraction.getArgumentsByParameterName().get("tagDescription").equals((Object)"tEsT123"));

            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));

        }
    }
 
    public static class findTagAndCategoryContains extends TagsTest {
        @Test
        public void matchContainsUsesLowerCase() {
            tags.findTagContains("tEsT123");
            
            assertThat(finderInteraction.getFinderMethod(), is(FinderMethod.ALL_MATCHES));
//            assertThat(finderInteraction.getResultType(), IsisMatchers.classEqualTo(TagCategory.class));
            assertThat(finderInteraction.getQueryName(), is("tagContains"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("tagDescription"), is((Object)"test123"));
            assertTrue(finderInteraction.getArgumentsByParameterName().get("tagDescription").equals((Object)"test123"));
            assertFalse(finderInteraction.getArgumentsByParameterName().get("tagDescription").equals((Object)"tEsT123"));

            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));

        }
    }
    
}
