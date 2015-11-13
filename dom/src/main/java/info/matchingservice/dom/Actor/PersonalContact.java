/*
 *
 *  Copyright 2015 Yodo Int. Projects and Consultancy
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
package info.matchingservice.dom.Actor;

import info.matchingservice.dom.MatchingTrustedContact;
import org.apache.isis.applib.annotation.*;

import javax.inject.Inject;
import javax.jdo.JDOHelper;
import javax.jdo.annotations.InheritanceStrategy;
import java.util.List;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findPersonalContactUniqueContact", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Actor.PersonalContact "
                    + "WHERE ownedBy == :ownedBy && contactPerson == :contact")   ,
    @javax.jdo.annotations.Query(
            name = "findPersonalContact", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Actor.PersonalContact "
                    + "WHERE ownedBy == :ownedBy"),
    @javax.jdo.annotations.Query(
            name = "findPersonalContactReferringToPerson", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Actor.PersonalContact "
                    + "WHERE contact.matches(:contact)")                    
})
@DomainObject(editing=Editing.DISABLED)
public class PersonalContact extends MatchingTrustedContact {

    @Action(semantics = SemanticsOf.SAFE)
    public String getOID() {
        return JDOHelper.getObjectId(this).toString();
    }
    
	//** API: PROPERTIES **//
	
	//** contactPerson **//
    private Person contactPerson;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Property(editing=Editing.DISABLED)
    public Person getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(final Person contact) {
        this.contactPerson = contact;
    }
    
    public List<Person> autoCompleteContactPerson(String search) {
        return persons.findPersons(search);
    }
    //-- contactPerson --//
 
    //** owner **//
    private Person owner;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Property(editing=Editing.DISABLED)
    @PropertyLayout()
    public Person getOwner() {
        return owner;
    }

    public void setOwner(final Person owner) {
        this.owner = owner;
    }
    //--  owner --//

    public String title(){
        return "CONTACT_WITH " + getContactPerson().title();
    }

    @Inject
    Persons persons;

}
