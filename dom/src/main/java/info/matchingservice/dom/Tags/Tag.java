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

import info.matchingservice.dom.MatchingDomainObject;

import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.joda.time.LocalDate;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "tagContains", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Tags.Tag "
                    + "WHERE tagDescription.indexOf(:tagDescription) >= 0"),
        @javax.jdo.annotations.Query(
                name = "tagMatches", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Tags.Tag "
                        + "WHERE tagDescription == :tagDescription"),
        @javax.jdo.annotations.Query(
                name = "tagAndCategoryMatches", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Tags.Tag "
                        + "WHERE tagDescription == :tagDescription && tagCategory == :tagCategory"),
        @javax.jdo.annotations.Query(
                name = "tagAndCategoryContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Tags.Tag "
                        + "WHERE tagDescription.indexOf(:tagDescription) >= 0 && tagCategory == :tagCategory"),
        @javax.jdo.annotations.Query(
                name = "tagAndCategoryThreshold", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Tags.Tag "
                        + "WHERE tagDescription.indexOf(:tagDescription) >= 0 && tagCategory == :tagCategory && numberOfTimesUsed >= :numberOfTimesUsed")                        
})

@DomainObject(editing=Editing.DISABLED)
public class Tag extends MatchingDomainObject<Tag> {
    
    public Tag() {
        super("tagDescription, tagCategory");
    }
    
    public String title(){
        return this.tagDescription;
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
        
        Tag tag = (Tag) obj;
        
        // Case of characters in tagCategoryDescription will be ignored when comparing tagCategories
        if (tag.getTagDescription().equalsIgnoreCase(this.getTagDescription()) && tag.getTagCategory() == this.getTagCategory()){
            return true;
        }
        return false;
    }
    
    private String tagDescription;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @PropertyLayout(
            describedAs="Omschrijving in zo min mogelijk woorden; liefst slechts een.",
            typicalLength=80)
    @MemberOrder(sequence="2")
    @Property(editing=Editing.DISABLED)
    public String getTagDescription() {
        return tagDescription;
    }
    
    public void setTagDescription(final String tagDescription) {
        this.tagDescription = tagDescription;
    }
    
    private TagCategory tagCategory;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @PropertyLayout(
            describedAs="Kies de categorie")
    @MemberOrder(sequence="3")
    public TagCategory getTagCategory(){
        return tagCategory;
    }
    
    public void setTagCategory(final TagCategory tagCategory){
        this.tagCategory = tagCategory;
    }
    
    
    // Convenience bijvoorbeeld voor DB uitdraaien
    private String displCategory;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    @PropertyLayout(hidden=Where.EVERYWHERE)
    public String getDisplCategory(){
        return displCategory;
    }
    
    public void setDisplCategory(final String displCategory){
        this.displCategory = displCategory;
    }
    
    private LocalDate dateLastUsed;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    public LocalDate getDateLastUsed() {
		return dateLastUsed;
	}

	public void setDateLastUsed(LocalDate dateLastUsed) {
		this.dateLastUsed = dateLastUsed;
	}
	
	private Integer numberOfTimesUsed;
	
	@javax.jdo.annotations.Column(allowsNull = "true")
	public Integer getNumberOfTimesUsed() {
		return numberOfTimesUsed;
	}

	public void setNumberOfTimesUsed(Integer numberOfTimesUsed) {
		this.numberOfTimesUsed = numberOfTimesUsed;
	}
    
    //delete action /////////////////////////////////////////////////////////////////////////////////////
    
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public List<Tag> deleteTag(
            @ParameterLayout(named="confirmDelete")
            @Parameter(optionality=Optionality.OPTIONAL)
            boolean confirmDelete
            ){
        container.removeIfNotAlready(this);
        container.informUser("Tag verwijderd");
        return tags.allTags();
    }
    
    public String validateDeleteTag(boolean confirmDelete) {
    	if (getNumberOfTimesUsed()>0){
    		return "STILL_IN_USE_REMOVE_DEPENDENCIES_FIRST";
    	}
        return confirmDelete? null:"CONFIRM_DELETE";
    }
    
    //END ACTIONS ////////////////////////////////////////////////////////////////////////////////////////////


	@Inject
    private DomainObjectContainer container;
    
    @Inject
    Tags tags;
}
