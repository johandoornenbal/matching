package info.matchingservice.dom.Match;

import com.google.common.collect.ComparisonChain;

import info.matchingservice.dom.MatchingDomainObject;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Need.VacancyProfile;
import info.matchingservice.dom.Need.VacancyProfileElement;
import info.matchingservice.dom.Profile.ProfileElement;

import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.ViewModel;

@ViewModel
public class ElementComparison extends MatchingDomainObject<ElementComparison>{
    
    public ElementComparison(VacancyProfile elementOwner, VacancyProfileElement elementInitiator, ProfileElement matchingProfileElement, Actor matchingProfileOwner, Integer value){
        super("elementOwner");
        this.elementOwner = elementOwner;
        this.elementInitiator = elementInitiator;
        this.matchingProfileElement = matchingProfileElement;
        this.matchingProfileOwner = matchingProfileOwner;
        this.calculatedMatchingValue = value;        
    }
    
    private VacancyProfile elementOwner;
    
    @Hidden
    public VacancyProfile getElementOwner() {
        return elementOwner;
    }
    
    public void setElementOwner(final VacancyProfile owner) {
        this.elementOwner = owner;
    }
    
    private VacancyProfileElement elementInitiator;
    
    @Hidden
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
    
    public int compareTo(ElementComparison that) {
        return ComparisonChain.start()
            .compare(this.calculatedMatchingValue, that.calculatedMatchingValue)
            .compare(this.matchingProfileOwner, that.matchingProfileOwner)
            .result();
    }

}
