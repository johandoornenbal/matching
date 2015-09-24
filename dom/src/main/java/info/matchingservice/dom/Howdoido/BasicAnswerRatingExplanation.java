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

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Where;

import info.matchingservice.dom.Howdoido.Interfaces.RepresentationType;

/**
 * Created by jodo on 05/09/15.
 */
@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
@DomainObject(editing = Editing.DISABLED)
public class BasicAnswerRatingExplanation extends BasicAnswerRating {

    //region > explanation (property)
    private String explanation;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "true")
    @PropertyLayout(multiLine = 5)
    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(final String explanation) {
        this.explanation = explanation;
    }

    public BasicAnswer updateExplanation(
            @ParameterLayout(named = "explanation", multiLine = 5)
            final String explanation) {
        setExplanation(explanation);
        return this;
    }

    public boolean hideUpdateExplanation(final String explanation){

        if (getBasicForm().getFormCreator().getOwnedBy().equals(getContainer().getUser().getName())) {
            return false;
        }

        return true;
    }
    //endregion

    @Override
    @PropertyLayout(hidden = Where.EVERYWHERE)
    public RepresentationType getRepresentationType() {
        return BasicRepresentationType.RATING_WITH_EXPLANATION;
    }

}
