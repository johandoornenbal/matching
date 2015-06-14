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

import java.util.ArrayList;
import java.util.Iterator;
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
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;

import info.matchingservice.dom.Match.PersistedProfileElementComparison;
import info.matchingservice.dom.Match.PersistedProfileElementComparisons;
import info.matchingservice.dom.Match.ProfileElementComparison;
import info.matchingservice.dom.Match.ProfileMatchingService;
import info.matchingservice.dom.MatchingSecureMutableObject;

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

    //region > widgetType (property)
    private ProfileElementWidgetType widgetType;

    @javax.jdo.annotations.Column(allowsNull = "true")
    public ProfileElementWidgetType getWidgetType() {
        return widgetType;
    }

    public void setWidgetType(final ProfileElementWidgetType widgetType) {
        this.widgetType = widgetType;
    }
    //endregion
    
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

    //region > isActive (property)
    private boolean isActive;

    @javax.jdo.annotations.Column(allowsNull = "true")
    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(final boolean isActive) {
        this.isActive = isActive;
    }
    //endregion
    
	//-- API: PROPERTIES --//
	
    //** API: COLLECTIONS **//
	//-- API: COLLECTIONS --//
    
	//** API: ACTIONS **//
    
    //** updateWidgetType **//
    
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT, restrictTo = RestrictTo.PROTOTYPING)
    @ActionLayout()
    public ProfileElement updateWidgetType(ProfileElementWidgetType widgetType) {
    	this.setWidgetType(widgetType);
    	return this;
    }
    
    public ProfileElementWidgetType default0UpdateWidgetType() {
        return getWidgetType();
    }
    
    //-- updateWidgetType --//

    //** updateWeight **//

    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout()
    public ProfileElement updateWeight(Integer weight) {
        this.setWeight(weight);
        return this;
    }

    public Integer default0UpdateWeight() {
        return getWeight();
    }

    //-- updateWeight --//

    //** activate **//

    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout()
    public ProfileElement activate() {
        setIsActive(true);
        return this;
    }

    //-- activate --//

    //** deactivate **//

    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout()
    public ProfileElement deactivate() {
        setIsActive(false);
        return this;
    }

    //-- deactivate --//
	
    //** deleteProfileElement **//
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout()
    public Profile deleteProfileElement(
            @ParameterLayout(named="confirmDelete")
            @Parameter(optionality=Optionality.OPTIONAL)
            boolean confirmDelete
            ){
    	List<PersistedProfileElementComparison> elements = comparisons.findProfileElementComparisonsByElement(this);
    	for (final Iterator<PersistedProfileElementComparison> iterator = elements.iterator(); iterator.hasNext();)
    	{
    		iterator.next().deletePersistedProfileElementComparison();
    	}
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
    
    @Inject
    PersistedProfileElementComparisons comparisons;
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
