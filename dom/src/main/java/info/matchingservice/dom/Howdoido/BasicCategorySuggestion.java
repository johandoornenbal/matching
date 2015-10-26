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

import info.matchingservice.dom.MatchingDomainObject;
import org.apache.isis.applib.annotation.*;

import javax.jdo.annotations.*;

/**
 * Created by jodo on 31/08/15.
 */
@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
@Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@DomainObject(editing = Editing.DISABLED, autoCompleteRepository = BasicCategories.class)
@DomainObjectLayout()
public class BasicCategorySuggestion extends MatchingDomainObject {

    //region > name (property)
    private String name;

    public BasicCategorySuggestion() {
        super("name");
    }

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "false")
    @Title
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
    //endregion

    //region > parentCategory (property)
    private BasicCategory parentCategory;

    @MemberOrder(sequence = "2")
    @Column(allowsNull = "false")
    public BasicCategory getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(final BasicCategory parentCategory) {
        this.parentCategory = parentCategory;
    }
    //endregion


}
