import java.lang.reflect.Field;
import java.util.ArrayList;
import java.lang.Math;

public class MessierObject implements Comparable<MessierObject> {

    private String messierNumber;
    private String ngcicNumber;
    private String commonName;
    private String type;
    private double distance;
    private String constellation;
    private double apparentMagnitude;
    private double rightAscension; // Stored as radians
    private double declination; // Stored as radians

    /**
     * Constructor with table entry string.
     * 
     * @param entry table entry string
     */
    public MessierObject(String entry) {

        String[] values = parseEntry(entry);

        /**
         * Find a better way of assigning values if possible
         * 
         * Use try blocks for parsing.
         */
        this.messierNumber = values[0];
        this.ngcicNumber = values[1];
        this.commonName = values[2];
        this.type = values[3];

        // If distance is a range, take shortest
        // In future, may have to save as a range and
        // have a method to return shortest, furthest ect
        if (values[4].indexOf("-") != -1) {
            this.distance = Double.parseDouble(values[4].split("-")[0]);
        } else {
            this.distance = Double.parseDouble(values[4]);
        }

        this.constellation = values[5];
        this.apparentMagnitude = Double.parseDouble(values[6]);
        this.rightAscension = rightAscensionToRadians(values[7]);
        this.declination = declinationToRadians(values[8]);
    }

    /**
     * Constructor with all values fields.
     * 
     * @param messierNumber
     * @param ngcicNumber
     * @param commonName
     * @param type
     * @param distance
     * @param constellation
     * @param apparentMagnitude Stored as radians
     * @param rightAscension Stored as radians
     * @param declination
     */
    public MessierObject(String messierNumber, String ngcicNumber, String commonName, String type, double distance,
            String constellation, double apparentMagnitude, double rightAscension, double declination) {

        this.messierNumber = messierNumber;
        this.ngcicNumber = ngcicNumber;
        this.commonName = commonName;
        this.type = type;
        this.distance = distance;
        this.constellation = constellation;
        this.apparentMagnitude = apparentMagnitude;
        this.rightAscension = rightAscension;
        this.declination = declination;

    }

    /**
     * Internal method to parse through the given table entry, picking out fields
     * 
     * @param entry The table entry line
     * @return The fields in string array
     */
    private static String[] parseEntry(String entry) {

        String[] values = entry.split(",");

        for (int i = 0; i < values.length; i++) {
            values[i] = values[i].trim();
        }

        return values;

        // entry = entry.replace('"', '\u0000');
        // entry = entry.replace('\s', '\u0000');
    }

    /**
     * Converts a string with a series of measurements into an ArrayList of doubles.
     * 
     * @param measurement The measurement as "(value)(unit) (value)(unit)..."
     * @return An ArrayList of doubles
     */
    private static ArrayList<Double> measurementToDoubles(String measurement) {

        // Convert the string into an arraylist of doubles to be worked with
        String[] strings = measurement.split(" ");
        ArrayList<Double> values = new ArrayList<Double>();

        for (String value : strings) {
            value = value.substring(0, value.length() - 1);
            values.add(Double.parseDouble(value));
        }

        return values;
    }

    /**
     * Converts a string of right ascension into radians.
     * 
     * @param rightAscension The right ascension as "(hours)h (minutes)m (seconds)s"
     * @return The right ascension in radians
     */
    private static double rightAscensionToRadians(String rightAscension) {

        ArrayList<Double> values = measurementToDoubles(rightAscension);

        return Math.toRadians(values.get(0) + (values.get(1) / 60) + (values.get(2) / 3600));
    }

    /**
     * Converts a string of declination into radians.
     * 
     * @param declination The given declination as "(degrees)° (arcminutes)'
     *                    (arcseconds)""
     * @return The declination in radians
     */
    private static double declinationToRadians(String declination) {

        ArrayList<Double> values = measurementToDoubles(declination);

        return Math.toRadians(values.get(0) + (values.get(1) / 60) + (values.get(2) / 3600));
    }

    @Override
    /**
     * Compares apparent magnitude.
     * 
     * @param object MessierObject to be compared
     * @return Comparison integer
     */
    public int compareTo(MessierObject object) {

        return Double.compare(this.getApparentMagnitude(), object.getApparentMagnitude());
    }

    /**
     * @return A string containing all fields in the original format
     */
    public String toString() {

        Field[] fields = this.getClass().getDeclaredFields();
        String properties = "";

        for (Field field : fields) {
            try {
                if (properties != "") {
                    properties += ", " + field.get(this);

                } else {
                    properties += field.get(this);
                }

            } catch (IllegalAccessException exception) {
                exception.printStackTrace();
            }
        }

        return properties;
    }

    /* --------------------------- Getters and Setters -------------------------- */

    public String getMessierNumber() {
        return this.messierNumber;
    }

    public String getNgcicNumber() {
        return this.ngcicNumber;
    }

    public String getCommonName() {
        return this.commonName;
    }

    public String getType() {
        return this.type;
    }

    public double getDistance() {
        return this.distance;
    }

    public String getConstellation() {
        return this.constellation;
    }

    public double getApparentMagnitude() {
        return this.apparentMagnitude;
    }

    public double getRightAscension() {
        return this.rightAscension;
    }

    public double getDeclination() {
        return this.declination;
    }
}
