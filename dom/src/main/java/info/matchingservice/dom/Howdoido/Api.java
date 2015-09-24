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

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

/**
 * Created by jodo on 30/08/15.
 */
@DomainService(nature = NatureOfService.VIEW_MENU_ONLY)
@DomainServiceLayout(named = "How Do I Do")
public class Api {

    @Action(semantics = SemanticsOf.SAFE)
    public BasicUser activeUser() {
        return basicUsers.findBasicUserByLogin(container.getUser().getName());
    }


    //region > createBasicUser (method)

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public BasicUser createBasicUser(
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "name")
            final String name,
            @ParameterLayout(named = "email")
            @Parameter(regexPattern ="^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}$")
            final String email) {

        return basicUsers.createBasicUser(name, email);
    }

    public String validateCreateBasicUser(final String name, final String email) {

        //check valid email
        for (BasicUser bu : basicUsers.allBasicUsers()){
            if (bu.getEmail().toLowerCase().equals(email.toLowerCase())) {
                return "This emailaddress is already taken";
            }
        }

        return null;
    }
    //endregion

    //region > createTopCategory (method)
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public BasicCategory createTopCategory(
            @ParameterLayout(named = "name")
            final String name) {
        return basicCategories.createBasicCategory(name, null);
    }

    public String validateCreateTopCategory(final String name) {

        //check valid name
        for (BasicCategory bc : basicCategories.allBasicCategories()){
            if (name.toLowerCase().equals(bc.getName().toLowerCase())) {
                return "Name of basic category should be unique";
            }
        }

        return null;
    }

    //endregion

    @Action(semantics = SemanticsOf.SAFE)
    public List<BasicCategory> findCategory(
            @ParameterLayout(named = "zoek...")
            final String search) {
        return basicCategories.findByNameContains(search);
    }

    public BasicTemplate createBasicTemplate(
            @ParameterLayout(named = "name")
            final String name,
            @ParameterLayout(named = "category")
            final BasicCategory category,
            @ParameterLayout(named = "template owner")
            final BasicUser templateOwner
    ) {
        return basicTemplates.createBasicTemplate(name, category, templateOwner);
    }

    public List<BasicCategory> autoComplete1CreateBasicTemplate(String search) {
        return basicCategories.findByNameContains(search);
    }

    public List<BasicUser> autoComplete2CreateBasicTemplate(String search) {
        return basicUsers.allBasicUsers();
    }

    public List<BasicCategory> allBasicCategories() {
        return basicCategories.allBasicCategories();
    }

    public List<BasicUser> allUsers() {
        return basicUsers.allBasicUsers();
    }

    public List<BasicTemplate> allTemplates() {return basicTemplates.allBasicTemplates();}

    @Inject
    BasicUsers basicUsers;

    @Inject
    BasicCategories basicCategories;

    @Inject
    BasicTemplates basicTemplates;

    @Inject
    DomainObjectContainer container;
}
