package info.matchingservice.fixture.demand;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Tags.Tags;
import info.matchingservice.fixture.actor.PersonForFrans;
import info.matchingservice.fixture.tag.TagCategoriesFixture;
import info.matchingservice.fixture.tag.TagsFixture;

import javax.inject.Inject;

public class PassionTagsForFrans extends PassionTagsAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForFrans(), executionContext);
        executeChild(new DemandsForFrans(), executionContext);
        executeChild(new TagCategoriesFixture(), executionContext);
        executeChild(new TagsFixture(), executionContext);
        
        
        createTagHolder(
                persons.findPersons("Hals*").get(0).getMyDemands().last().getDemandProfiles().last().getProfileElement().first(), 
                "zeilschepen", 
                executionContext
                );
        createTagHolder(
                persons.findPersons("Hals*").get(0).getMyDemands().last().getDemandProfiles().last().getProfileElement().first(), 
                "schilderen", 
                executionContext
                );
    }
    
    @Inject
    private Persons persons;
    
    @Inject
    Tags tags;
    
    
}
