import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessagePage  extends JFrame implements ActionListener {
    JFrame parent;
    JTextArea textArea;
    JScrollPane scrollPane;

    JButton returnBtn;

    /**Creates Frame for displaying message */
public MessagePage(String message,JFrame parent){
    this.parent = parent;
    setTitle("Message");
    setSize(400,400);
    setLayout(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    textArea = new JTextArea(message);
    textArea.setEditable(false);
    scrollPane = new JScrollPane(textArea);
    returnBtn = new JButton("Return");

    scrollPane.setBounds(40,40,300,200);
    returnBtn.setBounds(150,275,100,50);

    returnBtn.addActionListener(this);

    add(scrollPane);
    add(returnBtn);

    Main.centreWindow(this);
    setVisible(true);
    setResizable(false);
}


    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();
        parent.setVisible(true);
    }
}
