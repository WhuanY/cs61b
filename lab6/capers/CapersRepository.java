package capers;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import static capers.Utils.*;

/** A repository for Capers 
 * @author Walter Wu
 * The structure of a Capers Repository is as follows:
 *
 * .capers/ -- top level folder for all persistent data in your lab12 folder
 *    - dogs/ -- folder containing all of the persistent data for dogs
 *    - story -- file containing the current story
 *
 */
public class CapersRepository {
    /** Current Working Directory. */
    static final File CWD = new File(System.getProperty("user.dir"));

    /** Main metadata folder. */
    static final File CAPERS_FOLDER = Utils.join(CWD,".capers");

    /**
     * Does required filesystem operations to allow for persistence.
     * (creates any necessary folders or files)
     * Remember: recommended structure (you do not have to follow):
     *
     * .capers/ -- top level folder for all persistent data in your lab12 folder
     *    - dogs/ -- folder containing all of the persistent data for dogs
     *    - story -- file containing the current story
     */
    public static void setupPersistence() {
        if (!CAPERS_FOLDER.exists()) {
            CAPERS_FOLDER.mkdir();
        }
        File dogsDir = Utils.join(CAPERS_FOLDER,"dogs");
        if (!dogsDir.exists()) {
            dogsDir.mkdir();
        }

        File storyFile = Utils.join(CAPERS_FOLDER, "story");
        try {
            if (!storyFile.exists()) {
                storyFile.createNewFile();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Appends the first non-command argument in args
     * to a file called `story` in the .capers directory.
     * @param text String of the text to be appended to the story
     */
    public static void writeStory(String text) {
        File storyFile = Utils.join(CAPERS_FOLDER, "story");
        String StoryContent;
        if (readContentsAsString(storyFile).compareTo("") == 0){
            StoryContent = text;
        }
        else {
            StoryContent = readContentsAsString(storyFile) + "\n" + text;
        }
        Utils.writeContents(storyFile, StoryContent);
        System.out.println(readContentsAsString(storyFile));
    }

    /**
     * Creates and persistently saves a dog using the first
     * three non-command arguments of args (name, breed, age).
     * Also prints out the dog's information using toString().
     */
    public static void makeDog(String name, String breed, int age) {
        Dog newDog = new Dog(name, breed, age);
        System.out.println(newDog.toString());
        File outFile = Utils.join(CAPERS_FOLDER,"dogs",name);
        writeObject(outFile, newDog);
    }

    /**
     * Advances a dog's age persistently and prints out a celebratory message.
     * Also prints out the dog's information using toString().
     * Chooses dog to advance based on the first non-command argument of args.
     * @param name String name of the Dog whose birthday we're celebrating.
     */
    public static void celebrateBirthday(String name) {
        File inFile = Utils.join(CAPERS_FOLDER,"dogs",name);
        if (!inFile.exists()) {
            System.out.println("The dog you are celebrating doesn't exist! Try a new Dog");
        }
        Dog birthdayDog  = readObject(inFile, Dog.class);
        birthdayDog.haveBirthday();
        writeObject(inFile, birthdayDog);
    }
}
