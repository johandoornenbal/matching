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


@DomainService(menuOrder = "15", repositoryFor = Organisation.class)
@Named("Organisaties")
public class Organisations extends MatchingDomainService<Organisation> {
    
    public Organisations() {
        super(Organisations.class, Organisation.class);
    }
    
    @ActionSemantics(Of.NON_IDEMPOTENT)
    @Named("Maak je organisatie aan in het systeem")
    public Organisation newOrganisation(
            final @Named("Uniek ID") String uniquePartyId,
            final @Named("Organisatie naam") String organisationName
            ) {
        return newOrganisation(uniquePartyId, organisationName, currentUserName()); // see region>helpers
    }
    
    public boolean hideNewOrganisation() {
        return hideNewOrganisation(currentUserName());
    }
    
    /**
     * There should be at most 1 instance for each owner - contact combination.
     * 
     */
    public String validateNewOrganisation(
            final @Named("Uniek ID") String uniquePartyId,
            final @Named("Organisatie naam") String organisationName
            ) {
        return validateNewOrganisation(uniquePartyId, organisationName, currentUserName());
    }
    
    @MemberOrder(sequence="5")
    @Named("Dit is jouw organisatie ...")
    public List<Organisation> thisIsYou() {
        return thisIsYou(currentUserName());
    }
    
    @MemberOrder(sequence="10")
    @Named("Alle organisaties")
    public List<Organisation> allOrganisations() {
        return allInstances();
    }
    
    
    //region > otherPersons (contributed collection)
    @MemberOrder(sequence="110")
    @NotInServiceMenu
    @ActionSemantics(Of.SAFE)
    @NotContributed(As.ACTION)
    @Render(Type.EAGERLY)
    @Named("Alle andere organisaties")
    public List<Organisation> AllOtherOrganisations(final Organisation organisationMe) {
        final List<Organisation> allOrganisations = allOrganisations();
        return Lists.newArrayList(Iterables.filter(allOrganisations, excluding(organisationMe)));
    }

    private static Predicate<Organisation> excluding(final Organisation organisation) {
        return new Predicate<Organisation>() {
            @Override
            public boolean apply(Organisation input) {
                return input != organisation;
            }
        };
    }
    //endregion
    
   
    
    @MemberOrder(sequence="100")
    @Named("Vind op organisatie naam")
    public List<Organisation> findOrganisations(
            @Named("Organisatie naam (wildcards * toegestaan)")
            final String organisationname
            ) {
        return allMatches("matchOrganisationByOrganisationName", "organisationName", StringUtils.wildcardToCaseInsensitiveRegex(organisationname));
    }
    
    @MemberOrder(sequence="105")
    @Named("Vind op overeenkomst naam organisatie")
    public List<Organisation> findOrganisationContains(
            @Named("Organisatie naam (wildcards * toegestaan)")
            final String organisationname
            ) {
        return allMatches("matchOrganisationByOrganisationNameContains", "organisationName", organisationname);
    }
    
    // Region>helpers ////////////////////////////
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    @Programmatic //userName can now also be set by fixtures
    public Organisation newOrganisation(
            final @Named("Uniek ID") String uniquePartyId,
            final @Named("Organisatie naam") String organisationName,
            final String userName) {
        final Organisation organisation = newTransientInstance(Organisation.class);
        organisation.setUniqueActorId(uniquePartyId);
        organisation.setOrganisationName(organisationName);
        organisation.setOwnedBy(userName);
        persist(organisation);
        return organisation;
    }
    
    @Programmatic //userName can now also be set by fixtures
    public boolean hideNewOrganisation(String userName) {
        QueryDefault<Organisation> query = 
                QueryDefault.create(
                        Organisation.class, 
                    "findOrganisationUnique", 
                    "ownedBy", userName);        
        return container.firstMatch(query) != null?
        true        
        :false;        
    }
    
    @Programmatic //userName can now also be set by fixtures
    public String validateNewOrganisation(
            final @Named("Uniek ID") String uniquePartyId,
            final @Named("Organisatie naam") String organisationName,
            final String userName) {
        
        QueryDefault<Organisation> query = 
                QueryDefault.create(
                        Organisation.class, 
                    "findOrganisationUnique", 
                    "ownedBy", userName);        
        return container.firstMatch(query) != null?
        "Deze organisatie is al aangemaakt. Pas je gegevens eventueel aan in plaats van hier een nieuwe te maken."        
        :null;
        
    }
    
    @Programmatic //userName can now also be set by fixtures
    public List<Organisation> thisIsYou(final String userName) {
        QueryDefault<Organisation> query = 
                QueryDefault.create(
                        Organisation.class, 
                    "findOrganisationUnique", 
                    "ownedBy", userName);          
        return allMatches(query);
    }
    
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
}
