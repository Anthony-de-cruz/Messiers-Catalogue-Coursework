package src.MessierProgram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.text.DecimalFormat;
import java.lang.Math;
import java.util.regex.Pattern;

public class MessierObject implements Comparable<MessierObject> {

    private String messierNumber;
    private String ngcicNumber;
    private List<String> commonNames;
    private String type;
    private Double[] distanceRange;
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

        // Input validation done by setters.
        try {
            String[] values = parseEntry(entry);

            setMessierNumber(values[0]);
            setNgcicNumber(values[1]);
            setCommonNames(values[2]);
            setType(values[3]);
            setDistanceRange(values[4]);
            setConstellation(values[5]);
            setApparentMagnitude(Double.parseDouble(values[6]));
            setRightAscensionTime(values[7]);
            setDeclinationAngle(values[8]);

        } catch (InvalidEntryException exception) {
            throw exception;
        }
    }

    /**
     * Constructor with all value fields.
     * 
     * @param messierNumber
     * @param ngcicNumber
     * @param commonNames
     * @param type
     * @param distance
     * @param constellation
     * @param apparentMagnitude
     * @param rightAscension    In radians
     * @param declination       In radians
     * @throws InvalidEntryException Thrown if any values fail validation
     */
    public MessierObject(String messierNumber, String ngcicNumber, List<String> commonNames, String type,
            Double[] distanceRange,
            String constellation, double apparentMagnitude, double rightAscension, double declination)
            throws InvalidEntryException {

        try {
            setMessierNumber(messierNumber);
            setNgcicNumber(ngcicNumber);
            setCommonNames(commonNames);
            setType(type);
            setDistanceRange(distanceRange);
            setConstellation(constellation);
            setApparentMagnitude(apparentMagnitude);
            setRightAscensionRadians(rightAscension);
            setDeclinationRadians(declination);

        } catch (InvalidEntryException exception) {
            throw exception;
        }
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
     * Creates and returns a string of all of the objects fields in the database
     * format.
     * 
     * @return The object as a string
     */
    public String toString() {

        String[] properties = { getMessierNumber(), getNgcicNumber(), commonNamesToString(), getType(),
                distanceRangeToString(), getConstellation(), Double.toString(getApparentMagnitude()),
                getRightAscensionTime(), getDeclinationAngle() };

        String string = "";

        for (int i = 0; i < properties.length; i++) {

            if (i != 0) {
                string += ", " + properties[i];

            } else {
                string += properties[i];
            }
        }

        return string;
    }

    /**
     * Calculate the angular distance between this object and the passed one.
     * 
     * @param object The messier object you want to get the distance to
     * @return The angular distance in radians
     */
    public double calcAngularDistance(MessierObject object) {

        // Formula: cos(θ) = sin(δ1) * sin(δ2) + cos(δ1) * cos(δ2) * cos(α1 - α2)
        // δ == Declination
        // α == Right ascension
        return Math.acos(((Math.sin(getDeclinationRadians()) * Math.sin(object.getDeclinationRadians()))
                + (Math.cos(getDeclinationRadians()) * Math.cos(object.getDeclinationRadians())
                        * (Math.cos(getRightAscensionRadians() - object.getRightAscensionRadians())))));
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

    /* -------------------------------------------------------------------------- */
    /* --------------------------- Getters and Setters -------------------------- */
    /* -------------------------------------------------------------------------- */
    /* ------------------ Setters include the input validation. ----------------- */

    public String getMessierNumber() {
        return this.messierNumber;
    }

    /**
     * Set Messier Number. Must conform to "M1234".
     * 
     * @apiNote Checked against regex:^M[0-9]+$
     * 
     * @param messierNumber The Messier Number string
     * @throws InvalidEntryException thrown if it doesn't conform
     */
    public void setMessierNumber(String messierNumber) throws InvalidEntryException {
        Pattern pattern = Pattern.compile("^M[0-9]+$");

        if (pattern.matcher(messierNumber).find()) {
            this.messierNumber = messierNumber;

        } else {
            throw new InvalidEntryException(
                    "Invalid Messier Number. Must conform to " + pattern.toString() + ", got: " + messierNumber);
        }
    }

    public String getNgcicNumber() {
        return this.ngcicNumber;
    }

    /**
     * Set NGC/IC Number. Must conform to "{NGC | IC} 1234".
     * 
     * @apiNote Checked against regex:^\"(((NGC )|(IC ))[0-9]+)|-\"$
     * 
     * @param ngcicNumber The NGC/IC Number string
     * @throws InvalidEntryException thrown if it doesn't conform
     */
    public void setNgcicNumber(String ngcicNumber) throws InvalidEntryException {
        Pattern pattern = Pattern.compile("^\"(((NGC )|(IC ))[0-9]+)|-\"$");

        if (pattern.matcher(ngcicNumber).find()) {
            this.ngcicNumber = ngcicNumber;

        } else {
            throw new InvalidEntryException(
                    "Invalid NGC/IC Number. Must conform to " + pattern.toString() + ", got: " + ngcicNumber);
        }
    }

    public List<String> getCommonNames() {
        return this.commonNames;
    }

    /**
     * Create a string containing all common names in the dataset format.
     * 
     * @return The string of common names
     */
    public String commonNamesToString() {

        String string = "\"";

        for (int i = 0; i < this.commonNames.size(); i++) {

            if (i == 0) {
                string += this.commonNames.get(i);

            } else if (i == this.commonNames.size() - 1 && this.commonNames.size() != 2) {
                string += ", or " + this.commonNames.get(i);

            } else if (i == this.commonNames.size() - 1 && this.commonNames.size() == 2) {
                string += " or " + this.commonNames.get(i);

            } else {
                string += ", " + this.commonNames.get(i);
            }
        }
        string += "\"";

        return string;
    }

    public void setCommonNames(List<String> commonNames) {
        // Just a list of strings, no real validation required.
        this.commonNames = commonNames;
    }

    /**
     * Set Common Names by turning the field into a list. Must conform to "{name1}"
     * |
     * "{name1}, {name2}, or {name3}" | "{name1} or {name2}".
     * 
     * @apiNote Checked against regex:^\".+$\"
     * @apiNote Split via regex:( or )|(, or )|,
     * 
     * @param field
     * @throws InvalidEntryException
     */
    public void setCommonNames(String field) throws InvalidEntryException {
        Pattern pattern = Pattern.compile("^\".+\"$");

        if (pattern.matcher(field).find()) {
            field = field.replace("\"", "\u0000");
            String[] values = field.split("( or )|(, or )|,");

            for (int i = 0; i < values.length; i++) {
                values[i] = values[i].trim();
            }

            this.commonNames = Arrays.asList(values);

        } else {
            throw new InvalidEntryException(
                    "Invalid common names. Must conform to " + pattern.toString() + ", got: " + field);
        }
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        // Just a string, no real validation required.
        this.type = type;
    }

    public Double[] getDistanceRange() {
        return this.distanceRange;
    }

    public double getLowestDistance() {
        return this.distanceRange[0];
    }

    public double getMeanDistance() {
        return (this.distanceRange[0] + this.distanceRange[1]) / 2;
    }

    public double getHighestDistance() {
        return this.distanceRange[1];
    }

    /**
     * Returns the distance range as a string in the form "{shortest}-{longest}".
     * 
     * @return The string
     */
    public String distanceRangeToString() {
        if (this.distanceRange[0].equals(this.distanceRange[1])) {
            return this.distanceRange[0].toString();

        } else {
            return this.distanceRange[0] + "-" + this.distanceRange[1];
        }
    }

    public void setDistanceRange(double lowestDistance, double highestDistance) {
        this.distanceRange = new Double[] { lowestDistance, highestDistance };
    }

    /**
     * Set distance range in the form [shortest, longest].
     * 
     * @param distanceRange The array containing the distances
     * @throws InvalidEntryException Thrown if passed array is not 2 elements in
     *                               length
     */
    public void setDistanceRange(Double[] distanceRange) throws InvalidEntryException {
        if (distanceRange.length != 2) {
            throw new InvalidEntryException(
                    "Expected distanceRange[] array to be 2 elements, got: " + distanceRange.length + ".");
        } else {
            this.distanceRange = distanceRange;
        }
    }

    /**
     * Set distance range by turning the field into an array of doubles. Must
     * conform to "1.1" | "1.2-2.3".
     * 
     * @apiNote Checked against regex:^([0-9]+.[0-9]+)-([0-9]+.[0-9]+)$
     * @apiNote or checked against regex:^[0-9]+.[0-9]+$
     * 
     * @param field
     * @throws InvalidEntryException
     */
    public void setDistanceRange(String field) throws InvalidEntryException {
        Pattern patternRange = Pattern.compile("^([0-9]+.[0-9]+)-([0-9]+.[0-9]+)$");
        Pattern patternSingle = Pattern.compile("^[0-9]+.[0-9]+$");

        if (patternRange.matcher(field).find()) {

            String[] distances = field.split("-");

            this.distanceRange = new Double[2];
            this.distanceRange[0] = Double.parseDouble(distances[0]);
            this.distanceRange[1] = Double.parseDouble(distances[1]);

        } else if (patternSingle.matcher(field).find()) {

            this.distanceRange = new Double[] { Double.parseDouble(field), Double.parseDouble(field) };

        } else {
            throw new InvalidEntryException("Invalid distance range. Must conform to " + patternRange.toString() + " | "
                    + patternSingle.toString() + ", got: " + field);
        }
    }

    public String getConstellation() {
        return this.constellation;
    }

    public void setConstellation(String constellation) {
        // Just a string, no real validation required.
        this.constellation = constellation;
    }

    public double getApparentMagnitude() {
        return this.apparentMagnitude;
    }

    /**
     * Set the apparent magnitude.
     * 
     * @param apparentMagnitude The apparent magnitude
     * @throws InvalidEntryException Thrown if the passed value is less than or
     *                               equal to zero
     */
    public void setApparentMagnitude(double apparentMagnitude) throws InvalidEntryException {

        if (apparentMagnitude <= 0) {
            throw new InvalidEntryException(
                    "Apparent magnitudes cannot be less than or equal to zero, got: " + apparentMagnitude + ".");
        }
        this.apparentMagnitude = apparentMagnitude;
    }

    public double getRightAscensionRadians() {
        return this.rightAscension;
    }

    public String getRightAscensionTime() {
        return rightAscensionToTime(this.rightAscension);
    }

    public void setRightAscensionRadians(double raAsRadian) {
        // Just a double, no real validation required.
        this.rightAscension = raAsRadian;
    }

    public void setRightAscensionTime(String field) throws InvalidEntryException {
        Pattern pattern = Pattern.compile("^[0-9]+h [0-9]+m [0-9]+.[0-9]{4}s$");

        if (pattern.matcher(field).find()) {
            this.rightAscension = rightAscensionToRadians(field);
        } else {
            throw new InvalidEntryException(
                    "Invalid Right Ascension. Must conform to " + pattern.toString() + ", got: " + field);
        }

    }

    public double getDeclinationRadians() {
        return this.declination;
    }

    public String getDeclinationAngle() {
        return declinationToAngle(this.declination);
    }

    public void setDeclinationRadians(double decAsRadian) {
        // Just a double, no real validation required.
        this.declination = decAsRadian;
    }

    public void setDeclinationAngle(String field) throws InvalidEntryException {
        Pattern pattern = Pattern.compile("^[-0-9]+° [0-9]+\' [0-9]+.[0-9]{4}\"$");

        if (pattern.matcher(field).find()) {
            this.declination = declinationToRadians(field);

        } else {
            throw new InvalidEntryException(
                    "Invalid Declination. Must conform to " + pattern.toString() + ", got: " + field);
        }

    }
}
