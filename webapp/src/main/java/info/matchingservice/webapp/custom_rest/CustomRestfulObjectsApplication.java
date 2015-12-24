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

package info.matchingservice.webapp.custom_rest;

import info.matchingservice.webapp.custom_rest.v2.PersonResource;
import info.matchingservice.webapp.custom_rest.v2.UserAuthentificationResource;
import info.matchingservice.webapp.custom_rest.v2.UserRegistrationResource;
import org.apache.isis.viewer.restfulobjects.server.RestfulObjectsApplication;

/**
 * Created by jodo on 15/05/15.
 */
public class CustomRestfulObjectsApplication extends RestfulObjectsApplication {

    public CustomRestfulObjectsApplication() {

        addClass(UserRegistrationResource.class);
        addClass(UserRegistrationLegacyResource.class);
        addClass(UserAuthentificationResource.class);
        addClass(ProfileResource.class);
        addClass(FindResource.class);
        addClass(HowdoidoResource.class);
        addClass(PersonResource.class);
        addClass(DemandResource.class);
        addClass(SupplyResource.class);
        addClass(LoginResource.class);

    }

}
