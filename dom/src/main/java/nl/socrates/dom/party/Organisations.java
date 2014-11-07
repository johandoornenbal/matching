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

@DomainService(menuOrder = "30", repositoryFor = Organisation.class)
@Named("Organisaties")
public class Organisations extends SocratesDomainService<Organisation> {
    
    public Organisations() {
        super(Organisations.class, Organisation.class);
    }

    @ActionSemantics(Of.NON_IDEMPOTENT)
    @MemberOrder(name = "Contacten", sequence = "1")
    @Named("Nieuwe organisatie")    
    public Organisation newOrganisation (
            final @Named("Referentie") @Optional @RegEx(validation=RegexValidation.REFERENCE) String reference,
            final @Named("Naam organisatie") String organisationName,
            final @Named("Naam contactpersoon") @Optional String contactpersoon
            ) {
        final Organisation organisation = newTransientInstance(Organisation.class);
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
    public List<Organisation> allOrganisations() {
        return allInstances();
    }   

}
