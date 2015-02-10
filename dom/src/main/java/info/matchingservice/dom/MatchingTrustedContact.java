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
package info.matchingservice.dom;

import java.util.List;

import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import com.google.common.base.Objects;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.query.QueryDefault;

/**
 * Establishes a trust relationship between the user creating an instance (owner) and the chosen contact.
 * The level of Trust is expressed by enum TrustLevel level.
 * 
 * The idea is: the higher the level of trust, the more information of contained in instances (of classes 
 * extending YodoSecureMutableObject) created by the owner will be available.
 * 
 * There should be at most 1 instance for each owner - contact combination.
 *
 * @version $Rev$ $Date$
 */
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
            name = "findMatchingTrustedContact", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.MatchingTrustedContact "
                    + "WHERE ownedBy == :ownedBy"),
    @javax.jdo.annotations.Query(
            name = "findMatchingTrustedUniqueContact", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.MatchingTrustedContact "
                    + "WHERE ownedBy == :ownedBy && contact == :contact")
})

@DomainObject(editing=Editing.DISABLED)
public class MatchingTrustedContact extends MatchingSecureMutableObject<MatchingTrustedContact> {
    public MatchingTrustedContact() {
        super("contact, trustLevel, ownedBy");
    }
    
    private String ownedBy;
    
    @Override
    @PropertyLayout(hidden=Where.EVERYWHERE)
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(final String owner) {
        this.ownedBy = owner;
    }
    
    /**
     * Hides instances that are not owned except for admin
     * NOTE: does not hide instances in Lists; only the forms
     */
    public boolean hidden() {
        // user is owner
        if (Objects.equal(getOwnedBy(), container.getUser().getName())){
            return false;
        }
        // user is referred to
        if (Objects.equal(getContact(), container.getUser().getName())){
            return false;
        }
        // user is admin of app
        if (container.getUser().hasRole(".*matching-admin")) {
            return false;
        }
        return true;
    }
    
    /**
     * Temp method to display the owner
     */
    @PropertyLayout(hidden=Where.EVERYWHERE)
    public String getEigenaar() {
        return getOwnedBy();
    }
    
    /**
     * contact contains username of the contact
     */
    private String contact;
    @PropertyLayout(hidden=Where.EVERYWHERE)
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getContact() {
        return contact;
    }
    
    public void setContact(final String contact) {
        this.contact=contact;
    }
    
    /**
     * level contains an enum TrustLevel, indicating the circle of trust
     */
    private TrustLevel trustLevel;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "30")
    public TrustLevel getTrustLevel() {
        return trustLevel;
    }
    
    public void setTrustLevel(final TrustLevel level) {
        this.trustLevel = level;
    }
    
    public TrustLevel defaultTrustLevel() {
        return TrustLevel.ENTRY_LEVEL;
    }
    
    // Region //// Delete action //////////////////////////////
    @ActionLayout()
    public List<MatchingTrustedContact> delete(
            @ParameterLayout(named="confirmDelete")
            @Parameter(optionality=Optionality.OPTIONAL)
            boolean confirmDelete
            ) { 
        container.removeIfNotAlready(this);
        
        container.informUser("Contact verwijderd");
        
        QueryDefault<MatchingTrustedContact> query = 
                QueryDefault.create(
                        MatchingTrustedContact.class, 
                    "findMatchingTrustedContact", 
                    "ownedBy", getOwnedBy());
        
        return (List<MatchingTrustedContact>) container.allMatches(query);   
    }
    
    public String validateDelete(boolean confirmDelete) {
        return confirmDelete? null:"CONFIRM_DELETE";
    }
    
    /**
     * Hides the Delete action unless for owner / admin
     * NOTE: since the instance is hidden unless owner / admin it is a bit superfluous
     */
    public boolean hideDelete() {
        // user is owner
        if (Objects.equal(getOwnedBy(), currentUserName())){
            return false;
        }
        // user is admin of app
        if (container.getUser().hasRole(".*matching-admin")) {
            return false;
        }
        return true;
    }
    
    // Region //Edit TrustLevel
    
    public MatchingTrustedContact editTrustlevel(TrustLevel trustLevel) {
        this.setTrustLevel(trustLevel);
        return this;
    }
    
    public TrustLevel default0EditTrustlevel(){
        return getTrustLevel();
    }
    
    
    // Region //// helpers
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    // Region //// injections ///////////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
    
}
