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

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import info.matchingservice.dom.Match.ProfileMatch;
import info.matchingservice.dom.MatchingDomainService;

@DomainService(repositoryFor = ProfileMatchAssessment.class, nature=NatureOfService.DOMAIN)
public class ProfileMatchAssessments extends MatchingDomainService<ProfileMatchAssessment> {

    public ProfileMatchAssessments() {
        super(ProfileMatchAssessments.class, ProfileMatchAssessment.class);
    }

    @Programmatic
    public List<ProfileMatchAssessment> findProfileMatchesAssessmentByProfileMatch(final ProfileMatch profileMatch){

        return allMatches("findProfileMatchesAssessmentByProfileMatch", "profileMatch", profileMatch);

    }
    


}
