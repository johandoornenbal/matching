package nl.socrates.dom.party;

public class PersonContactEvent {
    
    PersonContactEvent(String testString, SocPerson owner, SocPerson contact){
        this.testString = testString;
        this.contactOwner = owner;
        this.contact = contact;
    };
    
    private String testString;
    
    public String getTestString() {
        return testString;
    }
    
    public void setTestString(final String teststring) {
        this.testString = teststring;
    }
    
    private SocPerson contactOwner;
    
    public SocPerson getContactOwner() {
        return contactOwner;
    }

    public void setContactOwner(final SocPerson contact) {
        this.contactOwner = contact;
    }
    
    private SocPerson contact;
    
    public SocPerson getContact() {
        return contact;
    }
    
    public void setContact(final SocPerson contact) {
        this.contact = contact;
    }
}
