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
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.joda.time.LocalDateTime;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by jodo on 31/08/15.
 */
@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = BasicRequest.class)
public class BasicRequests extends MatchingDomainService<BasicRequest> {

    public BasicRequests() {
        super(BasicRequests.class, BasicRequest.class);
    }

    @Programmatic
    public List<BasicRequest> allBasicQuestions () {
        return allInstances(BasicRequest.class);
    }

    @Programmatic
    public BasicRequest createBasicRequest(
            final BasicUser requestOwner,
            final BasicUser requestReceiver,
            final BasicTemplate template) {

        BasicRequest newBasicRequest = newTransientInstance(BasicRequest.class);

        newBasicRequest.setRequestOwner(requestOwner);
        newBasicRequest.setRequestReceiver(requestReceiver);
        newBasicRequest.setBasicTemplate(template);
        newBasicRequest.setRequestDenied(false);
        newBasicRequest.setRequestHonoured(false);
        newBasicRequest.setDateTime(LocalDateTime.now());

        persistIfNotAlready(newBasicRequest);
        getContainer().flush(); /* Needed for getOID() on BasicRequest; nullpointer otherwise*/

        return newBasicRequest;

    }


    @Programmatic
    public List<BasicRequest> findRequestByOwner(final BasicUser owner) {
        return allMatches("findBasicRequestByOwner", "owner", owner);
    }

    @Programmatic
    public List<BasicRequest> findDeniedRequestByOwner(final BasicUser owner) {
        return allMatches("findBasicDeniedRequestByOwner", "owner", owner);
    }

    @Programmatic
    public List<BasicRequest> findRequestByRequestReceiver(final BasicUser requestReceiver) {
        return allMatches("findBasicRequestByRequestReceiver", "requestReceiver", requestReceiver);
    }

    // Api v1
    @Programmatic
    public BasicRequest matchRequestApiId(final String id) {

        for (BasicRequest d : allInstances(BasicRequest.class)) {
            String[] parts = d.getOID().split(Pattern.quote("[OID]"));
            String part1 = parts[0];
            String ApiId = "L_".concat(part1);
            if (id.equals(ApiId)) {
                return d;
            }
        }

        return null;

    }

}
