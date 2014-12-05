package info.matchingservice.dom.Match;

import info.matchingservice.dom.Profile.ProfileElementNumeric;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.dom.Profile.ProfileType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Named;

import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NotContributed;
import org.apache.isis.applib.annotation.NotContributed.As;
import org.apache.isis.applib.annotation.NotInServiceMenu;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;

@DomainService
public class NumericElementComparisonService extends AbstractService {
    // Thresholds
    final Integer MATCHING_ElEMENT_THRESHOLD = 70;
    
    //NUMERIC MATCHES//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //return matches on Numeric ProfileElements only for profiles of Type Supply_Person_Profile
    @NotInServiceMenu
    @NotContributed(As.ACTION)
    @ActionSemantics(Of.SAFE)
    @Render(Type.EAGERLY)
    @Named("Gevonden matching persoon profiel elementen")
    public List<ElementComparison> showElementMatches(ProfileElementNumeric element){
        
        List<ElementComparison> elementMatches = new ArrayList<ElementComparison>();
        
        //Init Test: Only if there are any Profiles
        if (container.allInstances(ProfileElementNumeric.class).isEmpty()) {
            return elementMatches;
        }
        
        for (ProfileElementNumeric e : container.allInstances(ProfileElementNumeric.class)) {
            if (e.getProfileElementOwner().getSupplyProfileOwner()!=null  &&  e.getProfileElementOwner().getProfileType() == ProfileType.PERSON_PROFILE && e.getProfileElementType() == ProfileElementType.NUMERIC){
                // uitsluiten van dezelfde owner
                // drempelwaarde is MATCHING_THRESHOLD
                Integer matchValue = 100 - 10*Math.abs(element.getNumericValue() - e.getNumericValue());
                if (matchValue >= MATCHING_ElEMENT_THRESHOLD && !e.getOwnedBy().equals(element.getOwnedBy())) {
                    ElementComparison matchTmp = new ElementComparison(element.getProfileElementOwner(), element, e, e.getProfileElementOwner(), e.getProfileElementOwner().getSupplyProfileOwner().getSupplyOwner(), matchValue);
                    elementMatches.add(matchTmp);
                }
            }
        }
        Collections.sort(elementMatches);
        Collections.reverse(elementMatches);
        return elementMatches;
    }
    
    // Hide the contributed List except on Profiles of type Demand_Person_Profile
    public boolean hideShowElementMatches(ProfileElementNumeric element){
        return element.getProfileElementOwner().getDemandProfileOwner()==null;
    }
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
}

