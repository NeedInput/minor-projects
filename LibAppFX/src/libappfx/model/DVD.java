package libappfx.model;

import java.util.StringTokenizer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * DVD.java
 *
 * A simple class to hold data about a DVD item.
 *
 * @author Orien Goulding
 * @version 1.0
 *
 * Date Written: 22nd August 2014
 *
 * Subject: Object Oriented Application Development 
 * Assignment: 1 
 * Tutor: Mary Martin
 *
 */
public final class DVD {

    private StringProperty mTitle;// Title of the DVD
    private StringProperty mGenre;// Genre of the DVD
    private StringProperty mDirector;// Director of the feature
    private StringProperty mImageURL;// URL of the image for the DVD
    private StringProperty mDuration;// Run time of the feature
    private StringProperty mDatePurchased;// Date the DVD was purchased

    /**
     * no argument constructor
     *
     * Sets all data members to null
     */
    public DVD() {
        this(null, null, null, null, null, null);
    }

    /**
     * six argument (all details) constructor
     *
     * This initializes all data members and allows appropriate data members to
     * be set.
     *
     * @param title a String for the title of this DVD
     * @param genre a String for the genre of this DVD
     * @param director a String for the director of this DVD
     * @param imageURL a String for the image URL of this DVD
     * @param duration a String for the duration of this DVD
     * @param datePurchased a String for the date this DVD was purchased
     */
    public DVD(String title, String genre, String director, String imageURL, String duration, String datePurchased) {
        mTitle = new SimpleStringProperty(title);
        mGenre = new SimpleStringProperty(genre);
        mDirector = new SimpleStringProperty(director);
        mImageURL = new SimpleStringProperty(imageURL);
        mDuration = new SimpleStringProperty(duration);
        mDatePurchased = new SimpleStringProperty(datePurchased);
    }

    /**
     * Returns a string representation of this item. This includes the names and
     * values of data members.
     *
     * @return a String representation of this DVD object including the data
     * member names/descriptions and values
     */
    @Override
    public String toString() {
        return "Title{" + ", title=" + getTitle() + ", genre=" + getGenre() + ", director=" + getDirector()
                + ", imageURL=" + getImageURL() + "duration=" + getDuration() + ", datePurchased=" + getDatePurchased() + "}";
    }

    /**
     * Returns a String representation of this item in a csv format.
     *
     * @return a string containing DVD details separated by commas
     */
    public String toCSV() {
        return getTitle() + "," + getGenre() + "," + getDirector() + "," + getImageURL() + "," + getDuration() + "," + getDatePurchased();
    }

    /**
     * Creates a DVD object from a string
     *
     * @param line a string containing DVD details from external store
     * @return a DVD object
     */
    public static DVD createDVD(String line) {
        StringTokenizer theTokens = new StringTokenizer(line, ",");
        DVD newDVD = new DVD();
        if (theTokens.countTokens() == 6) {
            newDVD.setTitle(theTokens.nextToken().trim());
            newDVD.setGenre(theTokens.nextToken().trim());
            newDVD.setDirector(theTokens.nextToken().trim());
            newDVD.setImageURL(theTokens.nextToken().trim());
            newDVD.setDuration(theTokens.nextToken().trim());
            newDVD.setDatePurchased(theTokens.nextToken().trim());
            return newDVD;
        } else {
            return null;
        }
    }

    /**
     * Retrieves the duration of the DVD
     *
     * @return the string mDuration
     */
    public String getDuration() {
        return mDuration.get();
    }

    /**
     * Sets the duration of the DVD
     *
     * @param duration the mDuration to set
     */
    public void setDuration(String duration) {
        this.mDuration.set(duration);
    }

    /**
     * Retrieves the duration of the DVD
     *
     * @return the StringProperty mDuration
     */
    public StringProperty durationProperty() {
        return mDuration;
    }

    /**
     * Retrieves the genre of the DVD
     *
     * @return the string mGenre
     */
    public String getGenre() {
        return mGenre.get();
    }

    /**
     * Sets the duration of the DVD
     *
     * @param genre the mGenre to set
     */
    public void setGenre(String genre) {
        this.mGenre.set(genre);
    }

    /**
     * Retrieves the duration of the DVD
     *
     * @return the StringProperty mGenre
     */
    public StringProperty genreProperty() {
        return mGenre;
    }

    /**
     * Retrieves the image URL of the DVD
     *
     * @return the string mImageURL
     */
    public String getImageURL() {
        return mImageURL.get();
    }

    /**
     * Sets the image URL of the DVD
     *
     * @param imageURL the mImageURL to set
     */
    public void setImageURL(String imageURL) {
        this.mImageURL.set(imageURL);
    }

    /**
     * Retrieves the image URL of the DVD
     *
     * @return the StringProperty mImageURL
     */
    public StringProperty imageURLProperty() {
        return mImageURL;
    }

    /**
     * Retrieves the director of the DVD
     *
     * @return the string mDirector
     */
    public String getDirector() {
        return mDirector.get();
    }

    /**
     * Sets the director of the DVD
     *
     * @param director the mDirector to set
     */
    public void setDirector(String director) {
        this.mDirector.set(director);
    }

    /**
     * Retrieves the director of the DVD
     *
     * @return the StringProperty mDirector
     */
    public StringProperty directorProperty() {
        return mDirector;
    }

    /**
     * Retrieves the title of the DVD
     *
     * @return the string mTitle
     */
    public String getTitle() {
        return mTitle.get();
    }

    /**
     * Sets the title of the DVD
     *
     * @param title the mTitle to set
     */
    public void setTitle(String title) {
        this.mTitle.set(title);
    }

    /**
     * Retrieves the title of the DVD
     *
     * @return the StringProperty mTitle
     */
    public StringProperty titleProperty() {
        return mTitle;
    }

    /**
     * Retrieves the Date the DVD was purchased
     *
     * @return the string mDatePurchased
     */
    public String getDatePurchased() {
        return mDatePurchased.get();
    }

    /**
     * Sets the date the DVD was purchased
     *
     * @param datePurchased the mDatePurchased to set
     */
    public void setDatePurchased(String datePurchased) {
        this.mDatePurchased.set(datePurchased);
    }

    /**
     * Retrieves the Date the DVD was purchased
     *
     * @return the StringProperty mDatePurchased
     */
    public StringProperty datePurchasedProperty() {
        return mDatePurchased;
    }

    /**
     * Compare the specified object and this item for equality.
     *
     * To be equal obj2 must: (a) not be null and (b) must be a DVD object or a
     * subclass of DVD and (c) all data members must be equal.
     *
     * Note: the equals method should always return a value.
     *
     * @param obj2 the object to be compared for equality with this item
     * @return true if the objects are equal, otherwise false
     */
    @Override
    public boolean equals(Object obj2) {
        DVD dvd2;
        boolean equal;

        equal = false;
        if (obj2 != null && obj2.getClass() == this.getClass()) {
            dvd2 = (DVD) obj2;
            if (dvd2.mTitle.equals(mTitle)
                    && dvd2.mGenre.equals(mGenre)
                    && dvd2.mDirector.equals(mDirector)
                    && dvd2.mImageURL.equals(mImageURL)
                    && dvd2.mDuration == mDuration
                    && dvd2.mDatePurchased.equals(mDatePurchased)) {
                equal = true;
            }
        }

        return equal;
    }

}
