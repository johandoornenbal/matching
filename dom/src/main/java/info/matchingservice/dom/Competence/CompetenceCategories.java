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

import info.matchingservice.dom.MatchingDomainService;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(repositoryFor = CompetenceCategory.class, nature=NatureOfService.DOMAIN)
@DomainServiceLayout(
        named="Beheer",
        menuBar = DomainServiceLayout.MenuBar.PRIMARY,
        menuOrder = "80"
)
public class CompetenceCategories extends MatchingDomainService<CompetenceCategory> {
    
    public CompetenceCategories(){
        super(CompetenceCategories.class, CompetenceCategory.class);
    }
    
    @Programmatic
    public List<CompetenceCategory> allCompetenceCategories() {
        return allInstances();
    }
    
    @ActionLayout()
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public CompetenceCategory createCompetenceCategory(
            @ParameterLayout(
                    named = "competenceCategoryDescription",
                    describedAs="Verzameling van aantal competenties om op te matchen.",
                    typicalLength=80
                    )
            final String competenceCategoryDescription
            ){
        final CompetenceCategory newCompetenceCategory = newTransientInstance(CompetenceCategory.class);
        newCompetenceCategory.setCompetenceCategoryDescription(competenceCategoryDescription.toLowerCase());
        persist(newCompetenceCategory);
        return newCompetenceCategory;
    }
    
    public String validateCreateCompetenceCategory(final String competenceCategoryDescription){
        if (!this.findCompetenceCategoryMatches(competenceCategoryDescription).isEmpty()){
            return "ONE_INSTANCE_AT_MOST";
        }
        return null;
    }
    
    @Programmatic
    public List<CompetenceCategory> findCompetenceCategoryContains(final String competenceCategoryDescription){
        return allMatches("competenceCategoryContains", "competenceCategoryDescription", competenceCategoryDescription.toLowerCase());
    }
    
    @Programmatic
    public List<CompetenceCategory> findCompetenceCategoryMatches(final String competenceCategoryDescription){
        return allMatches("competenceCategoryMatches", "competenceCategoryDescription", competenceCategoryDescription.toLowerCase());
    }

}
