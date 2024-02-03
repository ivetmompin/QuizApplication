package home.StudyingFeatures;

import GeneralFeatures.Lesson;
import home.Login;
import home.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class Quiz extends JFrame implements ActionListener{

    private JButton next,submit,back;
    private final JLabel qno;
    private JLabel question;
    private JTextField tfanswer;
    private final List<String> toAskQuestions;
    private final List<String> toAskAnswers;
    private Lesson lesson;
    public static String username;

    public static int timer = 60;
    public static boolean ans_given = false;
    public static int count=0,randomQuestion=0;
    public static int score = 0;

    public Quiz(String username,int indexLesson){
        ans_given=true;
        lesson = new Lesson();
        lesson = lesson.getLessons().get(indexLesson);
        toAskQuestions = lesson.getAllQuestions();
        toAskAnswers = lesson.getAllAnswers();
        this.username=username;

        setBounds(50,0,1440,850);
        getContentPane().setBackground(Color.white);
        setLayout(null);

        ImageIcon image1 = new ImageIcon("icons/quiz.jpg");
        JLabel image = Login.utilities.styleAndConfigureImage(0,0,1440,392,image1);
        add(image);

        qno = new JLabel();
        qno.setBounds(100,450,50,30);
        qno.setFont(new Font("Tahoma",Font.PLAIN,24));
        add(qno);

        question = new JLabel();
        question = Login.utilities.styleAndConfigureLabel(150,450,900,30,question,"bold");
        add(question);

        JLabel answer = new JLabel("Enter your answer: ");
        answer = Login.utilities.styleAndConfigureLabel(150,520,400,30, answer,"bold");
        add(answer);

        tfanswer = new JTextField();
        tfanswer = Login.utilities.styleAndConfigureTextField(150,560,600,25,tfanswer);
        add(tfanswer);

        next = new JButton("Next");
        next = Login.utilities.styleAndConfigureButton(1100,550,200,40,next,this);
        add(next);

        submit = new JButton("Submit");
        submit = Login.utilities.styleAndConfigureButton(1100,600,200,40,submit,this);
        submit.setEnabled(false);

        back = new JButton("Back");
        back = Login.utilities.styleAndConfigureButton(1100,650,200,40,back,this);

        add(submit);
        add(back);
        start(count);

        setVisible(true);
    }

    public void paint(Graphics g){
        super.paint(g);
        String time = "Time left - " + timer + " seconds"; //15
        g.setColor(Color.red);
        g.setFont(new Font("Tahoma",Font.BOLD,25));
        if(timer>0){
            g.drawString(time,1100,500);
        }else{
            g.drawString("Time is Up!!",1100,500);
        }
        timer--;//14
        try{
            Thread.sleep(1000);
            repaint();
        }catch(Exception e){
            e.printStackTrace();
        }
        if(timer<0){
            timer = 60;
            checkEndOfQuestionArray();
            if(count==toAskQuestions.size()){//submit
                checkIfUserGaveAnswer();
                setVisible(false);
            }else if(count<(toAskQuestions.size()-1)){//next
                checkIfUserGaveAnswer();
                count++;
                start(count);
            }
        }
    }

    public void start(int count){
        randomQuestion = new Random().nextInt(0,toAskQuestions.size());
        qno.setText(""+(count+1)+". ");
        question.setText(toAskQuestions.get(randomQuestion));
    }

    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==next){
            repaint();
            checkIfUserGaveAnswer();
            checkCorrectAnswer();
            count++;
            checkEndOfQuestionArray();
            ans_given=false;
            toAskAnswers.remove(randomQuestion);
            toAskQuestions.remove(randomQuestion);
            if(count<lesson.getNumSections()) {
                tfanswer.setText("");
                timer=60;
                start(count);
            }
        }else if(ae.getSource()==submit){
            checkIfUserGaveAnswer();
            checkCorrectAnswer();
            ans_given=false;
            count=0;
            setVisible(false);
            tfanswer.setText("");
            new Score(username, score,lesson.getLessonName());
        }else if(ae.getSource()==back){
            count=0;
            setVisible(false);
            tfanswer.setText("");
            new MainMenu(username);
        }
    }

    public void checkIfUserGaveAnswer(){
        if(tfanswer.getText()==null||tfanswer.getText().length()<=0){
            ans_given=false;
        }
    }

    public void checkEndOfQuestionArray(){
        if(count==(toAskQuestions.size()-1)){
            next.setEnabled(false);
            submit.setEnabled(true);
        }
    }

    public void checkCorrectAnswer(){
        if(ans_given) {
            if (tfanswer.getText().equals(toAskAnswers.get(randomQuestion))){
                score += 10;
            } else {
                score += 0;
            }
        }
    }
}
