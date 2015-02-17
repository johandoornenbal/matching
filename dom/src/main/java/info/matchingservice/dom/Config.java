package info.matchingservice.dom;

import info.matchingservice.dom.Rules.ProfileTypeMatchingRule;

import javax.inject.Singleton;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)

@Singleton
public class Config extends MatchingDomainObject<Config> {

	public Config() {
		super("demoFixturesLoaded");
	}
	
	private boolean demoFixturesLoaded;
	
	@javax.jdo.annotations.Column(allowsNull = "true")
	public boolean getDemoFixturesLoaded() {
		return demoFixturesLoaded;
	}

	public void setDemoFixturesLoaded(boolean demoFixturesLoaded) {
		this.demoFixturesLoaded = demoFixturesLoaded;
	}
	
	private ProfileTypeMatchingRule profileTypeMatchingRule;
	
	@javax.jdo.annotations.Column(allowsNull = "true")
	public ProfileTypeMatchingRule getProfileTypeMatchingRule() {
		return profileTypeMatchingRule;
	}

	public void setProfileTypeMatchingRule(ProfileTypeMatchingRule profileTypeMatchingRule) {
		this.profileTypeMatchingRule = profileTypeMatchingRule;
	}

}
