package home.EditingFeatures;

import GeneralFeatures.Lesson;
import home.Login;
import home.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuEdit extends JFrame implements ActionListener {

    String name,lessonName;
    JButton edit,delete,back,yes,no;
    JDialog dialogDeleted, dialogConfirm;

    public MenuEdit(String username, String lessonName){

        this.lessonName=lessonName;
        this.name=username;

        getContentPane().setBackground(Color.white);
        setLayout(null);

        dialogDeleted = new JDialog(this," ",true);
        dialogDeleted = Login.utilities.setDialog(400,300,500,200,dialogDeleted,"Lesson has been deleted successfully");
        dialogDeleted.setVisible(false);

        configureConfirmationDialog();

        JLabel heading = new JLabel("Welcome " +name +" to editing mode");
        heading = Login.utilities.styleAndConfigureLabel(150,40,500,30,heading,"title");
        add(heading);

        JLabel rules = new JLabel("What do you want to do with the lesson?");
        rules = Login.utilities.styleAndConfigureLabel(230,80,700,60,rules,"bold");
        add(rules);

        back = new JButton("Back");
        back = Login.utilities.styleAndConfigureButton(250,300,200,30,back,this);
        add(back);

        delete = new JButton("Delete Lesson");
        delete = Login.utilities.styleAndConfigureButton(250,220,200,30,delete,this);
        add(delete);

        edit = new JButton("Edit Lesson Content");
        edit = Login.utilities.styleAndConfigureButton(250,170,200,30,edit,this);
        add(edit);

        setSize(700,450);
        setLocation(300,150);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==edit) {
            setVisible(false);
            new EditContentMenu(name,lessonName);
        }else if(ae.getSource()==delete) {
            Lesson lesson = new Lesson();
            lesson.removeLesson(lessonName);
            dialogConfirm.setVisible(true);
        }else if(ae.getSource()==yes){
            Lesson lesson = new Lesson();
            lesson.removeLesson(lessonName);
            dialogConfirm.setVisible(false);
            dialogDeleted.setVisible(true);
            setVisible(false);
            new MainMenu(name);
        }else if(ae.getSource()==no){
            dialogConfirm.setVisible(false);
        } else if(ae.getSource()==back){
            setVisible(false);
            new MainMenu(name);
        }
    }

    public void configureConfirmationDialog(){
        yes = new JButton("YES");
        no = new JButton("NO");
        dialogConfirm = new JDialog(this," ",true);
        dialogConfirm = Login.utilities.styleAndConfigureConfirmationDialog(400,300,500,200,dialogConfirm,yes,no,this,"Are you sure you want to delete this lesson?");
    }
}
