package home.StudyingFeatures;

import GeneralFeatures.Lesson;
import home.Login;
import home.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LessonStudy extends JFrame implements ActionListener {
    private String username;
    private List<Lesson> lessons;
    private JButton back,next;
    private JComboBox comboBox;

    public LessonStudy(String username){
        this.username=username;
        Lesson lesson = new Lesson();
        this.lessons = lesson.getLessons();
        setLayout(new GridLayout(1,2));

        JPanel panelLeft = new JPanel();
        panelLeft = setPanelLeft(panelLeft);
        add(panelLeft);

        JPanel panelRight = new JPanel();
        panelRight = setPanelRight(panelRight);
        add(panelRight);
        if(lessons.size()<5){
            setSize(1200, 600 + (this.lessons.size() * 60));
        }
        else if(lessons.size()<9 && lessons.size()>=5) {
            setSize(1200, 300 + (this.lessons.size() * 60));
        }else{
            setSize(1200, 900);
        }
        setLocation(150,50);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        int numQuestionToChange = comboBox.getSelectedIndex();
        if(ae.getSource()==next){
            setVisible(false);
            new Quiz(username,numQuestionToChange);
        }
        if(ae.getSource()==back){
            setVisible(false);
            new MainMenu(username);
        }
    }

    public JPanel setPanelLeft(JPanel panel){
        panel.setLayout(null);
        panel.setSize(600,500);
        panel.setBackground(new Color(230, 230, 250));
        panel.setVisible(true);
        JLabel title = new JLabel("Available lessons");
        title= Login.utilities.styleAndConfigureLabel(160,100,600,40,title,"title");
        panel.add(title);
        for(int i=0;i<lessons.size();i++){
            JLabel lessonName = new JLabel();
            lessonName.setText((i+1)+". "+lessons.get(i).getLessonName());
            lessonName=Login.utilities.styleAndConfigureLabel(250,160+(i*30),400,20,lessonName,"normal");
            panel.add(lessonName);
        }
        return panel;
    }

    public JPanel setPanelRight(JPanel panel){

        panel = Login.utilities.styleAndConfigurePanel(lessons.size(),Color.WHITE,panel);

        JLabel title = new JLabel("Okay, "+username+"! Let's study a lesson");
        title=Login.utilities.styleAndConfigureLabel(50,100,600,40,title,"title");
        panel.add(title);

        JLabel numLesson = new JLabel("Select the number of the lesson to study ");
        numLesson = Login.utilities.styleAndConfigureLabel(50,180,400,30, numLesson,"bold");
        panel.add(numLesson);

        comboBox = new JComboBox();
        comboBox = Login.utilities.styleAndSetComboBox(50,220,50,25,lessons.size(),comboBox);
        panel.add(comboBox);

        next = new JButton("Next");
        next = Login.utilities.styleAndConfigureButton(150,300,120,25,next,this);
        panel.add(next);

        back = new JButton("Back");
        back = Login.utilities.styleAndConfigureButton(330,300,120,25,back,this);
        panel.add(back);

        return panel;
    }
}
