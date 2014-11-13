package info.matchingservice.dom.Need;

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
public class Vpe_Keyword extends VacancyProfileElement {
    
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
