import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainPage extends JFrame implements ActionListener {
    JFrame mainFrame;
    JLabel label;
    JButton accessBtn, messageBtn, createUserBtn;

    /**It creates a frame, which is the beginning of the program, and provides the transition between the pages. */
    public MainPage(){
        setTitle("Main Page");
        setSize(600,500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        label = new JLabel("Welcome to MessageBox");
        label.setFont(new Font(label.getFont().getName(), Font.BOLD, 20));
        accessBtn = new JButton("Access");
        messageBtn = new JButton("Leave a message");
        createUserBtn = new JButton("Create User");

        label.setBounds(150,100,300,75);
        accessBtn.setBounds(225,200,100,50);
        messageBtn.setBounds(185,275,200,75);
        createUserBtn.setBounds(225,375,100,50);

        add(label);
        add(createUserBtn);
        add(accessBtn);
        add(messageBtn);

        accessBtn.addActionListener(this);
        messageBtn.addActionListener(this);
        createUserBtn.addActionListener(this);

        Main.centreWindow(this);
        setVisible(true);
        setResizable(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == messageBtn){
            MessageRegister messageRegister = new MessageRegister();
        }
        else if(e.getSource() == accessBtn){
            AccessView accessView = new AccessView();
        }
        else{
            CreateUser user = new CreateUser();
        }
        setVisible(false);
        dispose();
    }



}
