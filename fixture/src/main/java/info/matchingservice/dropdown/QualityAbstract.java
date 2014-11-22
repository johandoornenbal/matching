package info.matchingservice.dropdown;

import info.matchingservice.dom.Dropdown.Quality;
import info.matchingservice.dom.Dropdown.Qualities;


import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class QualityAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected Quality createQuality (
            String keyWord,
            ExecutionContext executionContext
            ) {
        Quality newQuality = qualities.newQuality(keyWord);
        
        return executionContext.add(this, newQuality);
    }

    //region > injected services
    @javax.inject.Inject
    private Qualities qualities;
    

}