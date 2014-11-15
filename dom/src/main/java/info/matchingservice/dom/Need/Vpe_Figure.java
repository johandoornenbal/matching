package info.matchingservice.dom.Need;

import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;

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
    public Integer getFigure() {
        return figure;
    }
    
    public void setFigure(final Integer figure) {
        this.figure = figure;
    }

}
