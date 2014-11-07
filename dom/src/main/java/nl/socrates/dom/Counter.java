package nl.socrates.dom;

import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.DomainObjectContainer;


//TODO: dit zou een class van tellers moeten worden; geen idee hoe dit te initieren en werkend te krijgen per click

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public final class Counter {
    
    public void createCounter(){
       Counter counter;
       counter = container.newTransientInstance(Counter.class);
       counter.setPersonClick(0);
       container.persistIfNotAlready(counter);
       return;
    }
    
    public static Integer personClick;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public Integer getPersonClick() {
        return personClick;
    }
    
    public void setPersonClick(final Integer personclick){
       personClick = personclick;
    }
    
    public void addPersonClick() {
        personClick ++;
    }
    
    
    @javax.inject.Inject 
    DomainObjectContainer container;
}
