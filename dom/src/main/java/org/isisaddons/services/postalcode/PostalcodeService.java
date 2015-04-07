package org.isisaddons.services.postalcode;

public interface PostalcodeService {
	
	public Address addressFromPostalCode(String countryCode, String postalCode);
	public Address addressFromPostalCode(String countryCode, String postalCode, Integer houseNumber);
	public Location locationFromPostalCode(String countryCode, String postalCode);
	public Location locationFromPostalCode(String countryCode, String postalCode, Integer houseNumber);
	public AddressAndLocation addressAndLocationFromPostalCode(String countryCode, String postalCode);
	public AddressAndLocation addressAndLocationFromPostalCode(String countryCode, String postalCode, Integer houseNumber);
}
