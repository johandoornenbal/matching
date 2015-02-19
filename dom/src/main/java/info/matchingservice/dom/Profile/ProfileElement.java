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

package info.matchingservice.dom.Profile;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.Match.PersistedProfileElementComparison;
import info.matchingservice.dom.Match.PersistedProfileElementComparisons;
import info.matchingservice.dom.Match.ProfileElementComparison;
import info.matchingservice.dom.Match.ProfileMatchingService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Persistent;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;

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
            name = "findProfileElementByOwnerProfile", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.ProfileElement "
                    + "WHERE profileElementOwner == :profileElementOwner"),
    @javax.jdo.annotations.Query(
            name = "findProfileElementByOwnerProfileAndDescription", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.ProfileElement "
                    + "WHERE profileElementOwner == :profileElementOwner && profileElementDescription.indexOf(:profileElementDescription) >= 0")
})
@DomainObject(editing=Editing.DISABLED)
public class ProfileElement extends MatchingSecureMutableObject<ProfileElement> {

	//** API: PROPERTIES **//
	
	//** profileElementDescription **//
	@Persistent
    private String profileElementDescription;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getProfileElementDescription(){
        return profileElementDescription;
    }
    
    public void setProfileElementDescription(final String description) {
        this.profileElementDescription = description;
    }
    //-- profileElementDescription --//
    
    //** weight **//
    @Persistent
    private Integer weight;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    public Integer getWeight() {
        return weight;
    }
    
    public void setWeight(final Integer weight){
        this.weight=weight;
    }
    //-- weight --//
	
	//** uniqueItemId **//
    @Persistent
    private UUID uniqueItemId;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    public UUID getUniqueItemId() {
        return uniqueItemId;
    }
    
    public void setUniqueItemId(final UUID uniqueItemId) {
        this.uniqueItemId = uniqueItemId;
    }
    //-- uniqueItemId --//
    
    //** profileElementOwner **//
    @Persistent
    private Profile profileElementOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    @PropertyLayout(hidden=Where.PARENTED_TABLES)
    public Profile getProfileElementOwner() {
        return profileElementOwner;
    }
    
    public void setProfileElementOwner(final Profile vacancyProfileOwner) {
        this.profileElementOwner = vacancyProfileOwner;
    }
    //-- profileElementOwner --//
    
    //** profileElementType **//
    @Persistent
    private ProfileElementType profileElementType;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    @PropertyLayout(hidden=Where.EVERYWHERE)
    public ProfileElementType getProfileElementType(){
        return profileElementType;
    }
    
    public void setProfileElementType(final ProfileElementType profileElementCategory){
        this.profileElementType = profileElementCategory;
    }
    //-- profileElementType --//
    
    //** displayValue **//
    // Should be set by subClasses
    @Persistent
    private String displayValue;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @PropertyLayout(hidden=Where.OBJECT_FORMS)
    public String getDisplayValue(){
        return displayValue;
    }
    
    public void setDisplayValue(final String displayValue){
        this.displayValue = displayValue;
    }
    //-- displayValue --//
    
	//-- API: PROPERTIES --//
	
    //** API: COLLECTIONS **//
	//-- API: COLLECTIONS --//
    
	//** API: ACTIONS **//
	
    //** deleteProfileElement **//
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout()
    public Profile deleteProfileElement(
            @ParameterLayout(named="confirmDelete")
            @Parameter(optionality=Optionality.OPTIONAL)
            boolean confirmDelete
            ){
        container.removeIfNotAlready(this);
        container.informUser("Element verwijderd");
        return getProfileElementOwner();
    }
    
    public String validateDeleteProfileElement(boolean confirmDelete) {
        return confirmDelete? null:"CONFIRM_DELETE";
    }
    //-- deleteProfileElement --//
    
    //** actionCollectProfileElementComparisons **//
   
    @Inject
    ProfileMatchingService profileMatchingService;
    
