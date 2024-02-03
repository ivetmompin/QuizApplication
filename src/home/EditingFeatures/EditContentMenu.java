package home.EditingFeatures;

import home.Login;
import home.MainMenu;
import home.EditingFeatures.LessonEditing.AddQuestion;
import home.EditingFeatures.LessonEditing.DeleteQuestion;
import home.EditingFeatures.LessonEditing.EditQuestion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditContentMenu extends JFrame implements ActionListener{
    String name,lessonName;
    JButton add,edit,delete,back,next;

    public EditContentMenu(String username, String lessonName){
        this.name=username;
        this.lessonName=lessonName;
        getContentPane().setBackground(Color.white);
        setLayout(null);

        JLabel heading = new JLabel("Okay, "+name+" let's edit the content");
        heading = Login.utilities.styleAndConfigureLabel(150,40,500,30,heading,"title");
        add(heading);

        JLabel rules = new JLabel("What do you want to do?");
        rules = Login.utilities.styleAndConfigureLabel(300,80,700,60,rules,"bold");
        add(rules);


        back = new JButton("Back");
        back = Login.utilities.styleAndConfigureButton(400,330,100,30,back,this);
        add(back);

        next = new JButton("Next");
        next = Login.utilities.styleAndConfigureButton(250,330,100,30,next,this);
        add(next);

        delete = new JButton("Delete Question");
        delete = Login.utilities.styleAndConfigureButton(300,250,150,30,delete,this);
        add(delete);

        edit = new JButton("Edit Question");
        edit = Login.utilities.styleAndConfigureButton(300,200,150,30,edit,this);
        add(edit);

        add = new JButton("Add Question");
        add = Login.utilities.styleAndConfigureButton(300,150,150,30,add,this);
        add(add);

        setSize(800,500);
        setLocation(300,150);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==add){
            setVisible(false);
            new AddQuestion(name,lessonName);
        }else if(ae.getSource()==edit) {
            setVisible(false);
            new EditQuestion(name,lessonName);
        }else if(ae.getSource()==delete){
            setVisible(false);
            new DeleteQuestion(name,lessonName);
        }else if(ae.getSource()==back){
            setVisible(false);
            new MainMenu(name);
        }
    }
}
