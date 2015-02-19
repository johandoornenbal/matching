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

package info.matchingservice.dom.Profile;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findProfileElementOfType", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.ProfileElementUseTimePeriod "
                    + "WHERE profileElementOwner == :profileElementOwner && profileElementType == :profileElementType")
})
public class ProfileElementUseTimePeriod extends ProfileElement {
    
    //REPRESENTATIONS /////////////////////////////////////////////////////////////////////////////////////
	
	// Put on the parent as a work-a-round for persistence problems
	
//	@Persistent
//    private boolean useTimePeriod;
//    
//    @javax.jdo.annotations.Column(allowsNull = "false")
//	public boolean getUseTimePeriod() {
//		return useTimePeriod;
//	}
//
//	public void setUseTimePeriod(boolean useTimePeriod) {
//		this.useTimePeriod = useTimePeriod;
//	}
    
	
    // Business rules:
    // Only on type USE_TIME_PERIOD
    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public ProfileElement updateUseTimePeriod(
    		@ParameterLayout(named = "useTimePeriod")
            boolean useTimePeriod
            ){
        this.setUseTimePeriod(useTimePeriod);
        return this;
    }
    
    public boolean default0UpdateUseTimePeriod() {
        return getUseTimePeriod();
    }
    
    
    public boolean hideUpdateUseTimePeriod(boolean useTimePeriod){
    	if (getProfileElementType() == ProfileElementType.USE_TIME_PERIOD){
    		return false;
    	}
    	
    	return true;
    }
        
}
