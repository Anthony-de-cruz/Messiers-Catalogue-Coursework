/**
 * @author Anthony de Cruz
 */
public class MessierProgram {

    public static void main(String[] args) {

        test1();
    }

    /**
     * Setup the catalogue.
     * 
     * @return The Messier Object Catalogue
     */
    public static MessierCatalogue setup() {

        //todo Read from file

        //todo Create catalogue

        return new MessierCatalogue();
    }

    /* --------------------------------- Queries -------------------------------- */

    /**
     * Display the catalogue, arranged in order of ascending apparent magnitude
     * (bright-est object first).
     */
    public static void queryA() {

    }

    /**
     * Display the average apparent magnitude of all open clusters.
     */
    public static void queryB() {

    }

    /**
     * Display the details of the most distant globular cluster.
     */
    public static void queryC() {

    }

    /**
     * Display the details of the object in the constellaon Sagittarius with the
     * lowest declination.
     */
    public static void queryD() {

    }

    /**
     * Display the details of the object that is closest in the sky to M45 (in the
     * sense of having the smallest angular distance). Hint: test your code using
     * M42 (the Great Orion Nebula) as M42 and M43 (De Mairan’s Nebula) are both are
     * part of the Orion molecular cloud complex, and so they should have a small
     * angular separaon (approximately 8 arc minutes)
     */
    public static void queryE() {

    }

    /* ---------------------------------- Tests --------------------------------- */

    public static void test1() {

        String demo1 = "M1, \"NGC 1952\", \"Crab Nebula\", Supernova remnant, 4.9-8.1, Taurus, 8.4, 5h 34m 31.9400s, 22° 0' 52.2000\"";
        String demo2 = "M2, \"NGC 7089\", \"-\", Globular cluster, 33.0, Aquarius, 6.5, 21h 33m 27.0200s, 0° 49' 23.7000\"";
        String demo3 = "M17, \"NGC 6618\", \"Omega, Swan, Horseshoe, Lobster, or Checkmark Nebula\", H II region nebula with cluster, 5.0-6.0, Sagittarius, 6.0, 18h 20m 26.0000s, -16° 10' 36.0000\"";

        
        System.out.println("\n--- Obj1 ---");        

        MessierObject obj1 = new MessierObject(demo3);
        System.out.println(demo3);
        System.out.println(obj1.toString());
        System.out.println(obj1.getNgcicNumber());
        System.out.println(obj1.getDeclinationString());

        System.out.println("\n--- Obj2 ---");

        MessierObject obj2 = new MessierObject(demo2);
        System.out.println(demo2);
        System.out.println(obj2.toString());
        System.out.println(obj2.getMeanDistance());
        System.out.println(obj2.getRightAscensionString());

        System.out.println("\n--- Comparison ---");

        System.out.println(obj1.compareTo(obj2));

        System.out.println("\n--- Catalogue ---");

        MessierCatalogue catalogue = new MessierCatalogue(new MessierObject[] {obj1, obj2});

        System.out.println(catalogue.toString());
    }

}
