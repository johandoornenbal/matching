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
package info.matchingservice.dom.Assessment;

import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;

import info.matchingservice.dom.Match.ProfileMatch;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findProfileMatchesAssessmentByProfileMatch", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Assessment.ProfileMatchAssessment "
                        + "WHERE targetOfAssessment == :profileMatch")
})
public class ProfileMatchAssessment extends Assessment {
    
	//** API: PROPERTIES **//
    private ProfileMatch targetOfAssessment;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @PropertyLayout()
    @Property(editing=Editing.DISABLED)
    public ProfileMatch getTargetOfAssessment() {
        return targetOfAssessment;
    }
    
    public void setTargetOfAssessment(final ProfileMatch object) {
        this.targetOfAssessment = object;
    }       
	//-- API: PROPERTIES --//
}