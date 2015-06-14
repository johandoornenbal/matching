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

package info.matchingservice.dom.Profile;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;

import info.matchingservice.dom.MatchingDomainService;

/**
 * Created by jodo on 14/06/15.
 */
@DomainService(nature = NatureOfService.DOMAIN)
public class ProfileElementChoices extends MatchingDomainService<ProfileElementChoice> {

    public ProfileElementChoices() {
        super(ProfileElementChoices.class, ProfileElementChoice.class);
    }

    @Programmatic
    public List<ProfileElementChoice> profileElementChoices(DemandOrSupply demandOrSupply){
        return allMatches("findProfileElementChoicesByDemandOrSupply", "demandOrSupply", demandOrSupply);
    }

    public List<ProfileElementChoice> allProfileElementChoices() {
        return allInstances(ProfileElementChoice.class);
    }

    public ProfileElementChoice createProfileElementChoice(
            final @ParameterLayout(named = "demandOrSupply") DemandOrSupply demandOrSupply,
            final @ParameterLayout(named = "widgetType") ProfileElementWidgetType widgetType,
            final @ParameterLayout(named = "elementDescription") String elementDescription,
            final @ParameterLayout(named = "action") String action
    ){

        final ProfileElementChoice newElement = newTransientInstance(ProfileElementChoice.class);

        newElement.setDemandOrSupply(demandOrSupply);
        newElement.setWidgetType(widgetType);
        newElement.setElementDescription(elementDescription);
        newElement.setAction(action);

        persistIfNotAlready(newElement);

        return newElement;

    }

}
