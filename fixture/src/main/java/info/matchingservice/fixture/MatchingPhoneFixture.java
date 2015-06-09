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

package info.matchingservice.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import info.matchingservice.fixture.communicationChannels.TestPhone;

public class MatchingPhoneFixture extends FixtureScript {

    public MatchingPhoneFixture() {
        super(null, "matchingdemo-fixture");
    }

    @Override
    protected void execute(ExecutionContext executionContext) {
        // prereqs
//        executeChild(new TeardownFixture(), executionContext);
        
        // create
        executeChild(new TestPhone(), executionContext);
        
    }

}