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

package info.matchingservice.dom.Match.MatchingHelpers;

import org.joda.time.LocalDate;
import org.joda.time.Years;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Match.ProfileElementComparison;
import info.matchingservice.dom.Profile.ProfileElementNumeric;
import info.matchingservice.dom.Profile.ProfileElementUsePredicate;
import info.matchingservice.dom.Profile.ProfileType;

/**
 * Created by jodo on 29/08/15.
 */
@DomainService(nature = NatureOfService.DOMAIN)
public class ProfileElementAgeComparison {

    public ProfileElementComparison getProfileElementComparison(
            final ProfileElementNumeric demandProfileElementNumeric,
            final ProfileElementUsePredicate supplyProfileElementUsePredicate
    ){

        Integer matchValue = 0;

        // When supply profile date of birth is meant to be used, indicated by supplyProfileElement.getUseAge() == true
        if (supplyProfileElementUsePredicate.getUseAge()) {

            // Make sure the supplyProfileElement belongs to a Person Profile of a Person with dateOfBirth property not null
            Person supplyProfileElementPersonOwner = (Person) supplyProfileElementUsePredicate.getProfileElementOwner().getOwner();


            if (

                    supplyProfileElementPersonOwner.getDateOfBirth() != null
                            &&
                            supplyProfileElementUsePredicate.getProfileElementOwner().getType() == ProfileType.PERSON_PROFILE
                    ) {
                LocalDate supplyDateOfBirth = supplyProfileElementPersonOwner.getDateOfBirth();
                new LocalDate();
                LocalDate toDay = LocalDate.now();
                Integer age = Years.yearsBetween(supplyDateOfBirth, toDay).getYears();
                Integer delta = Math.abs(age - demandProfileElementNumeric.getNumericValue());

                // The value of the match is 100 minus 5 x the difference in years between the demand age and the supplied age
                matchValue = 100 - 5 * delta;
                if (matchValue < 0) {
                    matchValue = 0;
                }

                System.out.println("match from getProfileElementAgeComparison() in ProfileMatchingService.class:");
                System.out.println("owner username: " + supplyProfileElementPersonOwner.getOwnedBy() + " - dateOfBirth: " + supplyProfileElementPersonOwner.getDateOfBirth());
                System.out.println("demanded age: " + demandProfileElementNumeric.getNumericValue() + " - matchValue: " + matchValue);
            }

        }

        ProfileElementComparison profileElementComparison = new ProfileElementComparison(
                demandProfileElementNumeric.getProfileElementOwner(),
                demandProfileElementNumeric,
                supplyProfileElementUsePredicate,
                supplyProfileElementUsePredicate.getProfileElementOwner(),
                supplyProfileElementUsePredicate.getProfileElementOwner().getOwner(),
                matchValue,
                demandProfileElementNumeric.getWeight()
        );
        return profileElementComparison;

    }

}
