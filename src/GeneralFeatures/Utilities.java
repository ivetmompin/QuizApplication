package GeneralFeatures;

import home.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Utilities {
    public Utilities(){
    }
    public JButton styleAndConfigureButton(int x, int y, int width, int height,JButton button, ActionListener al){
        button.setBounds(x,y,width,height);
        button.setBackground(new Color(95, 0, 160));
        button.setForeground(Color.WHITE);
        button.addActionListener(al);
        return button;
    }

    public JLabel styleAndConfigureLabel(int x, int y, int width, int height,JLabel label,String type){
        label.setBounds(x,y,width,height);
        switch (type) {
            case "title" ->{
                label.setForeground(new Color(95, 0, 160));
                label.setFont(new Font("Viner Hand ITC", Font.BOLD, 28));
            }
            case "normal" -> label.setFont(new Font("Abadi Extra Light", Font.PLAIN, 15));
            case "bold" -> label.setFont(new Font("Abadi Extra Light", Font.BOLD, 15));
        }
        return label;
    }

    public JTextField styleAndConfigureTextField(int x, int y, int width, int height,JTextField textField){
        textField.setBounds(x,y,width,height);
        textField.setFont(new Font("Abadi Extra Light", Font.PLAIN,15));
        return textField;
    }

    public JLabel styleAndConfigureImage(int x, int y, int width, int height,ImageIcon imageIcon){
        JLabel image = new JLabel(imageIcon);
        image.setBounds(x,y,width,height);
        return image;
    }

    public JDialog setDialog(int x, int y, int width, int height, JDialog dialog, String message){
        dialog.setLayout(null);
        dialog.setLocation(x,y);
        dialog.setSize(width,height);
        dialog.setBackground(Color.WHITE);
        JLabel label = new JLabel(message);
        label = styleAndConfigureLabel(100,50,300,30,label,"normal");
        dialog.add(label);
        return dialog;
    }

    public JComboBox styleAndSetComboBox(int x, int y, int width, int height, int numElements, JComboBox comboBox){
        for(int i=0;i<numElements;i++){
            comboBox.addItem(i+1);
        }
        comboBox.setSize(width,height);
        comboBox.setLocation(x,y);
        comboBox.setForeground(Color.white);
        comboBox.setBackground(new Color(95, 0, 160));
        comboBox.setFont(new Font("Abadi Extra Light", Font.PLAIN, 15));
        return comboBox;
    }

    public JPanel styleAndConfigurePanel(int numElements, Color background, JPanel panel){
        panel.setLayout(null);
        panel.setBackground(background);
        panel.setVisible(true);
        if(numElements<5){
            panel.setSize(600, 600 + (numElements * 60));
        }
        else if(numElements<9 && numElements>5) {
            panel.setSize(600, 300 + (numElements * 60));
        }else{
            panel.setSize(600, 900);
        }
        return panel;
    }

    public JDialog styleAndConfigureConfirmationDialog(int x, int y, int width, int height, JDialog dialog,JButton yes, JButton no, ActionListener actionListener, String message){
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog= setDialog(x,y,width,height,dialog,message);
        yes = styleAndConfigureButton(130,200,40,25,yes,actionListener);
        no = styleAndConfigureButton(230,200,40,25,no,actionListener);
        dialog.add(yes);
        dialog.add(no);
        dialog.setVisible(false);
        return dialog;
    }
}
