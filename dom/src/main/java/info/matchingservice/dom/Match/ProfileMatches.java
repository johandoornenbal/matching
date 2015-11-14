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

package info.matchingservice.dom.Match;

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.TrustLevel;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.joda.time.LocalDateTime;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@DomainService(repositoryFor = ProfileMatch.class, nature=NatureOfService.VIEW_CONTRIBUTIONS_ONLY)
@DomainServiceLayout(menuOrder="100")
public class ProfileMatches extends MatchingDomainService<ProfileMatch> {

    public ProfileMatches() {
        super(ProfileMatches.class, ProfileMatch.class);
    }

    @Programmatic
    public List<ProfileMatch> allProfileMatches() {
        return container.allInstances(ProfileMatch.class);
    }
    
    @Programmatic
    public List<ProfileMatch> findProfileMatchesByDemandProfile(Profile demandProfile) {
    	return allMatches("findProfileMatchesByDemandProfile",
                "profile", demandProfile);
    }

    @Programmatic
    public List<ProfileMatch> findProfileMatchesByDemandProfileAndStatus(final Profile demandProfile, final CandidateStatus candidateStatus) {
        return allMatches("findProfileMatchesByDemandProfileAndStatus",
                "profile", demandProfile, "candidateStatus", candidateStatus);
    }
    
    @ActionLayout(hidden=Where.EVERYWHERE)
    public ProfileMatch newProfileMatch(
            @ParameterLayout(named="ownerActor")
            Person ownerActor,
            @ParameterLayout(named="supplyCandidate")
            Person vacancyCandidate,
            @ParameterLayout(named="demandProfile")
            Profile vacancyProfile,
            @ParameterLayout(named="matchingSupplyProfile")
            Profile matchingSupplyProfile
            ){
        return newProfileMatch(ownerActor, vacancyCandidate, vacancyProfile, matchingSupplyProfile, currentUserName(), CandidateStatus.CANDIDATE);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<ProfileMatch> collectProfileMatches(final Actor supplyCandidate){
        return allMatches("findProfileMatchesBySupplyCandidate",
                "supplyCandidate", supplyCandidate);
    }

    // BusinessRule: hide for users not in intimate circle
    public boolean hideCollectProfileMatches(final Actor supplyCandidate){
        return supplyCandidate.allowedTrustLevel(TrustLevel.INTIMATE);
    }

    //Region> Helpers ///////////////////////////////
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    @Programmatic
    public ProfileMatch newProfileMatch(
            Person ownerActor,
            Person supplyCandidate,
            Profile demandProfile,
            Profile matchingSupplyProfile,
            String owner,
            CandidateStatus candidateStatus
            ){
        ProfileMatch newMatch = newTransientInstance(ProfileMatch.class);
        final UUID uuid=UUID.randomUUID();
        newMatch.setUniqueItemId(uuid);
        newMatch.setOwner(ownerActor);
        newMatch.setSupplyCandidate(supplyCandidate);
        newMatch.setProfile(demandProfile);
        newMatch.setMatchingSupplyProfile(matchingSupplyProfile);
        newMatch.setOwnedBy(owner);
        newMatch.setCandidateStatus(candidateStatus);
        newMatch.setTimeStamp(new LocalDateTime().now());
        persist(newMatch);
        getContainer().flush();
        return newMatch;
    }

    // Api v1
    @Programmatic
    public ProfileMatch matchProfileApiId(final String id) {

        for (ProfileMatch p : allInstances(ProfileMatch.class)) {
            String[] parts = p.getOID().split(Pattern.quote("[OID]"));
            String part1 = parts[0];
            if (id.equals(part1)) {
                return p;
            }
        }

        return null;

    }
    
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;

}
