/**
 *  Copyright 2015 Yodo Int. Projects and Consultancy
 *
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package info.matchingservice.dom.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Programmatic;

import info.matchingservice.dom.MatchingDomainService;

@DomainService(repositoryFor = ProfileElementTag.class)
public class ProfileElementTags extends MatchingDomainService<ProfileElementTag> {
    
    public ProfileElementTags() {
        super(ProfileElementTags.class, ProfileElementTag.class);
    }
    
    @Programmatic
    public List<ProfileElementTag> allElementsOnDemands(){
    	List<ProfileElementTag> tempList = new ArrayList<ProfileElementTag>();;
    	for (ProfileElementTag e : container.allInstances(ProfileElementTag.class)){
    		if (e.getProfileElementOwner().getDemandOrSupply()==DemandOrSupply.DEMAND){
    			tempList.add(e);
    		}
    	}
    	return tempList;
    }
    
    // with currentUserName
    @Programmatic
    public ProfileElementTag createProfileElementTag(
            final String description,
            final Integer weight,
            final ProfileElementType profileElementType,
            final Profile profileOwner
            ){
        return createProfileElementTag(description, weight, profileElementType, profileOwner, currentUserName());
    }
    
    // without currentUser (for fixtures)
    @Programmatic
    public ProfileElementTag createProfileElementTag(
            final String description,
            final Integer weight,
            final ProfileElementType profileElementType,
            final Profile profileOwner,
            final String ownedBy
            ){
        final ProfileElementTag newProfileElement = newTransientInstance(ProfileElementTag.class);
        final UUID uuid=UUID.randomUUID();
        newProfileElement.setUniqueItemId(uuid);
        newProfileElement.setDescription(description);
        newProfileElement.setWeight(weight);
        newProfileElement.setProfileElementType(profileElementType);
        newProfileElement.setProfileElementOwner(profileOwner);
        newProfileElement.setIsActive(true);
        newProfileElement.setWidgetType(ProfileElementWidgetType.TAGS);
        newProfileElement.setOwnedBy(ownedBy);
        persist(newProfileElement);
        return newProfileElement;
    }
    
    @Programmatic
    public ArrayList<ProfileElementTag> chooseElementsOnSupplyProfiles(
    		List<ProfileType> profileTypeArray, 
    		List<ProfileElementType> profileElementTypeArray,
    		String ownedBy
    		){
    	ArrayList<ProfileElementTag> elementList = new ArrayList<ProfileElementTag>();
    	for (ProfileElementTag e : container.allInstances(ProfileElementTag.class)) {
    		if (!e.getOwnedBy().equals(ownedBy) && e.getProfileElementOwner().getDemandOrSupply() == DemandOrSupply.SUPPLY){
    			for (ProfileType pt: profileTypeArray){
    				if (e.getProfileElementOwner().getProfileType() == pt){
    					for (ProfileElementType pet: profileElementTypeArray){
    						if(e.getProfileElementType() == pet){
    							elementList.add(e);
    						}
    					}	
    				}
    			}
    		}
    	}
    	return elementList;
    }
    

    
    // Region>helpers ///////////////////////////////
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
}

