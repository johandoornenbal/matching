package info.matchingservice.dom.Need;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public class VacancyProfileTextElement extends VacancyProfileElement {
    
    private String text;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getText() {
        return text;
    }
    
    public void setText(final String text){
        this.text = text;
    }

}
