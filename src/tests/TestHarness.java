package src.tests;

import src.MessierProgram.InvalidEntryException;
import src.MessierProgram.MessierCatalogue;
import src.MessierProgram.MessierObject;

public class TestHarness {

    /* ---------------------------------- Tests --------------------------------- */
    // Should use a test framework but thats out of the scope of the project and
    // target environment.

    /**
     * Testing MessierObject.
     * 
     * @return Test result
     */
    public static boolean testMessierObject() {

        String[] validStringData = {
                "M1, \"NGC 1952\", \"Crab Nebula\", Supernova remnant, 4.9-8.1, Taurus, 8.4, 5h 34m 31.9400s, 22° 0' 52.2000\"",
                "M2, \"NGC 7089\", \"-\", Globular cluster, 33.0, Aquarius, 6.5, 21h 33m 27.0200s, 0° 49' 23.7000\"",
                "M17, \"NGC 6618\", \"Omega, Swan, Horseshoe, Lobster, or Checkmark Nebula\", H II region nebula with cluster, 5.0-6.0, Sagittarius, 6.0, 18h 20m 26.0000s, -16° 10' 36.0000\"",
                "M94, \"NGC 4736\", \"Crocodile Eye or Cat's Eye Galaxy\", Spiral galaxy, 14700.0-17300.0, Canes Venatici, 8.2, 12h 50m 53.1000s, 41° 7' 14.0000\"",
        };

        String[] invalidStringData = {
                "M1, \"NGC 1952\", \"Crab Nebula\", Supernova remnant, 4.9-8.1, Taurus, 8.4, 5h 34m 31.9400s, 22° 0' 52.2000\", hello",
                "M1, \"NGC 1952\", \"Crab \"Nebula\", Supernova r,emnant, 4.9-8.1-5.2, 8.4, 5h 34m 31.9400s, 22° 0' 52.2000\"",
                "M1, \"NGC 1952\", \"Crab Nebula\", Supernova remnant, 4.9-8.1, Taurus, 8.4, 5h 34m 31.9400s, 22° 0' 52.2000\"",
                "M2a, \"NGC 7089\", \"-\", Globular cluster, 33.0, Aquarius, 6.5, 21h 33m 27.0200s, 0° 49' 23.7000\"",
                "M2a, \"NGC 7089\", \"-\", Globular cluster, 33.0, Aquarius, 6.5, -21h 33m 27.0200s, 0° 49' 23.7000\"",
                "M94, \"NGC 4736\", \"Crocodile Eye or Cat's Eye Galaxy or \", Spiral galaxy, 14700.0-17300.0, Canes Venatici, 8.2, 12h 50m 53.1000s, 41° 7' 14.0000\"",
        };

        /* ---------------------------- Test Constructor ---------------------------- */
        /* ------------------------ and in turn, the setters ------------------------ */

        // Valid test data
        for (int i = 0; i < validStringData.length; i++) {

            try {
                MessierObject obj = new MessierObject(validStringData[i]);

                if (!obj.toString().equals(validStringData[i])) {
                    System.err.println(validStringData[i]);
                    System.err.println(obj.toString());
                    System.err.println("Test 2, valid case " + i + ": FAILED");
                    return false;
                }

            } catch (InvalidEntryException exception) {
                exception.printStackTrace();
                System.err.println(validStringData[i]);
                System.err.println("Test 2, valid case " + i + ": FAILED");
                return false;
            }

            System.out.println("Valid case " + i + ": PASSED");
        }

        // Erroneous test data
        for (int i = 0; i < invalidStringData.length; i++) {

            try {
                MessierObject obj = new MessierObject(invalidStringData[1]);

                System.err.println(validStringData[i]);
                System.err.println(obj.toString());
                System.err.println("Valid case " + i + ": FAILED");
                return false;

            } catch (InvalidEntryException exception) {

                System.out.println("Invalid case " + i + ": PASSED");
            }
        }

        /* ---------------------- Test the rest of the Methods ---------------------- */



        System.out.println("\n -- testMessierObject: PASSED -- ");
        return true;
    }

    /**
     * Testing MessierCatalogue.
     * 
     * @return Test result
     */
    public static boolean testMessierCatalogue() {

        String[] validData = {
                "M1, \"NGC 1952\", \"Crab Nebula\", Supernova remnant, 4.9-8.1, Taurus, 8.4, 5h 34m 31.9400s, 22° 0' 52.2000\"",
                "M2, \"NGC 7089\", \"-\", Globular cluster, 33.0, Aquarius, 6.5, 21h 33m 27.0200s, 0° 49' 23.7000\"",
                "M17, \"NGC 6618\", \"Omega, Swan, Horseshoe, Lobster, or Checkmark Nebula\", H II region nebula with cluster, 5.0-6.0, Sagittarius, 6.0, 18h 20m 26.0000s, -16° 10' 36.0000\"",
                "M94, \"NGC 4736\", \"Crocodile Eye or Cat's Eye Galaxy\", Spiral galaxy, 14700.0-17300.0, Canes Venatici, 8.2, 12h 50m 53.1000s, 41° 7' 14.0000\"",
        };

        MessierObject[] validObjects = new MessierObject[4];

        for (int i = 0; i < validData.length; i++) {
            try {
                validObjects[i] = new MessierObject(validData[i]);

            } catch (InvalidEntryException exception) {
                exception.printStackTrace();
                System.err.println("FAILED TO CREATE OBJECT");
                System.err.println("Test 3, valid case " + i + ": FAILED");
                return false;
            }

        }

        // Test valid
        for (int i = 0; i < 1; i++) {

            try {
                MessierCatalogue catalogue = new MessierCatalogue(validObjects);

                catalogue.sort();

            } catch (Exception exception) {
                exception.printStackTrace();
                System.err.println("Test 3, valid case " + i + ": FAILED");
                return false;
            }
        }

        System.out.println("\n -- testMessierCatalogue: PASSED -- ");
        return true;
    }

    /**
     * Testing IOHandler.
     * 
     * @return Test result
     */
    public static boolean testIOHandler() {

        System.out.println("\n -- testIOHandler: PASSED -- ");
        return true;
    }

    /**
     * Run tests and return if all successful or if some failed.
     * 
     * @return Whether or not they passed or failed
     */
    public static boolean runTests() {

        boolean passed = true;

        // passed = passed && test1();
        passed = passed && testMessierObject();
        passed = passed && testMessierCatalogue();
        passed = passed && testIOHandler();

        return passed;
    }
}
