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

package info.matchingservice.fixture.howdoido;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import info.matchingservice.dom.Howdoido.BasicRequest;
import info.matchingservice.dom.Howdoido.BasicRequests;
import info.matchingservice.dom.Howdoido.BasicTemplate;
import info.matchingservice.dom.Howdoido.BasicUser;

public abstract class BasicRequestAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected BasicRequest createBasicRequest(
            BasicUser requestOwner,
            BasicUser requestReceiver,
            BasicTemplate template,
            ExecutionContext executionContext
            ) {

        BasicRequest newBasicRequest = basicRequests.createBasicRequest(requestOwner,requestReceiver,template);
        return executionContext.addResult(this, newBasicRequest);
    }
    
    //region > injected services
    @javax.inject.Inject
    BasicRequests basicRequests;
}
