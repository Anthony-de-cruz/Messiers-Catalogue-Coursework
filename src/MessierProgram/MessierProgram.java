package src.MessierProgram;

import src.tests.TestHarness;

/**
 * @author Anthony de Cruz
 */
public class MessierProgram {

    public static void main(String[] args) {

        if (TestHarness.runTests()) {
            System.out.println("<-- All tests passed -->");

        } else {
            System.out.println("<-- Some tests failed -->");
        }
    }

    /**
     * Setup the catalogue.
     * 
     * @return The Messier Object Catalogue
     */
    public static MessierCatalogue setup() {

        // todo Read from file

        // todo Create catalogue

        MessierCatalogue catalogue = new MessierCatalogue();

        return catalogue;
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
}
