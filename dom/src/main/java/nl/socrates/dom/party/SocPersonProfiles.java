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

@DomainService(repositoryFor=SocPersonProfile.class)
public class SocPersonProfiles extends AbstractFactoryAndRepository {
    
    @Named("Alle profielen")
    @NotInServiceMenu
    public List<SocPersonProfile> listAll() {
        return container.allInstances(SocPersonProfile.class);
    }
    
    @MemberOrder(name = "PersonProfiles", sequence = "1")
    @Named("Voeg profiel toe")
    @NotInServiceMenu
    public SocPersonProfile createProfile(
        @Named("Profiel naam") final String profilename,
        final SocPerson person,
        final TrustLevel level,
        @Optional @Named("Foto") Blob picture ) {
        final SocPersonProfile pf = container.newTransientInstance(SocPersonProfile.class);
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
                QueryDefault<SocPersonProfile> query = 
                        QueryDefault.create(
                        SocPersonProfile.class, 
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
