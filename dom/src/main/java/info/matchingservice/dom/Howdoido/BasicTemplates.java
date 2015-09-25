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

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import info.matchingservice.dom.MatchingDomainService;

/**
 * Created by jodo on 31/08/15.
 */
@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = BasicTemplate.class)
public class BasicTemplates extends MatchingDomainService<BasicTemplate> {

    public BasicTemplates() {
        super(BasicTemplates.class, BasicTemplate.class);
    }

    public List<BasicTemplate> allBasicTemplates () {
        return allInstances(BasicTemplate.class);
    }

    @Programmatic
    public BasicTemplate createBasicTemplate (final String name, final BasicCategory category, final BasicUser templateOwner) {

        BasicTemplate newTemplate = newTransientInstance(BasicTemplate.class);

        newTemplate.setName(name.toLowerCase());
        newTemplate.setBasicCategory(category);
        newTemplate.setTemplateOwner(templateOwner);
        newTemplate.setOwnedBy(templateOwner.getOwnedBy());

        persistIfNotAlready(newTemplate);


        return newTemplate;

    }



}
