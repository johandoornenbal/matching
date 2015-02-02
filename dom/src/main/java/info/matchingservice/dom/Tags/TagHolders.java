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

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NotInServiceMenu;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;

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
    
    //BUSINESSRULES:
    // only on profile element with ProfileElementType = PASSION_TAGS
    // every tag choice at most once (no doubles)
    @ActionLayout(named="Nieuwe passie tagholder")
    @NotInServiceMenu
    public ProfileElement newPassionTagHolder(
            @ParameterLayout(
                    named = "ownerElement")
            final ProfileElement ownerElement,
            @ParameterLayout(
                    named = "tag")
            final Tag tag
            ){
        final TagHolder newTagHolder = newTransientInstance(TagHolder.class);
        final UUID uuid=UUID.randomUUID();
        newTagHolder.setUniqueItemId(uuid);
        newTagHolder.setOwnerElement(ownerElement);
        newTagHolder.setTag(tag);
        persist(newTagHolder);
        return ownerElement;
    }
    
    public List<Tag> autoComplete1NewPassionTagHolder(final String search) {
        return tags.findTagAndCategoryContains(search, tagCategories.findTagCategoryMatches("passie").get(0));
    }
    
    public boolean hideNewPassionTagHolder(final ProfileElement ownerElement,final Tag tag){
        // only on profile element with ProfileElementType = PASSION_TAGS
        if (ownerElement.getProfileElementType() == ProfileElementType.PASSION_TAGS){
            return false;
        }
        
        return true;
    }
    
    public String validateNewPassionTagHolder(final ProfileElement ownerElement,final Tag tag){
        // only on profile element with ProfileElementType = PASSION_TAGS
        if (ownerElement.getProfileElementType() == ProfileElementType.PASSION_TAGS){
            return null;
        }
        
        // every tag choice at most once (no doubles)
        //TODO: werk niet goed
        if (tagHolders.findTagHolder(ownerElement, tag).get(0).equals(null)){
            return "Deze tag was er al. Kies een andere.";
        }
        
        return "Alleen op een Profiel Element van type 'Passion_tags'";
    }
    
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
