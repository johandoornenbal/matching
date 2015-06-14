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

package info.matchingservice.fixture.ProfileElementConfig;

import info.matchingservice.dom.Profile.DemandOrSupply;
import info.matchingservice.dom.Profile.ProfileElementWidgetType;

/**
 * Created by jodo on 14/06/15.
 */
public class ProfileElementChoicesForSupply extends ProfileElementChoiceAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createElement(
                DemandOrSupply.SUPPLY,
                ProfileElementWidgetType.TEXTAREA,
                "PASSION_ELEMENT",
                "createPassionElement",
                executionContext);

        createElement(
                DemandOrSupply.SUPPLY,
                ProfileElementWidgetType.TAGS,
                "BRANCHE_TAGS_ELEMENT",
                "createBrancheTagElement",
                executionContext);

        createElement(
                DemandOrSupply.SUPPLY,
                ProfileElementWidgetType.TAGS,
                "QUALITY_TAGS_ELEMENT",
                "createQualityTagElement",
                executionContext);

        createElement(
                DemandOrSupply.SUPPLY,
                ProfileElementWidgetType.TAGS,
                "WEEKDAY_TAGS_ELEMENT",
                "createWeekdayTagElement",
                executionContext);

        createElement(
                DemandOrSupply.SUPPLY,
                ProfileElementWidgetType.TEXT,
                "LOCATION_ELEMENT",
                "createLocationElement",
                executionContext);

        createElement(
                DemandOrSupply.SUPPLY,
                ProfileElementWidgetType.NUMBER,
                "HOURLY_RATE_ELEMENT",
                "createHourlyRateElement",
                executionContext);

        createElement(
                DemandOrSupply.SUPPLY,
                ProfileElementWidgetType.PREDICATE,
                "USE_AGE_ELEMENT",
                "createUseAgeElement",
                executionContext);

        createElement(
                DemandOrSupply.SUPPLY,
                ProfileElementWidgetType.PREDICATE,
                "USE_TIME_PERIOD_ELEMENT",
                "createUseTimePeriodElement",
                executionContext);

        createElement(
                DemandOrSupply.SUPPLY,
                ProfileElementWidgetType.SELECT,
                "EDUCATION_LEVEL",
                "createEducationLevelElement",
                executionContext);

    }


}
