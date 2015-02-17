package info.matchingservice.dom.Rules;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;

import info.matchingservice.dom.MatchingDomainObject;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findProfileTypeMatchingRuleUnique", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Rules.ProfileTypeMatchingRule "
                    + "WHERE uniqueRuleName.matches(:uniqueRuleName)")                
})
@DomainObject(editing=Editing.DISABLED)
public class ProfileTypeMatchingRule extends MatchingDomainObject<ProfileTypeMatchingRule> {

	public ProfileTypeMatchingRule() {
		super("uniqueRuleName");
	}
	
	private String uniqueRuleName;
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	public String getUniqueRuleName() {
		return uniqueRuleName;
	}

	public void setUniqueRuleName(String uniqueRuleName) {
		this.uniqueRuleName = uniqueRuleName;
	}
	
	private String canMatchProfileOfType;

	@javax.jdo.annotations.Column(allowsNull = "false")
	public String getCanMatchProfileOfType() {
		return canMatchProfileOfType;
	}

	public void setCanMatchProfileOfType(String canMatchProfileOfType) {
		this.canMatchProfileOfType = canMatchProfileOfType;
	}
	
	private Integer matchingProfileValueThreshold;
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	public Integer getMatchingProfileValueThreshold() {
		return matchingProfileValueThreshold;
	}

	public void setMatchingProfileValueThreshold(
			Integer matchingProfileValueThreshold) {
		this.matchingProfileValueThreshold = matchingProfileValueThreshold;
	}
	
    public String title() {
        return getUniqueRuleName();
    }

}
