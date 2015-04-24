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

package info.matchingservice.dom.CommunicationChannels;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;

import info.matchingservice.dom.MatchingDomainService;

/**
 * Created by jonathan on 3-4-15.
 */
@DomainService(nature=NatureOfService.VIEW_MENU_ONLY)
@DomainServiceLayout(menuBar = DomainServiceLayout.MenuBar.PRIMARY, named = "ComChannels")
public class CommunicationChannelMenu extends MatchingDomainService<CommunicationChannel>{


	public CommunicationChannelMenu() {
		super(CommunicationChannelMenu.class, CommunicationChannel.class);
	}


    public List<CommunicationChannel> allEmails(){

        return communicationChannels.allCommunicationChannels(Email.class);


    }


    public List<CommunicationChannel> allPhones(){

        return communicationChannels.allCommunicationChannels(Phone.class);


    }



    public List<CommunicationChannel> allAddress(){

        return communicationChannels.allCommunicationChannels(Address.class);


    }


    @Inject
    CommunicationChannels communicationChannels;


}
