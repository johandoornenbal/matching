package info.matchingservice.dom;

import info.matchingservice.dom.Rules.ProfileTypeMatchingRule;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

@DomainService(repositoryFor=Config.class, nature=NatureOfService.VIEW)
public class Configs extends MatchingDomainService<Config> {

	public Configs() {
		super(Configs.class, Config.class);
	}
	
	public List<Config> allConfigs(){
		return container.allInstances(Config.class);
	}
	
	public Config createConfig(
			final ProfileTypeMatchingRule profileTypeMatchingRule
			){
		Config newConfig = newTransientInstance(Config.class);
		newConfig.setDemoFixturesLoaded(true);
		newConfig.setProfileTypeMatchingRule(profileTypeMatchingRule);
		persist(newConfig);
		return newConfig;
	}
	
	public String validateCreateConfig(
			final ProfileTypeMatchingRule profileTypeMatchingRule
			){
		if (!this.allConfigs().isEmpty()){
			return "CONFIG_ONLY_ONCE";
		}
		return null;
	}
	
	@Inject
    DomainObjectContainer container;

}
