package info.matchingservice.dom.CommunicationChannels;

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.MatchingDomainObject;

import org.apache.isis.applib.annotation.DomainObject;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.VersionStrategy;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@javax.jdo.annotations.Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
@DomainObject(autoCompleteRepository=CommunicationChannels.class, autoCompleteAction = "autoComplete")
public abstract class CommunicationChannel extends
		MatchingDomainObject<CommunicationChannel> {

	public CommunicationChannel(String keyProperties) {
		super("person, type");
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
