package info.matchingservice.dom.Need;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public class VacancyProfileFigureElement extends VacancyProfileElement {
    
    private Integer figure;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "10")
    public Integer getFigure() {
        return figure;
    }
    
    public void setFigure(final Integer figure) {
        this.figure = figure;
    }
    
    @Named("Bewerk getal")
    public VacancyProfileFigureElement EditFigure(
            @Named("getal")
            Integer newInt
            ){
        this.setFigure(newInt);
        return this;
    }
    
    public Integer default0EditFigure() {
        return getFigure();
    }
    
    // Helpers
    
    public String toString(){
        return "Gezocht getal: " + getFigure();
    }
}
