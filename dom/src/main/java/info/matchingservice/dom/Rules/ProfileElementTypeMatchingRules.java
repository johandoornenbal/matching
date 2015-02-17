package info.matchingservice.dom.Rules;

import info.matchingservice.dom.MatchingDomainService;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;

@DomainService(nature=NatureOfService.DOMAIN)
public class ProfileElementTypeMatchingRules extends MatchingDomainService<ProfileElementTypeMatchingRule> {

	public ProfileElementTypeMatchingRules() {
		super(ProfileElementTypeMatchingRules.class, ProfileElementTypeMatchingRule.class);
	}
	
	public ProfileElementTypeMatchingRule createProfileElementTypeMatchingRule(
			final String uniqueRuleName,
			final String canMatchProfileElementOfType,
			final Integer matchingProfileElementValueThreshold
			){
		final ProfileElementTypeMatchingRule newRule = newTransientInstance(ProfileElementTypeMatchingRule.class);
		newRule.setUniqueRuleName(uniqueRuleName);
		newRule.setCanMatchProfileElementOfType(canMatchProfileElementOfType);
		newRule.setMatchingProfileElementValueThreshold(matchingProfileElementValueThreshold);
		persist(newRule);
		return newRule;
	}
	
	public ProfileElementTypeMatchingRule findProfileElementTypeMatchingRule(String uniqueRuleName){
        QueryDefault<ProfileElementTypeMatchingRule> query = 
                QueryDefault.create(
                		ProfileElementTypeMatchingRule.class, 
                    "findProfileElementTypeMatchingRuleUnique", 
                    "uniqueRuleName", uniqueRuleName);
        return container.firstMatch(query);
	}
	
	@Inject
	 private DomainObjectContainer container;

}
