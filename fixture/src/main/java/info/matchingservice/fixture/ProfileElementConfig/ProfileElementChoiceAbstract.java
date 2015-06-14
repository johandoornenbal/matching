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

import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import info.matchingservice.dom.Profile.DemandOrSupply;
import info.matchingservice.dom.Profile.ProfileElementChoice;
import info.matchingservice.dom.Profile.ProfileElementChoices;
import info.matchingservice.dom.Profile.ProfileElementWidgetType;

public abstract class ProfileElementChoiceAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected ProfileElementChoice createElement(
            final @ParameterLayout(named = "demandOrSupply") DemandOrSupply demandOrSupply,
            final @ParameterLayout(named = "widgetType") ProfileElementWidgetType widgetType,
            final @ParameterLayout(named = "elementDescription") String elementDescription,
            final @ParameterLayout(named = "action") String action,
            ExecutionContext executionContext
    ) {
        ProfileElementChoice profileElementChoice =
                profileElementChoices.createProfileElementChoice(
                        demandOrSupply,
                        widgetType,
                        elementDescription,
                        action);
                       
        return executionContext.add(this, profileElementChoice);
    }
    
    
    //region > injected services
    @javax.inject.Inject
    private ProfileElementChoices profileElementChoices;

}