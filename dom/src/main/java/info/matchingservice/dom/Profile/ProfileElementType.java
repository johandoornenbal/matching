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

package info.matchingservice.dom.Profile;

import java.util.Iterator;

import org.joda.time.Days;

import org.isisaddons.services.postalcode.postcodenunl.Haversine;

import info.matchingservice.dom.Match.MatchingHelpers.ProfileElementAgeComparison;
import info.matchingservice.dom.Match.MatchingHelpers.ProfileElementTagComparison;
import info.matchingservice.dom.Match.ProfileElementComparison;
import info.matchingservice.dom.Tags.Tag;
import info.matchingservice.dom.Tags.TagHolder;
import info.matchingservice.dom.TitledEnum;

public enum ProfileElementType implements TitledEnum {
    
    QUALITY("Quality"){
        @Override
        public ProfileElementComparison getProfileElementComparison(
                final ProfileElement demandProfileElement,
                final ProfileElement supplyProfileElement){
            return null;
        }
    },
    LOCATION("Zipcode"){
        @Override
        public ProfileElementComparison getProfileElementComparison(
                final ProfileElement demandProfileElement,
                final ProfileElement supplyProfileElement){

            if (demandProfileElement.getProfileElementType() == ProfileElementType.LOCATION &&
                    supplyProfileElement.getProfileElementType() == ProfileElementType.LOCATION) {

                ProfileElementLocation demandProfileElementLocation = (ProfileElementLocation) demandProfileElement;
                ProfileElementLocation supplyProfileElementLocation = (ProfileElementLocation) supplyProfileElement;

                // stub
                Integer matchValue = 0;
                double distance = Haversine.haversine(supplyProfileElementLocation.getLatitude(), supplyProfileElementLocation.getLongitude(), demandProfileElementLocation.getLatitude(), demandProfileElementLocation.getLongitude());

                if (distance < 200) {
                    matchValue = 10;
                }
                if (distance < 175) {
                    matchValue = 20;
                }
                if (distance < 150) {
                    matchValue = 30;
                }
                if (distance < 125) {
                    matchValue = 40;
                }
                if (distance < 100) {
                    matchValue = 50;
                }
                if (distance < 75) {
                    matchValue = 60;
                }
                if (distance < 50) {
                    matchValue = 65;
                }
                if (distance < 40) {
                    matchValue = 70;
                }
                if (distance < 35) {
                    matchValue = 75;
                }
                if (distance < 30) {
                    matchValue = 80;
                }
                if (distance < 25) {
                    matchValue = 85;
                }
                if (distance < 20) {
                    matchValue = 90;
                }
                if (distance < 15) {
                    matchValue = 95;
                }
                if (distance < 10) {
                    matchValue = 100;
                }

                System.out.println("match from getProfileElementLocationComparison() in ProfileMatchingService.class:");

                ProfileElementComparison profileElementComparison = new ProfileElementComparison(
                        demandProfileElement.getProfileElementOwner(),
                        demandProfileElement,
                        supplyProfileElement,
                        supplyProfileElement.getProfileElementOwner(),
                        supplyProfileElement.getProfileElementOwner().getOwner(),
                        matchValue,
                        demandProfileElement.getWeight()
                );
                return profileElementComparison;

            }

            return null;
        }
    },
    TEXT("Text"){
        @Override
        public ProfileElementComparison getProfileElementComparison(
                final ProfileElement demandProfileElement,
                final ProfileElement supplyProfileElement){
            return null;
        }
    },
    NUMERIC("Numeric"){
        @Override
        public ProfileElementComparison getProfileElementComparison(
                final ProfileElement demandProfileElement,
                final ProfileElement supplyProfileElement){
            return null;
        }
    },
    PASSION("Passion"){
        @Override
        public ProfileElementComparison getProfileElementComparison(
                final ProfileElement demandProfileElement,
                final ProfileElement supplyProfileElement){
            return null;
        }
    },
    PASSION_TAGS("Passion_tags"){
        @Override
        public ProfileElementComparison getProfileElementComparison(
                final ProfileElement demandProfileElement,
                final ProfileElement supplyProfileElement){

            if (demandProfileElement.getProfileElementType() == ProfileElementType.PASSION_TAGS &&
                    supplyProfileElement.getProfileElementType() == ProfileElementType.PASSION) {

                Integer matchValue = 0;
                ProfileElementTag demandProfileElementTag = (ProfileElementTag) demandProfileElement;
                ProfileElementText supplyProfileElementText = (ProfileElementText) supplyProfileElement;

                for (final Iterator<TagHolder> it = demandProfileElementTag.getCollectTagHolders().iterator(); it.hasNext();)
                {

                    Tag tag = it.next().getTag();

                    if (supplyProfileElementText.getTextValue().toLowerCase().matches("(.*)" + tag.getTagDescription() + "(.*)"))
                    {

                        matchValue += 100;
                        System.out.println("match from getProfileElementPassionTagComparison() in ProfileMatchingService.class:");
                        System.out.println("supply of: " + supplyProfileElement.getOwnedBy() + "type: " + supplyProfileElement.getProfileElementType().toString());
                        System.out.println(tag.getTagDescription() + " matchValue: " + matchValue);

                    }
                }

                // take the average matchValue of all Tags
                if (demandProfileElementTag.getCollectTagHolders().size() > 0){

                    matchValue = matchValue / demandProfileElementTag.getCollectTagHolders().size();

                } else {

                    matchValue = 0;

                }

                ProfileElementComparison profileElementComparison = new ProfileElementComparison(
                        demandProfileElement.getProfileElementOwner(),
                        demandProfileElement,
                        supplyProfileElement,
                        supplyProfileElement.getProfileElementOwner(),
                        supplyProfileElement.getProfileElementOwner().getOwner(),
                        matchValue,
                        demandProfileElement.getWeight()
                );
                return profileElementComparison;

            }
            return null;
        }
    },
    BRANCHE_TAGS("Branche_tags"){
        @Override
        public ProfileElementComparison getProfileElementComparison(
                final ProfileElement demandProfileElement,
                final ProfileElement supplyProfileElement){

            if (demandProfileElement.getProfileElementType() == ProfileElementType.BRANCHE_TAGS &&
                    supplyProfileElement.getProfileElementType() == ProfileElementType.BRANCHE_TAGS) {

                ProfileElementTag demandProfileElementTag = (ProfileElementTag) demandProfileElement;
                ProfileElementTag supplyProfileElementTag = (ProfileElementTag) supplyProfileElement;
                ProfileElementTagComparison profileElementTagComparison = new ProfileElementTagComparison();

                return profileElementTagComparison.getProfileElementComparison(demandProfileElementTag, supplyProfileElementTag);

            }
            return null;
        }
    },
    QUALITY_TAGS("Quality_tags"){
        @Override
        public ProfileElementComparison getProfileElementComparison(
                final ProfileElement demandProfileElement,
                final ProfileElement supplyProfileElement){

            if (demandProfileElement.getProfileElementType() == ProfileElementType.QUALITY_TAGS &&
                    supplyProfileElement.getProfileElementType() == ProfileElementType.QUALITY_TAGS) {

                ProfileElementTag demandProfileElementTag = (ProfileElementTag) demandProfileElement;
                ProfileElementTag supplyProfileElementTag = (ProfileElementTag) supplyProfileElement;
                ProfileElementTagComparison profileElementTagComparison = new ProfileElementTagComparison();

                return profileElementTagComparison.getProfileElementComparison(demandProfileElementTag, supplyProfileElementTag);

            }
            return null;
        }
    },
    WEEKDAY_TAGS("Weekday_tags"){
        @Override
        public ProfileElementComparison getProfileElementComparison(
                final ProfileElement demandProfileElement,
                final ProfileElement supplyProfileElement){
            if (demandProfileElement.getProfileElementType() == ProfileElementType.WEEKDAY_TAGS &&
                    supplyProfileElement.getProfileElementType() == ProfileElementType.WEEKDAY_TAGS) {

                ProfileElementTag demandProfileElementTag = (ProfileElementTag) demandProfileElement;
                ProfileElementTag supplyProfileElementTag = (ProfileElementTag) supplyProfileElement;
                ProfileElementTagComparison profileElementTagComparison = new ProfileElementTagComparison();

                return profileElementTagComparison.getProfileElementComparison(demandProfileElementTag, supplyProfileElementTag);

            }
            return null;

        }
    },
    TIME_PERIOD ("Time_period"){
        @Override
        public ProfileElementComparison getProfileElementComparison(
                final ProfileElement demandProfileElement,
                final ProfileElement supplyProfileElement){

            if (demandProfileElement.getProfileElementType() == ProfileElementType.TIME_PERIOD &&
                    supplyProfileElement.getProfileElementType() == ProfileElementType.USE_TIME_PERIOD) {

                ProfileElementTimePeriod demandProfileElementTimePeriod = (ProfileElementTimePeriod) demandProfileElement;
                ProfileElementUsePredicate supplyProfileElementUsePredicate = (ProfileElementUsePredicate) supplyProfileElement;

                Integer matchValue = 0;

                // When supply profile dates are meant to be used, indicated by supplyProfileElement.getUseTimePeriod() == true
                if (supplyProfileElementUsePredicate.getUseTimePeriod()) {

                    // Default for supply with Use Time Period set to true
                    matchValue = 100;

                    // if the endDate on demandProfile element is there and the startDate on supplyProfile also
                    // and if startdate later than enddate value = 0;
                    //
                    //	(pic)
                    // 	demand -------------------*enddate* xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                    //	supply xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx *startdate* ----------------
                    if (
                            supplyProfileElement.getProfileElementOwner().getStartDate() != null
                                    &&
                                    supplyProfileElement.getProfileElementOwner().getStartDate().isAfter(demandProfileElementTimePeriod.getEndDate())

                            ) {
                        matchValue = 0;

                        System.out.println("match from getProfileElementTimePeriodComparison() in ProfileMatchingService.class:");
                        System.out.println(supplyProfileElement.getProfileElementOwner().getOwner().toString() + " >> start supply later than end demand");

                    }

                    // if the startDate on demandProfile element is there and the endDate on supplyProfile also
                    // and if startdate later than enddate value = 0;
                    //
                    // 	(pic)
                    // 	demand xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx *startdate* ----------------
                    // 	supply -------------------*enddate* xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

                    if (
                            supplyProfileElement.getProfileElementOwner().getEndDate() != null
                                    &&
                                    demandProfileElementTimePeriod.getStartDate().isAfter(supplyProfileElement.getProfileElementOwner().getEndDate())

                            ) {
                        matchValue = 0;

                        System.out.println("match from getProfileElementTimePeriodComparison() in ProfileMatchingService.class:");
                        System.out.println(supplyProfileElement.getProfileElementOwner().getOwner().toString() + " >> end supply before start demand");
                    }

                    // if supply start <= demand start and supply end <= demand end
                    // calculate relative value
                    //
                    //	(pic)
                    // 	demand xxxxxxxxxxxxxxxxxxx *startdate* ------------------------------- *enddate* xxxxxxxxxxxxxxxxxxxxxxxxx
                    // 	supply xxxxxx[*startdate*] ----------------------------- *enddate* xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

                    if (
                            (
                                    supplyProfileElement.getProfileElementOwner().getEndDate() != null
                                            &&
                                            supplyProfileElement.getProfileElementOwner().getEndDate().isBefore(demandProfileElementTimePeriod.getEndDate().plusDays(1))
                            )
                                    &&
                                    (
                                            (
                                                    supplyProfileElement.getProfileElementOwner().getStartDate() != null
                                                            &&
                                                            supplyProfileElement.getProfileElementOwner().getStartDate().isBefore(demandProfileElementTimePeriod.getStartDate().plusDays(1))
                                            )
                                                    ||
                                                    (
                                                            supplyProfileElement.getProfileElementOwner().getStartDate() == null
                                                    )
                                    )
                            ) {

                        final int demandDays = Days.daysBetween(demandProfileElementTimePeriod.getStartDate(), demandProfileElementTimePeriod.getEndDate()).getDays();
                        final int deltaDays = Days.daysBetween(supplyProfileElement.getProfileElementOwner().getEndDate(), demandProfileElementTimePeriod.getEndDate()).getDays();

                        final double value = 100 * (1 - (double) deltaDays / (double) demandDays);

                        matchValue = (int) value;

                    }

                    // if supply start > demand start and supply end > demand end
                    // calculate relative value
                    //
                    //	(pic)
                    // 	demand xxxxxxxx *startdate* ------------------------------- *enddate* xxxxxxxxxxxxxxxxxxxxxxxxx
                    // 	supply xxxxxxxxxxxxxxxxxxxxxxxx*startdate* ----------------------------- [*enddate*] xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                    if (

                            (
                                    supplyProfileElement.getProfileElementOwner().getStartDate() != null
                                            &&
                                            supplyProfileElement.getProfileElementOwner().getStartDate().isAfter(demandProfileElementTimePeriod.getStartDate().minusDays(1))
                            )
                                    &&
                                    (
                                            (
                                                    supplyProfileElement.getProfileElementOwner().getEndDate() != null
                                                            &&
                                                            supplyProfileElement.getProfileElementOwner().getEndDate().isAfter(demandProfileElementTimePeriod.getEndDate().minusDays(1))
                                            )
                                                    ||
                                                    (
                                                            supplyProfileElement.getProfileElementOwner().getEndDate() == null
                                                    )
                                    )

                            ) {

                        final int demandDays = Days.daysBetween(demandProfileElementTimePeriod.getStartDate(), demandProfileElementTimePeriod.getEndDate()).getDays();
                        final int deltaDays = Days.daysBetween(demandProfileElementTimePeriod.getStartDate(), supplyProfileElement.getProfileElementOwner().getStartDate()).getDays();

                        final double value = 100 * (1 - (double) deltaDays / (double) demandDays);

                        matchValue = (int) value;

                    }

                    // if supply start > demand start and supply end < demand end
                    // calculate relative value
                    //
                    //	(pic)
                    // 	demand xxxxxxxx *startdate* -------------------------------------------------------------- *enddate* xxxxxxxxxxxxxxxxxxxxxxxxx
                    // 	supply xxxxxxxxxxxxxxxxxxxxxxxx*startdate* ----------------------------- *enddate* xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                    if (

                            supplyProfileElement.getProfileElementOwner().getStartDate() != null
                                    &&
                                    supplyProfileElement.getProfileElementOwner().getEndDate() != null
                                    &&
                                    supplyProfileElement.getProfileElementOwner().getStartDate().isAfter(demandProfileElementTimePeriod.getStartDate())
                                    &&
                                    supplyProfileElement.getProfileElementOwner().getEndDate().isBefore(demandProfileElementTimePeriod.getEndDate().plusDays(1))

                            ) {

                        final int demandDays = Days.daysBetween(demandProfileElementTimePeriod.getStartDate(), demandProfileElementTimePeriod.getEndDate()).getDays();
                        final int deltaDays =
                                Days.daysBetween(demandProfileElementTimePeriod.getStartDate(),
                                        supplyProfileElement.getProfileElementOwner().getStartDate()).
                                        getDays()
                                        +
                                        Days.daysBetween(supplyProfileElement.getProfileElementOwner().
                                                        getEndDate(),
                                                demandProfileElementTimePeriod.getEndDate()
                                        ).getDays();

                        final double value = 100 * (1 - (double) deltaDays / (double) demandDays);

                        matchValue = (int) value;

                        System.out.println("value: " + value);
                        System.out.println("match from getProfileElementTimePeriodComparison() in ProfileMatchingService.class:");
                        System.out.println(supplyProfileElement.getProfileElementOwner().getOwner().toString() + " >> start supply after start demand and end supply before end demand");
                        System.out.println("matchValue:  " + matchValue);
                        System.out.println("demand:  " + demandProfileElementTimePeriod.getStartDate().toString() + " - " + demandProfileElementTimePeriod.getEndDate().toString());
                        System.out.println("supply:  " + supplyProfileElement.getProfileElementOwner().getStartDate().toString() + " - " + supplyProfileElement.getProfileElementOwner().getEndDate().toString());
                        System.out.println("demandDays: " + demandDays + "  deltaDays: " + deltaDays);

                    }

                }

                ProfileElementComparison profileElementComparison = new ProfileElementComparison(
                        demandProfileElement.getProfileElementOwner(),
                        demandProfileElement,
                        supplyProfileElement,
                        supplyProfileElement.getProfileElementOwner(),
                        supplyProfileElement.getProfileElementOwner().getOwner(),
                        matchValue,
                        demandProfileElement.getWeight()
                );
                return profileElementComparison;

            }

