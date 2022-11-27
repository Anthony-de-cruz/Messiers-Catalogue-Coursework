import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class MessierCatalogue {

    private List<MessierObject> messierObjects;

    /**
     * Constructor for an empty set of Messier Objects.
     */
    public MessierCatalogue() {

        this.messierObjects = new ArrayList<MessierObject>();
    }

    /**
     * Constructor for an array of MessierObjects.
     * 
     * @param messierObjects
     */
    public MessierCatalogue(MessierObject[] messierObjects) {

        this.messierObjects = new ArrayList<>(Arrays.asList(messierObjects));
    }

    /* ---------------------------- Mutating the set ---------------------------- */

    /**
     * Add an object to the set.
     * 
     * @param messierObject
     */
    public void add(MessierObject messierObject) {
        this.messierObjects.add(messierObject);
    }

    /**
     * Remove the first Messier Object from the set by Messier number.
     * 
     * @param messierNumber The Messier number of the object you want to remove
     * @throws NoSuchElementException Thrown is the object is not found
     */
    public void removeByNumber(String messierNumber) throws NoSuchElementException {

        for (MessierObject object : this.messierObjects) {

            if (object.getMessierNumber().equals(messierNumber)) {
                this.messierObjects.remove(object);
            }
        }

        throw new NoSuchElementException();
    }

    /**
     * Remove the first Messier Object from the set by object reference.
     * 
     * @param messierObject The reference to the Messier Object that you want to
     *                      remove
     * @throws NoSuchElementException Thrown is the object is not found
     */
    public void removeByReference(MessierObject messierObject) throws NoSuchElementException {

        for (MessierObject object : this.messierObjects) {

            if (object == messierObject) {
                this.messierObjects.remove(object);
            }
        }

        throw new NoSuchElementException();
    }

    /**
     * 
     */
    public void sort() {

    }

    /* ------------------------------ Getting data ------------------------------ */

    @Override
    /**
     * 
     * @return
     */
    public String toString() {

        String catalogue = "";

        for (MessierObject object : this.messierObjects) {
            catalogue += "\n" + object.toString();
        }

        return catalogue;
    }

    /**
     * Get the size of the set of Messier Objects.
     * 
     * @return The size of the set
     */
    public int size() {
        return this.messierObjects.size();
    }
}
