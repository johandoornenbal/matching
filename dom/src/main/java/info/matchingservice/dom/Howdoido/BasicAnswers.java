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

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.TrustLevel;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import java.util.List;

/**
 * Created by jodo on 31/08/15.
 */
@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = BasicAnswer.class)
public class BasicAnswers extends MatchingDomainService<BasicAnswer> {

    public BasicAnswers() {
        super(BasicAnswers.class, BasicAnswer.class);
    }

    @Programmatic
    public List<BasicAnswer> allBasicAnswers () {
        return allInstances(BasicAnswer.class);
    }

    @Programmatic
    public List<BasicAnswer> findByForm(final BasicForm form) {
        return allMatches("findBasicAnswerByForm", "basicForm", form);
    }

    @Programmatic
    public BasicAnswerRating createBasicAnswerWithRating(
            final BasicForm form,
            final BasicQuestion basicQuestion,
            final TrustLevel trustLevel
    ) {
        BasicAnswerRating answer = newTransientInstance(BasicAnswerRating.class);

        answer.setBasicForm(form);
        answer.setQuestionToAnswer(basicQuestion.getBasicQuestion());
        answer.setMatchingTrustlevel(trustLevel);
        answer.setQuestionId(basicQuestion.getId());

        persistIfNotAlready(answer);

        return answer;
    }

    @Programmatic
    public BasicAnswerRatingExplanation createBasicAnswerWithRatingAndExplanation(
            final BasicForm form,
            final BasicQuestion basicQuestion,
            final TrustLevel trustLevel
    ) {
        BasicAnswerRatingExplanation answer = newTransientInstance(BasicAnswerRatingExplanation.class);

        answer.setBasicForm(form);
        answer.setQuestionToAnswer(basicQuestion.getBasicQuestion());
        answer.setMatchingTrustlevel(trustLevel);
        answer.setQuestionId(basicQuestion.getId());

        persistIfNotAlready(answer);

        return answer;
    }

}
