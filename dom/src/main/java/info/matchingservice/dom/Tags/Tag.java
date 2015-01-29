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

import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Where;

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
                        + "WHERE tagDescription == :tagDescription && tagCategory == :tagCategory")                         
})

@DomainObject(editing=Editing.DISABLED)
public class Tag extends MatchingMutableObject<Tag> {
    
    public Tag() {
        super("tagDescription, tagCategory");
    }
    
    public String title(){
        return this.tagDescription;
    }
    
    private String tagDescription;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @PropertyLayout(
            named="Tag",
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
            named="Competentie categorie",
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
    
    //delete action /////////////////////////////////////////////////////////////////////////////////////
    
    @PropertyLayout(named = "Tag verwijderen")
    public List<Tag> DeleteTag(
            @ParameterLayout(named="areYouSure")
            @Parameter(optional=Optionality.TRUE)
            boolean areYouSure
            ){
        container.removeIfNotAlready(this);
        container.informUser("Tag verwijderd");
        return tags.allTags();
    }
    
    public String validateDeleteTag(boolean areYouSure) {
        return areYouSure? null:"Geef aan of je wilt verwijderen";
    }
    
    //END ACTIONS ////////////////////////////////////////////////////////////////////////////////////////////
 
    @Inject
    private DomainObjectContainer container;
    
    @Inject
    Tags tags;
}
