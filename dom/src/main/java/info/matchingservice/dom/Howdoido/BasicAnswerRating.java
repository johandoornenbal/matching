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
public class BasicAnswerRating extends BasicAnswer {

    //region > rating (property)
    private Rating rating;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "true")
    public Rating getRating() {
        return rating;
    }

    public void setRating(final Rating rating) {
        this.rating = rating;
    }

    public BasicAnswer updateRating(
            @ParameterLayout(named = "rating")
            final Rating rating) {
        setRating(rating);
        return this;
    }

    public boolean hideUpdateRating(final Rating rating){

        if (getBasicForm().getFormCreator().getOwnedBy().equals(getContainer().getUser().getName())) {
            return false;
        }

        return true;
    }
    //endregion


    @Override
    @PropertyLayout(hidden = Where.EVERYWHERE)
    public RepresentationType getRepresentationType() {
        return BasicRepresentationType.RATING;
    }

    enum Rating {
        EXCELLENT(5),
        GOOD(4),
        AVERAGE(3),
        POOR(2),
        BAD(1);

        private Integer ratingValue;

        Rating (Integer ratingValue) {
            this.ratingValue = ratingValue;
        }

        Integer ratingValue() {
            return this.ratingValue;
        }
    }


}
