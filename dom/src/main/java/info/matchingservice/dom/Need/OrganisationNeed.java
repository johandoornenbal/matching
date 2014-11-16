package info.matchingservice.dom.Need;

import info.matchingservice.dom.Actor.Organisation;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Persistent;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public class OrganisationNeed extends Need {
    
//    private Organisation needOwner;
//    
//    @Override
//    @javax.jdo.annotations.Column(allowsNull = "false")
//    @Disabled
//    @Named("Opdrachtgever")
//    public Organisation getNeedOwner() {
//        return needOwner;
//    }
//    
//    public void setNeedOwner(final Organisation needOwner) {
//        this.needOwner = needOwner;
//    }
    
    // Region> Vacancies
    
    private SortedSet<VacancyProfile> vacancyProfiles = new TreeSet<VacancyProfile>();
    
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "vacancyOwner", dependentElement = "true")
    @Named("Mijn stoelen")
    public SortedSet<VacancyProfile> getVacancyProfiles() {
        return vacancyProfiles;
    }
    
    public void setVacancyProfiles(final SortedSet<VacancyProfile> vac){
        this.vacancyProfiles = vac;
    }
    
    @Named("Nieuwe stoel")
    public VacancyProfile newVacancyProfile(
            @Named("Omschrijving van 'stoel'")
            final  String vacancyDescription
            ) {
        return newVacancyProfile(vacancyDescription, this, currentUserName());
    }
    
    // helpers
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    public String toString() {
        return getNeedDescription() + " - " + getNeedOwner().title();
    }
    
    @Programmatic
    public VacancyProfile newVacancyProfile(final String vacancyDescription, final OrganisationNeed vacancyOwner, final String ownedBy) {
        return allvacancies.newVacancy(vacancyDescription, vacancyOwner, ownedBy);
    }
    
    //Injection
    
    @Inject
    VacancyProfiles allvacancies;
    
    @javax.inject.Inject
    private DomainObjectContainer container;

}
