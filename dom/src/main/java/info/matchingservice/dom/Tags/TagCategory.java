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

import info.matchingservice.dom.MatchingMutableObject;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Persistent;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.SemanticsOf;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "tagCategoryContains", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Tags.TagCategory "
                    + "WHERE tagCategoryDescription.indexOf(:tagCategoryDescription) >= 0"),
    @javax.jdo.annotations.Query(
            name = "tagCategoryMatches", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Tags.TagCategory "
                    + "WHERE tagCategoryDescription == :tagCategoryDescription")                     
})
@DomainObject(editing=Editing.DISABLED, autoCompleteRepository=TagCategories.class, autoCompleteAction="autoComplete")
public class TagCategory extends MatchingMutableObject<TagCategory> {

    public TagCategory(){
        super("tagCategoryDescription");
    }
    
    public String title(){
        return this.tagCategoryDescription;
    }
    
    //equals() implementation in order to compare tagCategories in future matching algorythms
    @Override
    public boolean equals(Object obj){
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false; 
        }
        
        TagCategory cat = (TagCategory) obj;
        
        // Case of characters in tagCategoryDescription will be ignored when comparing tagCategories
        if (cat.getTagCategoryDescription().equalsIgnoreCase(this.getTagCategoryDescription())){
            return true;
        }
        return false;
    }
    
    private String tagCategoryDescription;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @PropertyLayout(
            describedAs="Verzameling van aantal tags op onderwerp zoals steekwoorden in passieomschrijving",
            typicalLength=80)
    public String getTagCategoryDescription() {
        return tagCategoryDescription;
    }
    
    public void setTagCategoryDescription(final String tagCategoryDescription) {
        this.tagCategoryDescription = tagCategoryDescription;
    }
    
    private SortedSet<Tag> collectTags = new TreeSet<Tag>();
    
    @Persistent(mappedBy = "tagCategory", dependentElement = "true")
    @CollectionLayout(render=RenderType.EAGERLY)
    public SortedSet<Tag> getCollectTags() {
        return collectTags;
    }
    
    public void setCollectTags(final SortedSet<Tag> tags){
        this.collectTags = tags;
    }
    
    //delete action /////////////////////////////////////////////////////////////////////////////////////
    
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public List<TagCategory> deleteTagCategory(
            @ParameterLayout(named="confirmDelete")
            @Parameter(optionality=Optionality.OPTIONAL)
            boolean confirmDelete
            ){
        container.removeIfNotAlready(this);
        container.informUser("Competentie categorie verwijderd");
        return tagCategories.allTagCategories();
    }
    
    //Businessrule: only an empty tagCategory can be deleted
    public String validateDeleteTagCategory(boolean confirmDelete) {
        if (!this.getCollectTags().isEmpty()){
            return "REMOVE_DEPENDENCIES_FIRST";
        }
        return confirmDelete? null:"CONFIRM_DELETE";
    }
    
    //END ACTIONS ////////////////////////////////////////////////////////////////////////////////////////////
    
    @Inject
    TagCategories tagCategories;
    
    @Inject
    private DomainObjectContainer container;
}
