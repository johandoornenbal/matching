package info.matchingservice.dom.CommunicationChannels;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
//@javax.jdo.annotations.DatastoreIdentity(
//        strategy = IdGeneratorStrategy.NATIVE,
//        column = "id")
//@javax.jdo.annotations.Discriminator(
//        strategy = DiscriminatorStrategy.CLASS_NAME,
//        column = "discriminator")
//@javax.jdo.annotations.Version(
//		strategy = VersionStrategy.VERSION_NUMBER,
//		column = "version")
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findByPerson", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.CommunicationChannels.Email "
                    + "WHERE person == :person")
})
public class Email extends CommunicationChannel {




	public Email() {
		super("email, person");
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

			final @ParameterLayout(named="Email")String address)	{

		setEmail(address);
		return this;
	}

	public String default0UpdateEmail(){
		return this.getEmail();
	}




}
