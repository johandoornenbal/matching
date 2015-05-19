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

package org.isisaddons.services.remoteprofiles;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import org.glassfish.jersey.client.ClientProperties;

import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

@DomainService(nature = NatureOfService.VIEW)
public class LinkedInProfileService extends AbstractService  {

	public LinkedInResponse runLinkedInService(String token) {

		Gson gson = new Gson();

		try {
			javax.ws.rs.client.Client client = ClientBuilder.newClient();
			client.property(ClientProperties.CONNECT_TIMEOUT, 1000);
		    client.property(ClientProperties.READ_TIMEOUT,    1000);
			
			String resourceString = "https://api.linkedin.com/v1/people/~?format=json&oauth2_access_token="
					.concat(token);
			WebTarget webResource2 = client.target(resourceString);
			Invocation.Builder invocationBuilder =
					webResource2.request(MediaType.APPLICATION_JSON);
			Response response = invocationBuilder.get();
			if (response.getStatus() != 200) {
				throw new RuntimeException("" + response.getStatus());
			}
			System.out.println("running LinkedInService: " + response.getStatus());

			LinkedInResponse linkedInResponse = new LinkedInResponse();
			linkedInResponse = gson.fromJson(response.readEntity(String.class), LinkedInResponse.class);
			linkedInResponse.setSuccess(true);
//			System.out.println(linkedInResponse.getFirstName());
//			System.out.println(linkedInResponse.getLastName());
//			System.out.println(linkedInResponse.getId());
//			System.out.println(linkedInResponse.getHeadline());
//			System.out.println(linkedInResponse.getSuccess());
			return linkedInResponse;

		} catch (Exception e) {
			System.out.println("status:" + e.getMessage());
			if (e.getMessage().equals("404")) {
				System.out.println("Resource not found.");
			}
			if (e.getMessage().equals("500")) {
				System.out.println("An unknown error occured.");
			}

			LinkedInResponse emptyResponse = new LinkedInResponse();
			emptyResponse.setId("");
			emptyResponse.setFirstName("");
			emptyResponse.setLastName("");
			emptyResponse.setHeadline("");
			emptyResponse.setSuccess(false);
			return emptyResponse;
		}
	}

}
