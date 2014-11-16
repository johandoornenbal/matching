package info.matchingservice.dom.Need;

import info.matchingservice.dom.MatchingDomainService;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Named;

@DomainService(menuOrder = "50", repositoryFor = Need.class)
@Named("Tafels")
public class Needs extends MatchingDomainService<Need> {

    public Needs() {
        super(Needs.class, Need.class);
    }
    
    @Named("Alle tafels")
    public List<Need> allNeeds() {
        return allInstances();
    }

}
