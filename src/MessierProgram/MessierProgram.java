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
    public static MessierCatalogue fetchData(String path) {

        MessierCatalogue catalogue = new MessierCatalogue();

        try {
            IOHandler ioHandler = new IOHandler(path);

            ArrayList<String> entries = ioHandler.getEntries();

            for (String entry : entries) {
                catalogue.add(new MessierObject(entry));
            }

        } catch (InvalidEntryException | IOException exception) {
            exception.printStackTrace();

            if (exception instanceof InvalidEntryException) {
                System.out.println("\nFatal error: " + path + " contains invalid data entry.");

            } else {
                System.out.println("\nFatal error: Failed to read: " + path);
            }

            System.exit(0);
        }

        return catalogue;
    }

    public static void runQueries(String path) {

        MessierCatalogue catalogue = fetchData(path);

        queryA(catalogue);
        queryB(catalogue);
        queryC(catalogue);
        queryD(catalogue);
        queryE(catalogue);
    }

    public static void runTests() {

        if (TestHarness.runTests()) {
            System.out.println("\n<-- All tests passed :) -->");

        } else {
            System.out.println("\n<-- Some tests failed :( -->");
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

        MessierCatalogue openClusters = catalogue.getByType("Open cluster");

        double avgApparentMagnitude = 0.0;

        for (MessierObject object : openClusters.getList()) {
            avgApparentMagnitude += object.getApparentMagnitude();
        }

        System.out.println(
                "Average apparent magnitude of open clusters: " + (avgApparentMagnitude /= openClusters.size()));
    }

    /**
     * Display the details of the most distant globular cluster.
     */
    public static void queryC(MessierCatalogue catalogue) {

        System.out.println("\n--------------------------------- Query C --------------------------------\n");

        MessierCatalogue globularClusters = catalogue.getByType("Globular cluster");

        MessierObject mostDistantObject = globularClusters.get(0);

        for (MessierObject globularCluster : globularClusters.getList()) {
            if (globularCluster.getLowestDistance() > mostDistantObject.getLowestDistance()) {
                mostDistantObject = globularCluster;
            }
        }

        System.out.println("Most distant globular cluster:\n" + mostDistantObject.toString());
    }

    /**
     * Display the details of the object in the constellation Sagittarius with the
     * lowest declination.
     */
    public static void queryD(MessierCatalogue catalogue) {

        System.out.println("\n--------------------------------- Query D --------------------------------\n");

        MessierCatalogue sagittarius = catalogue.getByConstellation("Sagittarius");

        MessierObject lowestObject = sagittarius.get(0);

        for (int i = 1; i < sagittarius.size(); i++) {

            if (sagittarius.get(i).getDeclinationRadians() < lowestObject.getDeclinationRadians()) {
                lowestObject = sagittarius.get(i);
            }
        }

        System.out.println("Object with the lowest declination within Sagittarius:\n" + lowestObject.toString());
    }

    /**
     * Display the details of the object that is closest in the sky to M45 (in the
     * sense of having the smallest angular distance). Hint: test your code using
     * M42 (the Great Orion Nebula) as M42 and M43 (De Mairan’s Nebula) are both are
     * part of the Orion molecular cloud complex, and so they should have a small
     * angular separation (approximately 8 arc minutes)
     */
    public static void queryE(MessierCatalogue catalogue) {

        System.out.println("\n--------------------------------- Query E --------------------------------\n");

        MessierObject M45 = catalogue.getByMessierNumber("M45");

        MessierObject closestObject = catalogue.getClosest(M45);
        System.out.println(M45);
        System.out.println(closestObject);
        System.out.println("\n" + closestObject.getMessierNumber() + " is "
                + Math.toDegrees(M45.calcAngularDistance(closestObject)) + "° away from M45");
    }
}
