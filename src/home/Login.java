package home;

import GeneralFeatures.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener{
    private JButton mainMenu;
    private JButton back;
    private JTextField tfname;
    public static Utilities utilities;
    private boolean errorInsertion;
    Login(){
        utilities=new Utilities();
        errorInsertion=false;
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        ImageIcon image1 = new ImageIcon("src/icons/login.jpg");
        JLabel image = utilities.styleAndConfigureImage(0,0,600,500,image1);
        add(image);

        JLabel heading = new JLabel("Study Tool");
        heading = utilities.styleAndConfigureLabel(800,80,300,45,heading,"title");
        add(heading);

        JLabel name = new JLabel("Enter your name");
        name = utilities.styleAndConfigureLabel(735,170,300,20,name,"bold");
        add(name);

        tfname = new JTextField();
        tfname = utilities.styleAndConfigureTextField(735,200,300,25,tfname);
        add(tfname);

        mainMenu = new JButton("Next");
        mainMenu = utilities.styleAndConfigureButton(735,290,120,25,mainMenu,this);
        add(mainMenu);

        back = new JButton("Exit");
        back = utilities.styleAndConfigureButton(915,290,120,25,back,this);
        add(back);

        setSize(1200,500);
        setLocation(200,150);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==mainMenu){
            checkAllFieldsCorrect();
            if(!errorInsertion) {
                String name = tfname.getText();
                setVisible(false);
                new MainMenu(name);
            }else{
                paint(getGraphics(),"ERROR! Name cannot be empty");
                errorInsertion=false;
            }
        }else if(ae.getSource()==back){
            setVisible(false);
        }
    }

    public void paint(Graphics g, String error){
        super.paint(g);
        g.setColor(Color.red);
        g.setFont(new Font("Tahoma",Font.BOLD,15));
        if(errorInsertion){
            g.drawString(error,700,390);
        }
    }

    private void checkAllFieldsCorrect() {
        if(tfname.getText() == null||tfname.getText().length()<=0){
            errorInsertion=true;
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
