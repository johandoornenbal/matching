/*
 *
 *  Copyright 2015 Yodo Int. Projects and Consultancy
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package info.matchingservice.dom.Assessment;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Profile.Profile;


@DomainService(repositoryFor = Assessment.class, nature=NatureOfService.VIEW_CONTRIBUTIONS_ONLY)
public class Assessments extends MatchingDomainService<Assessment> {

    public Assessments() {
        super(Assessments.class, Assessment.class);
    }
    
    @Programmatic
    public List<Assessment> allAssessments(){
        return allInstances();
    }
    
    //** API: ACTIONS **//
    
    //** createDemandAssessment **//
    public Assessment createDemandAssessment(
            @ParameterLayout(named="targetOfAssessment")
            final Demand targetOfAssessment,
            @ParameterLayout(named="assessmentDescription")
            final String assessmentDescription,
            @ParameterLayout(named="feedback", multiLine=3)
            final String feedback
            ){
    	return createDemandAssessment(targetOfAssessment, persons.findPersonUnique(currentUserName()), assessmentDescription, feedback, currentUserName());
    }
    
    // Business rule:
    // No feedback on own demand
    public boolean hideCreateDemandAssessment(
            final Demand targetOfAssessment,
            final String assessmentDescription,
            final String feedback            
            ){
        if (targetOfAssessment.getOwnedBy().equals(currentUserName())){
            return true;
        }
        
        return false;
    }
    //-- createDemandAssessment --//
    
    //** createSupplyAssessment **//
    public Assessment createSupplyAssessment(
            @ParameterLayout(named="targetOfAssessment")
            final Supply targetOfAssessment,
            @ParameterLayout(named="assessmentDescription")
            final String assessmentDescription,
            @ParameterLayout(named="feedback", multiLine=3)
            final String feedback
            ){
        return createSupplyAssessment(targetOfAssessment, persons.findPersonUnique(currentUserName()), assessmentDescription, feedback, currentUserName());
    }
    
    //BUSINESS RULE
    //Geen feedback op eigen supply
    public boolean hideCreateSupplyAssessment(
            final Supply targetObject,
            final String description,
            final String feedback            
            ){
        if (targetObject.getOwnedBy().equals(currentUserName())){
            return true;
        }
        
        return false;
    }
    //-- createSupplyAssessment --//
    
    //** createProfileAssessment **//
    public Assessment createProfileAssessment(
            @ParameterLayout(named="targetOfAssessment")
            final Profile targetOfAssessment,
            @ParameterLayout(named="assessmentDescription")
            final String assessmentDescription,
            @ParameterLayout(named="feedback", multiLine=3)
            final String feedback
            ){
        return createProfileAssessment(
                targetOfAssessment,
                persons.findPersonUnique(currentUserName()),
                assessmentDescription,
                feedback,
                currentUserName());
    }
    
    //BUSINESS RULE
    //Geen feedback op eigen profile
    public boolean hideCreateProfileAssessment(
            final Profile targetObject,
            final String description,
            final String feedback            
            ){
        if (targetObject.getOwnedBy().equals(currentUserName())){
            return true;
        }
        
        return false;
    }
    //-- createProfileAssessment --//
    
    //-- API: ACTIONS --//

    //** HELPERS **//
    //** HELPERS: generic service helpers **//
    private String currentUserName() {
        return container.getUser().getName();
    }
    //-- HELPERS: generic service helpers --//
    //** HELPERS: programmatic actions **//
    
    @Programmatic
    public Assessment createDemandAssessment(
            final Demand targetObject,
            final Actor ownerActor,
            final String description,
            final String feedback,
            final String ownedBy
            ){
        final DemandFeedback newAs = newTransientInstance(DemandFeedback.class);
        final UUID uuid=UUID.randomUUID();
        newAs.setUniqueItemId(uuid);
        newAs.setTargetOfAssessment(targetObject);
        newAs.setTargetOwnerActor(targetObject.getDemandOwner());
        newAs.setAssessmentOwnerActor(ownerActor);
        newAs.setAssessmentDescription(description);
        newAs.setFeedback(feedback);
        newAs.setOwnedBy(ownedBy);
        persist(newAs);
        return newAs;
    }
    
    @Programmatic
    public Assessment createSupplyAssessment(
            final Supply targetObject,
            final Actor ownerActor,
            final String description,
            final String feedback,
            final String ownedBy
            ){
        final SupplyFeedback newAs = newTransientInstance(SupplyFeedback.class);
        final UUID uuid=UUID.randomUUID();
        newAs.setUniqueItemId(uuid);
        newAs.setTargetOfAssessment(targetObject);
        newAs.setTargetOwnerActor(targetObject.getSupplyOwner());
        newAs.setAssessmentOwnerActor(ownerActor);
        newAs.setAssessmentDescription(description);
        newAs.setFeedback(feedback);
        newAs.setOwnedBy(ownedBy);
        persist(newAs);
        return newAs;
    }

    @Programmatic
    public Assessment createProfileAssessment(
            final Profile targetObject,
            final Actor ownerActor,
            final String description,
            final String feedback,
            final String ownedBy
    ){
        final ProfileFeedback newAs = newTransientInstance(ProfileFeedback.class);
        final UUID uuid=UUID.randomUUID();
        newAs.setUniqueItemId(uuid);
        newAs.setTargetOfAssessment(targetObject);
        newAs.setTargetOwnerActor(targetObject.getActorOwner());
        newAs.setAssessmentOwnerActor(ownerActor);
        newAs.setAssessmentDescription(description);
        newAs.setFeedback(feedback);
        newAs.setOwnedBy(ownedBy);
        persist(newAs);
        return newAs;
    }

    //-- HELPERS: programmatic actions --//
    
    //-- HELPERS --//
    
	//** INJECTIONS **//
    @javax.inject.Inject
    private DomainObjectContainer container;
    
    @Inject
    private Persons persons;
	//-- INJECTIONS --//

}
