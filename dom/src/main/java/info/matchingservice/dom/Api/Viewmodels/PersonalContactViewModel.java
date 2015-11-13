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
        this.contactUri = personalContact.getContactPerson().getUri();
        this.contactFullName = personalContact.getContactPerson().title();
        this.contactId = personalContact.getContactPerson().getIdAsInt();
        this.contactImageUrl = personalContact.getContactPerson().getImageUrl();
        this.owner = personalContact.getOwner().getUri();
        this.ownerFullName = personalContact.getOwner().title();
        this.ownerId = personalContact.getOwner().getIdAsInt();
        this.ownerImageUrl = personalContact.getOwner().getImageUrl();
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

    //region > contactImageUrl (property)
    private String contactImageUrl;

    @MemberOrder(sequence = "1")
    public String getContactImageUrl() {
        return contactImageUrl;
    }

    public void setContactImageUrl(final String contactImageUrl) {
        this.contactImageUrl = contactImageUrl;
    }
    //endregion

    //region > contactId (property)
    private Integer contactId;

    @MemberOrder(sequence = "1")
    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(final Integer contactId) {
        this.contactId = contactId;
    }
    //endregion

    //region > contactUri (property)
    private String contactUri;

    @MemberOrder(sequence = "1")
    public String getContactUri() {
        return contactUri;
    }

    public void setContactUri(final String contactUri) {
        this.contactUri = contactUri;
    }
    //endregion


    //region > owner (property)
    private String owner;

    @MemberOrder(sequence = "1")
    public String getOwner() {
        return owner;
    }

    public void setOwner(final String owner) {
        this.owner = owner;
    }
    //endregion

    //region > ownerFullName (property)
    private String ownerFullName;

    @MemberOrder(sequence = "1")
    public String getOwnerFullName() {
        return ownerFullName;
    }

    public void setOwnerFullName(final String ownerFullName) {
        this.ownerFullName = ownerFullName;
    }
    //endregion

    //region > ownerImageUrl (property)
    private String ownerImageUrl;

    @MemberOrder(sequence = "1")
    public String getOwnerImageUrl() {
        return ownerImageUrl;
    }

    public void setOwnerImageUrl(final String ownerImageUrl) {
        this.ownerImageUrl = ownerImageUrl;
    }
    //endregion

    //region > ownerId (property)
    private Integer ownerId;

    @MemberOrder(sequence = "1")
    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(final Integer ownerId) {
        this.ownerId = ownerId;
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
