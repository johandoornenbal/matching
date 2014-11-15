package info.matchingservice.dom.Actor;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Utils.StringUtils;

import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.NotContributed;
import org.apache.isis.applib.annotation.NotContributed.As;
import org.apache.isis.applib.annotation.NotInServiceMenu;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;
import org.apache.isis.applib.query.QueryDefault;


@DomainService(menuOrder = "18", repositoryFor = System.class)
@Named("Systemen")
public class Systems extends MatchingDomainService<System> {
    
    public Systems() {
        super(Systems.class, System.class);
    }
    
    @ActionSemantics(Of.NON_IDEMPOTENT)
    @Named("Maak je systeem aan in het systeem")
    public System newSystem(
            final @Named("Uniek ID") String uniquePartyId,
            final @Named("Systeem naam") String systemName
            ) {
        return newSystem(uniquePartyId, systemName, currentUserName()); // see region>helpers
    }
    
    public boolean hideNewSystem() {
        return hideNewSystem(currentUserName());
    }
    
    /**
     * There should be at most 1 instance for each owner - contact combination.
     * 
     */
    public String validateNewSystem(
            final @Named("Uniek ID") String uniquePartyId,
            final @Named("Systeem naam") String systemName
            ) {
        return validateNewSystem(uniquePartyId, systemName, currentUserName());
    }
    
    @MemberOrder(sequence="5")
    @Named("Dit is jouw systeem ...")
    public List<System> thisIsYou() {
        return thisIsYou(currentUserName());
    }
    
    @MemberOrder(sequence="10")
    @Named("Alle systemen")
    public List<System> allSystems() {
        return allInstances();
    }
    
    
    //region > otherPersons (contributed collection)
    @MemberOrder(sequence="110")
    @NotInServiceMenu
    @ActionSemantics(Of.SAFE)
    @NotContributed(As.ACTION)
    @Render(Type.EAGERLY)
    @Named("Alle andere systemen")
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
    @Named("Vind op systeemnaam")
    public List<System> findSystems(
            @Named("Systeem naam (wildcards * toegestaan)")
            final String systemname
            ) {
        return allMatches("matchSystemBySystemName", "systemName", StringUtils.wildcardToCaseInsensitiveRegex(systemname));
    }
    
    @MemberOrder(sequence="105")
    @Named("Vind op overeenkomst systeemnaam")
    public List<System> findSystemnameContains(
            @Named("Systeem naam (wildcards * toegestaan)")
            final String systemname
            ) {
        return allMatches("matchSystemBySystemNameContains", "systemName", systemname);
    }
    
    // Region>helpers ////////////////////////////
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    @Programmatic //userName can now also be set by fixtures
    public System newSystem(
            final @Named("Uniek ID") String uniquePartyId,
            final @Named("Systeem naam") String systemName,
            final String userName) {
        final System system = newTransientInstance(System.class);
        system.setUniqueActorId(uniquePartyId);
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
            final @Named("Uniek ID") String uniquePartyId,
            final @Named("Systeem naam") String systemName,
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
