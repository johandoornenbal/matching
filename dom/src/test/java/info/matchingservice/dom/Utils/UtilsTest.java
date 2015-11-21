package info.matchingservice.dom.Utils;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by jodo on 21/11/15.
 */
public class UtilsTest {


    final Utils utils = new Utils();


    @Test
    public void testIsValidDate(){

        String validDate1 = "2000-12-31";
        String validDate2 = "2000-02-29";

        String inValidDate1 = "1999-02-29";
        String inValidDate2 = "2000-1-01";
        String inValidDate3 = "2000-01-1";

        assertTrue(utils.isValidDate(validDate1));
        assertTrue(utils.isValidDate(validDate2));

        assertFalse(utils.isValidDate(inValidDate1));
        assertFalse(utils.isValidDate(inValidDate2));
        assertFalse(utils.isValidDate(inValidDate3));
    }

}
