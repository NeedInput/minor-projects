package libappfx.model;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * IOManager.java
 *
 * A class providing methods for reading and writing files
 *
 * @author Orien Goulding
 * @version 1.0
 *
 * Date Written: 22nd August 2014
 *
 * Subject: Object Oriented Application Development Assignment: 1 Tutor: Mary
 * Martin
 *
 */
public class IOManager {

    /**
     * Read a text file and converts it to ArrayList of DVD objects
     *
     * @param file text file containing DVD object strings
     * @return ArrayList of DVD objects
     */
    public static ArrayList<DVD> readTextDatabase(File file) {
        //-------------------------------------
        ArrayList<DVD> p = new ArrayList<>();
        FileReader fr;
        BufferedReader br;
        String line;
        DVD newDVD;

        System.out.println("File name and path: " + file.getName() + " " + file);

        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            System.err.println("No DVD Database ");
            return p;
        }
        try {
            br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                newDVD = DVD.createDVD(line);
                if (newDVD != null) {
                    p.add(newDVD);
                }
            }
        } catch (EOFException eof) {
            System.out.println("Finished reading file\n");
        } catch (IOException e) {
            System.err.println("Error during read\n" + e.toString());
        }
        try {
            fr.close();
        } catch (IOException e) {
            System.err.println("File not closed properly\n" + e.toString());
            System.exit(1);
        }
        return p;
    }

    /**
     * Records an ArrayList of DVD objects as a text file
     *
     * @param file the file to be written too.
     * @param aL an ArrayList of DVD objects to be recorded.
     */
    public static void writeTextDatabase(File file, ArrayList<DVD> aL) {
        //-----------------------------------

        try {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                for (DVD dvd : aL) {
                    bw.write(dvd.toCSV() + "\n");
                }
                System.out.println("finished writing...");
                bw.flush();
            }
        } catch (IOException e) {
            System.err.println("File not closed properly\n" + e.toString());
            System.exit(1);
        }
    }

    /**
     * Read a xml file and converts it to ArrayList of DVD objects.
     *
     * @param fileName name of xml file containing DVD object strings
     * @return an ArrayList of DVD objects
     */
    public static ArrayList<DVD> readXMLDatabase(String fileName) {
        ArrayList<DVD> dvdAL = new ArrayList();
        XMLDecoder d;

        try {
            d = new XMLDecoder(
                    new BufferedInputStream(
                            new FileInputStream(fileName)));
        } catch (FileNotFoundException e) {
            System.err.println("No DVD Database ");
            return dvdAL;
        }

        try {
            while (true) {
                dvdAL.add((DVD) d.readObject());
            }
        } catch (ArrayIndexOutOfBoundsException exception) {
        }
        d.close();
        return dvdAL;

    }

    /**
     * Records an ArrayList of DVD objects as a xml file
     *
     * @param fileName name of xml file to be written too.
     * @param dvdAL an ArrayList of DVD objects to be recorded.
     */
    public static void writeXMLDatabase(String fileName, ArrayList<DVD> dvdAL) {

        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fileName)))) {
            dvdAL.stream().forEach((dvd) -> {
                encoder.writeObject(dvd);
            });
        } catch (FileNotFoundException e) {
            System.err.println("File not closed properly\n" + e.toString());
            System.exit(1);
        }
    }

    /**
     * Read a text file and converts it to ArrayList of genre strings
     *
     * @param fileName text file containing genre strings
     * @return ArrayList of genre strings
     */
    public static ArrayList<String> readGenreList(String fileName) {

        ArrayList<String> genreList = new ArrayList();

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));//decorator design pattern
            String genreName;

            while ((genreName = br.readLine()) != null) {
                System.out.println("genre is..." + genreName);
                genreList.add(genreName);
            }
        } catch (FileNotFoundException fnfe) {
            System.err.println("genre File not Found!" + fnfe.toString());
        } catch (IOException ex) {
            Logger.getLogger(IOManager.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Error during open\n" + ex.toString());
        }

        return genreList;
    }
}
