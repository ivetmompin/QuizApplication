package home.CreatingFeatures;

import GeneralFeatures.Lesson;
import GeneralFeatures.Section;
import com.google.gson.Gson;
import home.Login;
import home.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class Create extends JFrame implements ActionListener {

    private JTextField tfname,tfQuantity;
    private JButton next,back;
    private final String username;
    private int correct;
    private boolean errorInsertion;

    public Create(String name){
        this.username=name;
        errorInsertion=false;
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        ImageIcon image1 = new ImageIcon("src/icons/login.jpg");
        JLabel image = Login.utilities.styleAndConfigureImage(0,0,600,500,image1);
        add(image);

        JLabel heading = new JLabel("Let's create a Lesson!");
        heading = Login.utilities.styleAndConfigureLabel(750,60,300,45,heading,"title");
        add(heading);

        JLabel nameLesson = new JLabel("Enter the name of your lesson");
        nameLesson = Login.utilities.styleAndConfigureLabel(700,150,300,20,nameLesson,"bold");
        add(nameLesson);

        tfname = new JTextField();
        tfname = Login.utilities.styleAndConfigureTextField(735,200,300,25,tfname);
        add(tfname);

        JLabel sectionQuantity = new JLabel("How many sections will have your lesson?");
        sectionQuantity = Login.utilities.styleAndConfigureLabel(700,250,500,20,sectionQuantity,"bold");
        add(sectionQuantity);

        tfQuantity = new JTextField();
        tfQuantity = Login.utilities.styleAndConfigureTextField(735,300,300,25,tfQuantity);
        add(tfQuantity);

        next = new JButton("Next");
        next = Login.utilities.styleAndConfigureButton(735,400,120,25,next,this);
        add(next);

        back = new JButton("Back");
        back = Login.utilities.styleAndConfigureButton(915,400,120,25,back,this);
        add(back);

        setSize(1200,600);
        setLocation(200,150);
        setVisible(true);
    }

    public void paint(Graphics g, String error){
        super.paint(g);
        g.setColor(Color.red);
        g.setFont(new Font("Tahoma",Font.BOLD,15));
        if(errorInsertion){
            g.drawString(error,700,390);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==next){
            correct = 0;
            checkAllFieldsCorrect();
            checkLessonDoesNotExist();
            paint(getGraphics(),getCorrectMessageToPrint());
            if(correct==0) {
                String lessonName = tfname.getText();
                String numSections = tfQuantity.getText();
                tfname.setText("");
                tfQuantity.setText("");
                setVisible(false);
                new QuestionAsk(new Lesson(),username,lessonName,Integer.parseInt(numSections),new ArrayList<>(), new ArrayList<>(),1,"",new ArrayList<>());
            }
        }if(ae.getSource()==back){
            tfname.setText("");
            tfQuantity.setText("");
            setVisible(false);
            new MainMenu(username);
        }
    }

    private String getCorrectMessageToPrint() {
        switch(correct){
            case 1->{
                return "ERROR! The name cannot be empty.";
            }case 2->{
                return "ERROR! The number of questions has to be greater than 0.";
            }case 3->{
                return "ERROR! The number introduced is not an integer.";
            }case 4->{
                return "ERROR! Lesson name nor question number are correct.";
            }case 5->{
                return "ERROR! "+tfname.getText()+" already exists.";
            }
            default ->{return "";}
        }
    }

    private void checkAllFieldsCorrect() {
        try{
            if(tfname.getText() == null){
                correct=1;
                if(Integer.parseInt(tfQuantity.getText())<=0){
                    correct=4;
                }
                errorInsertion=true;
            } else {
                tfname.getText();
            }
            if(Integer.parseInt(tfQuantity.getText())<=0){
                correct=2;
                errorInsertion=true;
            }
        }catch(Exception e){
            if(errorInsertion){
                correct = 4;
            }else {
                correct = 3;
            }
            errorInsertion=true;
        }
    }

    public void checkLessonDoesNotExist(){
        Lesson lesson = new Lesson();
        List<String> namesLessons = new ArrayList<>();
        namesLessons=lesson.getLessonsNames(namesLessons,new Gson());
        if(namesLessons!=null) {
            if (namesLessons.contains(tfname.getText())) {
                errorInsertion = true;
                correct = 5;
            }
        }
    }
}
