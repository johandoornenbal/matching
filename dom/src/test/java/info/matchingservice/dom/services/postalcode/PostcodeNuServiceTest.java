package info.matchingservice.dom.services.postalcode;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.isisaddons.services.postalcode.Address;
import org.isisaddons.services.postalcode.AddressAndLocation;
import org.isisaddons.services.postalcode.Location;
import org.isisaddons.services.postalcode.PostalcodeService;
import org.isisaddons.services.postalcode.postcodenunl.PostcodeNuService;

import static org.hamcrest.Matchers.is;

public class PostcodeNuServiceTest {

	PostalcodeService service;

	@Before
	public void setup() {
		service = new PostcodeNuService();
	}
	
	
	@Test
	@Ignore
	public void happyCase() {
		// Given, when
		// without housenumber
		Address address = service.addressFromPostalCode(null,"8926PJ");
		Location location = service.locationFromPostalCode(null,"8926PJ");
		AddressAndLocation addressAndLocation = service.addressAndLocationFromPostalCode(null,"8926PJ");
		// with housenumber
		Address address2 = service.addressFromPostalCode(null,"8926PJ", 11);
		Location location2 = service.locationFromPostalCode(null,"8926PJ", 11);
		AddressAndLocation addressAndLocation2 = service.addressAndLocationFromPostalCode(null,"8926PJ", 11);
		// Then
		Assert.assertThat(address.getSucces(), is(true));
		Assert.assertThat(address.getStreet(),
				is("Bongastate"));
		
		Assert.assertThat(location.getSucces(), is(true));
		Assert.assertEquals(location.getLatitude(), 53.21, 5);
		Assert.assertEquals(location.getLongitude(), 5.83, 5);
		
		Assert.assertThat(addressAndLocation.getSucces(), is(true));
		Assert.assertThat(addressAndLocation.getStreet(), is("Bongastate"));
		Assert.assertEquals(addressAndLocation.getLatitude(), 53.215478, .0001);
		Assert.assertEquals(addressAndLocation.getLongitude(), 5.834471, .0001);
		
		Assert.assertThat(address2.getSucces(), is(true));
		Assert.assertThat(address2.getStreet(),
				is("Bongastate"));
		
		Assert.assertThat(location2.getSucces(), is(true));
		Assert.assertEquals(location2.getLatitude(), 53.2152, 0);
		Assert.assertEquals(location2.getLongitude(), 5.83454, 0);
		
		Assert.assertThat(addressAndLocation2.getSucces(), is(true));
		Assert.assertThat(addressAndLocation2.getStreet(), is("Bongastate"));
		Assert.assertEquals(addressAndLocation2.getLatitude(), 53.2152, 0);
		Assert.assertEquals(addressAndLocation2.getLongitude(), 5.83454, 0);
		
	}

	@Test
	public void sadCase() {
		// Given, when
		
		Address address = service.addressFromPostalCode(null,"JKH ASHFushdf iuahsdfapsdf");
		Location location = service.locationFromPostalCode(null,"JKH ASHFushdf iuahsdfapsdf");
		AddressAndLocation addressAndLocation = service.addressAndLocationFromPostalCode(null,"JKH ASHFushdf iuahsdfapsdf");
		// Then
		Assert.assertThat(address.getSucces(), is(false));
		Assert.assertThat(address.getStreet(), is(""));
		
		Assert.assertThat(location.getSucces(), is(false));
		Assert.assertEquals(location.getLatitude(), 0, 0);
		Assert.assertEquals(location.getLongitude(), 0, 0);
		
		Assert.assertThat(addressAndLocation.getSucces(), is(false));
		Assert.assertThat(addressAndLocation.getStreet(), is(""));
		Assert.assertEquals(addressAndLocation.getLatitude(), 0, 0);
		Assert.assertEquals(addressAndLocation.getLongitude(), 0, 0);
	}
	
	
	@Test
	@Ignore
	public void caseNonExistingHousenumber() {
		// Given, when
		
		AddressAndLocation addressAndLocation = service.addressAndLocationFromPostalCode(null,"8926PJ", 0);
		
		// Then still returns street and location
		Assert.assertThat(addressAndLocation.getSucces(), is(true));
		Assert.assertThat(addressAndLocation.getStreet(), is("Bongastate"));
		Assert.assertEquals(addressAndLocation.getLatitude(), 53.21, 5);
	}

	
}
