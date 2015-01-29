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

import java.util.List;

import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.ParameterLayout;

import info.matchingservice.dom.MatchingDomainService;

@DomainService(repositoryFor = TagCategory.class)
@DomainServiceLayout(
        named="Beheer",
        menuBar = DomainServiceLayout.MenuBar.PRIMARY,
        menuOrder = "80"
)
public class TagCategories extends MatchingDomainService<TagCategory> {
    
    public TagCategories(){
        super(TagCategories.class, TagCategory.class);
    }
    
    @ActionLayout(named="Alle tag categorieën")
    public List<TagCategory> allTagCategories() {
        return allInstances();
    }
    
    @ActionLayout(named="Nieuwe tag categorie")
    public TagCategory newTagCategory(
            @ParameterLayout(
                    named = "tagCategoryDescription",
                    describedAs="Verzameling van aantal tags om op te matchen.",
                    typicalLength=80
                    )
            final String tagCategoryDescription
            ){
        final TagCategory newTagCategory = newTransientInstance(TagCategory.class);
        newTagCategory.setTagCategoryDescription(tagCategoryDescription.toLowerCase());
        persist(newTagCategory);
        return newTagCategory;
    }
    
    //Businessrule: a tagCategory must be unique
    public String validateNewTagCategory(final String tagCategoryDescription){
        if (!this.findTagCategoryMatches(tagCategoryDescription).isEmpty()){
            return "Deze tagcategorie is al eerder ingevoerd";
        }
        return null;
    }
    
    public List<TagCategory> findTagCategoryContains(final String tagCategoryDescription){
        return allMatches("tagCategoryContains", "tagCategoryDescription", tagCategoryDescription.toLowerCase());
    }
    
    public List<TagCategory> findTagCategoryMatches(final String tagCategoryDescription){
        return allMatches("tagCategoryMatches", "tagCategoryDescription", tagCategoryDescription.toLowerCase());
    }

}
