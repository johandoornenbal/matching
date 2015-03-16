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

import info.matchingservice.dom.Tags.TagHolder;
import info.matchingservice.dom.Tags.TagHolders;

import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Persistent;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.RenderType;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findProfileElementOfType", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.ProfileElementTag "
                    + "WHERE profileElementOwner == :profileElementOwner && profileElementType == :profileElementType")
})    
@DomainObject(editing=Editing.DISABLED)
public class ProfileElementTag extends ProfileElement {
	
	//** API: PROPERTIES **//
	//-- API: PROPERTIES --//
	//** API: COLLECTIONS **//
    
    private SortedSet<TagHolder> collectTagHolders = new TreeSet<TagHolder>();

    @CollectionLayout(render=RenderType.EAGERLY)
    @Persistent(mappedBy = "ownerElement", dependentElement = "true")
    public SortedSet<TagHolder> getCollectTagHolders() {
        return collectTagHolders;
    }

    public void setCollectTagHolders(SortedSet<TagHolder> tagholders) {
        this.collectTagHolders = tagholders;
        
    }
	//-- API: COLLECTIONS --//
	//** API: ACTIONS **//
    
    //** createPassionTagHolder **//
    // Business rule:
    // only on profile element with ProfileElementType = PASSION_TAGS
    // just one word ...
    // every tag choice at most once (no doubles)
    // assumes there is a category 'passie', otherwise creates one.
    public ProfileElementTag createPassionTagHolder(
        	@ParameterLayout(named = "tagProposalWord")
        	@Parameter(regexPattern="^\\S+$")
        	final String tagProposalWord
		)
		{
			tagHolders.createTagHolder(this, tagProposalWord, "passie", currentUserName());
			return this;
		}
    
    public boolean hideCreatePassionTagHolder(final String tagProposalWord){

    	// only on profile element with ProfileElementType = PASSION_TAGS
        if (this.getProfileElementType() == ProfileElementType.PASSION_TAGS){
            return false;
        }
        
        return true;
    }
    
    public String validateCreatePassionTagHolder(final String tagProposalWord){
        // only on profile element with ProfileElementType = PASSION_TAGS
        if (this.getProfileElementType() == ProfileElementType.PASSION_TAGS){
            return null;
        }
        // every tag choice at most once (no doubles)
        //TODO: Nog maken
        
        return "ONLY_ON_PASSION_TAGS_PROFILE_ELEMENT";
    }
    //-- createPassionTagHolder --//
    
    //** createBrancheTagHolder **//
    
    // Business rule:
    // only on profile element with ProfileElementType = BRANCHE_TAGS
    // just one word ...
    // every tag choice at most once (no doubles)
    // assumes there is a category 'branche', otherwise creates one.
    public ProfileElementTag createBrancheTagHolder(
        	@ParameterLayout(named = "tagProposalWord")
        	@Parameter(regexPattern="^\\S+$")
        	final String tagProposalWord
		)
		{
			tagHolders.createTagHolder(this, tagProposalWord, "branche", currentUserName());
			return this;
		}
    
    public boolean hideCreateBrancheTagHolder(final String tagProposalWord){

    	// only on profile element with ProfileElementType = BRANCHE_TAGS
        if (this.getProfileElementType() == ProfileElementType.BRANCHE_TAGS){
            return false;
        }
        
        return true;
    }
    
    public String validateCreateBrancheTagHolder(final String tagProposalWord){
        // only on profile element with ProfileElementType = BRANCHE_TAGS
        if (this.getProfileElementType() == ProfileElementType.BRANCHE_TAGS){
            return null;
        }
        // every tag choice at most once (no doubles)
        //TODO: Nog maken
        
        return "ONLY_ON_BRANCHE_TAGS_PROFILE_ELEMENT";
    }   
    //-- createBrancheTagHolder --//
    
    //** createQualityTagHolder **//
    // Business rule:
    // only on profile element with ProfileElementType = QUALITY_TAGS
    // every tag choice at most once (no doubles)
    // assumes there is a category 'kwaliteit', otherwise creates one.
    public ProfileElementTag createQualityTagHolder(
        	@ParameterLayout(named = "tagProposalWord")
        	final String tagProposalWord
		)
		{
			tagHolders.createTagHolder(this, tagProposalWord, "kwaliteit", currentUserName());
			return this;
		}
    
