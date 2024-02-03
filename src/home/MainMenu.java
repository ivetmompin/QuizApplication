package home;

import home.CreatingFeatures.Create;
import home.EditingFeatures.Edit;
import home.StudyingFeatures.LessonStudy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame implements ActionListener {
    String name;
    JButton create,edit,start,back;

    public MainMenu(String name){
        this.name=name;
        getContentPane().setBackground(Color.white);
        setLayout(null);

        JLabel heading = new JLabel("Welcome "+name+" to Study Tool");
        heading = Login.utilities.styleAndConfigureLabel(150,40,500,30,heading,"title");
        add(heading);

        JLabel rules = new JLabel("What do you want to do?");
        rules = Login.utilities.styleAndConfigureLabel(300,80,700,60,rules,"normal");
        add(rules);


        back = new JButton("Back");
        back = Login.utilities.styleAndConfigureButton(300,330,200,30,back,this);
        add(back);

        start = new JButton("Study Lesson");
        start = Login.utilities.styleAndConfigureButton(300,280,200,30,start,this);
        add(start);

        create = new JButton("Create Lesson");
        create = Login.utilities.styleAndConfigureButton(300,180,200,30,create,this);
        add(create);

        edit = new JButton("Edit Lesson");
        edit = Login.utilities.styleAndConfigureButton(300,230,200,30,edit,this);
        add(edit);

        setSize(800,450);
        setLocation(300,150);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==create) {
            setVisible(false);
            new Create(name);
        }else if(ae.getSource()==edit){
            setVisible(false);
            new Edit(name);
        }else if(ae.getSource()==start){
            setVisible(false);
            new LessonStudy(name);
        } else if(ae.getSource()==back){
            setVisible(false);
            new Login();
        }
    }
}
