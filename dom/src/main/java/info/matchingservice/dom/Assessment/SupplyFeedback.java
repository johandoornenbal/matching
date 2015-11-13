/*
 *
 *  Copyright 2015 Yodo Int. Projects and Consultancy
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package info.matchingservice.dom.Assessment;

import info.matchingservice.dom.DemandSupply.Supply;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
public class SupplyFeedback extends Assessment {
    
	//** API: PROPERTIES **//

    private Supply targetOfAssessment;

    @javax.jdo.annotations.Column(allowsNull = "false", name = "supplyId")
    @Property(editing= Editing.DISABLED)
    @PropertyLayout()
    public Supply getTargetOfAssessment() {
        return targetOfAssessment;
    }

    public void setTargetOfAssessment(final Supply targetOfAssessment) {
        this.targetOfAssessment = targetOfAssessment;
    }


    private String feedback;
    
    @PropertyLayout(multiLine=3)
    @javax.jdo.annotations.Column(allowsNull = "true")
    public String getFeedback() {
        return feedback;
    }
    
    public void setFeedback(final String feedback) {
        this.feedback = feedback;
    }
    //-- API: PROPERTIES --//
}
