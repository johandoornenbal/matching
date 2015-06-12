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

package info.matchingservice.dom.Match;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.SemanticsOf;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Profile.DemandOrSupply;
import info.matchingservice.dom.Profile.Profile;

/**
 * Created by jodo on 12/06/15.
 */
@DomainService(repositoryFor = ProfileComparison.class, nature= NatureOfService.VIEW_CONTRIBUTIONS_ONLY)
public class ProfileComparisons extends MatchingDomainService<ProfileComparison> {

    public ProfileComparisons() {
        super(ProfileComparisons.class, ProfileComparison.class);
    }

    @Action(semantics = SemanticsOf.SAFE)
    public List<ProfileComparison> allProfileComparisons() {
        return container.allInstances(ProfileComparison.class);
    }

    // Contributed collection
    @Action(semantics = SemanticsOf.SAFE)
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<ProfileComparison> collectProfileComparisons(final Profile demandProfile) {
        return allMatches("findProfileComparisonByDemandProfile", "demandProfile", demandProfile);
    }

    public boolean hideCollectProfileComparisons(final Profile demandProfile) {

        return demandProfile.getDemandOrSupply()== DemandOrSupply.SUPPLY;
    }

    // Contributed collection
    @Action(semantics = SemanticsOf.SAFE)
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<ProfileComparison> collectDemandProfileComparisons(final Profile supplyProfile) {
        return allMatches("findProfileComparisonBySupplyProfile", "supplyProfile", supplyProfile);
    }

    public boolean hideCollectDemandProfileComparisons(final Profile supplyProfile) {

        return supplyProfile.getDemandOrSupply()== DemandOrSupply.DEMAND;
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @Programmatic
    public void createProfileComparison(
            final @ParameterLayout(named = "demandProfile") Profile demandProfile,
            final @ParameterLayout(named = "matchingSupplyProfile") Profile matchingSupplyProfile,
            final @ParameterLayout(named = "calculatedMatchingValue") Integer calculatedMatchingValue
            ){

        ProfileComparison newProfileComparison = newTransientInstance(ProfileComparison.class);
        newProfileComparison.setDemandProfile(demandProfile);
        newProfileComparison.setMatchingSupplyProfile(matchingSupplyProfile);
        newProfileComparison.setCalculatedMatchingValue(calculatedMatchingValue);
        container.persistIfNotAlready(newProfileComparison);
    }

    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
}
