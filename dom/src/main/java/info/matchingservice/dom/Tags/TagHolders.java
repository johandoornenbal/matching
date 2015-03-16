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

package info.matchingservice.dom.Tags;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Profile.ProfileElement;
import info.matchingservice.dom.Profile.ProfileElementTag;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.joda.time.LocalDate;

@DomainService(repositoryFor = TagHolder.class, nature=NatureOfService.DOMAIN)
@DomainServiceLayout(
        named="Beheer",
        menuBar = DomainServiceLayout.MenuBar.PRIMARY,
        menuOrder = "80"
)
public class TagHolders extends MatchingDomainService<TagHolder> {
    

        
	//** API: PROPERTIES **//
	//-- API: PROPERTIES --//
	//** API: COLLECTIONS **//
	//-- API: COLLECTIONS --//
	//** API: ACTIONS **//
	//-- API: ACTIONS --//
	//** GENERIC OBJECT STUFF **//
	//** constructor **//
    public TagHolders(){
        super(TagHolders.class, TagHolder.class);
    }
	//** ownedBy - Override for secure object **//
	//-- GENERIC OBJECT STUFF --//
	//** HELPERS **//
    //** HELPERS: generic service helpers **//   
	//-- HELPERS: generic service helpers --//
	//** HELPERS: programmatic actions **//
    
    @Programmatic
    public List<TagHolder> findTagHolder(final ProfileElement ownerElement, final Tag tag){
        return allMatches("findTagHolder", "ownerElement", ownerElement, "tag", tag);
    }
    
    /**
     * returns the TagHolders on ownerElement that contain a Tag with tagDescription = param tagDescription
     * 
     * @param ownerElement
     * @param tagDescription
     * @return
     */
    @Programmatic
    public List<TagHolder> findTagHolderByTagDescription(final ProfileElementTag ownerElement, final String tagDescription){
        
    	List<TagHolder> tagHoldersWithDescription = new ArrayList<TagHolder>();
    	List<Tag> tagsWithSameDescription = new ArrayList<Tag>();
    
    	tagsWithSameDescription = tags.findTagContains(tagDescription);
    	
    	for (final Iterator<TagHolder> i = ownerElement.getCollectTagHolders().iterator(); i.hasNext();) {
    	
    		TagHolder tagholder = i.next();
    		
    		for (final Iterator<Tag> it = tagsWithSameDescription.iterator(); it.hasNext();) {
    			
    			String description = it.next().getTagDescription();
    			
    			if (tagholder.getTag().getTagDescription().equals(description)) {
    				
    				tagHoldersWithDescription.add(tagholder);
    				
    			}
    			
    		}
    		
    	}
    	
    	return tagHoldersWithDescription;
    }
    
    
    /**
     * This functions returns a tagHolder on a ProfileElement (actually a ProfileTagElement).
     * It checks if the tagCategory and the Tag already exists by testing on the tag(Category)Description.
     * If the tagCategory or Tag does not exist it will be created.
     * NOTE: make sure to test tagCategory in order not to accidentally create tagCategories!!
     * 
     * @param ownerElement
     * @param tagProposalWord
     * @param tagCategoryDescription
     * @param ownedBy
     * @return
     */
    @Programmatic
    public ProfileElement createTagHolder(
            final ProfileElement ownerElement,
    		final String tagProposalWord,
    		final String tagCategoryDescription,
    		final String ownedBy
    		){
    	Tag newTag;
    	TagCategory newTagCategory;
    	if (tagCategories.findTagCategoryMatches(tagCategoryDescription).isEmpty()){
    		//create new tagCategory
    		newTagCategory = tagCategories.createTagCategory(tagCategoryDescription);
    	}
    	else
    	{
    		newTagCategory = tagCategories.findTagCategoryMatches(tagCategoryDescription).get(0);
    	}
    	if (tags.findTagAndCategoryMatches(tagProposalWord.toLowerCase(), newTagCategory).isEmpty()){
    		//make a new tag
    		newTag = tags.createTag(tagProposalWord, newTagCategory);
    	} else {
    		newTag = tags.findTagAndCategoryMatches(tagProposalWord.toLowerCase(), newTagCategory).get(0);
    	}
    	
        final TagHolder newTagHolder = newTransientInstance(TagHolder.class);
        final UUID uuid=UUID.randomUUID();
        // administration of tag usage
        newTag.setDateLastUsed(LocalDate.now());
        if (newTag.getNumberOfTimesUsed()==null){
        	newTag.setNumberOfTimesUsed(1);
        }
        else
        {
        	newTag.setNumberOfTimesUsed(newTag.getNumberOfTimesUsed()+1);
        }
        // END administration of tag usage
        newTagHolder.setUniqueItemId(uuid);
        newTagHolder.setOwnerElement(ownerElement);
        newTagHolder.setTag(newTag);
        newTagHolder.setOwnedBy(ownedBy);
        persist(newTagHolder);
    	
    	return ownerElement;
    }   
    
	//-- HELPERS: programmatic actions --// 
    
	//-- HELPERS --//
    
	//** INJECTIONS **//
    
    @Inject
    Tags tags;
    
    @Inject
    TagCategories tagCategories;
    
    @Inject
    TagHolders tagHolders;
    
	//-- INJECTIONS --//
    
	//** HIDDEN: PROPERTIES **//
	//-- HIDDEN: PROPERTIES --//
	//** HIDDEN: ACTIONS **//
	//-- HIDDEN: ACTIONS --//

}
