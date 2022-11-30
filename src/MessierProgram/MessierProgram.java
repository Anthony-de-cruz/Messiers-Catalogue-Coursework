package src.MessierProgram;

import java.io.IOException;
import java.util.ArrayList;

import src.tests.TestHarness;

/**
 * @author Anthony de Cruz
 */
public class MessierProgram {

    public static void main(String[] args) {

        
        runTests();

        String path = "messier.txt";

        runQueries(path);
    }

    /**
     * Setup the catalogue.
     * 
     * @return The Messier Object Catalogue
     */
    public static MessierCatalogue fetchData(String path) throws InvalidEntryException, IOException {

        MessierCatalogue catalogue = new MessierCatalogue();
        
        try {
            IOHandler ioHandler = new IOHandler(path);

            ArrayList<String> entries = ioHandler.getEntries();

            for (String entry : entries) {
                catalogue.add(new MessierObject(entry));
            }

        } catch (InvalidEntryException | IOException exception) {
            throw exception;
        }

        return catalogue;
    }

    public static void runQueries(String path) {

        MessierCatalogue catalogue;

        try {
            catalogue = fetchData(path);

            queryA(catalogue);
            queryB(catalogue);
            queryC(catalogue);
            queryD(catalogue);
            queryE(catalogue);

        } catch (InvalidEntryException | IOException exception) {
            exception.printStackTrace();

            if (exception instanceof InvalidEntryException) {
                System.out.println("\nFatal error: " + path + " contains invalid data entry.");
    
            } else {
                System.out.println("\nFatal error: Failed to read: " + path);
            }

            System.exit(0);
        }

    }

    public static void runTests() {

        if (TestHarness.runTests()) {
            System.out.println("<-- All tests passed -->");

        } else {
            System.out.println("<-- Some tests failed -->");
            System.exit(0);
        }
    }

    /* --------------------------------- Queries -------------------------------- */

    /**
     * Display the catalogue, arranged in order of ascending apparent magnitude
     * (bright-est object first).
     */
    public static void queryA(MessierCatalogue catalogue) {

        System.out.println("\n--------------------------------- Query A --------------------------------\n");
        catalogue.sort();
        System.out.println(catalogue.toString());
    }

    /**
     * Display the average apparent magnitude of all open clusters.
     */
    public static void queryB(MessierCatalogue catalogue) {

        System.out.println("\n--------------------------------- Query B --------------------------------\n");

    }

    /**
     * Display the details of the most distant globular cluster.
     */
    public static void queryC(MessierCatalogue catalogue) {

        System.out.println("\n--------------------------------- Query C --------------------------------\n");

    }

    /**
     * Display the details of the object in the constellaon Sagittarius with the
     * lowest declination.
     */
    public static void queryD(MessierCatalogue catalogue) {

        System.out.println("\n--------------------------------- Query D --------------------------------\n");

    }

    /**
     * Display the details of the object that is closest in the sky to M45 (in the
     * sense of having the smallest angular distance). Hint: test your code using
     * M42 (the Great Orion Nebula) as M42 and M43 (De Mairan’s Nebula) are both are
     * part of the Orion molecular cloud complex, and so they should have a small
     * angular separaon (approximately 8 arc minutes)
     */
    public static void queryE(MessierCatalogue catalogue) {

        System.out.println("\n--------------------------------- Query E --------------------------------\n");

    }
}
