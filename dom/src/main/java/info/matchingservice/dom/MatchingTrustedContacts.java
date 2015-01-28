package info.matchingservice.dom;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.query.QueryDefault;

@DomainService(repositoryFor = MatchingTrustedContact.class)
@DomainServiceLayout(named="Contacts", menuOrder="40")
public class MatchingTrustedContacts extends MatchingDomainService<MatchingTrustedContact> {
    
    public MatchingTrustedContacts() {
        super(MatchingTrustedContacts.class, MatchingTrustedContact.class);
    }
    
    @MemberOrder(sequence = "30")
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
                    TrustLevel rights = container.firstMatch(q).getTrustLevel();
                    return rights;
                }
        return null;
    }


    @javax.inject.Inject
    private DomainObjectContainer container;
    
}
