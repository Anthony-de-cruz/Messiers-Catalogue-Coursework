package src.MessierProgram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * To do list
 * - Create test case
 */

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
     * Empty the list of MessierObjects.
     */
    public void removeAll() {
        this.messierObjects = new ArrayList<MessierObject>();
    }

    /**
     * Sort the Messier Object list based on it's comparator (apparent magnitude).
     */
    public void sort() {
        Collections.sort(this.messierObjects);
    }

    /* ---------------------------- Catalogue Queries --------------------------- */

    /**
     * Create a string containing the Messier Objects in the database format.
     * 
     * @return The string
     */
    public String toString() {

        String catalogue = "";

        for (int i = 0; i < size(); i++) {

            if (i != 0) {
                catalogue += "\n" + this.messierObjects.get(i).toString();

            } else {
                catalogue += this.messierObjects.get(i).toString();
            }
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

    /**
     * See if list is empty.
     * 
     * @return Whether or not it is empty
     */
    public boolean isEmpty() {
        return this.messierObjects.isEmpty();
    }

    public MessierObject getByMessierNumber(String messierNumber) {
        
        for (MessierObject object : this.messierObjects) {

            if (object.getMessierNumber().equals(messierNumber)) {
                return object;
            }
        }

        throw new NoSuchElementException("Messier number: " + messierNumber + " does not exist.");
    }

    /**
     * 
     * @param type
     * @return
     */
    public MessierCatalogue getByType(String type) {

        MessierCatalogue objects = new MessierCatalogue();

        for (MessierObject object : this.messierObjects) {

            if (object.getType().equals(type)) {
                objects.add(object);
            }
        }

        return objects;
    }

    /**
     * 
     * @param constellation
     * @return
     */
    public MessierCatalogue getByConstellation(String constellation) {

        MessierCatalogue objects = new MessierCatalogue();

        for (MessierObject object : this.messierObjects) {

            if (object.getConstellation().equals(constellation)) {
                objects.add(object);
            }
        }

        return objects;
    }

    /**
     * 
     * @param object
     * @return
     */
    public MessierObject getClosest(MessierObject object) {

        if (isEmpty()) {
            return null;
        }

        MessierObject closestObject = get(0);
        double shortestAngularDistance = Double.POSITIVE_INFINITY;

        for (MessierObject objects : this.messierObjects) {

            double angularDistance = object.calcAngularDistance(objects);

            if (angularDistance != 0 && angularDistance < shortestAngularDistance) {
                shortestAngularDistance = angularDistance;
                closestObject = objects;
            }
        }
        return closestObject;
    }

    /**
     * 
     * @param index
     * @return
     */
    public MessierObject get(int index) {
        return this.messierObjects.get(index);
    }

    /* --------------------------- Getters and Setters -------------------------- */

    public List<MessierObject> getList() {
        return this.messierObjects;
    }

    public void setList(List<MessierObject> list) {
        this.messierObjects = list;
    }
}
