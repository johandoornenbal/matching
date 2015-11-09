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
        this.contactPersonHref = personalContact.getContactPerson().getUri();
        this.contactFullName = personalContact.getContactPerson().title();
        this.contactPersonId = personalContact.getContactPerson().getIdAsInt();
        this.contactPersonImageUrl = personalContact.getContactPerson().getImageUrl();
        this.ownerPerson = personalContact.getOwnerPerson().getUri();
        this.trustLevel = personalContact.getTrustLevel().toString();
    }


    //region > contactFullName (property)
    private String contactFullName;

    @MemberOrder(sequence = "1")
    public String getContactFullName() {
        return contactFullName;
    }

    public void setContactFullName(final String contactFullName) {
        this.contactFullName = contactFullName;
    }
    //endregion

    //region > contactPersonImageUrl (property)
    private String contactPersonImageUrl;

    @MemberOrder(sequence = "1")
    public String getContactPersonImageUrl() {
        return contactPersonImageUrl;
    }

    public void setContactPersonImageUrl(final String contactPersonImageUrl) {
        this.contactPersonImageUrl = contactPersonImageUrl;
    }
    //endregion

    //region > contactPersonId (property)
    private Integer contactPersonId;

    @MemberOrder(sequence = "1")
    public Integer getContactPersonId() {
        return contactPersonId;
    }

    public void setContactPersonId(final Integer contactPersonId) {
        this.contactPersonId = contactPersonId;
    }
    //endregion

    //region > contactPersonHref (property)
    private String contactPersonHref;

    @MemberOrder(sequence = "1")
    public String getContactPersonHref() {
        return contactPersonHref;
    }

    public void setContactPersonHref(final String contactPersonHref) {
        this.contactPersonHref = contactPersonHref;
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
