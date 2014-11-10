package info.matchingservice.dom.Need;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Party.Person;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(menuOrder = "40", repositoryFor = Need.class)
@Named("Needs")
public class Needs extends MatchingDomainService<Need> {

    public Needs() {
        super(Needs.class, Need.class);
    }
    
    public List<Need> allNeeds() {
        return allInstances();
    }
    
    @Programmatic
    public Need newNeed(
            final String needDescription,
            final Person needOwner
            ){
        final Need newNeed = newTransientInstance(Need.class);
        newNeed.setNeedDescription(needDescription);
        newNeed.setNeedOwner(needOwner);
        persist(newNeed);
        return newNeed;
    }

}
