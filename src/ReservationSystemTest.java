import java.util.ArrayList;
import junit.framework.TestCase;

/**
 * Tests the ReservationSystem class.
 * 
 * @author chrcoe
 * @version July 17, 2012
 */
public class ReservationSystemTest extends TestCase {

    private ReservationSystem rSys;
    private ArrayList<String> flightInfo;
    
    /**
     * Sets up the test cases.
     */
    protected void setUp() throws Exception {
        super.setUp();
        flightInfo = new ArrayList<String>();
        flightInfo.add("Columbus:Cleveland:120");
        flightInfo.add("Cleveland:Detroit:40");
        flightInfo.add("Columbus:Detroit:180");
        rSys = new ReservationSystem(flightInfo);
    }

    
    /**
     * Test the ReservationSystem constructor.
     */
    public void testReservationSystem() {
        assertNotNull("should not be null", rSys);
    }

    /**
     * Test the findFlights method.
     */
    public void testFindFlights() {
        ArrayList<String> flightInfo2 = rSys.findFlights("Columbus");
        assertTrue("did not contain the proper info",
                flightInfo2.contains("Columbus,Cleveland:120"));
        assertTrue("did not contain the proper info",
                flightInfo2.contains("Columbus,Cleveland,Detroit:160"));
    }
    
    /**
     * Test unreachable status for both source and dest.
     */
    public void testUnreachable() {
        flightInfo.add("Cincinatti:Columbus:100");
        
        rSys = new ReservationSystem(flightInfo);
        ArrayList<String> flightInfo2 = rSys.findFlights("Columbus");
        flightInfo2 = rSys.findFlights("Columbus");
        assertFalse("did not contain the proper info",
                flightInfo2.contains("Columbus,Cincinatti:100"));
        
    }

}
