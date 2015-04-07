package org.isisaddons.services.postalcode.postcodenunl;

public class PostcodeNuResponseResource {
	
	private Boolean success;
	private String street;
	private String postcode;
	private String town;
	private String municipality;
	private String province;
	private double latitude;
	private double longitude;

	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getMunicipality() {
		return municipality;
	}
	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
	

}
