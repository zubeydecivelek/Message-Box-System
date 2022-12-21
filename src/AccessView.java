import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccessView  extends JFrame implements ActionListener{
    JLabel codeNameLabel, messagePassLabel, usernameLabel, userPassLabel, showPassLabel;
    JTextField codeNameField, usernameField;
    JPasswordField messagePassField, userPassField;
    JCheckBox checkBox;
    JButton viewBtn, homeBtn, resetBtn;
    JSeparator jSeparator;

    /**Creates Frame for displaying a message*/
    public AccessView(){
        setTitle("Message View");
        setSize(350,500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        codeNameLabel = new JLabel("Message Codename");
        messagePassLabel = new JLabel("Message Password");
        usernameLabel = new JLabel("Username");
        userPassLabel = new JLabel("User Password");
        showPassLabel = new JLabel("Show Password");

        codeNameField = new JTextField();
        messagePassField = new JPasswordField();
        usernameField = new JTextField();
        userPassField = new JPasswordField();

        checkBox = new JCheckBox();
        jSeparator = new JSeparator(SwingConstants.HORIZONTAL);

        viewBtn = new JButton("View");
        homeBtn = new JButton("Home");
        resetBtn = new JButton("Reset");

        codeNameLabel.setBounds(30,50,130,20);
        messagePassLabel.setBounds(30,100,120,20);
        jSeparator.setBounds(20,160,300,10);
        usernameLabel.setBounds(30,200,100,20);
        userPassLabel.setBounds(30,250,100,20);
        showPassLabel.setBounds(200,300,100,20);
        checkBox.setBounds(170,300,25,20);

        codeNameField.setBounds(175,50,120,20);
        codeNameField.setBorder(new LineBorder(Color.black,1));
        messagePassField.setBounds(175,100,120,20);
        messagePassField.setBorder(new LineBorder(Color.black,1));
        usernameField.setBounds(175,200,120,20);
        usernameField.setBorder(new LineBorder(Color.black,1));
        userPassField.setBounds(175,250,120,20);
        userPassField.setBorder(new LineBorder(Color.black,1));

        viewBtn.setBounds(50,350,100,40);
        homeBtn.setBounds(125,400,100,40);
        resetBtn.setBounds(200,350,100,40);

        add(codeNameLabel);
        add(messagePassLabel);
        add(jSeparator);
        add(usernameLabel);
        add(userPassLabel);
        add(showPassLabel);
        add(codeNameField);
        add(messagePassField);
        add(usernameField);
        add(userPassField);
        add(checkBox);
        add(homeBtn);
        add(viewBtn);
        add(resetBtn);

        checkBox.addActionListener(this);
        viewBtn.addActionListener(this);
        resetBtn.addActionListener(this);
        homeBtn.addActionListener(this);

        Main.centreWindow(this);
        setVisible(true);
        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == checkBox){
            if (userPassField.getEchoChar() != '\u0000' ) {
                userPassField.setEchoChar('\u0000');
            } else {
                userPassField.setEchoChar((Character) UIManager.get("PasswordField.echoChar"));
            }
        }
        if(e.getSource() == resetBtn){
            reset();
        }
        else if(e.getSource() == viewBtn){
            control();
        } else if (e.getSource() ==homeBtn) {
            setVisible(false);
            dispose();
            MainPage mainPage = new MainPage();
        }
    }

    /**This function resets textFields.*/
    private void reset(){
        codeNameField.setText("");
        messagePassField.setText("");
        usernameField.setText("");
        userPassField.setText("");
    }

    /**This function checks the information required to display the message.
     * If the information is correct, it redirects to the message display page.*/
    private void control(){
        Message message = controlMessage();
        if(message == null || !controlUser()){
            JOptionPane.showMessageDialog(this,"Message not found in system.");
            return;
        }
        MessagePage messagePage = new MessagePage(message.getContent(),this);
        setVisible(false);

    }

    private Message controlMessage(){
        String codename = codeNameField.getText(),
                messagePass = String.valueOf(messagePassField.getPassword());
        for (Message message:Main.messages){
            if(codename.equals(message.getMessageID()) && Main.hashPassword(messagePass).equals(message.getPassword())){
                return message;
            }
        }
        return null;
    }

    private boolean controlUser(){
        String username = usernameField.getText(),
                userPass = String.valueOf(userPassField.getPassword());
        for(User user:Main.users){
            if(username.equals(user.getUsername()) && Main.hashPassword(userPass).equals(user.getPassword())){
                return true;
            }
        }
        return false;
    }

}
