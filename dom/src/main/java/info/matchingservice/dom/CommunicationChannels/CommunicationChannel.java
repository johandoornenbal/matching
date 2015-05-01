package info.matchingservice.dom.CommunicationChannels;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Title;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.MatchingDomainObject;
import info.matchingservice.dom.MatchingMutableObject;

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
		MatchingMutableObject<CommunicationChannel> {

	public CommunicationChannel(String keyProperties) {
		super("person, type");
	}

	//** deleteCommunicationChannel **//
	@Action(semantics= SemanticsOf.NON_IDEMPOTENT)
	@ActionLayout()
	public Person deleteCommunicationChannel(
			@ParameterLayout(named="confirmDelete")
			@Parameter(optionality= Optionality.OPTIONAL)
			boolean confirmDelete
	){


		container.removeIfNotAlready(this);
		container.informUser("Element verwijderd");
		return getPerson();
	}

	public String validateDeleteCommunicationChannel(boolean confirmDelete) {
		return confirmDelete? null:"CONFIRM_DELETE";
	}
	//-- deleteCommunicationChannel --//

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
	@Title(append = "")
	public CommunicationChannelType getType() {
		return type;
	}

	public void setType(CommunicationChannelType type) {
		this.type = type;
	}

	@javax.inject.Inject
	private DomainObjectContainer container;



}
