import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Message {
    private String messageID;
    private String content;
    private String receiverID;
    private String password;

    public String getPassword() {
        return password;
    }

    public Message(String messageID, String content, String receiver, String password, boolean hashed) {
        this.messageID = messageID;
        this.content = content;
        this.receiverID = receiver;
        if(hashed)
            this.password = password;
        else
            this.password = Main.hashPassword(password);
    }

    @Override
    public String toString() {
        return messageID + "," + content + "," + receiverID + "," + password + ";";
    }

    public String getMessageID() {
        return messageID;
    }

    public String getContent() {
        return content;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void writeToFile(byte[] messagePassword) throws IOException {
        String value = getMessageID() + " " + getContent() + " " + getReceiverID() + " ";
        byte[] valueArray = value.getBytes(StandardCharsets.UTF_8);
        byte[] finalArray = new byte[valueArray.length + messagePassword.length];
        int i,k;
        for (i = 0, k = 0; i < finalArray.length; i ++){
            if (i >= valueArray.length){
                finalArray[i] = messagePassword[k];
                k++;
            } else {
                finalArray[i] = valueArray[i];
            }
        }
        String fileName = "MessageFile";

        boolean fileExist = Files.exists(Paths.get(fileName));

        FileOutputStream fileOutputStream = new FileOutputStream(fileName, fileExist);
        fileOutputStream.write(finalArray);
    }


}
