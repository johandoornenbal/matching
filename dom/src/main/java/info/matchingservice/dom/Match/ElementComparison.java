package info.matchingservice.dom.Match;

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Need.VacancyProfile;
import info.matchingservice.dom.Need.VacancyProfileElement;
import info.matchingservice.dom.Profile.ProfileElement;

import org.apache.isis.applib.annotation.ViewModel;

@ViewModel
public class ElementComparison {
    
    public ElementComparison(VacancyProfile elementOwner, VacancyProfileElement elementInitiator, ProfileElement matchingProfileElement, Actor matchingProfileOwner, Integer value){
        this.elementOwner = elementOwner;
        this.elementInitiator = elementInitiator;
        this.matchingProfileElement = matchingProfileElement;
        this.matchingProfileOwner = matchingProfileOwner;
        this.calculatedMatchingValue = value;        
    }
    
    private VacancyProfile elementOwner;
    
    public VacancyProfile getElementOwner() {
        return elementOwner;
    }
    
    public void setElementOwner(final VacancyProfile owner) {
        this.elementOwner = owner;
    }
    
    private VacancyProfileElement elementInitiator;
    
    public VacancyProfileElement getElementInitiator() {
        return elementInitiator;
    }
    
    public void setElementInitiator(final VacancyProfileElement initiator) {
        this.elementInitiator = initiator;
    }
    
    private ProfileElement matchingProfileElement;
    
    public ProfileElement getMatchingProfileElement(){
        return matchingProfileElement;
    }
    
    public void setMatchingProfileElement(final ProfileElement element) {
        this.matchingProfileElement = element;
    }
    
    private Actor matchingProfileOwner;
    
    public Actor getMatchingProfileOwner(){
        return matchingProfileOwner;
    }
    
    public void setMatchingProfileOwner(final Actor owner) {
        this.matchingProfileOwner = owner;
    }
    
    private Integer calculatedMatchingValue;
    
    public Integer getCalculatedMatchingValue() {
        return calculatedMatchingValue;
    }
    
    public void setCalculatedMatchingValue(final Integer matchingvalue) {
        this.calculatedMatchingValue = matchingvalue;
    }
    
    //helpers
    
    public String toString() {
        return getElementInitiator().toString() + " vs. " + getMatchingProfileElement().toString();
    }

}
