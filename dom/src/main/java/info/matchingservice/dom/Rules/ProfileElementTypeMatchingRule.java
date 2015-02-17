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
            name = "findProfileElementTypeMatchingRuleUnique", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Rules.ProfileElementTypeMatchingRule "
                    + "WHERE uniqueRuleName.matches(:uniqueRuleName)")                
})
@DomainObject(editing=Editing.DISABLED)
public class ProfileElementTypeMatchingRule extends MatchingDomainObject<ProfileElementTypeMatchingRule> {

	public ProfileElementTypeMatchingRule() {
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
	
	private String canMatchProfileElementOfType;

	@javax.jdo.annotations.Column(allowsNull = "false")
	public String getCanMatchProfileElementOfType() {
		return canMatchProfileElementOfType;
	}

	public void setCanMatchProfileElementOfType(String canMatchProfileElementOfType) {
		this.canMatchProfileElementOfType = canMatchProfileElementOfType;
	}
	
	private Integer matchingProfileElementValueThreshold;
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	public Integer getMatchingProfileElementValueThreshold() {
		return matchingProfileElementValueThreshold;
	}

	public void setMatchingProfileElementValueThreshold(
			Integer matchingProfileElementValueThreshold) {
		this.matchingProfileElementValueThreshold = matchingProfileElementValueThreshold;
	}
	
    public String title() {
        return getUniqueRuleName();
    }

}
