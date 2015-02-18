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
import javax.jdo.annotations.Persistent;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.joda.time.LocalDate;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findProfileElementOfType", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.ProfileElementTimePeriod "
                    + "WHERE profileElementOwner == :profileElementOwner && profileElementType == :profileElementType")
})
public class ProfileElementTimePeriod extends ProfileElement {
    
    //REPRESENTATIONS /////////////////////////////////////////////////////////////////////////////////////
    
    @Persistent
	private LocalDate startDate;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	@Persistent
    private LocalDate endDate;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
    
    
    // Business rules:
    // Only on type TIME_PERIOD
	// Normal validation of date: Start before End and End after toDay
    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public ProfileElement updateTimePeriod(
    		@ParameterLayout(named = "startDate")
            LocalDate startDate,
            @ParameterLayout(named = "endDate")
            LocalDate endDate
            ){
        this.setStartDate(startDate);
        this.setEndDate(endDate);
        return this;
    }
    
    public LocalDate default0UpdateTimePeriod() {
        return getStartDate();
    }
    
    public LocalDate default1UpdateTimePeriod() {
        return getEndDate();
    }
    
    public boolean hideUpdateTimePeriod(LocalDate startDate, LocalDate endDate){
    	if (getProfileElementType() == ProfileElementType.TIME_PERIOD){
    		return false;
    	}
    	
    	return true;
    }
    
    public String validateUpdateTimePeriod(LocalDate startDate, LocalDate endDate) {
    	
    	final LocalDate today = LocalDate.now();
    	if (endDate != null && endDate.isBefore(today))
    	{
    		return "ENDDATE_BEFORE_TODAY";
    	}
    	
    	if (
    			endDate != null 
    			
    			&& 
    			
    			startDate != null
    			
    			&&
    			
    			endDate.isBefore(startDate)
    			
    			)
    	{
    		return "ENDDATE_BEFORE_STARTDATE";
    	}
    	
    	return null;
    }
    
}
