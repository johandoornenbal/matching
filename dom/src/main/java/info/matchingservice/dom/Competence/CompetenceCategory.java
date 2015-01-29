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

package info.matchingservice.dom.Competence;

import info.matchingservice.dom.MatchingMutableObject;

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
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.RenderType;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "competenceCategoryContains", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Competence.CompetenceCategory "
                    + "WHERE competenceCategoryDescription.indexOf(:competenceCategoryDescription) >= 0"),
    @javax.jdo.annotations.Query(
            name = "competenceCategoryMatches", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Competence.CompetenceCategory "
                    + "WHERE competenceCategoryDescription == :competenceCategoryDescription")                     
})
@DomainObject(editing=Editing.DISABLED, autoCompleteRepository=CompetenceCategories.class, autoCompleteAction="autoComplete")
public class CompetenceCategory extends MatchingMutableObject<CompetenceCategory> {

    public CompetenceCategory(){
        super("competenceCategoryDescription");
    }
    
    public String title(){
        return this.competenceCategoryDescription;
    }
    
    private String competenceCategoryDescription;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @PropertyLayout(
            named="Competentie categorie",
            describedAs="Verzameling van aantal competenties om op te matchen",
            typicalLength=80)
    public String getCompetenceCategoryDescription() {
        return competenceCategoryDescription;
    }
    
    public void setCompetenceCategoryDescription(final String competenceCategoryDescription) {
        this.competenceCategoryDescription = competenceCategoryDescription;
    }
    
    private SortedSet<Competence> competences = new TreeSet<Competence>();
    
    @Persistent(mappedBy = "competenceCategory", dependentElement = "true")
    @CollectionLayout(render=RenderType.EAGERLY)
    public SortedSet<Competence> getCompetences() {
        return competences;
    }
    
    public void setCompetences(final SortedSet<Competence> competences){
        this.competences = competences;
    }
    
    //delete action /////////////////////////////////////////////////////////////////////////////////////
    
    @PropertyLayout(
            named = "Competentie categorie verwijderen"
            )
    public List<CompetenceCategory> DeleteCompetenceCategory(
            @ParameterLayout(named="areYouSure")
            @Parameter(optional=Optionality.TRUE)
            boolean areYouSure
            ){
        container.removeIfNotAlready(this);
        container.informUser("Competentie categorie verwijderd");
        return competenceCategories.allCompetenceCategories();
    }
    
    public String validateDeleteCompetenceCategory(boolean areYouSure) {
        if (!this.getCompetences().isEmpty()){
            return "Er zijn nog competenties in deze catagorie. Verwijder deze eerst!";
        }
        return areYouSure? null:"Geef aan of je wilt verwijderen";
    }
    
    //END ACTIONS ////////////////////////////////////////////////////////////////////////////////////////////
    
    @Inject
    CompetenceCategories competenceCategories;
    
    @Inject
    private DomainObjectContainer container;
}
