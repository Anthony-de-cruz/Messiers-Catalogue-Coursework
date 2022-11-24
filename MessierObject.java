import java.util.ArrayList;
import java.text.DecimalFormat;
import java.lang.Math;

public class MessierObject implements Comparable<MessierObject> {

    private String messierNumber;
    private String ngcicNumber;
    private String commonName;
    private String type;
    private ArrayList<Double> distanceRange;
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

        try {
            String[] values = parseEntry(entry);

            this.messierNumber = values[0];
            this.ngcicNumber = values[1];
            this.commonName = values[2];
            this.type = values[3];

            String[] distances = values[4].split("-");
            this.distanceRange = new ArrayList<>();
            for (String distance : distances) {
                this.distanceRange.add(Double.parseDouble(distance));
            }

            this.constellation = values[5];
            this.apparentMagnitude = Double.parseDouble(values[6]);
            this.rightAscension = rightAscensionToRadians(values[7]);
            this.declination = declinationToRadians(values[8]);

        } catch (InvalidEntryException exception) {
            System.out.println(exception.toString());
        }

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
     * @param rightAscension    Stored as radians
     * @param declination
     */
    public MessierObject(String messierNumber, String ngcicNumber, String commonName, String type,
            ArrayList<Double> distanceRange,
            String constellation, double apparentMagnitude, double rightAscension, double declination) {

        this.messierNumber = messierNumber;
        this.ngcicNumber = ngcicNumber;
        this.commonName = commonName;
        this.type = type;
        this.distanceRange = distanceRange;
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
    private static String[] parseEntry(String entry) throws InvalidEntryException {

        if (entry.indexOf(",") == -1) {
            throw new InvalidEntryException("Invalid string format.");
        }

        String[] values = entry.split(",");

        for (int i = 0; i < values.length; i++) {
            values[i] = values[i].trim();
        }

        if (values.length != 9) {
            throw new InvalidEntryException("Incorrect number of fields.");
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
    private static double rightAscensionToRadians(String rightAscensionStr) {

        ArrayList<Double> values = measurementToDoubles(rightAscensionStr);

        return Math.toRadians((values.get(0) + (values.get(1) / 60) + (values.get(2) / 3600)) * 15);
    }

    /**
     * Converts right ascension to a string.
     * 
     * @param rightAscensionRad The right ascension in radians
     * @return The right ascension as "(hours)h (minutes)m (seconds)s"
     */
    private static String rightAscensionToTime(double rightAscensionRad) {

        double time = Math.toDegrees(rightAscensionRad) / 15;

        double hours = Math.floor(time);
        double minutes = Math.floor((time - hours) * 60);
        double seconds = (((time - hours) * 60) - minutes) * 60.0;

        DecimalFormat decimalFormat = new DecimalFormat("#.0000");

        return ((int) hours + "h " + (int) minutes + "m " + decimalFormat.format(seconds) + "s");
    }

    /**
     * Converts a string of declination into radians.
     * 
     * @param declinationStr The declination as "(degrees)° (arcMinutes)'
     *                       (arcSeconds)""
     * @return The declination in radians
     */
    private static double declinationToRadians(String declinationStr) {

        ArrayList<Double> values = measurementToDoubles(declinationStr);

        return Math.toRadians(values.get(0) + (values.get(1) / 60) + (values.get(2) / 3600));
    }

    /**
     * Converts declination in radians to a string.
     * 
     * @param declinationRad The declination in radians
     * @return The declination as "(degrees)° (arcMinutes)' (arcSeconds)""
     */
    private static String declinationToAngle(double declinationRad) {

        double angle = Math.toDegrees(declinationRad);

        double degrees = Math.floor(angle);
        double arcMinutes = Math.floor((angle - degrees) * 60);
        double arcSeconds = (((angle - degrees) * 60) - arcMinutes) * 60.0;

        DecimalFormat decimalFormat = new DecimalFormat("#.0000");

        return ((int) degrees + "° " + (int) arcMinutes + "' " + decimalFormat.format(arcSeconds) + "\"");
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

    @Override
    /**
     * @return
     */
    public String toString() {

        // I think that there is enough changes to the final result to justify
        // hardcoding this instead of fetching a list of all fields, iterating over them
        // and appending them to a string. Perhaps a sign of poor implementation,
        // however.

        String properties = "";
        properties += this.messierNumber;
        properties += ", " + this.ngcicNumber;
        properties += ", " + this.commonName;
        properties += ", " + this.type;

        if (this.getDistanceRange().size() == 2) {
            properties += ", " + Double.toString(this.getLowestDistance()) + "-"
                    + Double.toString(this.getHighestDistance());
        } else {
            properties += ", " + Double.toString(this.getLowestDistance());
        }

        properties += ", " + this.constellation;
        properties += ", " + this.apparentMagnitude;
        properties += ", " + rightAscensionToTime(this.rightAscension);
        properties += ", " + declinationToAngle(this.declination);

        return properties;
    }

    /* --------------------------- Getters and Setters -------------------------- */

    public String getMessierNumber() {
        return this.messierNumber;
    }

    public String getNgcicNumber() {
        return this.ngcicNumber.replace('"', '\u0000');
    }

    public String getCommonName() {
        return this.commonName.replace('"', '\u0000');
    }

    public String getType() {
        return this.type;
    }

    public ArrayList<Double> getDistanceRange() {
        return this.distanceRange;
    }

    public double getLowestDistance() {
        return this.distanceRange.get(0);
    }

    public double getMeanDistance() {

        if (this.distanceRange.size() == 1) {
            return this.distanceRange.get(0);
        } else {
            return (this.distanceRange.get(0) + this.distanceRange.get(1)) / 2;
        }
    }

    public double getHighestDistance() {

        if (this.distanceRange.size() == 2) {
            return this.distanceRange.get(1);
        } else {
            return this.distanceRange.get(0);
        }

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
