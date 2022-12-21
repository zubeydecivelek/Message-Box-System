import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MessageRegister  extends JFrame implements ActionListener{
    JLabel userNameLabel,passwordLabel, confirmPasswordLabel, codeNameLabel, enterLabel;
    JButton createBtn, homeBtn;
    JTextField codeField;
    JPasswordField passField, confPassField;
    JTextArea messageArea;
    JComboBox<String> comboBox;

    /**Creates Frame for leaving a message*/
    public MessageRegister(){
        setTitle("Register Form");
        setSize(600,600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        userNameLabel = new JLabel("Auth.username*");
        passwordLabel = new JLabel("Password*");
        confirmPasswordLabel = new JLabel("Confirm Password*");
        codeNameLabel = new JLabel("Message codename*");
        enterLabel = new JLabel("ENTER YOUR MESSAGE*");

        createBtn = new JButton("Create Message");
        homeBtn =  new JButton("Home");

        passField = new JPasswordField();
        confPassField = new JPasswordField();
        codeField = new JTextField();

        messageArea = new JTextArea();

        createBtn.addActionListener(this);
        homeBtn.addActionListener(this);


        Main.centreWindow(this);
        setResizable(false);
        setUI();
    }

    public void setUI(){

        String[] users = new String[Main.users.size()];
        for(int i = 0;i<Main.users.size();i++){
            users[i] = Main.users.get(i).getUsername();
        }

        comboBox = new JComboBox<>(users);

        userNameLabel.setBounds(20, 50,150,20);
        passwordLabel.setBounds(20,100,100,20);
        confirmPasswordLabel.setBounds(300,100,175,20);
        codeNameLabel.setBounds(20,150,150,20);
        enterLabel.setBounds(20,250,150,20);

        createBtn.setBounds(125,450,150,50);
        homeBtn.setBounds(350,450,100,50);

        passField.setBounds(110,100,100,25);
        passField.setBorder(new LineBorder(Color.black,1));

        confPassField.setBounds(450,100,100,25);
        confPassField.setBorder(new LineBorder(Color.black,1));

        codeField.setBounds(170,150,100,25);
        codeField.setBorder(new LineBorder(Color.black,1));

        messageArea.setBounds(200,250,300,150);
        messageArea.setBorder(new LineBorder(Color.black,1));

        comboBox.setBounds(150,50,150,25);

        add(userNameLabel);
        add(passwordLabel);
        add(codeNameLabel);
        add(confirmPasswordLabel);
        add(enterLabel);
        add(createBtn);
        add(homeBtn);
        add(passField);
        add(confPassField);
        add(codeField);
        add(comboBox);
        add(messageArea);

        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == createBtn){
            create();
        } else if (e.getSource()==homeBtn) {
            MainPage mainPage = new MainPage();
            setVisible(false);
            dispose();
        }
    }

    /**This function checks the information needed to create the message.
     * If there is no missing information, it creates a message and saves the message.*/
    private void create(){
        String password = String.valueOf(passField.getPassword()),
                confirmPass = String.valueOf(confPassField.getPassword()),
                codeName = codeField.getText(),
                message  = messageArea.getText(),
                receiver = (String) comboBox.getSelectedItem();

        if (password.isEmpty() || confirmPass.isEmpty()){
            JOptionPane.showMessageDialog(this,"Passwords can not be empty!");
            return;
        } else if (codeName.isEmpty()) {
            JOptionPane.showMessageDialog(this,"Codename can not be empty!");
            return;
        } else if (message.isEmpty()) {
            JOptionPane.showMessageDialog(this,"Message can not be empty!");
            return;
        } else if (!password.equals(confirmPass)) {
            JOptionPane.showMessageDialog(this,"Passwords are not the same!");
            return;
        }
        for (Message mes: Main.messages){
            if (codeName.equals(mes.getMessageID())){
                JOptionPane.showMessageDialog(this,"Choose another message id!");
                return;
            }
        }

        Message message1 = new Message(codeName,message,receiver,password, false);
        Main.messages.add(message1);
        try{
            Main.encDec.writeMessageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(this,"Message added to the system","Result",JOptionPane.INFORMATION_MESSAGE);
        reset();

    }

    /**This function resets textFields.*/
    private void reset(){
        passField.setText("");
        confPassField.setText("");
        messageArea.setText("");
        codeField.setText("");
    }
}
