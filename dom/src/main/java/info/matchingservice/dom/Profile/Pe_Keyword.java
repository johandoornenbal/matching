package info.matchingservice.dom.Profile;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
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
    
    @Named("Bewerk steekwoorden")
    public Pe_Keyword EditKeywords(
            @Named("steekwoorden")
            @MultiLine
            String newInt
            ){
        this.setKeyWords(newInt);
        return this;
    }
    
    public String default0EditKeywords() {
        return getKeyWords();
    }

}
