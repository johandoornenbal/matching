package info.matchingservice.fixture;

import info.matchingservice.dom.Rules.ProfileTypeMatchingRules;


public class TestConfig extends ConfigAbstract {

	@Override
	protected void execute(ExecutionContext executionContext) {
		
		//preqs
        executeChild(new TestMatchingProfileTypeRules(), executionContext);
		
		createConfig(profileTypeMatchingRules.findProfileTypeMatchingRule("regel1"), executionContext);

	}
	
	@javax.inject.Inject
	ProfileTypeMatchingRules profileTypeMatchingRules;

}
