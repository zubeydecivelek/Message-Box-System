import javax.swing.*;
import java.io.IOException;

public class CreateUser  extends JFrame{
    JLabel usernameLabel, passwordLabel,showPasswordLabel;
    JButton createBtn, homeBtn;
    JCheckBox checkBox;
    JTextField userField;
    JPasswordField passwordField;

    /**Creates Frame for creating a user */
    public CreateUser(){
        setTitle("Create User");
        setSize(400,300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        usernameLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");
        showPasswordLabel = new JLabel("Show Password");

        checkBox = new JCheckBox();
        userField = new JTextField();
        passwordField = new JPasswordField();

        createBtn = new JButton("Create");
        homeBtn = new JButton("Home");

        usernameLabel.setBounds(50,40,70,20);
        passwordLabel.setBounds(50,100,100,20);

        userField.setBounds(200,40,100,20);
        passwordField.setBounds(200,100,100,20);

        checkBox.setBounds(200,150,22,20);
        showPasswordLabel.setBounds(230,150,100,20);
        createBtn.setBounds(75,200,100,20);
        homeBtn.setBounds(250,200,75,20);

        add(usernameLabel);
        add(passwordLabel);
        add(passwordField);
        add(showPasswordLabel);
        add(userField);
        add(checkBox);
        add(createBtn);
        add(homeBtn);

        checkBox.addActionListener(ae -> {
            if ( passwordField.getEchoChar() != '\u0000' ) {
                passwordField.setEchoChar('\u0000');
            } else {
                passwordField.setEchoChar((Character) UIManager.get("PasswordField.echoChar"));
            }});
        createBtn.addActionListener(action->{
            controlUserNameAndPassword();
        });
        homeBtn.addActionListener(act ->{
            setVisible(false);
            dispose();
            MainPage mainPage = new MainPage();
        });

        Main.centreWindow(this);
        setVisible(true);
        setResizable(false);
    }

    /**This function checks the information needed to create the user then creates the user.*/
    private void controlUserNameAndPassword(){
        String username = userField.getText();
        String password = String.valueOf(passwordField.getPassword());
        if (username.isEmpty()){
            JOptionPane.showMessageDialog(this,"Username can not be empty!");
            return;
        } else if (password.isEmpty()){
            JOptionPane.showMessageDialog(this,"Password can not be empty!");
            return;
        }
        //Checks if username is unique
        for(User user: Main.users){
            if(username.equals(user.getUsername())){
                JOptionPane.showMessageDialog(this,"This username is used!");
                return;
            }
        }
        Main.users.add(new User(username,password,false));
        userField.setText("");
        passwordField.setText("");
        try{
            Main.encDec.writeUserFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(this,"User created","Result",JOptionPane.INFORMATION_MESSAGE);

    }
}
