package info.matchingservice.dom;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.query.QueryDefault;

@DomainService(menuOrder = "40", repositoryFor = MatchingTrustedContact.class)
@Named("PrutsContacts")
public class MatchingTrustedContacts extends MatchingDomainService<MatchingTrustedContact> {
    
    public MatchingTrustedContacts() {
        super(MatchingTrustedContacts.class, MatchingTrustedContact.class);
    }
    
    @MemberOrder(name = "Pruts Personen", sequence = "30")
    public List<MatchingTrustedContact> allContacts() {
        return allInstances();
    }
    
    //hide except for admin
    public boolean hideAllContacts() {
        if (container.getUser().hasRole(".*matching-admin")) {
            return false;
        }
        return true;
    }
    
    TrustLevel trustLevel(String ownedBy, String userName) {
        QueryDefault<MatchingTrustedContact> q =
                QueryDefault.create(
                        MatchingTrustedContact.class, 
                        "findMatchingTrustedUniqueContact",
                        "ownedBy", ownedBy,
                        "contact", userName);
                if (container.allMatches(q).isEmpty()) {
                    return null;
                }
                if (!container.allMatches(q).isEmpty()) {
                    TrustLevel rights = container.firstMatch(q).getLevel();
                    return rights;
                }
        return null;
    }


    @javax.inject.Inject
    private DomainObjectContainer container;
    
}
