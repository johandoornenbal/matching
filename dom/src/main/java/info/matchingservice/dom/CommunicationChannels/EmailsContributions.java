package info.matchingservice.dom.CommunicationChannels;


import javax.inject.Inject;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Actor.Person;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;

@DomainService(nature=NatureOfService.VIEW_CONTRIBUTIONS_ONLY)
public class EmailsContributions extends MatchingDomainService<Email> {

	public EmailsContributions() {
		super(EmailsContributions.class, Email.class);
	}
	
	public Email createEmail(
			@ParameterLayout(named="emaial")
			final String string,
			final Person person){
		
		return emails.createEmail(string, person);
	}
	
	@Inject
	Emails emails;



}
