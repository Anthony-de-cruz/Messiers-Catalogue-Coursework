package src.MessierProgram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.text.DecimalFormat;
import java.lang.Math;

public class MessierObject implements Comparable<MessierObject> {

    private String messierNumber;
    private String ngcicNumber;
    private List<String> commonNames;
    private String type;
    private List<Double> distanceRange;
    private String constellation;
    private double apparentMagnitude;
    private double rightAscension; // Stored as radians
    private double declination; // Stored as radians

    private static int fieldCount = 9;

    /**
     * Constructor with table entry string.
     * 
     * @param entry Table entry string
     */
    public MessierObject(String entry) throws InvalidEntryException {

        try {
            String[] values = parseEntry(entry);

            this.messierNumber = values[0];
            this.ngcicNumber = values[1];
            this.commonNames = Arrays.asList(parseField(values[2]));
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
            throw exception;
        }
    }

    /**
     * Constructor with all values fields.
     * 
     * @param messierNumber
     * @param ngcicNumber
     * @param commonNames
     * @param type
     * @param distance
     * @param constellation
     * @param apparentMagnitude Stored as radians
     * @param rightAscension    Stored as radians
     * @param declination
     */
    public MessierObject(String messierNumber, String ngcicNumber, List<String> commonNames, String type,
            ArrayList<Double> distanceRange,
            String constellation, double apparentMagnitude, double rightAscension, double declination) {

        this.messierNumber = messierNumber;
        this.ngcicNumber = ngcicNumber;
        this.commonNames = commonNames;
        this.type = type;
        this.distanceRange = distanceRange;
        this.constellation = constellation;
        this.apparentMagnitude = apparentMagnitude;
        this.rightAscension = rightAscension;
        this.declination = declination;
    }

    /**
     * Parse through the given table entry, picking out fields,
     * leaving any delimeters wrapped by double quotation marks.
     * Specifically for quotation aware CSV.
     * 
     * @param entry     The table entry line
     * @param delimeter The delimeter character
     * @return The fields in string array
     */
    private static String[] parseEntry(String entry) throws InvalidEntryException {

        // Parser to iterates over every character, appending the field when it
        // encounters a comma not wrapped by a double quote. Whilst this could be done
        // with regex I felt that this implementation, whilst probably slower, is more
        // readable (and actually works) than the cryptic haze that is my bad attempt at
        // regex.
        // ,(?=([^\"]*\"[^\"]*\")*[^\"]*$)
        // ,(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)
        // (["'])(?:(?=(\\?))\2.)*?\1

        int prevPosition = 0;
        List<String> valuesList = new ArrayList<String>();
        boolean inQuotes = false;

        for (int position = 0; position < entry.length(); position++) {

            if (entry.charAt(position) == '\"') {
                inQuotes = !inQuotes;

            } else if (entry.charAt(position) == ',' && !inQuotes) {
                valuesList.add(entry.substring(prevPosition, position));
                prevPosition = position + 2;
            }
        }

        valuesList.add(entry.substring(prevPosition));

        String[] valuesArray = valuesList.toArray(new String[valuesList.size()]);

        if (valuesArray.length != fieldCount) {
            throw new InvalidEntryException(
                    "Invalid number of fields. Expected " + fieldCount + ", got: " + valuesArray.length);
        }

        return valuesArray;
    }

    /**
     * Parse through the given field.
     * 
     * @param field Field string to be parsed.
     * @return String array containing the values.
     */
    private static String[] parseField(String field) {

        field = field.replace('"', '\u0000');

        String[] values = field.split("( or )|(, or )|,");

        for (int i = 0; i < values.length; i ++) {
            values[i] = values[i].trim();
        }

        return values;
    }

    /**
     * Converts a string with a series of measurements into an ArrayList of doubles.
     * 
     * @param measurement The measurement as "(value)(unit) (value)(unit)..."
     * @return An ArrayList of doubles
     */
    private static Double[] measurementToDoubles(String measurement) {

        measurement = measurement.replaceAll("[hms°\'\"]", "\u0000");
        String[] strings = measurement.split(" ");
        Double[] values = new Double[strings.length];

        for (int i = 0; i < strings.length; i++) {
            values[i] = Double.parseDouble(strings[i]);
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

        Double[] values = measurementToDoubles(rightAscensionStr);

        return Math.toRadians((values[0] + (values[1] / 60) + (values[2] / 3600)) * 15);
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

        Double[] values = measurementToDoubles(declinationStr);

        return Math.toRadians(values[0] + (values[1] / 60) + (values[2] / 3600));
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

    /**
     * Take a list of strings and turns it into a string.
     *
     * @param list The list to become a string
     * @return The string
     */
    private String stringListToString(List<String> list) {

        String string = "\"";

        for (int i = 0; i < list.size(); i++) {

            if (i == 0) {
                string += list.get(i);

            } else if (i == list.size() - 1 && list.size() != 2) {
                string += ", or " + list.get(i);

            } else if (i == list.size() - 1 && list.size() == 2) {
                string += " or " + list.get(i);
            
            } else {
                string += ", " + list.get(i);
            }
        }
        string += "\"";

        return string;
    }

    /**
     * Creates and returns a string of all of the objects fields in the database
     * format.
     * 
     * @return The object as a string.
     */
    public String toString() {

        // I think that there is enough changes to the final result to justify
        // hardcoding this instead of fetching a list of all fields, iterating over them
        // and appending them to a string. However, this is perhaps a sign of poor
        // implementation.

        String properties = "";
        properties += this.messierNumber;
        properties += ", " + this.ngcicNumber;
        properties += ", " + stringListToString(this.commonNames);
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

    /* --------------------------- Getters and Setters -------------------------- */

    public String getMessierNumber() {
        return this.messierNumber;
    }

    public String getNgcicNumber() {
        return this.ngcicNumber.replace('"', '\u0000');
    }

    public List<String> getCommonNames() {
        return this.commonNames;
    }

    public String commonNamesToString() {
        return stringListToString(this.commonNames);
    }

    public String getType() {
        return this.type;
    }

    public List<Double> getDistanceRange() {
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

    public double getRightAscensionRadians() {
        return this.rightAscension;
    }

    public String getRightAscensionString() {
        return rightAscensionToTime(this.rightAscension);
    }

    public double getDeclinationRadians() {
        return this.declination;
    }

    public String getDeclinationString() {
        return declinationToAngle(this.declination);
    }
}
