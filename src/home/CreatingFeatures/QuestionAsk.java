package home.CreatingFeatures;

import GeneralFeatures.Lesson;
import GeneralFeatures.Section;
import home.Login;
import home.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class QuestionAsk extends JFrame implements ActionListener {
    private List<Section> sections;
    private List<String> questions;
    private List<String> answers;
    private JButton nextQuestion,nextSection,back;
    private final Lesson lesson;
    private JDialog confirmation;
    private JLabel numSection;
    private JTextField tfquestion, tfanswer,tfsection;
    private final String username;
    private final String lessonName;
    private String errorString;
    private final int numSections;
    private boolean filledFields;
    private int count;
    private JPanel panelLeft,panelRight;
    private int largestNum;

    public QuestionAsk(Lesson lesson,String username,String lessonName, int numSections, List<String> questions, List<String> answers, int currentSection,String sectionName,List<Section> sections
    ){
        this.username=username;
        this.count=currentSection;
        this.lesson = lesson;
        this.lessonName = lessonName;
        this.numSections = numSections;
        this.sections = sections;
        this.questions = questions;
        this.answers= answers;

        setLayout(new GridLayout(1,2));

        numSection = new JLabel("Section "+count+": ");
        numSection= Login.utilities.styleAndConfigureLabel(60,70,600,40,numSection,"bold");

        tfsection = new JTextField();
        tfsection.setText(sectionName);
        tfsection = Login.utilities.styleAndConfigureTextField(50,190,450,25,tfsection);

        tfquestion = new JTextField();
        tfquestion = Login.utilities.styleAndConfigureTextField(50,290,450,25,tfquestion);

        tfanswer = new JTextField();
        tfanswer = Login.utilities.styleAndConfigureTextField(50,380,450,25,tfanswer);

        nextQuestion = new JButton("Next Question");
        nextQuestion = Login.utilities.styleAndConfigureButton(50,450,120,25,nextQuestion,this);

        nextSection = new JButton("Submit Section");

        confirmation = new JDialog(this," ",true);
        confirmation = Login.utilities.setDialog(400,300,500,200,confirmation,"Lesson has been created successfully");
        confirmation.setVisible(false);

        back = new JButton("Back");
        back = Login.utilities.styleAndConfigureButton(430,450,120,25,back,this);

        largestNum = lesson.getLargestNumQuestions(sections);
        if(largestNum<5){
            setSize(1200, 600 + (largestNum * 60));
        }
        else if(largestNum<9 && largestNum>=5) {
            setSize(1200, 200 + (largestNum * 60));
        }else{
            setSize(1200, 900);
        }
        setLocation(150,50);
        setVisible(true);

        panelLeft = new JPanel();
        panelLeft=setPanelLeft();
        add(panelLeft);

        panelRight = new JPanel();
        panelRight=setPanelRight();
        add(panelRight);
    }

    public void paint(Graphics g, String error){
        super.paint(g);
        g.setColor(Color.red);
        g.setFont(new Font("Tahoma",Font.BOLD,15));
        if(!filledFields){
            g.drawString(error,50,550);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==nextQuestion){
            checkIfUserFilledInfo(false);
            paint(getGraphics(),errorString);
            if(filledFields) {
                questions.add(tfquestion.getText());
                answers.add(tfanswer.getText());
                recheckLargestNum();
            }
        }else if(ae.getSource()==nextSection){
            checkIfUserFilledInfo(true);
            paint(getGraphics(),errorString);
            count++;
            numSection.setText("Section "+count+": ");
            if(filledFields) {
                questions.add(tfquestion.getText());
                answers.add(tfanswer.getText());
                if (tfsection.getText() != null || tfsection.getText().length() > 0) {
                    Section section = new Section(tfsection.getText(), questions, answers);
                    section.setNumQuestions(questions.size());
                    tfsection.setText("");
                    sections.add(section);
                }
            }
            if(count>numSections){
                lesson.persistLesson(new Lesson(lessonName,numSections,sections));
                setVisible(false);
                confirmation.setVisible(true);
                new MainMenu(username);
            }else{
                questions.clear();
                answers.clear();
                recheckLargestNum();
            }
        }
        else if(ae.getSource()==back){
            setVisible(false);
            new Create(username);
        }
    }

    public JPanel setPanelLeft(){
        JPanel panel=new JPanel();
        panel = Login.utilities.styleAndConfigurePanel(largestNum,new Color(230, 230, 250),panel);
        JLabel title = new JLabel("Section's current status");
        title= Login.utilities.styleAndConfigureLabel(50,30,600,40,title,"title");
        panel.add(title);
        panel.add(numSection);
        System.out.println(questions.size());
        for(int i=0;i< questions.size();i++){
            JLabel labelQuestion = new JLabel();
            JLabel labelAnswer = new JLabel();
            labelQuestion.setText((i+1)+". "+questions.get(i));
            labelQuestion=Login.utilities.styleAndConfigureLabel(100,100+(i*60),400,20,labelQuestion,"bold");
            panel.add(labelQuestion);
            labelAnswer.setText(answers.get(i));
            labelAnswer=Login.utilities.styleAndConfigureLabel(120,120+(i*60),400,20,labelAnswer,"normal");
            panel.add(labelAnswer);
        }
        return panel;
    }

    public JPanel setPanelRight(){
        JPanel panel=new JPanel();
        panel = Login.utilities.styleAndConfigurePanel(largestNum,Color.WHITE,panel);

        JLabel title = new JLabel("Create Lesson");
        title=Login.utilities.styleAndConfigureLabel(200,30,200,40,title,"title");
        panel.add(title);
        panel.add(numSection);

        JLabel section = new JLabel("Enter the section name: ");
        section = Login.utilities.styleAndConfigureLabel(50,150,300,30, section,"bold");
        panel.add(section);
        panel.add(tfsection);

        JLabel question = new JLabel("Enter the question: ");
        question = Login.utilities.styleAndConfigureLabel(50,250,300,30, question,"bold");
        panel.add(question);
        panel.add(tfquestion);

        JLabel answer = new JLabel("Enter the answer: ");
        answer = Login.utilities.styleAndConfigureLabel(50,340,300,30, answer,"bold");
        panel.add(answer);
        panel.add(tfanswer);
        panel.add(nextQuestion);

        if(count>=numSections) {
            nextSection.setText("Submit Lesson");
        }
        nextSection = Login.utilities.styleAndConfigureButton(200, 450, 200, 25, nextSection, this);
        panel.add(nextSection);
        panel.add(back);
        return panel;
    }

    public void checkIfUserFilledInfo(boolean caseNewSection){
        filledFields =true;
        if(!caseNewSection) {
            if (tfquestion.getText() == null || tfquestion.getText().length() <= 0) {
                errorString = "ERROR! Question cannot be empty!";
                if (tfanswer.getText() == null || tfanswer.getText().length() <= 0) {
                    errorString = "ERROR! Question nor answer are entered!";
                }
                filledFields = false;
            }
            else if (tfanswer.getText() == null || tfanswer.getText().length() <= 0) {
                filledFields = false;
                errorString = "ERROR! Answer cannot be empty!";
            }
        }else{
            if(tfsection.getText()==null||tfsection.getText().length()<=0){
                errorString = "ERROR! Section name cannot be empty!";
                filledFields=false;
            }else {
                checkIfUserFilledInfo(false);
                errorString = "";
            }
        }
    }

    public void recheckLargestNum(){
        if(sections.size()>0) {
            largestNum = lesson.getLargestNumQuestions(sections);
        }else{
            largestNum=questions.size();
        }
        if(questions.size()>largestNum){
            largestNum=questions.size();
        }
        setVisible(false);
        new QuestionAsk(lesson,username,lessonName,numSections,questions,answers,count,tfsection.getText(),sections);
    }
}
