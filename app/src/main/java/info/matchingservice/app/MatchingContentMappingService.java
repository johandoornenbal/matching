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

import info.matchingservice.dom.Howdoido.BasicUser;
import info.matchingservice.dom.howdoido.BasicUserDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.schema.common.v1.OidDto;
import org.apache.isis.viewer.restfulobjects.applib.RepresentationType;
import org.apache.isis.viewer.restfulobjects.rendering.service.conmap.ContentMappingService;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.MediaType;
import java.util.List;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class MatchingContentMappingService implements ContentMappingService {

    private MapperFactory mapperFactory;

    @Programmatic
    @PostConstruct
    public void init() {
        mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.registerClassMap(
                mapperFactory.classMap(BasicUser.class, BasicUserDto.class)
                        .byDefault() // all fields are the compatible
                        .toClassMap());
        mapperFactory.registerClassMap(
                mapperFactory.classMap(Bookmark.class, OidDto.class)
                        .field("identifier", "objectIdentifier") // customized
                        .byDefault() // all other fields are compatible
                        .toClassMap());
    }

    @Programmatic
    @Override
    public Object map(
            final Object object,
            final List<MediaType> acceptableMediaTypes,
            final RepresentationType representationType) {

        if(object instanceof BasicUser) {
            final Bookmark bookmark = bookmarkService.bookmarkFor(object);

            final BasicUserDto dto = mapperFactory.getMapperFacade().map(object, BasicUserDto.class);
            final OidDto oidDto = mapperFactory.getMapperFacade().map(bookmark, OidDto.class);

            // manually wire together
            dto.setOid(oidDto);

            return dto;
        }

        return null;
    }

    //region > injected services
    @javax.inject.Inject
    private BookmarkService bookmarkService;
    //endregion

}
