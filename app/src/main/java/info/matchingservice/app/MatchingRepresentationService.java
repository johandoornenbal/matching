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
package info.matchingservice.app;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.viewer.restfulobjects.rendering.service.RepresentationServiceForRestfulObjects;

import javax.ws.rs.core.Response;

/**
 * Demonstrates an extension to the built-in representation service (a framework-provided service).
 */
@DomainService(
        nature = NatureOfService.DOMAIN
)
@DomainServiceLayout(
        menuOrder = "1" // override the framework-provided service
)
public class MatchingRepresentationService extends RepresentationServiceForRestfulObjects {

    @Override
    protected Response buildResponse(Response.ResponseBuilder responseBuilder) {
        responseBuilder.header("X-ResponseGeneratedBy", getClass().getCanonicalName());
        return super.buildResponse(responseBuilder);
    }

}
