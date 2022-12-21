import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    private String username;
    private String password;

    @Override
    public String toString() {
        return username + ',' + password + ";";
    }

    public User(String username, String password, boolean hashed) {
        this.username = username;
        if(hashed)
            this.password = password;
        else
            this.password = Main.hashPassword(password);
    }

    public void writeToFile(byte[] userPassword) throws IOException {
        byte[] userArray = (getUsername() + " ").getBytes(StandardCharsets.UTF_8);
        byte[] finalArray = new byte[userArray.length + userPassword.length];

        int i,k;
        for(i = 0, k = 0; i < finalArray.length; i ++){
            if (i >= userArray.length){
                finalArray[i] = userPassword[k];
                k++;
            } else {
                finalArray[i] = userArray[i];
            }
        }
        String fileName = "UserFile";

        boolean fileExist = Files.exists(Paths.get(fileName));

        FileOutputStream fileOutputStream = new FileOutputStream(fileName, fileExist);
        fileOutputStream.write(finalArray);
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
