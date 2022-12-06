package src.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import src.MessierProgram.IOHandler;
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
     * @param validStringData   A valid set of messier object entries
     * @param validMappedData   A valid set of messier fields
     * @param invalidStringData An invalid set of messier object entries
     * @param validMappedData   An invalid set of messier fields
     * @return Test result
     */
    public static boolean testMessierObject(String[] validStringData, HashMap<String, String> validMappedData,
            String[] invalidStringData, HashMap<String, String> invalidMappedData) {

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
                System.err.println("Invalid case " + i + ": FAILED");
                return false;

            } catch (InvalidEntryException exception) {

                System.err.println("Invalid case " + i + ": PASSED");
            }
        }

        System.err.println(" -- testMessierObject: PASSED -- \n");
        return true;
    }

    /**
     * Testing MessierCatalogue.
     * 
     * @param validStringData   A valid set of messier object entries
     * @param invalidStringData An invalid set of messier object entries
     * @return Test result
     */
    public static boolean testMessierCatalogue(String[] validStringData, String[] invalidStringData) {

        MessierObject[] validObjects = new MessierObject[validStringData.length];

        for (int i = 0; i < validStringData.length; i++) {
            try {
                validObjects[i] = new MessierObject(validStringData[i]);

            } catch (InvalidEntryException exception) {
                exception.printStackTrace();
                System.err.println("FAILED TO CREATE OBJECT");
                System.err.println("Valid case " + i + ": FAILED");
                return false;
            }
        }

        for (int i = 0; i < 1; i++) {

            try {
                MessierCatalogue catalogue = new MessierCatalogue(validObjects);
                catalogue.sort();

                System.err.println("Valid case " + i + ": PASSED");

            } catch (Exception exception) {
                exception.printStackTrace();
                System.err.println("Valid case " + i + ": FAILED");
                return false;
            }
        }

        System.out.println(" -- testMessierCatalogue: PASSED -- \n");
        return true;
    }

    /**
     * Testing IOHandler.
     * 
     * @param validPath   A valid file path
     * @param invalidPath An invalid file path
     * @return Test result
     */
    public static boolean testIOHandler(String validPath, String invalidPath) {

        // Test valid path
        try {
            IOHandler ioHandler = new IOHandler(validPath);

            ArrayList<String> entries = ioHandler.getEntries();

            MessierCatalogue catalogue = new MessierCatalogue();

            for (String entry : entries) {
                catalogue.add(new MessierObject(entry));
            }

            System.err.println("Valid case: PASSED");

        } catch (InvalidEntryException | IOException exception) {
            exception.printStackTrace();

            if (exception instanceof InvalidEntryException) {
                System.err.println("Valid case " + validPath + ": FAILED: Contains invalid data entry.");
                return false;

            } else {
                System.err.println("Valid case " + validPath + ": FAILED: Failed to read.");
                return false;
            }
        }

        // Test invalid path
        try {
            IOHandler ioHandler = new IOHandler(invalidPath);

            ioHandler.getEntries();

            System.err.println("Invalid case " + invalidPath + ": FAILED: Read and fetch was successful.");
            return false;

        } catch (InvalidEntryException | IOException exception) {
            System.err.println("Invalid case: PASSED");
        }

        System.err.println(" -- testIOHandler: PASSED -- \n");
        return true;
    }

    /**
     * Run tests and return if all successful or if some failed.
     * 
     * @return Whether or not they passed or failed
     */
    public static boolean runTests() {

        /* --------------------------- Generate Test Data --------------------------- */

        String[] validStringData = {
                "M1, \"NGC 1952\", \"Crab Nebula\", Supernova remnant, 4.9-8.1, Taurus, 8.4, 5h 34m 31.9400s, 22° 0' 52.2000\"",
                "M2, \"NGC 7089\", \"-\", Globular cluster, 33.0, Aquarius, 6.5, 21h 33m 27.0200s, 0° 49' 23.7000\"",
                "M17, \"NGC 6618\", \"Omega, Swan, Horseshoe, Lobster, or Checkmark Nebula\", H II region nebula with cluster, 5.0-6.0, Sagittarius, 6.0, 18h 20m 26.0000s, -16° 10' 36.0000\"",
                "M94, \"NGC 4736\", \"Crocodile Eye or Cat's Eye Galaxy\", Spiral galaxy, 14700.0-17300.0, Canes Venatici, 8.2, 12h 50m 53.1000s, 41° 7' 14.0000\"",
        };

        HashMap<String, String> validMappedData = new HashMap<String, String>();
        validMappedData.put("field",
                "M2, \"NGC 7089\", \"-\", Globular cluster, 33.0, Aquarius, 6.5, 21h 33m 27.0200s, 0° 49' 23.7000\"");
        validMappedData.put("messierNumber", "M2");
        validMappedData.put("ngcicNumber", "\"NGC 7089\"");
        validMappedData.put("commonNames", "-");
        validMappedData.put("type", "Globular cluster");
        validMappedData.put("distanceRange", "33.0");
        validMappedData.put("constellation", "Aquarius");
        validMappedData.put("apparentMagnitude", "6.5");
        validMappedData.put("rightAscensionTime", "21h 33m 27.0200s");
        validMappedData.put("declination", "0° 49' 23.7000\"");

        String[] invalidStringData = {
                "M1, \"NGC 1952\", \"Crab Nebula\", Supernova remnant, 4.9-8.1, Taurus, 8.4, 5h 34m 31.9400s, 22° 0' 52.2000\", hello",
                "M1, \"NGC 1952\", \"Crab \"Nebula\", Supernova r,emnant, 4.9-8.1-5.2, 8.4, 5h 34m 31.9400s, 22° 0' 52.2000\"",
                "M1, \"NGC 1952\", \"Crab Nebula\", Supernova remnant, 4.9-8.1, Taurus, 8.4, 5h 34m 31.9400s, 22° 0' 52.2000\"",
                "M2a, \"NGC 7089\", \"-\", Globular cluster, 33.0, Aquarius, 6.5, 21h 33m 27.0200s, 0° 49' 23.7000\"",
                "M2a, \"NGC 7089\", \"-\", Globular cluster, 33.0, Aquarius, 6.5, -21h 33m 27.0200s, 0° 49' 23.7000\"",
                "M94, \"NGC 4736\", \"Crocodile Eye or Cat's Eye Galaxy or \", Spiral galaxy, 14700.0-17300.0, Canes Venatici, 8.2, 12h 50m 53.1000s, 41° 7' 14.0000\"",
        };

        HashMap<String, String> invalidMappedData = new HashMap<String, String>();
        invalidMappedData.put("field",
                "M2, \"NGC 7089\", \"-\", Globular cluster, 33.0, Aquarius, 6.5, 21h 33m 27.0200s, 0° 49' 23.7000\"");
        invalidMappedData.put("messierNumber", "Md2");
        invalidMappedData.put("ngcicNumber", "NGC 708a9");
        invalidMappedData.put("commonNames", "");
        invalidMappedData.put("type", "Globular cluster, 42");
        invalidMappedData.put("distanceRange", "33.0*5");
        invalidMappedData.put("constellation", "Aquarius42");
        invalidMappedData.put("apparentMagnitude", "hmm");
        invalidMappedData.put("rightAscensionTime", "seconds 33m 27.0200s");
        invalidMappedData.put("declination", "0° 49'apodimasdasdsd 23.7000\"");

        String validPath = "messier.txt";
        String invalidPath = "monke/banan/messier.txt";

        /* -------------------------------- Run Tests ------------------------------- */

        boolean passed = true;

        passed = passed && testMessierObject(validStringData, validMappedData, invalidStringData, invalidMappedData);
        passed = passed && testMessierCatalogue(validStringData, invalidStringData);
        passed = passed && testIOHandler(validPath, invalidPath);

        return passed;
    }
}
