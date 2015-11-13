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

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Match.ProfileMatch;
import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Profile.Profile;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.joda.time.LocalDateTime;

import java.util.List;
import java.util.UUID;


@DomainService(repositoryFor = Assessment.class, nature=NatureOfService.DOMAIN)
public class Assessments extends MatchingDomainService<Assessment> {

    public Assessments() {
        super(Assessments.class, Assessment.class);
    }
    
    @Programmatic
    public List<Assessment> allAssessments(){
        return allInstances();
    }
    
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
        newAs.setTargetOwnerActor(targetObject.getOwner());
        newAs.setAssessmentOwnerActor(ownerActor);
        newAs.setDescription(description);
        newAs.setFeedback(feedback);
        newAs.setOwnedBy(ownedBy);
        newAs.setTimeStamp(LocalDateTime.now());
        persistIfNotAlready(newAs);
        getContainer().flush();
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
        newAs.setTargetOwnerActor(targetObject.getOwner());
        newAs.setAssessmentOwnerActor(ownerActor);
        newAs.setDescription(description);
        newAs.setFeedback(feedback);
        newAs.setOwnedBy(ownedBy);
        newAs.setTimeStamp(LocalDateTime.now());
        persistIfNotAlready(newAs);
        getContainer().flush();
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
        newAs.setDescription(description);
        newAs.setFeedback(feedback);
        newAs.setOwnedBy(ownedBy);
        newAs.setTimeStamp(LocalDateTime.now());
        persistIfNotAlready(newAs);
        getContainer().flush();
        return newAs;
    }

    @Programmatic
    public Assessment createProfileMatchAssessment(
            final ProfileMatch targetObject,
            final Actor ownerActor,
            final String description,
            final String ownedBy
    ){
        final ProfileMatchAssessment newAs = newTransientInstance(ProfileMatchAssessment.class);
        final UUID uuid=UUID.randomUUID();
        newAs.setUniqueItemId(uuid);
        newAs.setTargetOfAssessment(targetObject);
        newAs.setTargetOwnerActor(targetObject.getOwnerActor());
        newAs.setAssessmentOwnerActor(ownerActor);
        newAs.setDescription(description);
        newAs.setOwnedBy(ownedBy);
        newAs.setTimeStamp(LocalDateTime.now());
        persistIfNotAlready(newAs);
        getContainer().flush();
        return newAs;
    }

    
	//** INJECTIONS **//

	//-- INJECTIONS --//

}