    public boolean hideCreateQualityTagHolder(final String tagProposalWord){

    	// only on profile element with ProfileElementType = QUALITY_TAGS
        if (this.getProfileElementType() == ProfileElementType.QUALITY_TAGS){
            return false;
        }
        
        return true;
    }
    
    public String validateCreateQualityTagHolder(final String tagProposalWord){
        // only on profile element with ProfileElementType = QUALITY_TAGS
        if (this.getProfileElementType() == ProfileElementType.QUALITY_TAGS){
            return null;
        }
        // every tag choice at most once (no doubles)
        //TODO: Nog maken
        
        return "ONLY_ON_QUALITY_TAGS_PROFILE_ELEMENT";
    } 
    //-- createQualityTagHolder --//
    
    //** createWeekDayTagHolder **//
    // Business rule:
    // only on profile element with ProfileElementType = WEEKDAY_TAGS
    // every tag choice at most once (no doubles)
    // assumes there is a category 'weekdagen', otherwise creates one.
    public ProfileElementTag createWeekDayTagHolder(
        	@ParameterLayout(named = "tagProposalWord")
        	final String tagProposalWord
		)
		{
			tagHolders.createTagHolder(this, tagProposalWord, "weekdagen", currentUserName());
			return this;
		}
    
    public List<String> choices0CreateWeekDayTagHolder(){
    	
    	return Arrays.asList("maandag", "dinsdag", "woensdag", "donderdag", "vrijdag", "zaterdag", "zondag");
    	
    }
    
    public boolean hideCreateWeekDayTagHolder(final String tagProposalWord){

    	// only on profile element with ProfileElementType = WEEKDAY_TAGS
        if (this.getProfileElementType() == ProfileElementType.WEEKDAY_TAGS){
            return false;
        }
        
        return true;
    }
    
    public String validateCreateWeekDayTagHolder(final String tagProposalWord){
        // only on profile element with ProfileElementType = WEEKDAY_TAGS
        if (this.getProfileElementType() == ProfileElementType.WEEKDAY_TAGS){
        	
        	if (
        			tagProposalWord.toLowerCase().equals("maandag")
        			||
        			tagProposalWord.toLowerCase().equals("dinsdag")
        			||
        			tagProposalWord.toLowerCase().equals("woensdag")
        			||
        			tagProposalWord.toLowerCase().equals("donderdag")
        			||
        			tagProposalWord.toLowerCase().equals("vrijdag")
        			||
        			tagProposalWord.toLowerCase().equals("zaterdag")
        			||
        			tagProposalWord.toLowerCase().equals("zondag")
        			) 
        	{
            	
        		return null;
            
        	} else {
            	
        		return "NOT_A_WEEKDAY";
            
        	}
        }
        // every tag choice at most once (no doubles)
        //TODO: Nog maken
 
        return "ONLY_ON_WEEKDAY_TAGS_PROFILE_ELEMENT";
    } 
    //-- createQualityTagHolder --//    
    
	//-- API: ACTIONS --//
	//** GENERIC OBJECT STUFF **//
	//** constructor **//
	//** ownedBy - Override for secure object **//
	//-- GENERIC OBJECT STUFF --//
	//** HELPERS **//
    //** HELPERS: generic object helpers **//
    private String currentUserName() {
        return container.getUser().getName();
    }
	//-- HELPERS: generic object helpers --//
	//** HELPERS: programmatic actions **//
	//-- HELPERS: programmatic actions --// 
	//-- HELPERS --//
	//** INJECTIONS **//
    
    @Inject
    private TagHolders tagHolders;
    
    @Inject
    private DomainObjectContainer container;
    
	//-- INJECTIONS --//
	//** HIDDEN: PROPERTIES **//
	//-- HIDDEN: PROPERTIES --//
	//** HIDDEN: ACTIONS **//
	//-- HIDDEN: ACTIONS --//
    
}
