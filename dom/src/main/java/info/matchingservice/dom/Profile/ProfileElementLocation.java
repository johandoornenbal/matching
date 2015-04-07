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
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.isisaddons.services.postalcode.Location;
import org.isisaddons.services.postalcode.postcodenunl.PostcodeNuService;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findProfileElementOfType", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.ProfileElementLocation "
                    + "WHERE profileElementOwner == :profileElementOwner && profileElementType == :profileElementType")
})
public class ProfileElementLocation extends ProfileElement {
    
    //postCode /////////////////////////////////////////////////////////////////////////////////////
    
    private String postcode;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getPostcode(){
        return postcode;
    }
    
    public void setPostcode(final String value){
        this.postcode = value;
    }
    
    //latitude /////////////////////////////////////////////////////////////////////////////////////
    
    private double latitude;

    @javax.jdo.annotations.Column(allowsNull = "true")
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}


	//longitude /////////////////////////////////////////////////////////////////////////////////////

	private double longitude;
    
	@javax.jdo.annotations.Column(allowsNull = "true")
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	//isValid /////////////////////////////////////////////////////////////////////////////////////
	
	private boolean isValid;
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	public boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(boolean isValid) {
		this.isValid = isValid;
	}

    
    
    // BUSINESS RULE
    // Alleen op type LOCATION (Anders komt deze ook bij bijvoorbeeld Passie)
    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public ProfileElement updateLocation(
    		@ParameterLayout(
    				named = "postcode"
    				)
    		@Parameter(regexPattern="^[1-9]{1}[0-9]{3}[A-Z]{2}$")
            String postcode
            ){
    	PostcodeNuService service = new PostcodeNuService();
        Location location = service.locationFromPostalCode(null, postcode);
        this.setLongitude(location.getLongitude());
        this.setLatitude(location.getLatitude());
        this.setIsValid(location.getSucces());
        this.setPostcode(postcode);
        this.setDisplayValue(postcode);
        return this;
    }
    
    public String default0UpdateLocation() {
        return getPostcode();
    }
    
    public boolean hideUpdateLocation(String textValue){
    	if (getProfileElementType() == ProfileElementType.LOCATION){
    		return false;
    	}
    	
    	return true;
    }
    
}
