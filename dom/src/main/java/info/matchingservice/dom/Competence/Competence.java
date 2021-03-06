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

import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
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

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "competenceContains", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Competence.Competence "
                    + "WHERE competenceDescription.indexOf(:competenceDescription) >= 0"),
        @javax.jdo.annotations.Query(
                name = "competenceMatches", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Competence.Competence "
                        + "WHERE competenceDescription == :competenceDescription")                      
})

@DomainObject(editing=Editing.DISABLED)
public class Competence extends MatchingMutableObject<Competence> {
    
    public Competence() {
        super("competenceDescription, competenceCategory");
    }
    
    public String title(){
        return this.competenceDescription;
    }
    
    private String competenceDescription;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @PropertyLayout(
            describedAs="Omschrijving in zo min mogelijk woorden; liefst slechts een.",
            typicalLength=80)
    @MemberOrder(sequence="2")
    @Property(editing=Editing.DISABLED)
    public String getCompetenceDescription() {
        return competenceDescription;
    }
    
    public void setCompetenceDescription(final String competenceDescription) {
        this.competenceDescription = competenceDescription;
    }
    
    private CompetenceCategory competenceCategory;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @PropertyLayout(
            describedAs="Kies de categorie")
    @MemberOrder(sequence="3")
    public CompetenceCategory getCompetenceCategory(){
        return competenceCategory;
    }
    
    public void setCompetenceCategory(final CompetenceCategory competenceCategory){
        this.competenceCategory = competenceCategory;
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
    
    @ActionLayout()
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public List<Competence> deleteCompetence(
            @ParameterLayout(named="confirmDelete")
            @Parameter(optionality=Optionality.OPTIONAL)
            boolean confirmDelete
            ){
        container.removeIfNotAlready(this);
        container.informUser("Competentie verwijderd");
        return competences.allCompetences();
    }
    
    public String validateDeleteCompetence(boolean confirmDelete) {
        return confirmDelete? null:"CONFIRM_DELETE";
    }
    
    //END ACTIONS ////////////////////////////////////////////////////////////////////////////////////////////
 
    @Inject
    private DomainObjectContainer container;
    
    @Inject
    Competences competences;
}
