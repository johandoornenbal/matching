package info.matchingservice.fixture;

import info.matchingservice.dom.Config;
import info.matchingservice.dom.Configs;
import info.matchingservice.dom.Rules.ProfileTypeMatchingRule;
import info.matchingservice.dom.Rules.ProfileTypeMatchingRules;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class ConfigAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected Config createConfig (
    		ProfileTypeMatchingRule profileTypeMatchingRule,
            ExecutionContext executionContext
            ) {
        Config newConfig = configs.createConfig(profileTypeMatchingRule);
                       
        return executionContext.add(this, newConfig);
    }
    
    
    //region > injected services
    @javax.inject.Inject
    private Configs configs;

}