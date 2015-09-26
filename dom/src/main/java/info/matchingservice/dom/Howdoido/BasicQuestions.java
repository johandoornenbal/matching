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

package info.matchingservice.dom.Howdoido;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;

import info.matchingservice.dom.MatchingDomainService;

/**
 * Created by jodo on 31/08/15.
 */
@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = BasicQuestion.class)
public class BasicQuestions extends MatchingDomainService<BasicQuestion> {

    public BasicQuestions() {
        super(BasicQuestions.class, BasicQuestion.class);
    }

    @Programmatic
    public List<BasicQuestion> allBasicQuestions () {
        return allInstances(BasicQuestion.class);
    }

    @Programmatic
    public BasicQuestion createBasicQuestion(
            @ParameterLayout(named = "question")
            final String question,
            @ParameterLayout(named = "template")
            final BasicTemplate template,
            @ParameterLayout(named = "form type")
            final BasicRepresentationType formType) {

        BasicQuestion newQuestion = newTransientInstance(BasicQuestion.class);

        newQuestion.setBasicQuestion(question);
        newQuestion.setBasicTemplate(template);
        newQuestion.setBasicFormType(formType);
        newQuestion.setOwnedBy(template.getOwnedBy());

        persistIfNotAlready(newQuestion);


        return newQuestion;

    }



}
