package home.StudyingFeatures;

import GeneralFeatures.Lesson;
import com.google.gson.Gson;
import home.Login;
import home.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Score extends JFrame implements ActionListener {

    private Lesson lesson;
    private final int score;
    private final String username;
    private JButton again,back;
    private List<String> allQuestions;
    private List<String> allAnswers;

    public Score(String username,int score,String lessonName){
        this.username=username;
        this.score=score;
        this.lesson = new Lesson();
        this.lesson = lesson.getLesson(lessonName,new Gson());
        this.allQuestions = lesson.getAllQuestions();
        this.allAnswers = lesson.getAllAnswers();
        setBounds(200,100,1200,100+(allQuestions.size()*60));
        if(allQuestions.size()<5){
            setSize(1200, 600 + (allQuestions.size() * 60));
        }
        else if(allQuestions.size()<9 && allQuestions.size()>=5) {
            setSize(1200, 200 + (allQuestions.size() * 60));
        }else{
            setSize(1200, 900);
        }
        getContentPane().setBackground(Color.WHITE);
        setLayout(new GridLayout(1,2));

        JPanel panelLeft = new JPanel();
        panelLeft= setPanelLeft(panelLeft);
        add(panelLeft);

        JPanel panelRight = new JPanel();
        panelRight= setPanelRight(panelRight);
        add(panelRight);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==again){
            setVisible(false);
            new LessonStudy(username);
        }else if(ae.getSource()==back) {
            setVisible(false);
            new MainMenu(username);
        }
    }

    public JPanel setPanelLeft(JPanel panel){
        panel = Login.utilities.styleAndConfigurePanel(allQuestions.size(),new Color(230, 230, 250),panel);
        JLabel title = new JLabel("Lesson's answers:");
        title= Login.utilities.styleAndConfigureLabel(160,100,600,40,title,"title");
        panel.add(title);
        for(int i=0;i<allQuestions.size();i++){
            JLabel labelQuestion = new JLabel();
            JLabel labelAnswer = new JLabel();
            labelQuestion.setText((i+1)+". "+allQuestions.get(i));
            labelQuestion=Login.utilities.styleAndConfigureLabel(180,160+(i*60),400,20,labelQuestion,"bold");
            panel.add(labelQuestion);
            labelAnswer.setText(allAnswers.get(i));
            labelAnswer=Login.utilities.styleAndConfigureLabel(200,190+(i*60),400,20,labelAnswer,"normal");
            panel.add(labelAnswer);
        }
        return panel;
    }

    public JPanel setPanelRight(JPanel panel){

        panel = Login.utilities.styleAndConfigurePanel(allQuestions.size(),Color.WHITE,panel);

        JLabel heading = new JLabel("Thank you "+ username +" for studying "+lesson.getLessonName()+"! ");
        heading=Login.utilities.styleAndConfigureLabel(45,100,700,30,heading,"title");
        panel.add(heading);

        JLabel lblscore = new JLabel("Your score is "+score);
        lblscore = Login.utilities.styleAndConfigureLabel(200,200,300,30,lblscore,"normal");
        panel.add(lblscore);

        again = new JButton("Play Again");
        again = Login.utilities.styleAndConfigureButton(150,250,120,25,again,this);
        panel.add(again);

        back = new JButton("Back");
        back = Login.utilities.styleAndConfigureButton(330,250,120,25,back,this);
        panel.add(back);

        return panel;
    }
}
