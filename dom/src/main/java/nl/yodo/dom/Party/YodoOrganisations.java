package nl.yodo.dom.Party;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.ActionSemantics.Of;

import nl.yodo.dom.YodoDomainService;

@DomainService(menuOrder = "14", repositoryFor = YodoOrganisation.class)
@Named("Pruts Organisaties")
public class YodoOrganisations extends YodoDomainService<YodoOrganisation> {
    
    public YodoOrganisations() {
        super(YodoOrganisations.class, YodoOrganisation.class);
    }
    
    @ActionSemantics(Of.NON_IDEMPOTENT)
    public YodoOrganisation newOrganisation(
            final @Named("Uniek ID") String uniquePartyId,
            final @Named("Organisatie naam") String organisationName){
        return newOrganisation(uniquePartyId, organisationName, currentUserName()); // see region>helpers
    }
    
    public List<YodoOrganisation> allOrganisations() {
        return allInstances();
    }
    
    // Region>helpers ////////////////////////////
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    @Programmatic //userName can now also be set by fixtures
    public YodoOrganisation newOrganisation(
            final @Named("Uniek ID") String uniquePartyId,
            final @Named("Organisatie naam") String organisationName,
            final String userName) {
        YodoOrganisation org = newTransientInstance(YodoOrganisation.class);
        org.setOrganisationName(organisationName);
        org.setUniquePartyId(uniquePartyId);
        org.setOwnedBy(userName);
        persist(org);
        return org;        
    }
    
    // Region>injections ////////////////////////////
    
    @javax.inject.Inject
    private DomainObjectContainer container;
}
