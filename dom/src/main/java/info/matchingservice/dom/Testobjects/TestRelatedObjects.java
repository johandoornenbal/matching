package info.matchingservice.dom.Testobjects;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Hidden;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Actor.Person;
@DomainService(menuOrder = "10", repositoryFor = TestRelatedObject.class)
public class TestRelatedObjects extends MatchingDomainService<TestRelatedObject> {

    public TestRelatedObjects() {
        super(TestRelatedObjects.class, TestRelatedObject.class);
    }
    
    @Hidden
    public TestRelatedObject newTest(
            final String testveld,
            final Person owner
            ){
        final TestRelatedObject test = newTransientInstance(TestRelatedObject.class);
        test.setTestVeld(testveld);
        test.setOwnerPerson(owner);
        persist(test);
        return test;
    }
    
}
