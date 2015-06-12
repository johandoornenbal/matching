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

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

/**
 * Created by jonathan on 22-4-15.
 */



@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findProfileElementOfType", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Profile.RequiredProfileElementRole "
                        + "WHERE profileElementOwner == :profileElementOwner && profileElementType == :profileElementType")
})
public class RequiredProfileElementRole extends ProfileElement{


    private boolean student = false;

    @javax.jdo.annotations.Column(allowsNull = "true")
    public boolean getStudent() {
        return student;
    }

    public void setStudent(boolean student) {
        this.student = student;
    }

    private boolean professional = false;

    @javax.jdo.annotations.Column(allowsNull = "true")
    public boolean getProfessional() {
        return professional;
    }

    public void setProfessional(boolean professional) {
        this.professional = professional;
    }

    private boolean principal = false;

    @javax.jdo.annotations.Column(allowsNull = "true")
    public boolean getPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    //** API: ACTIONS **//

    //** updateStudent **//

    @Action(semantics= SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout()
    public RequiredProfileElementRole updateStudent(boolean student) {
        this.setStudent(student);
        return this;
    }

    public boolean default0UpdateStudent() {
        return getStudent();
    }

    //-- updateStudent --//

    //** updateProfessional **//

    @Action(semantics= SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout()
    public RequiredProfileElementRole updateProfessional(boolean professional) {
        this.setProfessional(professional);
        return this;
    }

    public boolean default0UpdateProfessional() {
        return getProfessional();
    }

    //-- updateProfessional --//

    //** updatePrincipal **//

    @Action(semantics= SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout()
    public RequiredProfileElementRole updatePrincipal(boolean principal) {
        this.setPrincipal(principal);
        return this;
    }

    public boolean default0UpdatePrincipal() {
        return getPrincipal();
    }

    //-- updatePrincipal --//



}
