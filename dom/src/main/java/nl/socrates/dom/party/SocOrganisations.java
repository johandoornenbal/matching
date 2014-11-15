package nl.socrates.dom.party;

import java.util.List;

import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Prototype;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.annotation.ActionSemantics.Of;

import nl.socrates.dom.RegexValidation;
import nl.socrates.dom.SocratesDomainService;

@DomainService(menuOrder = "30", repositoryFor = SocOrganisation.class)
@Named("Socrates Organisaties")
public class SocOrganisations extends SocratesDomainService<SocOrganisation> {
    
    public SocOrganisations() {
        super(SocOrganisations.class, SocOrganisation.class);
    }

    @ActionSemantics(Of.NON_IDEMPOTENT)
    @MemberOrder(name = "Contacten", sequence = "1")
    @Named("Nieuwe organisatie")    
    public SocOrganisation newOrganisation (
            final @Named("Referentie") @Optional @RegEx(validation=RegexValidation.REFERENCE) String reference,
            final @Named("Naam organisatie") String organisationName,
            final @Named("Naam contactpersoon") @Optional String contactpersoon
            ) {
        final SocOrganisation organisation = newTransientInstance(SocOrganisation.class);
        organisation.setReference(reference);
        organisation.setOrganisationName(organisationName);
        organisation.setContactpersoon(contactpersoon);
        organisation.updating();
        persist(organisation);
        return organisation;
    }
        
    // //////////////////////////////////////

    @Prototype
    @ActionSemantics(Of.SAFE)
    @MemberOrder(name = "Contacten", sequence = "99.2")
    @Named("Alle organisaties")
    public List<SocOrganisation> allOrganisations() {
        return allInstances();
    }   

}
