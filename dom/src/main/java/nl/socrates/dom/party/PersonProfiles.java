package nl.socrates.dom.party;

import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.NotInServiceMenu;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.value.Blob;

import nl.yodo.dom.TrustLevel;

@DomainService(repositoryFor=PersonProfile.class)
public class PersonProfiles extends AbstractFactoryAndRepository {
    
    @Named("Alle profielen")
    @NotInServiceMenu
    public List<PersonProfile> listAll() {
        return container.allInstances(PersonProfile.class);
    }
    
    @MemberOrder(name = "PersonProfiles", sequence = "1")
    @Named("Voeg profiel toe")
    @NotInServiceMenu
    public PersonProfile createProfile(
        @Named("Profiel naam") final String profilename,
        final SocPerson person,
        final TrustLevel level,
        @Optional @Named("Foto") Blob picture ) {
        final PersonProfile pf = container.newTransientInstance(PersonProfile.class);
        pf.setProfilename(profilename);
        pf.setPerson(person);
        pf.setProfileTrustlevel(level);
        pf.setPicture(picture);
        pf.setOwner(container.getUser().getName());
        container.persistIfNotAlready(pf);
        return pf;
    }
    
    public boolean hideCreateProfile(){
 
        // user is admin of socrates app
        if (container.getUser().hasRole("isisModuleSecurityRealm:socrates-admin")) {
            return false;
        }
        return true;
    }
    
    public String validateCreateProfile(
            final String profilename,
            final SocPerson person,
            final TrustLevel level,
            final Blob picture) {
                QueryDefault<PersonProfile> query = 
                        QueryDefault.create(
                        PersonProfile.class, 
                        "findProfileByPersonAndLevel", 
                        "person", person,
                        "level", level);
        return container.firstMatch(query) != null?
        "Je hebt al een profiel op dit level gemaakt. Pas dat profiel (eventueel) aan in plaats van hier een nieuwe te maken."        
        :null;
    }

    //region > injected services
    // //////////////////////////////////////

    @javax.inject.Inject 
    DomainObjectContainer container;

    //endregion
}
