package info.matchingservice.dom.CommunicationChannels;


import java.util.List;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Actor.Person;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(repositoryFor = Email.class, nature=NatureOfService.VIEW)
public class Emails extends MatchingDomainService<Email> {

	public Emails() {
		super(Emails.class, Email.class);
	}
	
	public List<Email> allEmails(){
		return allInstances();
	}
	
	@Action(semantics=SemanticsOf.SAFE)
	@CollectionLayout(render=RenderType.EAGERLY)
	public List<Email> findEmail(Person person){
		return allMatches("findByPerson", "personIsearchfor", person);
	}
	
	@Programmatic
	public Email createEmail(
			@ParameterLayout(named="email")
			final String string, 
			final Person person){
		
		final Email email = newTransientInstance(Email.class);
		
		email.setEmail(string);
		email.setPerson(person);
		
		persist(email);
		return email;
		
	}




	

}
