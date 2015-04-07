package org.isisaddons.services.postalcode.postcodenunl;

import org.isisaddons.services.postalcode.Address;
import org.isisaddons.services.postalcode.AddressAndLocation;
import org.isisaddons.services.postalcode.Location;

public class PostcodeNuResponse implements Address, Location, AddressAndLocation{

	private boolean succes;
	
	private PostcodeNuResponseResource resource;
	
	@Override
	public boolean getSucces() {
		return succes;
	}

	public void setSucces(boolean succes) {
		this.succes = succes;
	}

	public PostcodeNuResponseResource getResource() {
		return resource;
	}

	public void setResource(PostcodeNuResponseResource resource) {
		this.resource = resource;
	}

	@Override
	public String getStreet() {
		return getResource().getStreet();
	}

	@Override
	public double getLatitude() {
		return getResource().getLatitude();
	}

	@Override
	public double getLongitude() {
		return getResource().getLongitude();
	}

	@Override
	public double getDistanceToJeroen() {
		return Haversine.haversine(52.3735, 4.94636, getResource().getLatitude(), getResource().getLongitude());
	}

	@Override
	public double getDistanceToJohan() {
		return Haversine.haversine(53.2152, 5.83454, getResource().getLatitude(), getResource().getLongitude());
	}

	@Override
	public String getTown() {
		return getResource().getTown();
	}

	@Override
	public String getMunicipality() {
		return getResource().getMunicipality();
	}

	@Override
	public String getProvince() {
		return getResource().getProvince();
	}
}
