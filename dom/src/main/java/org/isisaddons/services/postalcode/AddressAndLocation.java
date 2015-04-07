package org.isisaddons.services.postalcode;

public interface AddressAndLocation {
	
	public boolean getSucces();
	public String getStreet();
	public String getTown();
	public String getMunicipality();
	public String getProvince();
	public double getLatitude();
	public double getLongitude();
	public double getDistanceToJeroen();
	public double getDistanceToJohan();	
}
