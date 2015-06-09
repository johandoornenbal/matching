/*
 * Copyright 2015 Yodo Int. Projects and Consultancy
 *
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package info.matchingservice.dom.Assessment;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.SemanticsOf;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Match.ProfileMatch;
import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.TrustLevel;

@DomainService(nature=NatureOfService.VIEW_CONTRIBUTIONS_ONLY)
public class AssessmentContributions extends MatchingDomainService<Assessment> {

    public AssessmentContributions() {
        super(AssessmentContributions.class, Assessment.class);
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
    	return assessments.createDemandAssessment(targetOfAssessment, persons.findPersonUnique(currentUserName()), assessmentDescription, feedback, currentUserName());
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
        return assessments.createSupplyAssessment(targetOfAssessment, persons.findPersonUnique(currentUserName()), assessmentDescription, feedback, currentUserName());
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
        return assessments.createProfileAssessment(
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

    //** createProfileMatchAssessment **//
    public Assessment createProfileMatchAssessment(
            @ParameterLayout(named="targetOfAssessment")
            final ProfileMatch targetOfAssessment,
            @ParameterLayout(named="assessmentDescription")
            final String assessmentDescription
    ){
        return assessments.createProfileMatchAssessment(
                targetOfAssessment,
                persons.findPersonUnique(currentUserName()),
                assessmentDescription,
                currentUserName());
    }

    //BUSINESS RULE
    //Geen feedback op eigen profile
    public boolean hideCreateProfileMatchAssessment(
            final ProfileMatch targetObject,
            final String description
    ){
        if (targetObject.getOwnedBy().equals(currentUserName())){
            return true;
        }

        return false;
    }
    //-- createProfileMatchAssessment --//

    //** collectAssessments **//

    @CollectionLayout(render = RenderType.EAGERLY)
    @Action(semantics = SemanticsOf.SAFE)
    public List<ProfileMatchAssessment> collectAssessments(final ProfileMatch profileMatch){

        return profileMatchAssessments.findProfileMatchesAssessmentByProfileMatch(profileMatch);

    }

    // BusinessRule: hide for users not in intimate circle
    public boolean hideCollectAssessments(final ProfileMatch profileMatch){

        return profileMatch.getOwnerActor().allowedTrustLevel(TrustLevel.INTIMATE);

    }

    //-- collectAssessments --//

    //-- API: ACTIONS --//

    //** HELPERS **//
    //** HELPERS: generic service helpers **//
    private String currentUserName() {
        return container.getUser().getName();
    }
    //-- HELPERS: generic service helpers --//
    //-- HELPERS --//

	//** INJECTIONS **//
    @Inject
    private DomainObjectContainer container;
    
    @Inject
    private Persons persons;

    @Inject
    private Assessments assessments;

    @Inject
    private ProfileMatchAssessments profileMatchAssessments;

	//-- INJECTIONS --//

}
