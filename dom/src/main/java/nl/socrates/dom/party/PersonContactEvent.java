package nl.socrates.dom.party;

public class PersonContactEvent {
    
    PersonContactEvent(String testString, Person owner, Person contact){
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
    
    private Person contactOwner;
    
    public Person getContactOwner() {
        return contactOwner;
    }

    public void setContactOwner(final Person contact) {
        this.contactOwner = contact;
    }
    
    private Person contact;
    
    public Person getContact() {
        return contact;
    }
    
    public void setContact(final Person contact) {
        this.contact = contact;
    }
}