    @Inject
    PersistedProfileElementComparisons persistedProfileElementComparisons;
    
    
    @Action(semantics=SemanticsOf.SAFE)
    public List<PersistedProfileElementComparison> actionCollectProfileElementComparisons(){
	   
    	List<PersistedProfileElementComparison> elementComparisons = new ArrayList<PersistedProfileElementComparison>();
	   
    	// see if there a elements at all
    	if (container.allInstances(ProfileElement.class).isEmpty()) {
    		
    		return elementComparisons;
    		
    	}
    	
    	// delete existing profile elements of the current user
    	persistedProfileElementComparisons.deleteProfileElementComparisons(getOwnedBy());
    	
    	// loop through all profile elements and collect 'valid' comparisons
    	for (ProfileElement supplyElement: container.allInstances(ProfileElement.class)) {
    		try
    		{
	    		if (
	    				!supplyElement.getProfileElementOwner().equals(null)
	    				
	    				&&
	    				
	    				// Only supplies
	    				supplyElement.getProfileElementOwner().getDemandOrSupply() == DemandOrSupply.SUPPLY
	    				
	    				&&
	    				
	    				// Only of other owners
	    				!supplyElement.getOwnedBy().equals(this.getOwnedBy())
	    				
	    				
	    			) 
	    		{
	    			try 
	    			{
	    				PersistedProfileElementComparison elementComparison = newTransientInstance(PersistedProfileElementComparison.class);
	    					    				
	    				if (!profileMatchingService.getProfileElementComparison(this.getProfileElementOwner(), this, supplyElement.getProfileElementOwner(), supplyElement).equals(null))
	    				{
	    					
	    					ProfileElementComparison tempComparison = profileMatchingService.getProfileElementComparison(this.getProfileElementOwner(), this, supplyElement.getProfileElementOwner(), supplyElement);
	    					elementComparison.setCalculatedMatchingValue(tempComparison.getCalculatedMatchingValue());
	    					elementComparison.setDemandProfileElement(tempComparison.getDemandProfileElement());
	    					elementComparison.setDemandProfileElementOwner(tempComparison.getDemandProfileElementOwner());
	    					elementComparison.setMatchingProfileElementActorOwner(tempComparison.getMatchingProfileElementActorOwner());
	    					elementComparison.setMatchingProfileElementOwner(elementComparison.getMatchingProfileElementOwner());
	    					elementComparison.setMatchingSupplyProfileElement(tempComparison.getMatchingSupplyProfileElement());
	    					elementComparison.setWeight(tempComparison.getWeight());
	    					elementComparison.setOwnedBy(this.getOwnedBy());
	    					final UUID uuid=UUID.randomUUID();
	    					elementComparison.setUniqueItemId(uuid);
	    					persistIfNotAlready(elementComparison);
	    					elementComparisons.add(elementComparison);
	    					
	    				}
	    			}
	    			catch(NullPointerException e)
	    			{
	    				
	    			}
	    		}
    		}
    		catch(NullPointerException e)
    		{
    		
    		}
    	}
    	
    	return elementComparisons;
    }
    
    public boolean hideActionCollectProfileElementComparisons() {
    	
    	return this.getProfileElementOwner().getDemandOrSupply() != DemandOrSupply.DEMAND;
    	
    }
    
    //-- --//
    
    
    //-- API: ACTIONS --//
	
	//** GENERIC OBJECT STUFF **//
	//** constructor **//
    public ProfileElement() {
        super("ownedBy, profileElementDescription, profileElementOwner, profileElementId, uniqueItemId");
    }
	//** ownedBy - Override for secure object **//
    private String ownedBy;
    
    @Override
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    @PropertyLayout(hidden=Where.NOWHERE)
    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(final String owner) {
        this.ownedBy = owner;
    }
	//-- GENERIC OBJECT STUFF --//
    
	//** HELPERS **//
    //** HELPERS: generic object helpers **//
    public String toString(){
        return this.profileElementDescription;
    }
	//-- HELPERS: generic object helpers --//
	//** HELPERS: programmatic actions **//
	//-- HELPERS: programmatic actions --// 
	//-- HELPERS --//
    
	//** INJECTIONS **//
    @javax.inject.Inject
    private DomainObjectContainer container;
	//-- INJECTIONS --//
    
	//** HIDDEN: PROPERTIES **//
    // Used in case owner chooses identical description and weight
    @SuppressWarnings("unused")
    private String profileElementId;

    @ActionLayout(hidden=Where.EVERYWHERE)
    public String getProfileElementId() {
        if (this.getId() != null) {
            return this.getId();
        }
        return "";
    }
    
    public void setProfileElementId() {
        this.profileElementId = this.getId();
    }
	//-- HIDDEN: PROPERTIES --//
    
	//** HIDDEN: ACTIONS **//
	//-- HIDDEN: ACTIONS --//

}
