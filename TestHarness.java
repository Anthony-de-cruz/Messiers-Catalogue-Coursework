public class TestHarness {

    /* ---------------------------------- Tests --------------------------------- */
    // Should use a test framework but thats out of the scope of the project.
    // This testing is NOT 100% thorough.

    public static boolean test1() {

        String demo1 = "M1, \"NGC 1952\", \"Crab Nebula\", Supernova remnant, 4.9-8.1, Taurus, 8.4, 5h 34m 31.9400s, 22° 0' 52.2000\"";
        String demo2 = "M2, \"NGC 7089\", \"-\", Globular cluster, 33.0, Aquarius, 6.5, 21h 33m 27.0200s, 0° 49' 23.7000\"";
        String demo3 = "M17, \"NGC 6618\", \"Omega, Swan, Horseshoe, Lobster, or Checkmark Nebula\", H II region nebula with cluster, 5.0-6.0, Sagittarius, 6.0, 18h 20m 26.0000s, -16° 10' 36.0000\"";
        String demo4 = "M94, \"NGC 4736\", \"Crocodile Eye or Cat's Eye Galaxy\", Spiral galaxy, 14700.0-17300.0, Canes Venatici, 8.2, 12h 50m 53.1000s, 41° 7' 14.0000\"";

        try {
            System.out.println("\n--- Obj1 ---");

            MessierObject obj1 = new MessierObject(demo3);
            System.out.println(demo3);
            System.out.println(obj1.toString());
            System.out.println(obj1.getNgcicNumber());
            System.out.println(obj1.getDeclinationString());

            System.out.println("\n--- Obj2 ---");

            MessierObject obj2 = new MessierObject(demo4);
            System.out.println(demo4);
            System.out.println(obj2.toString());
            System.out.println(obj2.getMeanDistance());
            System.out.println(obj2.getRightAscensionString());

            System.out.println("\n--- Comparison ---");

            System.out.println(obj1.compareTo(obj2));

            System.out.println("\n--- Catalogue ---");

            MessierCatalogue catalogue = new MessierCatalogue(new MessierObject[] { obj1, obj2 });

            System.out.println(catalogue.toString());

        } catch (InvalidEntryException exception) {
            exception.printStackTrace();

            return false;
        }

        return true;
    }

    /**
     * Testing MessierObject
     * 
     * @return Test result
     */
    public static boolean test2() {

        String[] validData = {
                "M1, \"NGC 1952\", \"Crab Nebula\", Supernova remnant, 4.9-8.1, Taurus, 8.4, 5h 34m 31.9400s, 22° 0' 52.2000\"",
                "M2, \"NGC 7089\", \"-\", Globular cluster, 33.0, Aquarius, 6.5, 21h 33m 27.0200s, 0° 49' 23.7000\"",
                "M17, \"NGC 6618\", \"Omega, Swan, Horseshoe, Lobster, or Checkmark Nebula\", H II region nebula with cluster, 5.0-6.0, Sagittarius, 6.0, 18h 20m 26.0000s, -16° 10' 36.0000\"",
                "M94, \"NGC 4736\", \"Crocodile Eye or Cat's Eye Galaxy\", Spiral galaxy, 14700.0-17300.0, Canes Venatici, 8.2, 12h 50m 53.1000s, 41° 7' 14.0000\"",
        };

        String[] invalidData = {
                "M1, \"NGC 1952\", \"Crab Nebula\", Supernova remnant, 4.9-8.1, Taurus, 8.4, 5h 34m 31.9400s, 22° 0' 52.2000\", hello",
                "M1, \"NGC 1952\", \"Crab \"Nebula\", Supernova r,emnant, 4.9-8.1, 8.4, 5h 34m 31.9400s, 22° 0' 52.2000\"",
                "M1, \"NGC 1952\", \"Crab Nebula\", Supernova remnant, 4.9-8.1, Taurus, 8.4, 5h 34m 31.9400s, 22° 0' 52.2000\"",
        };

        // Valid test data
        for (int i = 0; i < validData.length; i++) {

            try {
                MessierObject obj = new MessierObject(validData[i]);

                if (!obj.toString().equals(validData[i])) {
                    System.err.println(validData[i]);
                    System.err.println(obj.toString());
                    System.err.println("Test 2, valid case " + i + ": FAILED");
                    return false;
                }

            } catch (InvalidEntryException exception) {
                exception.printStackTrace();
                System.err.println(validData[i]);
                System.err.println("Test 2, valid case " + i + ": FAILED");
                return false;
            }

            System.out.println("Test 2, valid case " + i + ": PASSED");
        }

        // Erroneous test data
        for (int i = 0; i < invalidData.length; i++) {

            try {
                MessierObject obj = new MessierObject(invalidData[1]);

                System.err.println(validData[i]);
                System.err.println(obj.toString());
                System.err.println("Test 2, valid case " + i + ": FAILED");
                return false;

            } catch (InvalidEntryException exception) {

                System.out.println("Test 2, invalid case " + i + ": PASSED");
            }
        }

        System.out.println("\n -- Test 2: PASSED -- \n");
        return true;
    }

    /**
     * Testing MessierCatalogue
     * 
     * @return Test result
     */
    public static boolean test3() {

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

        System.out.println("\n -- Test 3: PASSED -- \n");
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
        passed = passed && test2();
        passed = passed && test3();

        return passed;
    }
}
