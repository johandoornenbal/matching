package info.matchingservice.dom.Match;

import info.matchingservice.dom.Profile.ProfileElementDropDown;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.dom.Profile.ProfileType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Named;

import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NotContributed;
import org.apache.isis.applib.annotation.NotContributed.As;
import org.apache.isis.applib.annotation.NotInServiceMenu;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService
public class DropDownElementComparisonService extends AbstractService {
    // Thresholds
    final Integer MATCHING_ElEMENT_THRESHOLD = 70;
    
    //  //DROPDOWNS//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //  //return matches on Dropdown ProfileElements only for profiles of Type Supply_Person_Profile
  @NotInServiceMenu
  @NotContributed(As.ACTION)
  @CollectionLayout(render=RenderType.EAGERLY)
  @Action(semantics=SemanticsOf.SAFE)
  @Named("Gevonden matching kwaliteiten op persoon profiel elementen")
  public List<ElementComparison> showDropDownElementMatches(ProfileElementDropDown element){
      
      List<ElementComparison> elementMatches = new ArrayList<ElementComparison>();
      
      //Init Test: Only if there are any Profiles
      if (container.allInstances(ProfileElementDropDown.class).isEmpty()) {
          return elementMatches;
      }
      
      for (ProfileElementDropDown e : container.allInstances(ProfileElementDropDown.class)) {
          if (e.getProfileElementOwner().getSupplyProfileOwner()!=null  &&  e.getProfileElementOwner().getProfileType() == ProfileType.PERSON_PROFILE && e.getProfileElementType() == ProfileElementType.QUALITY){
              // uitsluiten van dezelfde owner
              // drempelwaarde is MATCHING_THRESHOLD
              Integer matchValue=0;
              if (element.getDropDownValue().equals(e.getDropDownValue())){
                  matchValue=100;
              }
              if (matchValue >= MATCHING_ElEMENT_THRESHOLD && !e.getOwnedBy().equals(element.getOwnedBy())) {
                  ElementComparison matchTmp = new ElementComparison(element.getProfileElementOwner(), element, e, e.getProfileElementOwner() , e.getProfileElementOwner().getSupplyProfileOwner().getSupplyOwner(), matchValue);
                  elementMatches.add(matchTmp);
              }
          }
      }
      Collections.sort(elementMatches);
      Collections.reverse(elementMatches);
      return elementMatches;
  }
  
  // Hide the contributed List except on Profiles of type Demand_Person_Profile
  public boolean hideShowDropDownElementMatches(ProfileElementDropDown element){
      return element.getProfileElementOwner().getDemandProfileOwner()==null;
  }
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
}

