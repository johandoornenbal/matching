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
import info.matchingservice.dom.Profile.ProfileElementType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NotInServiceMenu;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.joda.time.LocalDate;

@DomainService(repositoryFor = TagHolder.class)
@DomainServiceLayout(
        named="Beheer",
        menuBar = DomainServiceLayout.MenuBar.PRIMARY,
        menuOrder = "80"
)
public class TagHolders extends MatchingDomainService<TagHolder> {
    
    public TagHolders(){
        super(TagHolders.class, TagHolder.class);
    }
    
    //************PASSION TAGS************************//
    
    //BUSINESSRULES:
    // only on profile element with ProfileElementType = PASSION_TAGS
    // just one word ...
    // every tag choice at most once (no doubles)    
    @NotInServiceMenu
    public ProfileElement newPassionTagHolder(
            @ParameterLayout(
                    named = "ownerElement")
            final ProfileElement ownerElement,
            @ParameterLayout(
                  named = "tagProposalWord")
            @Parameter(regexPattern="^\\S+$")
    		final String tagProposalWord
    		){
    	Tag newTag;
    	TagCategory tagCat = tagCategories.findTagCategoryMatches("passie").get(0);
    	if (tags.findTagAndCategoryMatches(tagProposalWord.toLowerCase(), tagCat).isEmpty()){
    		//make a new tag
    		newTag = tags.newTag(tagProposalWord, tagCat);
    	} else {
    		newTag = tags.findTagAndCategoryMatches(tagProposalWord.toLowerCase(), tagCat).get(0);
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
        persist(newTagHolder);
    	
    	return ownerElement;
    }
    

//    @ActionLayout(named="Nieuwe passie tag")
//    @NotInServiceMenu
//    public ProfileElement newPassionTagHolder_OLD(
//            @ParameterLayout(
//                    named = "ownerElement")
//            final ProfileElement ownerElement,
//            @ParameterLayout(
//                    named = "tag")
//            final Tag tag
//            ){
//        final TagHolder newTagHolder = newTransientInstance(TagHolder.class);
//        final UUID uuid=UUID.randomUUID();
//        // administration of tag usage
//        tag.setDateLastUsed(LocalDate.now());
//        if (tag.getNumberOfTimesUsed()==null){
//        	tag.setNumberOfTimesUsed(1);
//        }
//        else
//        {
//        	tag.setNumberOfTimesUsed(tag.getNumberOfTimesUsed()+1);
//        }
//        // END administration of tag usage
//        newTagHolder.setUniqueItemId(uuid);
//        newTagHolder.setOwnerElement(ownerElement);
//        newTagHolder.setTag(tag);
//        persist(newTagHolder);
//        return ownerElement;
//    }
    
//    public List<Tag> autoComplete1NewPassionTagHolder(final String search) {
//        return tags.findTagAndCategoryContains(search, tagCategories.findTagCategoryMatches("passie").get(0));
//    }
    
    public boolean hideNewPassionTagHolder(final ProfileElement ownerElement,final String tagProposalWord){
        // catch null value
    	if (ownerElement == null){
    		return true;
    	}
    	// only on profile element with ProfileElementType = PASSION_TAGS
        if (ownerElement.getProfileElementType() == ProfileElementType.PASSION_TAGS){
            return false;
        }
        
        return true;
    }
    
    public String validateNewPassionTagHolder(final ProfileElement ownerElement,final String tagProposalWord){
        // only on profile element with ProfileElementType = PASSION_TAGS
        if (ownerElement.getProfileElementType() == ProfileElementType.PASSION_TAGS){
            return null;
        }
        
        // every tag choice at most once (no doubles)
        //TODO: Nog maken
        
        return "Alleen op een Profiel Element van type 'Passion_tags'";
    }
    
    //************BRANCHE TAGS************************//
    
    //BUSINESSRULES:
    // only on profile element with ProfileElementType = BRANCHE_TAGS
    // just one word ...
    // every tag choice at most once (no doubles)    
    @NotInServiceMenu
    public ProfileElement newBrancheTagHolder(
            @ParameterLayout(
                    named = "ownerElement")
            final ProfileElement ownerElement,
            @ParameterLayout(
                  named = "tagProposalWord")
            @Parameter(regexPattern="^\\S+$")
    		final String tagProposalWord
    		){
    	Tag newTag;
    	TagCategory tagCat = tagCategories.findTagCategoryMatches("branche").get(0);
    	if (tags.findTagAndCategoryMatches(tagProposalWord.toLowerCase(), tagCat).isEmpty()){
    		//make a new tag
    		newTag = tags.newTag(tagProposalWord, tagCat);
    	} else {
    		newTag = tags.findTagAndCategoryMatches(tagProposalWord.toLowerCase(), tagCat).get(0);
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
        persist(newTagHolder);
    	
    	return ownerElement;
    }
    
    public boolean hideNewBrancheTagHolder(final ProfileElement ownerElement,final String tagProposalWord){
        // catch null value
    	if (ownerElement == null){
    		return true;
    	}
    	// only on profile element with ProfileElementType = BRANCHE_TAGS
        if (ownerElement.getProfileElementType() == ProfileElementType.BRANCHE_TAGS){
            return false;
        }
        
        return true;
    }
    
    public String validateNewBrancheTagHolder(final ProfileElement ownerElement,final String tagProposalWord){
        // only on profile element with ProfileElementType = BRANCHE_TAGS
        if (ownerElement.getProfileElementType() == ProfileElementType.BRANCHE_TAGS){
            return null;
        }
        
        // every tag choice at most once (no doubles)
        //TODO: Nog maken
        
        return "Alleen op een Profiel Element van type 'Branche_tags'";
    }
    
    //END ************BRANCHE TAGS************************//
    
    @Programmatic
    public List<TagHolder> findTagHolder(final ProfileElement ownerElement, final Tag tag){
        return allMatches("findTagHolder", "ownerElement", ownerElement, "tag", tag);
    }
    
    
     
    @Inject
    Tags tags;
    
    @Inject
    TagCategories tagCategories;
    
    @Inject
    TagHolders tagHolders;

}
