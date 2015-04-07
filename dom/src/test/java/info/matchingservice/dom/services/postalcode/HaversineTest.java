package info.matchingservice.dom.services.postalcode;

import static org.hamcrest.Matchers.is;

import org.isisaddons.services.postalcode.postcodenunl.Haversine;
import org.junit.Assert;
import org.junit.Test;

public class HaversineTest {
	
	private Haversine distance;

	@Test
	public void happyCase() {
		Assert.assertThat(distance.haversine(36.12, -86.67, 33.94, -118.40),
				is(2887.2599506071106));
		
	}

}