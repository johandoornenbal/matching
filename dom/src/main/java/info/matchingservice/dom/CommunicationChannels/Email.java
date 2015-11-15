package info.matchingservice.dom.CommunicationChannels;

import org.apache.isis.applib.annotation.*;
import org.isisaddons.module.security.dom.user.ApplicationUsers;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findByPerson", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.CommunicationChannels.Email "
                    + "WHERE person == :person")
})
public class Email extends CommunicationChannel {


	public Email() {
		//super("email, person");
	}


	
	@Column(allowsNull = "false")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	private String email;


	@Action(semantics = SemanticsOf.IDEMPOTENT)
	@ActionLayout(contributed = Contributed.AS_ACTION)
	public Email updateEmail(

			final @ParameterLayout(named="address")
			@Parameter(regexPattern ="^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}$") String address)	{

		setEmail(address);

		//If email.type is EMAIL_MAIN we need to update the email addres in security module as well
		if (this.getType().equals(CommunicationChannelType.EMAIL_MAIN)){

			applicationUsers.findUserByUsername(getContainer().getUser().getName()).setEmailAddress(address);

		}

		return this;
	}

	public String default0UpdateEmail(){
		return this.getEmail();
	}

	private String ownedBy;

	@Override
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing=Editing.DISABLED)
	@PropertyLayout(hidden= Where.EVERYWHERE)
	public String getOwnedBy() {
		return ownedBy;
	}

	public void setOwnedBy(final String owner) {
		this.ownedBy = owner;
	}


	@Inject
	ApplicationUsers applicationUsers;


}
