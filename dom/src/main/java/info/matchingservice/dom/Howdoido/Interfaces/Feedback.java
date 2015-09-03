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

package info.matchingservice.dom.Howdoido.Interfaces;

import info.matchingservice.dom.Howdoido.FeedbackRating;
import info.matchingservice.dom.TrustLevel;

/**
 * Created by jodo on 30/08/15.
 */
public interface Feedback {

    FeedbackRequest getFeedbackRequest();
    void setFeedbackRequest();

    FeedbackRating getRating();
    void setRating(FeedbackRating rating);

    TrustLevel accessibleFor();

    Feedback rateFeedback(FeedbackRating rating, FeedbackReceiver feedbackReceiver);

}
