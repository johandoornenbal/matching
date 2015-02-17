package info.matchingservice.fixture;

import info.matchingservice.dom.Profile.ProfileType;

public class TestMatchingProfileTypeRules extends
		ProfileTypeMatchingRuleAbstract {

	@Override
	protected void execute(ExecutionContext executionContext) {
		// TODO Auto-generated method stub
		
		createProfileTypeMatchingRule(
				"regel1",
				ProfileType.PERSON_PROFILE.toString() + "_" + ProfileType.ORGANISATION_PROFILE.toString(),
				10,
				executionContext
				);

	}

}
