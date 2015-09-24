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

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.TrustLevel;

/**
 * Created by jodo on 31/08/15.
 */
@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = BasicForm.class)
public class BasicForms extends MatchingDomainService<BasicForm> {

    public BasicForms() {
        super(BasicForms.class, BasicForm.class);
    }

    @Programmatic
    public List<BasicForm> allBasicForms () {
        return allInstances(BasicForm.class);
    }

    @Programmatic
    public List<BasicForm> findByCreator(final BasicUser creator) {
        return allMatches("findBasicFormByCreator", "formCreator", creator);
    }

    @Programmatic
    public List<BasicForm> findByReceiver(final BasicUser receiver) {
        return allMatches("findBasicFormByReceiver", "formReceiver", receiver);
    }

    @Programmatic
    public List<BasicForm> findByReceiverAndUnRated(final BasicUser receiver) {
        return allMatches("findBasicFormByReceiverAndRated", "formReceiver", receiver, "formRated", false);
    }

    @Programmatic
    public List<BasicForm> findByReceiverAndPublished(final BasicUser receiver) {
        return allMatches("findBasicFormByReceiverAndPublished", "formReceiver", receiver, "published", true);
    }

    @Programmatic
    public BasicForm createBasicForm(
            @ParameterLayout(named = "creator")
            final BasicUser creator,
            @ParameterLayout(named = "receiver")
            final BasicUser receiver,
            @ParameterLayout(named = "template")
            final BasicTemplate template
            ) {

        BasicForm newBasicForm = newTransientInstance(BasicForm.class);

        newBasicForm.setFormCreator(creator);
        newBasicForm.setFormReceiver(receiver);
        newBasicForm.setBasicTemplate(template);
        newBasicForm.setFormRated(false);
        newBasicForm.setPublished(false);

        persistIfNotAlready(newBasicForm);

        for (BasicQuestion q : template.getBasicQuestions()) {

            if (q.getFormType() == BasicRepresentationType.RATING) {
                basicAnswers.createBasicAnswerWithRating(newBasicForm, q ,TrustLevel.ENTRY_LEVEL);
            }

            if (q.getFormType() == BasicRepresentationType.RATING_WITH_EXPLANATION) {
                basicAnswers.createBasicAnswerWithRatingAndExplanation(newBasicForm, q, TrustLevel.ENTRY_LEVEL);
            }

        }


        return newBasicForm;

    }

    @Inject
    BasicAnswers basicAnswers;


}
