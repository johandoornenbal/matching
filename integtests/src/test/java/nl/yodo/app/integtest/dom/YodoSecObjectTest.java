package nl.yodo.app.integtest.dom;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import nl.yodo.app.integtest.YodoIntegrationTest;
import nl.yodo.dom.pruts.Secobject;
import nl.yodo.dom.pruts.Secobjects;

public class YodoSecObjectTest extends YodoIntegrationTest {
    
    @Inject
    Secobjects secobjects;
    
    public static class TestSecObject extends YodoSecObjectTest {
        
        Secobject t1;
        
        @Test
        public void valuesSet() throws Exception {
            t1=secobjects.allSecobjects().get(0);
            assertThat(t1.getName(), is("TestObject1 van test1"));
            assertThat(t1.getOwnedBy(), is("test1"));
            assertThat(t1.getTestLevelInner(), is("Inner test"));
        }       
    }
    
    public static class NewSecObject extends YodoSecObjectTest {
        
        Secobject t1;
        Secobject t2;
        
        @Before
        public void setUp() throws Exception {
            t1=secobjects.newSecobject("test");
            t1.setOwnedBy("test1");
            t1.setTestLevelInner("test voor innercircle");
            Integer maxindex = secobjects.allSecobjects().size() - 1;
            t2=secobjects.allSecobjects().get(maxindex); 
        }

    
        @Test
        public void valuesSet() throws Exception {
            assertThat(t1.getName(), is("test"));
            assertThat(t1.getOwnedBy(), is("test1"));
            assertThat(t1.getTestLevelInner(), is("test voor innercircle"));
            assertThat(t2.getName(), is("test"));
            assertThat(t2,is(t1));
        }
    }

}
