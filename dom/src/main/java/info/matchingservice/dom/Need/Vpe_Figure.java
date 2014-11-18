package info.matchingservice.dom.Need;

import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;

import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
public class Vpe_Figure extends VacancyProfileElement {
    
    private Integer figure;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "10")
    public Integer getFigure() {
        return figure;
    }
    
    public void setFigure(final Integer figure) {
        this.figure = figure;
    }
    
    // Region actions
    @Named("Bewerk getal")
    public Vpe_Figure EditFigure(
            @Named("getal")
            Integer newInt
            ){
        this.setFigure(newInt);
        return this;
    }
    
    public Integer default0EditFigure() {
        return getFigure();
    }
    
    @Named("Bewerk gewicht")
    public Vpe_Figure EditWeight(
            @Named("gewicht")
            Integer newInt
            ){
        this.setWeight(newInt);
        return this;
    }
    
    public Integer default0EditWeight() {
        return getWeight();
    }


    //Helpers
    
}
