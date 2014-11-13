package info.matchingservice.dom.Profile;

import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;

import org.apache.isis.applib.annotation.MultiLine;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
public class Pe_Keyword extends ProfileElement {
    
    private String keyWords;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MultiLine
    public String getKeyWords() {
        return keyWords;
    }
    
    public void setKeyWords(final String keywords) {
        this.keyWords = keywords;
    }

}
