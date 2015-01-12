package info.matchingservice.dom.Competence;

import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Immutable;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.PropertyLayout;

import info.matchingservice.dom.MatchingMutableObject;

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
@Immutable
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
            named="Competentie",
            describedAs="Omschrijving in zo min mogelijk woorden; liefst slechts een.",
            typicalLength=80)
    @MemberOrder(sequence="2")
    @Disabled
    public String getCompetenceDescription() {
        return competenceDescription;
    }
    
    public void setCompetenceDescription(final String competenceDescription) {
        this.competenceDescription = competenceDescription;
    }
    
    private CompetenceCategory competenceCategory;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @PropertyLayout(
            named="Competentie categorie",
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
    @Disabled
    @Hidden
    public String getDisplCategory(){
        return displCategory;
    }
    
    public void setDisplCategory(final String displCategory){
        this.displCategory = displCategory;
    }
    
    //delete action /////////////////////////////////////////////////////////////////////////////////////
    
    @PropertyLayout(
            named = "Competentie verwijderen"
            )
    public List<Competence> DeleteCompetence(
            @Optional
            @ParameterLayout(
                    named = "Verwijderen OK?"
                    )
            boolean areYouSure
            ){
        container.removeIfNotAlready(this);
        container.informUser("Competentie verwijderd");
        return competences.allCompetences();
    }
    
    public String validateDeleteCompetence(boolean areYouSure) {
        return areYouSure? null:"Geef aan of je wilt verwijderen";
    }
    
    //END ACTIONS ////////////////////////////////////////////////////////////////////////////////////////////
 
    @Inject
    private DomainObjectContainer container;
    
    @Inject
    Competences competences;
}
