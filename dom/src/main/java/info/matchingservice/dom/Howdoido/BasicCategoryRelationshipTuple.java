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

import javax.jdo.annotations.Column;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Programmatic;

import info.matchingservice.dom.Howdoido.Interfaces.FeedbackCategory;
import info.matchingservice.dom.Howdoido.Interfaces.FeedbackCategoryRelationshipTuple;

/**
 * Created by jodo on 02/09/15.
 */
@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByParent", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Howdoido.BasicCategoryRelationshipTuple "
                        + "WHERE parent == :parent"),
        @javax.jdo.annotations.Query(
                name = "findByChild", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Howdoido.BasicCategoryRelationshipTuple "
                        + "WHERE child == :child")

})
@DomainObject(autoCompleteRepository = BasicCategoryRelationshipTuples.class)
public class BasicCategoryRelationshipTuple implements FeedbackCategoryRelationshipTuple {

    //region > parent (property)
    private BasicCategory parent;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "false", name = "parentId")
    public BasicCategory getParent() {
        return parent;
    }

    public void setParent(final BasicCategory parent) {
        this.parent = parent;
    }
    //endregion

    //region > child (property)
    private BasicCategory child;

    @MemberOrder(sequence = "2")
    @Column(allowsNull = "false", name = "childId")
    public BasicCategory getChild() {
        return child;
    }

    public void setChild(final BasicCategory child) {
        this.child = child;
    }
    //endregion

    @Override
    @Programmatic
    public FeedbackCategory getChildCategory() {
        return getChild();
    }

    @Override
    @Programmatic
    public FeedbackCategory getParentCategory() {
        return getParent();
    }
}
