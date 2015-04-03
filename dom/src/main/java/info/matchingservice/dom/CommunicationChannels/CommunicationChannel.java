package info.matchingservice.dom.CommunicationChannels;

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.MatchingDomainObject;
import org.apache.isis.applib.annotation.DomainObject;

import javax.jdo.annotations.Column;

@DomainObject
public abstract class CommunicationChannel extends
		MatchingDomainObject<CommunicationChannel> {





	public CommunicationChannel(String keyProperties) {
		super(keyProperties);
		// TODO Auto-generated constructor stub
	}



	// kan ook met actor.
//	private Actor actor;
//
//	@Column(allowsNull = "false")
//	public Actor getActor() {
//		return actor;
//	}
//
//
//	public void setActor(final Actor actor) {
//		this.actor = actor;
//	}


	private Person person;

	@Column(allowsNull = "false")
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	private CommunicationChannelType type;

	@Column(allowsNull = "false")
	public CommunicationChannelType getType() {
		return type;
	}

	public void setType(CommunicationChannelType type) {
		this.type = type;
	}
}
