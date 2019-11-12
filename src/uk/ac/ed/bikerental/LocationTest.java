import static org.junit.Assert.*;

import org.junit.Test;

public class LocationTest {

	@Before
	public void setUp() {
		Location location1 = new Location("EH10 5NG", "ookyspooky");
		Location location2 = new Location("EH20 7GG", "hmmmhrng");
		Location location3 = new Location("GB5 12AH", "longwalk");
		boolean close = location1.isNearTo(location2);
		boolean far = location1.isNearTo(location3);
	}
	
	@Test
	public void isNearToTest() {
		
	}

}
