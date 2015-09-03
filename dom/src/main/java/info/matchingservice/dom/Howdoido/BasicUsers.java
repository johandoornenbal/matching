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

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;

import info.matchingservice.dom.CommunicationChannels.CommunicationChannels;
import info.matchingservice.dom.MatchingDomainService;

/**
 * Created by jodo on 30/08/15.
 */
@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = BasicUser.class)
@DomainServiceLayout()
public class BasicUsers extends MatchingDomainService<BasicUser> {

    public BasicUsers() {
        super(BasicUsers.class, BasicUser.class);
    }

    @Programmatic
    public BasicUser createBasicUser(
            final String name,
            final String email
    ){
        final BasicUser newUser = newTransientInstance(BasicUser.class);
        newUser.setName(name);
        newUser.setEmail(email.toLowerCase());
        newUser.setOwnedBy(currentUserName());
        persistIfNotAlready(newUser);

        return newUser;
    }


    @Action(semantics = SemanticsOf.SAFE)
    public List<BasicUser> allBasicUsers() {
        return allInstances();
    }

    // Region>helpers ////////////////////////////

    private String currentUserName() {
        return getUser().getName();
    }

    @Inject
    CommunicationChannels communicationChannels;

}
