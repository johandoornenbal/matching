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

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.ParameterLayout;

import info.matchingservice.dom.MatchingDomainService;

@DomainService(repositoryFor = Competence.class)
@DomainServiceLayout(
        named="Beheer",
        menuBar = DomainServiceLayout.MenuBar.PRIMARY,
        menuOrder = "80"
)
public class Competences extends MatchingDomainService<Competence> {
    
    public Competences(){
        super(Competences.class, Competence.class);
    }
    
    @ActionLayout(named="Alle competenties")
    public List<Competence> allCompetences() {
        return allInstances();
    }
    
    @ActionLayout(named="Nieuwe competentie")
    public CompetenceCategory newCompetence(
            @ParameterLayout(
                    named = "competenceCategory")
            final CompetenceCategory competenceCategory,
            @ParameterLayout(
                    named = "competenceDescription",
                    describedAs="Omschrijving in zo min mogelijk woorden; liefst slechts een.",
                    typicalLength=80
                    )
            final String competenceDescription
            ){
        final Competence newCompetence = newTransientInstance(Competence.class);
        newCompetence.setCompetenceDescription(competenceDescription.toLowerCase());
        newCompetence.setCompetenceCategory(competenceCategory);
        newCompetence.setDisplCategory(competenceCategory.title());
        persist(newCompetence);
        return competenceCategory;
    }
    
    public List<CompetenceCategory> autoComplete0NewCompetence(final String search) {
        return competenceCategories.findCompetenceCategoryContains(search);
    }
    
    public String validateNewCompetence(final CompetenceCategory competenceCategory, final String competenceDescription){
        if (!this.findCompetenceMatches(competenceDescription).isEmpty()){
            return "Deze competentie is al eerder ingevoerd";
        }
        return null;
    }
    
    public List<Competence> findCompetenceContains(final String competenceDescription){
        return allMatches("competenceContains", "competenceDescription", competenceDescription.toLowerCase());
    }
    
    public List<Competence> findCompetenceMatches(final String competenceDescription){
        return allMatches("competenceMatches", "competenceDescription", competenceDescription.toLowerCase());
    }    
    
    @Inject
    CompetenceCategories competenceCategories;

}
