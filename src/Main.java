import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Main {

    /** Stores user data's **/
    public static ArrayList<User> users = new ArrayList<>();
    /** Stores message data's **/
    public static ArrayList<Message> messages = new ArrayList<>();

    public static EncDec encDec;


    public static void main(String[] args){
        // Data file names
        String userFile = "UserFile.data";
        String messageFile = "MessageFile.data";

        // Data encryption and decryption / file reading, writing operations
        encDec = new EncDec(userFile,messageFile);
        encDec.readFiles();

        //Creates a MainPage frame.
        MainPage mainPage = new MainPage();

    }

    /**This function takes a string then hashes it. So passwords is stored securely.**/
    public static String hashPassword(String password){
        byte[] encrypted = null;
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            encrypted = sha256.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return new String(encrypted);
    }

    /**This function centers frames.**/
    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }
}