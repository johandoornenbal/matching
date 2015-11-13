package info.matchingservice.dom.Api.Viewmodels;

import info.matchingservice.dom.CommunicationChannels.Address;
import info.matchingservice.dom.CommunicationChannels.Email;
import info.matchingservice.dom.CommunicationChannels.Phone;
import org.apache.isis.applib.annotation.MemberOrder;

/**
 * Created by jodo on 03/11/15.
 */
public class CommunicationChannelViewModel extends ApiAbstractViewModel {

    public CommunicationChannelViewModel(){}

    public CommunicationChannelViewModel(final Address address) {
        super(address);
        this.address = address.getAddress();
        this.postalCode = address.getPostalCode();
        this.town = address.getTown();
        this.classType = address.getClass().getSimpleName();
        this.type = address.getType().title();
        this.owner = address.getPerson().getIdAsInt();
        this.ownerClassType = address.getPerson().getClass().getSimpleName();
    }

    public CommunicationChannelViewModel(final Email email){
        super(email);
        this.email = email.getEmail();
        this.classType = email.getClass().getSimpleName();
        this.type = email.getType().title();
        this.owner = email.getPerson().getIdAsInt();
        this.ownerClassType = email.getPerson().getClass().getSimpleName();
    }

    public CommunicationChannelViewModel(final Phone phone) {
        super(phone);
        this.phoneNumber = phone.getPhoneNumber();
        this.classType = phone.getClass().getSimpleName();
        this.type = phone.getType().title();
        this.owner = phone.getPerson().getIdAsInt();
        this.ownerClassType = phone.getPerson().getClass().getSimpleName();
    }

    //region > addressMain (property)
    private String address;

    @MemberOrder(sequence = "1")
    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }
    //endregion

    //region > postalCodeMain (property)
    private String postalCode;

    @MemberOrder(sequence = "1")
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }
    //endregion

    //region > townMain (property)
    private String town;

    @MemberOrder(sequence = "1")
    public String getTown() {
        return town;
    }

    public void setTownMain(final String town) {
        this.town = town;
    }
    //endregion

    //region > type (property)
    private String type;

    @MemberOrder(sequence = "1")
    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }
    //endregion

    //region > classType (property)
    private String classType;

    @MemberOrder(sequence = "1")
    public String getClassType() {
        return classType;
    }

    public void setClassType(final String classType) {
        this.classType = classType;
    }
    //endregion

    //region > email (property)
    private String email;

    @MemberOrder(sequence = "1")
    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }
    //endregion

    //region > phoneNumber (property)
    private String phoneNumber;

    @MemberOrder(sequence = "1")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    //endregion

    //region > owner (property)
    private Integer owner;

    @MemberOrder(sequence = "1")
    public Integer getOwner() {
        return owner;
    }

    public void setOwner(final Integer owner) {
        this.owner = owner;
    }
    //endregion

    //region > ownerClassType (property)
    private String ownerClassType;

    @MemberOrder(sequence = "1")
    public String getOwnerClassType() {
        return ownerClassType;
    }

    public void setOwnerClassType(final String ownerClassType) {
        this.ownerClassType = ownerClassType;
    }
    //endregion


}
