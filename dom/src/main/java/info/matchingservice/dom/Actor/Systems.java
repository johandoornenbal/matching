package info.matchingservice.dom.Actor;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Utils.StringUtils;

import java.util.List;
import java.util.UUID;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NotContributed;
import org.apache.isis.applib.annotation.NotContributed.As;
import org.apache.isis.applib.annotation.NotInServiceMenu;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;


@DomainService(repositoryFor = System.class)
@Hidden
@DomainServiceLayout(named="Systemen", menuOrder="18")
public class Systems extends MatchingDomainService<System> {
    
    public Systems() {
        super(Systems.class, System.class);
    }
    
    @ActionLayout(named="Maak je systeem aan in het systeem")
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public System newSystem(
            @ParameterLayout(named="systemName")
            final String systemName
            ) {
        return newSystem(systemName, currentUserName()); // see region>helpers
    }
    
    public boolean hideNewSystem() {
        return hideNewSystem(currentUserName());
    }
    
    /**
     * There should be at most 1 instance for each owner - contact combination.
     * 
     */
    public String validateNewSystem(
            final String systemName
            ) {
        return validateNewSystem(systemName, currentUserName());
    }
    
    @MemberOrder(sequence="5")
    @ActionLayout(named="Dit is jouw systeem ...")
    public List<System> thisIsYou() {
        return thisIsYou(currentUserName());
    }
    
    @MemberOrder(sequence="10")
    @ActionLayout(named="Alle systemen")
    public List<System> allSystems() {
        return allInstances();
    }
    
    
    //region > otherPersons (contributed collection)
    @MemberOrder(sequence="110")
    @NotInServiceMenu
    @NotContributed(As.ACTION)
    @ActionLayout(named="Alle andere systemen")
    @Action(semantics=SemanticsOf.SAFE)
    public List<System> AllOtherSystems(final System systemMe) {
        final List<System> allSystems = allSystems();
        return Lists.newArrayList(Iterables.filter(allSystems, excluding(systemMe)));
    }

    private static Predicate<System> excluding(final System system) {
        return new Predicate<System>() {
            @Override
            public boolean apply(System input) {
                return input != system;
            }
        };
    }
    //endregion
    
   
    
    @MemberOrder(sequence="100")
    @ActionLayout(named="Vind op systeemnaam")
    public List<System> findSystems(
            @ParameterLayout(named="systemName")
            final String systemName
            ) {
        return allMatches("matchSystemBySystemName", "systemName", StringUtils.wildcardToCaseInsensitiveRegex(systemName));
    }
    
    @MemberOrder(sequence="105")
    @ActionLayout(named="Vind op overeenkomst systeemnaam")
    public List<System> findSystemnameContains(
            @ParameterLayout(named="systemName")
            final String systemName
            ) {
        return allMatches("matchSystemBySystemNameContains", "systemName", systemName);
    }
    
    // Region>helpers ////////////////////////////
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    @Programmatic //userName can now also be set by fixtures
    public System newSystem(           
            final String systemName,
            final String userName) {
        final System system = newTransientInstance(System.class);
        final UUID uuid=UUID.randomUUID();
        system.setUniqueActorId(uuid);
        system.setSystemName(systemName);
        system.setOwnedBy(userName);
        persist(system);
        return system;
    }
    
    @Programmatic //userName can now also be set by fixtures
    public boolean hideNewSystem(String userName) {
        QueryDefault<System> query = 
                QueryDefault.create(
                        System.class, 
                    "findSystemUnique", 
                    "ownedBy", userName);        
        return container.firstMatch(query) != null?
        true        
        :false;        
    }
    
    @Programmatic //userName can now also be set by fixtures
    public String validateNewSystem(
            final String systemName,
            final String userName) {
        
        QueryDefault<System> query = 
                QueryDefault.create(
                        System.class, 
                    "findSystemUnique", 
                    "ownedBy", userName);        
        return container.firstMatch(query) != null?
        "Dit systeem is al aangemaakt. Pas je gegevens eventueel aan in plaats van hier een nieuwe te maken."        
        :null;
        
    }
    
    @Programmatic //userName can now also be set by fixtures
    public List<System> thisIsYou(final String userName) {
        QueryDefault<System> query = 
                QueryDefault.create(
                        System.class, 
                    "findSystemUnique", 
                    "ownedBy", userName);          
        return allMatches(query);
    }
    
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
}
