package info.matchingservice.dom.Competence;

import java.util.List;

import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.ParameterLayout;

import info.matchingservice.dom.MatchingDomainService;

@DomainService(repositoryFor = CompetenceCategory.class)
@DomainServiceLayout(
        named="Beheer",
        menuBar = DomainServiceLayout.MenuBar.PRIMARY,
        menuOrder = "80"
)
public class CompetenceCategories extends MatchingDomainService<CompetenceCategory> {
    
    public CompetenceCategories(){
        super(CompetenceCategories.class, CompetenceCategory.class);
    }
    
    @ActionLayout(named="Alle competentie categorieën")
    public List<CompetenceCategory> allCompetenceCategories() {
        return allInstances();
    }
    
    @ActionLayout(named="Nieuwe competentie categorie")
    public CompetenceCategory newCompetenceCategory(
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
    
    public String validateNewCompetenceCategory(final String competenceCategoryDescription){
        if (!this.findCompetenceCategoryMatches(competenceCategoryDescription).isEmpty()){
            return "Deze categorie is al eerder ingevoerd";
        }
        return null;
    }
    
    public List<CompetenceCategory> findCompetenceCategoryContains(final String competenceCategoryDescription){
        return allMatches("competenceCategoryContains", "competenceCategoryDescription", competenceCategoryDescription.toLowerCase());
    }
    
    public List<CompetenceCategory> findCompetenceCategoryMatches(final String competenceCategoryDescription){
        return allMatches("competenceCategoryMatches", "competenceCategoryDescription", competenceCategoryDescription.toLowerCase());
    }

}
