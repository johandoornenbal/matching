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

package nl.matching.app.webapp.scheduler;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.matchingservice.dom.Match.ProfileComparison;
import info.matchingservice.dom.Match.ProfileComparisons;

public class ProfileComparisonsDeletionJob extends AbstractIsisJob {

    /*
        This class is just in case some unvalid profileComparison slips through and remains in the database
     */

    final static Logger LOG = LoggerFactory.getLogger(ProfileComparisonsDeletionJob.class);

    protected void doExecute(JobExecutionContext context) {
        ProfileComparisons profileComparisons = getService(ProfileComparisons.class);
        LOG.info("Start Profilecomparison Deletion Job");
        for (ProfileComparison profileComparison : profileComparisons.allProfileComparisons()) {
            if (profileComparison.getDemandProfile() == null
                    ||
                    profileComparison.getMatchingSupplyProfile() == null
                    ||
                    profileComparison.getCalculatedMatchingValue() < 1) {
                LOG.info("Deleting " + profileComparison.toString());
                profileComparison.delete();

            }
        }
        LOG.info("End Profilecomparison Deletion Job");
    }
}
