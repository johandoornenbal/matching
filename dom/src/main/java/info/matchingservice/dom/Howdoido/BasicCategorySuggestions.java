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

import java.util.List;

/**
 * Created by jodo on 31/08/15.
 */
@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = BasicCategorySuggestion.class)
public class BasicCategorySuggestions extends MatchingDomainService<BasicCategorySuggestion> {

    public BasicCategorySuggestions() {
        super(BasicCategorySuggestions.class, BasicCategorySuggestion.class);
    }

    public List<BasicCategorySuggestion> allBasicCategorySuggestions () {
        return allInstances(BasicCategorySuggestion.class);
    }

    @Programmatic
    public BasicCategorySuggestion createBasicCategorySuggestion (final String name, final BasicCategory parent) {

        BasicCategorySuggestion newCategorySuggestion = newTransientInstance(BasicCategorySuggestion.class);

        newCategorySuggestion.setName(name.toLowerCase());
        newCategorySuggestion.setParentCategory(parent);
        persistIfNotAlready(newCategorySuggestion);

        return newCategorySuggestion;

    }


}
