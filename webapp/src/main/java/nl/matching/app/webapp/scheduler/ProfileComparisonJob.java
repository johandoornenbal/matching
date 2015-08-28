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

import info.matchingservice.dom.Match.ProfileMatchingService;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.Profiles;

public class ProfileComparisonJob extends AbstractIsisJob {

    final static Logger LOG = LoggerFactory.getLogger(ProfileComparisonJob.class);

    protected void doExecute(JobExecutionContext context) {
        ProfileMatchingService service = getService(ProfileMatchingService.class);
        Profiles profiles = getService(Profiles.class);
        LOG.info("Start Collect Profilecomparisons Job");
        for (Profile profile : profiles.allDemandProfiles()) {
            service.collectProfileComparisons(profile);
        }
        LOG.info("End Collect Profilecomparisons Job");
    }
}
