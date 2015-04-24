package info.matchingservice.dom.CommunicationChannels;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.MatchingDomainObject;

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
@DomainObject(autoCompleteRepository=CommunicationChannels.class,
		autoCompleteAction = "autoComplete",
		editing = Editing.DISABLED)
public abstract class CommunicationChannel extends
		MatchingDomainObject<CommunicationChannel> {

	public CommunicationChannel(String keyProperties) {
		super("person, type");
	}



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
