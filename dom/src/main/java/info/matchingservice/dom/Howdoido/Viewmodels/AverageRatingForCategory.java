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

package info.matchingservice.dom.Howdoido.Viewmodels;

import java.math.BigDecimal;

import org.apache.isis.applib.annotation.ViewModel;

import info.matchingservice.dom.Howdoido.BasicCategory;
import info.matchingservice.dom.Howdoido.BasicUser;

/**
 * Created by jodo on 08/09/15.
 */
@ViewModel()
public class AverageRatingForCategory {

    public AverageRatingForCategory() {

    }

    public AverageRatingForCategory(
            final BasicUser basicUser,
            final BasicCategory basicCategory,
            final BigDecimal averageRating,
            final Integer numberOfRatings) {

        this.basicUser = basicUser;
        this.basicCategory = basicCategory;
        this.averageRating = averageRating;
        this.numberOfRatings = numberOfRatings;

    }

    public BasicUser getBasicUser() {
        return basicUser;
    }

    public void setBasicUser(final BasicUser basicUser) {
        this.basicUser = basicUser;
    }

    public BasicCategory getBasicCategory() {
        return basicCategory;
    }

    public void setBasicCategory(final BasicCategory basicCategory) {
        this.basicCategory = basicCategory;
    }

    public BigDecimal getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(final BigDecimal averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(final Integer numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    private BasicUser basicUser;
    private BasicCategory basicCategory;
    private BigDecimal averageRating;
    private Integer numberOfRatings;

}
