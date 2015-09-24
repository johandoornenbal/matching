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

import org.apache.isis.applib.fixturescripts.FixtureScript;

import info.matchingservice.dom.Howdoido.BasicRepresentationType;
import info.matchingservice.dom.Howdoido.BasicQuestion;
import info.matchingservice.dom.Howdoido.BasicQuestions;
import info.matchingservice.dom.Howdoido.BasicTemplate;

public abstract class BasicQuestionAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected BasicQuestion createBasicQuestion(
            String question,
            BasicTemplate template,
            BasicRepresentationType formType,
            ExecutionContext executionContext
            ) {

        BasicQuestion newBasicQuestion = basicQuestions.createBasicQuestion(question, template,formType);
        return executionContext.addResult(this, newBasicQuestion);
    }
    
    //region > injected services
    @javax.inject.Inject
    BasicQuestions basicQuestions;
}
