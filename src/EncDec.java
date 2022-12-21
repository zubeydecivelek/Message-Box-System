import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


public class EncDec {
    String userFile;
    String messageFile;
    SecretKey key;
    Cipher cipher;

    /** This class encrypts the data, saves it in the data file, and reads the encrypted data file and decrypts it.*/
    public EncDec(String userFile, String messageFile){
        this.userFile = userFile;
        this.messageFile = messageFile;
        try{
            DESKeySpec keySpecG = new DESKeySpec("26578952".getBytes(StandardCharsets.UTF_8));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            key = keyFactory.generateSecret(keySpecG);
            cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

    }

    /** This function encrypts the given byte array.*/
    public byte[] encryptArray(byte[] array){
        byte[] encrypted = new byte[0];
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encrypted = cipher.doFinal(array);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e){
            e.printStackTrace();
        }
        return encrypted;
    }

    /** This function decrypts the given byte array.*/
    public byte[] decryptArray(byte[] array){
        byte[] decrypted = new byte[0];
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            decrypted = cipher.doFinal(array);

        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e){
            e.printStackTrace();
        }
        return decrypted;
    }

    /**This function encrypts the user data in the program and saves it to the data file.*/
    public void writeUserFile() throws IOException {
        String userString = "";
        for (User user : Main.users){
            userString+=user;
        }
        byte[] userArray = userString.getBytes(StandardCharsets.UTF_8);
        byte[] userEncryption = encryptArray(userArray);

        Path path = Paths.get(userFile);
        Files.write(path, userEncryption);
    }

    /**This function encrypts the message data in the program and saves it to the data file.*/
    public void writeMessageFile() throws IOException {
        String messageString = "";
        for (Message message : Main.messages){
            messageString+=message;
        }
        byte[] messageArray = messageString.getBytes(StandardCharsets.UTF_8);
        byte[] messageEncryption = encryptArray(messageArray);

        Path path = Paths.get(messageFile);
        Files.write(path, messageEncryption);

    }

    /**This function reads the recorded data files in the program, decrypts them and saves them in the program.*/
    public void readFiles(){
        boolean userExist = Files.exists(Paths.get(userFile));
        boolean messageExist = Files.exists(Paths.get(messageFile));
        try{
            if(userExist){
                Path path = Paths.get(userFile);
                byte[] userBytes = Files.readAllBytes(path);
                byte[] userDecrypt = decryptArray(userBytes);
                String fileStr = new String(userDecrypt);
                createUserList(fileStr);
            }
            if(messageExist){
                Path path = Paths.get(messageFile);
                byte[] messageBytes = Files.readAllBytes(path);
                byte[] messageDecrypt = decryptArray(messageBytes);

                String fileStr = new String(messageDecrypt);
                createMessageList(fileStr);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }


    /**This function separates the decrypted message data's and saves them in the program*/
    private void createMessageList(String string){
        String[] messageString = string.split(";");
        for(String line:messageString) {
            String[] splitLine = line.split(",");
            if(splitLine.length == 4){
                Main.messages.add(new Message(splitLine[0], splitLine[1], splitLine[2], splitLine[3], true));
            }
        }
    }

    /**This function separates the decrypted user data's and saves them in the program*/
    private void createUserList(String string){
        String[] userString = string.split(";");
        for(String line:userString){
            String[] splitLine = line.split(",");
            if(splitLine.length == 2){
                Main.users.add(new User(splitLine[0],splitLine[1],true));
            }
        }
    }

}
