package info.matchingservice.fixture;

import info.matchingservice.dom.Rules.ProfileTypeMatchingRule;
import info.matchingservice.dom.Rules.ProfileTypeMatchingRules;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class ProfileTypeMatchingRuleAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected ProfileTypeMatchingRule createProfileTypeMatchingRule (
    		String uniqueRuleName,
    		String canMatchProfileOfType,
    		Integer matchingProfileValueThreshold,
            ExecutionContext executionContext
            ) {
    	ProfileTypeMatchingRule newRule = profileTypeMatchingRules.createProfileTypeMatchingRule(uniqueRuleName, canMatchProfileOfType, matchingProfileValueThreshold);
                       
        return executionContext.add(this, newRule);
    }
    
    
    //region > injected services
    @javax.inject.Inject
    private ProfileTypeMatchingRules profileTypeMatchingRules;

}