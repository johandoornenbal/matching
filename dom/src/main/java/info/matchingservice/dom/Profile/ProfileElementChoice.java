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

package info.matchingservice.dom.Profile;

import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

import info.matchingservice.dom.MatchingDomainObject;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findProfileElementChoicesByDemandOrSupply", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Profile.ProfileElementChoice "
                        + "WHERE demandOrSupply == :demandOrSupply")
})
@DomainObject()
public class ProfileElementChoice extends MatchingDomainObject<ProfileElementChoice>{

    public ProfileElementChoice() {
        super("demandOrSupply, widgetType, description, action");
    }

    //region > demandOrSupply (property)
    private DemandOrSupply demandOrSupply;

    @MemberOrder(sequence = "1")
    @javax.jdo.annotations.Column(allowsNull = "false")
    public DemandOrSupply getDemandOrSupply() {
        return demandOrSupply;
    }

    public void setDemandOrSupply(final DemandOrSupply demandOrSupply) {
        this.demandOrSupply = demandOrSupply;
    }
    //endregion

    //region > widgetType (property)
    private ProfileElementWidgetType widgetType;

    @MemberOrder(sequence = "2")
    @javax.jdo.annotations.Column(allowsNull = "false")
    public ProfileElementWidgetType getWidgetType() {
        return widgetType;
    }

    public void setWidgetType(final ProfileElementWidgetType widgetType) {
        this.widgetType = widgetType;
    }
    //endregion

    //region > description (property)
    private String description;

    @MemberOrder(sequence = "3")
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
    //endregion

    //region > action (property)
    private String action;

    @MemberOrder(sequence = "4")
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getAction() {
        return action;
    }

    public void setAction(final String action) {
        this.action = action;
    }
    //endregion

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public List<ProfileElementChoice> deleteProfileElementChoice(
            @ParameterLayout(named="confirmDelete")
            @Parameter(optionality= Optionality.OPTIONAL)
            boolean confirmDelete
    ) {
        container.removeIfNotAlready(this);
        container.informUser("Elementkeuze verwijderd");
        return profileElementChoices.allProfileElementChoices();
    }

    public String validateDeleteProfileElementChoice(boolean confirmDelete) {
        return confirmDelete? null:"CONFIRM_DELETE";
    }

    @Inject
    DomainObjectContainer container;

    @Inject
    ProfileElementChoices profileElementChoices;

}
