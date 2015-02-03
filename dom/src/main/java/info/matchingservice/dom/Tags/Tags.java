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

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.joda.time.LocalDate;

@DomainService(repositoryFor = Tag.class)
@DomainServiceLayout(
        named="Beheer",
        menuBar = DomainServiceLayout.MenuBar.PRIMARY,
        menuOrder = "80"
)
public class Tags extends MatchingDomainService<Tag> {
    
    public Tags(){
        super(Tags.class, Tag.class);
    }
    
    @ActionLayout(named="Alle tags")
    public List<Tag> allTags() {
        return allInstances();
    }
    
    @ActionLayout(named="Nieuwe tag")
    public TagCategory newTag(
            @ParameterLayout(
                    named = "tagCategory")
            final TagCategory tagCategory,
            @ParameterLayout(
                    named = "tagDescription",
                    describedAs="Omschrijving in zo min mogelijk woorden; liefst slechts een.",
                    typicalLength=80
                    )
            final String tagDescription
            ){
        final Tag newTag = newTransientInstance(Tag.class);
        newTag.setTagDescription(tagDescription.toLowerCase());
        newTag.setTagCategory(tagCategory);
        newTag.setDisplCategory(tagCategory.title());
        newTag.setDateLastUsed(LocalDate.now());
        newTag.setNumberOfTimesUsed(0);
        persist(newTag);
        return tagCategory;
    }
    
    public List<TagCategory> autoComplete0NewTag(final String search) {
        return tagCategories.findTagCategoryContains(search);
    }
    
    //Businessrule: within a tagCategory a tag must be unique
    public String validateNewTag(final TagCategory tagCategory, final String tagDescription){
        if (!this.findTagAndCategoryMatches(tagDescription, tagCategory).isEmpty()){
            return "Deze tag is al eerder ingevoerd";
        }
        return null;
    }
    
    public List<Tag> findTagContains(final String tagDescription){
        return allMatches("tagContains", "tagDescription", tagDescription.toLowerCase());
    }
    
    public List<Tag> findTagMatches(final String tagDescription){
        return allMatches("tagMatches", "tagDescription", tagDescription.toLowerCase());
    }
    
    @Programmatic
    public List<Tag> findTagAndCategoryMatches(final String tagDescription, final TagCategory tagCategory){
        return allMatches("tagAndCategoryMatches", "tagDescription", tagDescription.toLowerCase(), "tagCategory", tagCategory);
    }
    
    @Programmatic
    public List<Tag> findTagAndCategoryContains(final String tagDescription, final TagCategory tagCategory){
        return allMatches("tagAndCategoryContains", "tagDescription", tagDescription.toLowerCase(), "tagCategory", tagCategory);
    }
    
    
    
    //For fixtures
    @Programmatic
    public Tag newTag(
            final String tagDescription,
            final TagCategory tagCategory
            ){
        final Tag newTag = newTransientInstance(Tag.class);
        newTag.setTagDescription(tagDescription.toLowerCase());
        newTag.setTagCategory(tagCategory);
        newTag.setDisplCategory(tagCategory.title());
        persist(newTag);
        return newTag;
    }
    
    @Inject
    TagCategories tagCategories;

}
