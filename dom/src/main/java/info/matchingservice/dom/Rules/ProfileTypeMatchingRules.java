package info.matchingservice.dom.Rules;

import info.matchingservice.dom.MatchingDomainService;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;

@DomainService(nature=NatureOfService.DOMAIN)
public class ProfileTypeMatchingRules extends MatchingDomainService<ProfileTypeMatchingRule> {

	public ProfileTypeMatchingRules() {
		super(ProfileTypeMatchingRules.class, ProfileTypeMatchingRule.class);
	}
	
	public ProfileTypeMatchingRule createProfileTypeMatchingRule(
			final String uniqueRuleName,
			final String canMatchProfileOfType,
			final Integer matchingProfileValueThreshold
			){
		final ProfileTypeMatchingRule newRule = newTransientInstance(ProfileTypeMatchingRule.class);
		newRule.setUniqueRuleName(uniqueRuleName);
		newRule.setCanMatchProfileOfType(canMatchProfileOfType);
		newRule.setMatchingProfileValueThreshold(matchingProfileValueThreshold);
		persist(newRule);
		return newRule;
	}
	
	public ProfileTypeMatchingRule findProfileTypeMatchingRule(String uniqueRuleName){
        QueryDefault<ProfileTypeMatchingRule> query = 
                QueryDefault.create(
                		ProfileTypeMatchingRule.class, 
                    "findProfileTypeMatchingRuleUnique", 
                    "uniqueRuleName", uniqueRuleName);
        return container.firstMatch(query);
	}
	
	@Inject
	 private DomainObjectContainer container;

}
