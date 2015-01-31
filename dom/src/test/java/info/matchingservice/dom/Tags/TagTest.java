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

import org.jmock.auto.Mock;
import org.junit.Rule;
import org.junit.Test;

import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2.Mode;

public class TagTest {
    
    @Rule
    public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(Mode.INTERFACES_AND_CLASSES);
    
    @Mock
    private TagCategory tagCategory;
    
    @Mock
    private TagCategory differentTagCategory;
    
    @Test
    public void testEquals(){
        Tag a = new Tag();
        Tag b = new Tag();
        Tag c = new Tag();
        Tag d = new Tag(); 
        a.setTagCategory(tagCategory);
        a.setTagDescription("test123");
        b.setTagCategory(tagCategory);
        b.setTagDescription("test123");
        c.setTagCategory(tagCategory);
        c.setTagDescription("tEsT123");
        d.setTagCategory(tagCategory);
        d.setTagDescription("tEsT1234");
        assertEquals(a, b);
        assertEquals(a, c);
        assertFalse(a.equals(d));
        c.setTagCategory(differentTagCategory);
        assertFalse(a.equals(c));
        assertFalse(c.equals(a));
        c.setTagCategory(tagCategory);
        assertEquals(a, c);
        assertEquals(b, c);
    }

}
