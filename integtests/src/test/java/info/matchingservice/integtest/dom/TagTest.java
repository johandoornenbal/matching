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
package info.matchingservice.integtest.dom;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import info.matchingservice.dom.Tags.Tag;
import info.matchingservice.dom.Tags.TagCategories;
import info.matchingservice.dom.Tags.TagCategory;
import info.matchingservice.dom.Tags.Tags;
import info.matchingservice.integtest.MatchingIntegrationTest;

public class TagTest extends MatchingIntegrationTest {
    
    @Inject
    TagCategories tagCategories;
    Tags tags;
    
    public static class newTagCategory extends TagTest {
        
        TagCategory tagCategory1;
        TagCategory tagCategory2;
        Tag tag1;
        Tag tag2;
        
        private static String LOWERCASE = "test";
        private static String UPPER_AND_LOWERCASE = "tEsT";
        
        @Before
        public void setUp() throws Exception{
            tagCategory1=tagCategories.newTagCategory(UPPER_AND_LOWERCASE);
            tagCategory2=tagCategories.newTagCategory(LOWERCASE);
            tag1 = new Tag();
            tag2 = new Tag();
            tag1.setTagDescription(UPPER_AND_LOWERCASE);
            tag1.setTagCategory(tagCategory1);
            tag2.setTagDescription(LOWERCASE);
            tag2.setTagCategory(tagCategory1);
        }
       
        @Test
        public void caseToLowerDescriptionTest() throws Exception {
            assertTrue(tagCategory1.getTagCategoryDescription().equals(LOWERCASE));
            assertFalse(tagCategory1.getTagCategoryDescription().equals(UPPER_AND_LOWERCASE));
            assertEquals(tagCategory1, tagCategory2);

            assertEquals(tag1, tag2);
        }      
    }

}
