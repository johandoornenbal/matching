package info.matchingservice.dom.Testobjects;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Hidden;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Actor.Person;
@DomainService(menuOrder = "10", repositoryFor = TestSecRelatedObject.class)
public class TestSecRelatedObjects extends MatchingDomainService<TestSecRelatedObject> {

    public TestSecRelatedObjects() {
        super(TestSecRelatedObjects.class, TestSecRelatedObject.class);
    }
    
    @Hidden
    public TestSecRelatedObject newSecTest(
            final String testveld,
            final Person owner
            ){
        final TestSecRelatedObject test = newTransientInstance(TestSecRelatedObject.class);
        test.setTestVeld(testveld);
        test.setOwnerPerson(owner);
        test.setOwnedBy(container.getUser().getName());
        persist(test);
        return test;
    }
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
    
}
