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

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(repositoryFor = TagCategory.class, nature=NatureOfService.DOMAIN)
@DomainServiceLayout(
        named="Beheer",
        menuBar = DomainServiceLayout.MenuBar.PRIMARY,
        menuOrder = "80"
)
public class TagCategories extends MatchingDomainService<TagCategory> {
    
    public TagCategories(){
        super(TagCategories.class, TagCategory.class);
    }
    
    @Programmatic
    public List<TagCategory> allTagCategories() {
        return allInstances();
    }
    
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public TagCategory createTagCategory(
            @ParameterLayout(
                    named = "tagCategoryDescription",
                    describedAs="Verzameling van aantal tags om op te matchen.",
                    typicalLength=80
                    )
            final String tagCategoryDescription
            ){
        final TagCategory createTagCategory = newTransientInstance(TagCategory.class);
        createTagCategory.setTagCategoryDescription(tagCategoryDescription.toLowerCase());
        persist(createTagCategory);
        return createTagCategory;
    }
    
    //Businessrule: a tagCategory must be unique
    public String validateCreateTagCategory(final String tagCategoryDescription){
        if (!this.findTagCategoryMatches(tagCategoryDescription).isEmpty()){
            return "ONE_INSTANCE_AT_MOST";
        }
        return null;
    }
    
    @Programmatic
    public List<TagCategory> findTagCategoryContains(final String tagCategoryDescription){
        return allMatches("tagCategoryContains", "tagCategoryDescription", tagCategoryDescription.toLowerCase());
    }
    
    @Programmatic
    public List<TagCategory> findTagCategoryMatches(final String tagCategoryDescription){
        return allMatches("tagCategoryMatches", "tagCategoryDescription", tagCategoryDescription.toLowerCase());
    }

}
