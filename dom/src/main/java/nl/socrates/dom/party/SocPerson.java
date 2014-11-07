/*
 *
 *  Copyright 2014 YODO international projects and consultancy
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package nl.socrates.dom.party;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Persistent;

import com.google.common.base.Objects;

import org.joda.time.LocalDate;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.AutoComplete;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.util.TitleBuffer;
import org.apache.isis.applib.value.Blob;

import nl.yodo.dom.TrustLevel;
import nl.socrates.dom.JdoColumnLength;


@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "matchByLastName", language = "JDOQL",
            value = "SELECT "
                    + "FROM nl.socrates.dom.party.SocPerson "
                    + "WHERE lastName.matches(:lastName)")
    })
@AutoComplete(repository=SocPersons.class,  action="autoComplete")
public class SocPerson extends SocParty {
    
    // /////// Security ////////////////////////////////////////////////
    
    public String disabled(Identifier.Type type){
        // user is owner
        if (Objects.equal(getOwner(), container.getUser().getName())) {
            return null;
        }
        // user is admin of socrates app
        if (container.getUser().hasRole("isisModuleSecurityRealm:socrates-admin")) {
            return null;
        }
        // user is neither owner nor admin
        return "Not allowed to modify / Niet toegestaan te wijzigen";
    }

       // just to see how the first role looks like
//    public String getYourRole() {
//        return container.getUser().getRoles().get(0).getName();
//    }
    
    private String owner;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Hidden
    public String getOwner() {
        return owner;
    }
    
    public void setOwner(final String owner)
    {
        this.owner=owner;
    }
    
    public void changeOwner(final String owner) {
        this.setOwner(owner);
    }
    public boolean hideChangeOwner(final String owner) {
        // user is admin of socrates app
        if (container.getUser().hasRole("isisModuleSecurityRealm:socrates-admin")) {
            return false;
        }
        return true;
    }
    
    public TrustLevel getViewerRights(){      
        QueryDefault<PersonContact> q =
                QueryDefault.create(
                        PersonContact.class, 
                        "findReferringContactUserName",
                        "ownerPerson", this,
                        "contactUserName", container.getUser().getName());
                if (container.allMatches(q).isEmpty()) {
                    return null;
                }
                if (!container.allMatches(q).isEmpty()) {
                    TrustLevel rights = container.firstMatch(q).getLevel();
                    return rights;
                }
        return null;
    }
    
    // // END SECURITY /////////////////////////////////////////////////////
    
    
    private String initials;

    @javax.jdo.annotations.Column(allowsNull = "true", length = JdoColumnLength.Person.INITIALS)
    @Named("Voorletter(s)")
    @Hidden(where=Where.ALL_TABLES)
    public String getInitials() {
        return initials;
    }

    public void setInitials(final String initials) {
        this.initials = initials;
    }
    

    // //////////////////////////////////////
    
    @Override
    public String disableName() {
        return "Cannot be updated directly; derived from first and last names";
    }

    // //////////////////////////////////////
    
    private String firstName;

    @javax.jdo.annotations.Column(allowsNull = "false", length = JdoColumnLength.PROPER_NAME)
    @MemberOrder(sequence = "10")
    @Named("Voornaam")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    // //////////////////////////////////////
    
    private String middleName;

    @javax.jdo.annotations.Column(allowsNull = "true", length = JdoColumnLength.MIDDLE_NAME)
    @MemberOrder(sequence = "20")
    @Named("Tussen")
    @Hidden(where=Where.ALL_TABLES)
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(final String middleName) {
        this.middleName = middleName;
    }


    // //////////////////////////////////////

    private String lastName;

    @javax.jdo.annotations.Column(allowsNull = "false", length = JdoColumnLength.PROPER_NAME)
    @MemberOrder(sequence = "30")
    @Named("Achternaam")
    @Hidden(where=Where.ALL_TABLES)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }
    
    //////////////////////////////////////////////////
    
    private String baptismalName;

    @Named("Doopnaam")
    @javax.jdo.annotations.Column(allowsNull = "true", length = JdoColumnLength.PROPER_NAME)
    @MemberOrder(sequence = "40")
    @Hidden(where=Where.ALL_TABLES)
    public String getBaptismalName() {
        return baptismalName;
    }

    public void setBaptismalName(String name) {
        this.baptismalName = name;
    }


    // //////////////////////////////////////
    
    private PersonGenderType gender;

    @javax.jdo.annotations.Column(allowsNull = "false", length = JdoColumnLength.TYPE_ENUM)
    @MemberOrder(sequence = "50")
    @Named("Geslacht")
    public PersonGenderType getGender() {
        return gender;
    }

    public void setGender(final PersonGenderType gender) {
        this.gender = gender;
    }

    public PersonGenderType defaultGender() {
        return PersonGenderType.UNKNOWN;
    }

    // //////////////////////////////////////
    
    private LocalDate dateOfBirth;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "60")
    @Named("Geboortedatum")
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    // //////////////////////////////////////
 
    private String placeOfBirth;

    @Named("Geboorteplaats")
    @javax.jdo.annotations.Column(allowsNull = "false", length = JdoColumnLength.PROPER_NAME)
    @MemberOrder(sequence = "70")
    @Hidden(where=Where.ALL_TABLES)
    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String name) {
        this.placeOfBirth = name;
    }

    // //////////////////////////////////////    
    
    private String nationality;

    @Named("Nationaliteit")
    @javax.jdo.annotations.Column(allowsNull = "false", length = JdoColumnLength.PROPER_NAME)
    @MemberOrder(sequence = "80")
    @Hidden(where=Where.ALL_TABLES)
    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
    
    // //////////////////////////////////////
    
    private SocPerson lastContact;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Named("Laatste die mij als contact toegevoegd heeft")
    @Disabled
    @Hidden(where=Where.ALL_TABLES)
    public SocPerson getLastContact() {
        return lastContact;
    }
    
    public void setLastContact(final SocPerson lastcontact) {
        this.lastContact = lastcontact;
    }
    
    // //////////////////////////////////////
    @Named("Maak een profiel")
    public List<PersonProfile> makeProfile(
            @Named("Profiel naam") final String profilename, 
            @Named("Vertrouwens niveau") final TrustLevel level, 
            @Optional @Named("Foto") final Blob picture){
        profiles.createProfile(
                profilename, 
                this, 
                level, 
                picture);
        QueryDefault<PersonProfile> query = 
                QueryDefault.create(
                     PersonProfile.class, 
                    "findProfileByPerson", 
                    "person", this);
        
        return (List<PersonProfile>) container.allMatches(query); 
    }
    
    public String validateMakeProfile(
            final String profilename,
            final TrustLevel level,
            final Blob picture) {
                QueryDefault<PersonProfile> query = 
                        QueryDefault.create(
                        PersonProfile.class, 
                        "findProfileByPersonAndLevel", 
                        "person", this,
                        "level", level);
        return container.firstMatch(query) != null?
        "Je hebt al een profiel op dit level gemaakt. Pas dat profiel (eventueel) aan in plaats van hier een nieuwe te maken."        
        :null;
    }
    
    public boolean hideMakeProfile(){
        // user is owner
        if (Objects.equal(getOwner(), container.getUser().getName())) {
            return false;
        }       
        // user is admin of socrates app
        if (container.getUser().hasRole("isisModuleSecurityRealm:socrates-admin")) {
            return false;
        }
        return true;
    }
    // //////////////////////////////////////
    
    public String validate() {
        if ((getFirstName() == null || getFirstName().isEmpty()) && (getInitials() == null || getInitials().isEmpty())) {
                return "Er moet tenminste een voorletter of een voornaam ingevuld zijn";}
        else {
            return null;
        }
    }

    public void updating() {
        TitleBuffer tb = new TitleBuffer();
//        TODO: deze werkt op een of andere manier niet goed met JDO
        setName(tb.append(getLastName()).append(",", getFirstName()).toString());
    }

    // /////////////////////////////////////// Profielen
    
    // PersonProfile (Collection)
//    private SortedSet<PersonProfile> personprofiles = new TreeSet<PersonProfile>();
//    
//    @Render(Type.EAGERLY)
//    @Persistent(mappedBy = "person", dependentElement = "true")
//    @Named("Profielen")
//    public SortedSet<PersonProfile> getPersonprofiles() {
//        return personprofiles;
//    }
//
//    public void setPersonprofiles(final SortedSet<PersonProfile> personProfiles){
//        this.personprofiles = personProfiles;
//    }
    
    @MemberOrder(sequence = "10")
    @Render(Type.EAGERLY)
    @Named("Profielen")
    public List<PersonProfile> getProfilesAllowedToView() {
        QueryDefault<PersonProfile> query =
                QueryDefault.create(
                        PersonProfile.class,
                        "findProfileByPerson",
                        "person", this);
        // user is owner
        if (Objects.equal(getOwner(), container.getUser().getName())){
            return container.allMatches(query);
        }
        // user is admin of socrates app
        if (container.getUser().hasRole("isisModuleSecurityRealm:socrates-admin")) {
            return container.allMatches(query);
        }
        List<PersonProfile> tempProfileList = new ArrayList<PersonProfile>();
        for (PersonProfile e: container.allMatches(query)) {
            if (getViewerRights()!=null && e.getProfileTrustlevel().compareTo(getViewerRights()) <= 0) {
                 tempProfileList.add(e);
            }
        }
        return tempProfileList;

    }

    // /////////////////////////////////////// Einde Profielen    
    
    private SortedSet<PersonContact> personcontacts = new TreeSet<PersonContact>();
    
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "ownerPerson", dependentElement = "true")
    @Named("Persoonlijke contacten")
    public SortedSet<PersonContact> getPersoncontacts() {
        return personcontacts;
    }
    
    public void setPersoncontacts(final SortedSet<PersonContact> personcontacts) {
        this.personcontacts = personcontacts;
    }
    

    @Render(Type.EAGERLY)
    @Named("Personen verwijzend naar mij (versie1)")
    public List<Referer> getReferringToMe1(){
        List<Referer> pReferring = new ArrayList<Referer>();
        for(PersonContact e: pcontacts.listAll()) {
            if (e.getContact() == this) {
                Referer referer = new Referer();
                referer.setOwner(e.getOwnerPerson());
                referer.setDate(e.getDateCreatedOn());
                referer.setLevel(e.getLevel());
                pReferring.add(referer);
            }
        }
        return pReferring;
    } 
    
    private SortedSet<PersonContact> personreferring = new TreeSet<PersonContact>();
    
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "contact", dependentElement = "true")
    @Named("Personen verwijzend naar mij (versie2)")
    public SortedSet<PersonContact> getPersonreferring() {
        return personreferring;
    }
    
    public void setPersonreferring(final SortedSet<PersonContact> personcontacts) {
        this.personreferring = personcontacts;
    }
    
    
    @Render(Type.EAGERLY)
    @Named("Bevestigde persoonlijke contacten")
    public List<PersonContact> getConfirmedContacts() {
        QueryDefault<PersonContact> query = 
                QueryDefault.create(
                    PersonContact.class, 
                    "findConfirmedContacts", 
                    "ownerPerson", this,
                    "status", PersonContactStatus.CONFIRMED);        
        return container.allMatches(query);
    }
    
    @Inject
    PersonContacts pcontacts;
    
    @javax.inject.Inject
    private DomainObjectContainer container;
    
    @Inject
    PersonProfiles profiles;
}
