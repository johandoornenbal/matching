package org.isisaddons.services.postalcode.postcodenunl;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.glassfish.jersey.client.ClientProperties;
import org.isisaddons.services.postalcode.Address;
import org.isisaddons.services.postalcode.AddressAndLocation;
import org.isisaddons.services.postalcode.Location;
import org.isisaddons.services.postalcode.PostalcodeService;

import com.google.gson.Gson;

@DomainService(nature = NatureOfService.VIEW)
public class PostcodeNuService extends AbstractService implements
		PostalcodeService {

	private static final String API_KEY = "f29dc2f513bbd1e6abf15a3e9bd1be6f6a6b5c91";

	public PostcodeNuResponse runPostcodeService(String postcode) {

		Gson gson = new Gson();

		try {
			javax.ws.rs.client.Client client = ClientBuilder.newClient();
			client.property(ClientProperties.CONNECT_TIMEOUT, 1000);
		    client.property(ClientProperties.READ_TIMEOUT,    1000);
			
			String resourceString = "http://api.postcodeapi.nu/"
					.concat(postcode);
			WebTarget webResource2 = client.target(resourceString);
			Invocation.Builder invocationBuilder =
					webResource2.request(MediaType.APPLICATION_JSON);
			invocationBuilder.header("Api-Key", API_KEY);
			Response response = invocationBuilder.get();
			if (response.getStatus() != 200) {
				throw new RuntimeException("" + response.getStatus());
			}
			System.out.println("running PostcodeNuService: " + response.getStatus());
			PostcodeNuResponse postcodeNuResponse = new PostcodeNuResponse();
			postcodeNuResponse = gson.fromJson(response.readEntity(String.class), PostcodeNuResponse.class);
			postcodeNuResponse.setSucces(true);
			return postcodeNuResponse;

		} catch (Exception e) {
			System.out.println("status:" + e.getMessage());
			if (e.getMessage().equals("401")) {
				System.out.println("A valid 'Api-Key' needs to be provided in the 'Headers' in order to use this API");
			}
			if (e.getMessage().equals("404")) {
				System.out.println("Resource not found.");
			}
			if (e.getMessage().equals("500")) {
				System.out.println("An unknown error occured.");
			}
			
			PostcodeNuResponse emptyResponse = new PostcodeNuResponse();
			PostcodeNuResponseResource emptyResource = new PostcodeNuResponseResource();
			emptyResource.setLatitude(0);
			emptyResource.setLongitude(0);
			emptyResource.setMunicipality("");
			emptyResource.setPostcode("0000AA");
			emptyResource.setProvince("");
			emptyResource.setStreet("");
			emptyResource.setSuccess(false);
			emptyResource.setTown("");
			emptyResponse.setResource(emptyResource);
			return emptyResponse;
		}
	}

	public PostcodeNuResponse runPostcodeService(String postcode, Integer housenumber) {

		Gson gson = new Gson();

		try {
			javax.ws.rs.client.Client client = ClientBuilder.newClient();
			client.property(ClientProperties.CONNECT_TIMEOUT, 1000);
		    client.property(ClientProperties.READ_TIMEOUT,    1000);
			
			String resourceString = "http://api.postcodeapi.nu/"
					.concat(postcode).concat("/").concat(housenumber.toString());
			WebTarget webResource2 = client.target(resourceString);
			Invocation.Builder invocationBuilder =
					webResource2.request(MediaType.APPLICATION_JSON);
			invocationBuilder.header("Api-Key", API_KEY);
			Response response = invocationBuilder.get();
			if (response.getStatus() != 200) {
				throw new RuntimeException("" + response.getStatus());
			}
			System.out.println("running PostcodeNuService: " + response.getStatus());
			PostcodeNuResponse postcodeNuResponse = new PostcodeNuResponse();
			postcodeNuResponse = gson.fromJson(response.readEntity(String.class), PostcodeNuResponse.class);
			postcodeNuResponse.setSucces(true);
			return postcodeNuResponse;

		} catch (Exception e) {
			System.out.println("status:" + e.getMessage());
			if (e.getMessage().equals("401")) {
				System.out.println("A valid 'Api-Key' needs to be provided in the 'Headers' in order to use this API");
			}
			if (e.getMessage().equals("404")) {
				System.out.println("Resource not found.");
			}
			if (e.getMessage().equals("500")) {
				System.out.println("An unknown error occured.");
			}
			
			PostcodeNuResponse emptyResponse = new PostcodeNuResponse();
			PostcodeNuResponseResource emptyResource = new PostcodeNuResponseResource();
			emptyResource.setLatitude(0);
			emptyResource.setLongitude(0);
			emptyResource.setMunicipality("");
			emptyResource.setPostcode("0000AA");
			emptyResource.setProvince("");
			emptyResource.setStreet("");
			emptyResource.setSuccess(false);
			emptyResource.setTown("");
			emptyResponse.setResource(emptyResource);
			return emptyResponse;
		}
	}

	@Override
	public Address addressFromPostalCode(String countryCode, String postalCode) {
		return runPostcodeService(postalCode);
	}

	@Override
	public Address addressFromPostalCode(String countryCode, String postalCode,
			Integer houseNumber) {
		return runPostcodeService(postalCode, houseNumber);
	}
	
	@Override
	public Location locationFromPostalCode(String countryCode, String postalCode) {
		return runPostcodeService(postalCode);
	}
	
	@Override
	public Location locationFromPostalCode(String countryCode, String postalCode,
			Integer houseNumber) {
		return runPostcodeService(postalCode, houseNumber);
	}
	
	@Override
	public AddressAndLocation addressAndLocationFromPostalCode(String countryCode, String postalCode) {
		return runPostcodeService(postalCode);
	}
	
	@Override
	public AddressAndLocation addressAndLocationFromPostalCode(String countryCode, String postalCode,
			Integer houseNumber) {
		return runPostcodeService(postalCode, houseNumber);
	}

}
