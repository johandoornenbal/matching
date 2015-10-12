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

package info.matchingservice.fixture.howdoido;

import info.matchingservice.dom.Howdoido.BasicRepresentationType;
import info.matchingservice.dom.Howdoido.BasicTemplates;

import javax.inject.Inject;

/**
 * Created by jodo on 03/09/15.
 */
public class BasicQuestionFixtures extends BasicQuestionAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        //preqs
        executionContext.executeChild(this, new BasicTemplateFixtures());

        createBasicQuestion("Vind je mij op academisch niveau functioneren?", basicTemplates.allBasicTemplates().get(0), BasicRepresentationType.RATING_WITH_EXPLANATION, executionContext);
        createBasicQuestion("Vind je mij goed in de groep functioneren?", basicTemplates.allBasicTemplates().get(1), BasicRepresentationType.RATING_WITH_EXPLANATION,executionContext);
        createBasicQuestion("Vind je mij goed fluit spelen?", basicTemplates.allBasicTemplates().get(2), BasicRepresentationType.RATING_WITH_EXPLANATION,executionContext);
        createBasicQuestion("Vind je dat ik er goed uitzie vandaag?", basicTemplates.allBasicTemplates().get(2), BasicRepresentationType.RATING ,executionContext);

    }

    @Inject
    BasicTemplates basicTemplates;

}
