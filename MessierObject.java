import java.util.Arrays;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class MessierObject {

    private String messierNumber;
    private String ngcicNumber;
    private String commonName;
    private String type;
    private ArrayList<Double> distanceRange;
    private String constellation;
    private double apparentMagnitude;
    private double rightAscension;
    private double declination;


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

        this.distanceRange = new ArrayList<Double>();

        try {
            this.distanceRange.add(Double.parseDouble(values[4].split("-")[0]));
            this.distanceRange.add(Double.parseDouble(values[4].split("-")[1]));

        } catch (java.lang.NumberFormatException numberFormatException) {
            System.out.println(numberFormatException);
        }

        this.constellation = values[5];
        this.apparentMagnitude = Double.parseDouble(values[6]);

    }

    private String[] parseEntry(String entry) {

        //todo Make sanitised not remove spaces between words in names
        String sanitised = entry.replace('"', '\u0000');//.replace('\s', '\u0000');
        String[] values = sanitised.split(",");

        return values;
    }
    
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
}
