/*
 * Copyright 2015 Yodo Int. Projects and Consultancy
 *
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package info.matchingservice.dom.Howdoido;

import info.matchingservice.dom.MatchingTrustedContact;
import org.apache.isis.applib.annotation.*;

import javax.inject.Inject;
import javax.jdo.JDOHelper;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import java.util.ArrayList;
import java.util.List;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findPersonalContactUniqueContact", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Howdoido.BasicContact "
                    + "WHERE ownedBy == :ownedBy && contactPerson == :contact")   ,
    @javax.jdo.annotations.Query(
            name = "findPersonalContact", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Howdoido.BasicContact "
                    + "WHERE ownedBy == :ownedBy"),
    @javax.jdo.annotations.Query(
            name = "findPersonalContactReferringToPerson", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Howdoido.BasicContact "
                    + "WHERE contact.matches(:contact)")                    
})
@DomainObject(editing=Editing.DISABLED, autoCompleteRepository = BasicContacts.class)
public class BasicContact extends MatchingTrustedContact {

    @Action(semantics = SemanticsOf.SAFE)
    public String getOID() {
        return JDOHelper.getObjectId(this).toString();
    }

	//** contactPerson **//
    private BasicUser contactPerson;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Property(editing=Editing.DISABLED)
    public BasicUser getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(final BasicUser contact) {
        this.contactPerson = contact;
    }
    
    public List<BasicUser> autoCompleteContactPerson(String search) {
        List<BasicUser> list = new ArrayList<>();

        if (basicUsers.findBasicUserByEmailContains(search).size() > 0 ) {
            for (BasicUser u : basicUsers.findBasicUserByEmailContains(search)) {
                list.add(u);
            }

        }
        if (basicUsers.findBasicUserByNameContains(search).size() > 0 ) {
            for (BasicUser u : basicUsers.findBasicUserByNameContains(search)) {
                if (!list.contains(u)) {
                    list.add(u);
                }
            }
        }

        return list;
    }
    //-- contactPerson --//
 
    //** ownerPerson **//
    private BasicUser ownerPerson;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Property(editing=Editing.DISABLED)
    @PropertyLayout()
    public BasicUser getOwnerPerson() {
        return ownerPerson;
    }

    public void setOwnerPerson(final BasicUser ownerPerson) {
        this.ownerPerson = ownerPerson;
    }
    //--  ownerPerson --//

    public String title(){
        return "CONTACT_WITH " + getContactPerson().title();
    }

	//** INJECTIONS **//
    @Inject
    BasicUsers basicUsers;

}