            return null;
        }
    },
    USE_TIME_PERIOD ("Use_time_period"){
        @Override
        public ProfileElementComparison getProfileElementComparison(
                final ProfileElement demandProfileElement,
                final ProfileElement supplyProfileElement){
            return null;
        }
    },
    AGE("Age"){

        @Override
        public ProfileElementComparison getProfileElementComparison(
                final ProfileElement demandProfileElement,
                final ProfileElement supplyProfileElement){

            if (demandProfileElement.getProfileElementType() == ProfileElementType.AGE &&
                    supplyProfileElement.getProfileElementType() == ProfileElementType.USE_AGE) {

                ProfileElementNumeric demandProfileElementNumeric = (ProfileElementNumeric) demandProfileElement;
                ProfileElementUsePredicate supplyProfileElementUsePredicate = (ProfileElementUsePredicate) supplyProfileElement;

                ProfileElementAgeComparison profileElementAgeComparison = new ProfileElementAgeComparison();

                return profileElementAgeComparison.getProfileElementComparison(demandProfileElementNumeric, supplyProfileElementUsePredicate);

            }

            return null;
        }
    },
    USE_AGE("Use_age"){
        @Override
        public ProfileElementComparison getProfileElementComparison(
                final ProfileElement demandProfileElement,
                final ProfileElement supplyProfileElement){
            return null;
        }
    },
    VERVOER("Eigen_Vervoer"){
        @Override
        public ProfileElementComparison getProfileElementComparison(
                final ProfileElement demandProfileElement,
                final ProfileElement supplyProfileElement){
            return null;
        }
    },
    QUALIFICATION_TAGS("Qualification_tags"){
        @Override
        public ProfileElementComparison getProfileElementComparison(
                final ProfileElement demandProfileElement,
                final ProfileElement supplyProfileElement){
            return null;
        }
    },
    ROLE_REQUIRED("Role"){
        @Override
        public ProfileElementComparison getProfileElementComparison(
                final ProfileElement demandProfileElement,
                final ProfileElement supplyProfileElement){
            return null;
        }
    },
    HOURLY_RATE("Hourly_rate"){
        @Override
        public ProfileElementComparison getProfileElementComparison(
                final ProfileElement demandProfileElement,
                final ProfileElement supplyProfileElement){

            if (demandProfileElement.getProfileElementType() == ProfileElementType.HOURLY_RATE &&
                    supplyProfileElement.getProfileElementType() == ProfileElementType.HOURLY_RATE) {

                ProfileElementNumeric demandProfileElementNumeric = (ProfileElementNumeric) demandProfileElement;
                ProfileElementNumeric supplyProfileElementNumeric = (ProfileElementNumeric) supplyProfileElement;

                Integer matchValue = 0;

                if (supplyProfileElementNumeric.getNumericValue() <= demandProfileElementNumeric.getNumericValue()) {
                    matchValue = 100;
                } else {
                    Long delta = Long.valueOf(supplyProfileElementNumeric.getNumericValue() - demandProfileElementNumeric.getNumericValue());
                    System.out.println("delta: " + delta);
                    // delta more than 40% of demand adds no points
                    Long percentageDelta = delta * 100 / demandProfileElementNumeric.getNumericValue();
                    System.out.println("percentageDelta: " + percentageDelta);
                    if (percentageDelta >= 40) {
                        matchValue = 0;
                    } else {
                        //divide 100 points over 40% and subtract from max matchingValue of 100
                        Long subtraction = percentageDelta * 100 / 40;
                        matchValue = (int) (long) (100 - subtraction);
                    }

                }

                System.out.println("match from getProfileElementHourlyRateComparison() in ProfileMatchingService.class:");
                System.out.println("supplied hourlyRate: " + supplyProfileElementNumeric.getNumericValue());
                System.out.println("demanded hourlyRate: " + demandProfileElementNumeric.getNumericValue() + " - matchValue: " + matchValue);

                ProfileElementComparison profileElementComparison = new ProfileElementComparison(
                        demandProfileElement.getProfileElementOwner(),
                        demandProfileElement,
                        supplyProfileElement,
                        supplyProfileElement.getProfileElementOwner(),
                        supplyProfileElement.getProfileElementOwner().getOwner(),
                        matchValue,
                        demandProfileElement.getWeight()
                );
                return profileElementComparison;

            }

            return null;
        }
    },
    EDUCATION_LEVEL("Education_level"){
        @Override
        public ProfileElementComparison getProfileElementComparison(
                final ProfileElement demandProfileElement,
                final ProfileElement supplyProfileElement){

            if (demandProfileElement.getProfileElementType() == ProfileElementType.EDUCATION_LEVEL &&
                    supplyProfileElement.getProfileElementType() == ProfileElementType.EDUCATION_LEVEL) {

                ProfileElementDropDown demandProfileElementDropDown = (ProfileElementDropDown) demandProfileElement;
                ProfileElementDropDown supplyProfileElementDropDown = (ProfileElementDropDown) supplyProfileElement;

                Integer matchValue = 0;

                if (supplyProfileElementDropDown.getDropDownValue().equals(demandProfileElementDropDown.getDropDownValue())) {
                    matchValue = 100;
                } else {
                    matchValue = 0;
                }

                System.out.println("match from getProfileElementDropDownComparison() in ProfileMatchingService.class:");
                System.out.println("supplied DropDownValue: " + supplyProfileElementDropDown.getDropDownValue());
                System.out.println("demanded DropDownValue: " + demandProfileElementDropDown.getDropDownValue() + " - matchValue: " + matchValue);

                ProfileElementComparison profileElementComparison = new ProfileElementComparison(
                        demandProfileElement.getProfileElementOwner(),
                        demandProfileElement,
                        supplyProfileElement,
                        supplyProfileElement.getProfileElementOwner(),
                        supplyProfileElement.getProfileElementOwner().getOwner(),
                        matchValue,
                        demandProfileElement.getWeight()
                );
                return profileElementComparison;
            }

            return null;
        }
    },
    TIME_AVAILABLE("time_available"){
        @Override
        public ProfileElementComparison getProfileElementComparison(ProfileElement demandProfileElement, ProfileElement supplyProfileElement) {

            //TODO
            return null;
        }
    },

    STORY("story"){
        @Override
        public ProfileElementComparison getProfileElementComparison(ProfileElement demandProfileElement, ProfileElement supplyProfileElement) {
            return null;
        }
    },
    URL_PROFILE_BACKGROUND("profile_background"){
        @Override
        public ProfileElementComparison getProfileElementComparison(ProfileElement demandProfileElement, ProfileElement supplyProfileElement) {
            return null;
        }
    },
    BRANCHE("Branche"){
        @Override
        public ProfileElementComparison getProfileElementComparison(ProfileElement demandProfileElement, ProfileElement supplyProfileElement) {
            return null;
        }
    },

    CITY("City"){
        @Override
        public ProfileElementComparison getProfileElementComparison(ProfileElement demandProfileElement, ProfileElement supplyProfileElement) {
            return null;
        }
    },
    HONOURSPROGRAM("HonoursProgram"){
        @Override
        public ProfileElementComparison getProfileElementComparison(ProfileElement demandProfileElement, ProfileElement supplyProfileElement) {
            return null;
        }
    }
    ;


    private String title;
    
    ProfileElementType (String title) {
        this.title = title;
    }
    
    public String title() {
        return title;
    }

    public abstract ProfileElementComparison getProfileElementComparison(final ProfileElement demandProfileElement, final ProfileElement supplyProfileElement);

}
