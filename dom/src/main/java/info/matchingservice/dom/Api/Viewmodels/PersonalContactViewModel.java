package info.matchingservice.dom.Api.Viewmodels;

import info.matchingservice.dom.Actor.PersonalContact;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Nature;

/**
 * Created by jodo on 02/11/15.
 */
@DomainObject(nature = Nature.VIEW_MODEL)
public class PersonalContactViewModel extends ApiAbstractViewModel {

    public PersonalContactViewModel(){}

    public PersonalContactViewModel(
            final PersonalContact personalContact
    ){
        super(personalContact);
        this.contactPerson = personalContact.getContactPerson().getUri();
        this.ownerPerson = personalContact.getOwnerPerson().getUri();
        this.trustLevel = personalContact.getTrustLevel().toString();
    }

    //region > contactPerson (property)
    private String contactPerson;

    @MemberOrder(sequence = "1")
    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(final String contactPerson) {
        this.contactPerson = contactPerson;
    }
    //endregion

    //region > ownerPerson (property)
    private String ownerPerson;

    @MemberOrder(sequence = "1")
    public String getOwnerPerson() {
        return ownerPerson;
    }

    public void setOwnerPerson(final String ownerPerson) {
        this.ownerPerson = ownerPerson;
    }
    //endregion

    //region > trustLevel (property)
    private String trustLevel;

    @MemberOrder(sequence = "1")
    public String getTrustLevel() {
        return trustLevel;
    }

    public void setTrustLevel(final String trustLevel) {
        this.trustLevel = trustLevel;
    }
    //endregion



}
