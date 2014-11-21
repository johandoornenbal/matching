package info.matchingservice.dom.Need;

import java.util.List;

import info.matchingservice.dom.Dropdown.Qualities;
import info.matchingservice.dom.Dropdown.Quality;

import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;


@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public class VP_DropDownElement extends VacancyProfileElement {
    
    private Quality keyword;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public Quality getKeyword() {
        return keyword;
    }
    
    public void setKeyword(final Quality keyword){
        this.keyword = keyword;
    }
    
    public List<Quality> autoCompleteKeyword(String search) {
        return qualities.allQualities();
    }
       
    @Inject
    Qualities qualities;

}
