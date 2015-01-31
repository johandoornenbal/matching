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

import static org.junit.Assert.*;

import org.junit.Test;

public class TagCategoryTest {
    
    @Test
    public void testEquals(){
        TagCategory a = new TagCategory();
        TagCategory b = new TagCategory();
        TagCategory c = new TagCategory();
        TagCategory d = new TagCategory();
        Tag f = new Tag();
        a.setTagCategoryDescription("test");
        b.setTagCategoryDescription("test");
        c.setTagCategoryDescription("tEsT");
        d.setTagCategoryDescription("1test");
        assertEquals(a, b);
        assertEquals(b, a);
        assertEquals(a, c);
        assertFalse(a.equals(d));
        b=null;
        assertFalse(a.equals(b));
        assertFalse(a.equals(f));
    }
    
}