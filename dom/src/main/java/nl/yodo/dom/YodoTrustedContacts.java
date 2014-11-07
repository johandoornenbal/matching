package nl.yodo.dom;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.query.QueryDefault;

@DomainService(menuOrder = "40", repositoryFor = YodoTrustedContact.class)
@Named("PrutsContacts")
public class YodoTrustedContacts extends YodoDomainService<YodoTrustedContact> {
    
    public YodoTrustedContacts() {
        super(YodoTrustedContacts.class, YodoTrustedContact.class);
    }
    
    @MemberOrder(name = "Pruts Personen", sequence = "30")
    public List<YodoTrustedContact> allContacts() {
        return allInstances();
    }
    
    //hide except for admin
    public boolean hideAllContacts() {
        if (container.getUser().hasRole(".*socrates-admin")) {
            return false;
        }
        return true;
    }
    
    TrustLevel trustLevel(String ownedBy, String userName) {
        QueryDefault<YodoTrustedContact> q =
                QueryDefault.create(
                        YodoTrustedContact.class, 
                        "findYodoTrustedUniqueContact",
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
