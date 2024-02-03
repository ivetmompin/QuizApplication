package home.EditingFeatures.LessonEditing;

import GeneralFeatures.Lesson;
import GeneralFeatures.Section;
import com.google.gson.Gson;
import home.EditingFeatures.EditContentMenu;
import home.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddQuestion extends JFrame implements ActionListener {

    private String username;
    private Lesson lesson;
    private JButton back,submit;
    private JComboBox comboBox;
    private JButton arrowLeft, arrowRight;
    private JTextField tfquestion,tfanswer;
    private JPanel panelLeft,panelRight;
    private boolean filledFields;
    private int largestNum, numSectionToShow;
    private String errorString;

    public AddQuestion(String username,String lessonName){
        this.username=username;
        filledFields=true;
        numSectionToShow=1;
        Lesson lesson = new Lesson();
        this.lesson = lesson.getLesson(lessonName,new Gson());
        setLayout(new GridLayout(1,2));

        panelLeft = new JPanel();
        panelLeft = setPanelLeft(panelLeft);
        add(panelLeft);

        panelRight = new JPanel();
        panelRight = setPanelRight(panelRight);
        add(panelRight);
        largestNum = this.lesson.getLargestNumQuestions(this.lesson.getSections());
        if(largestNum<5){
            setSize(1200, 600 + (largestNum * 60));
        }
        else if(largestNum<9 && largestNum>=5) {
            setSize(1200, 300 + (largestNum * 60));
        }else{
            setSize(1200, 900);
        }
        setLocation(150,50);
        setVisible(true);
    }

    public void paint(Graphics g, String error){
        super.paint(g);
        g.setColor(Color.red);
        g.setFont(new Font("Tahoma",Font.BOLD,15));
        if(!filledFields){
            g.drawString(error,900,550);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==arrowLeft){
            if(numSectionToShow>1) {
                numSectionToShow--;
            }
            setPanelLeft(panelLeft);
        }else if(ae.getSource()==arrowRight){
            if(numSectionToShow<lesson.getNumSections()){
                numSectionToShow++;
            }
            setPanelLeft(panelLeft);
        }
        else if(ae.getSource()==submit){
            int numSectionToAdd = comboBox.getSelectedIndex();
            checkIfUserFilledInfo();
            paint(getGraphics(),errorString);
            if(filledFields) {
                lesson.getSections().get(numSectionToAdd).getQuestions().add(tfquestion.getText());
                lesson.getSections().get(numSectionToAdd).getAnswers().add(tfanswer.getText());
                int newNumQuestions = lesson.getSections().get(numSectionToAdd).getNumQuestions()+1;
                lesson.getSections().get(numSectionToAdd).setNumQuestions(newNumQuestions);
                lesson.removeLesson(lesson.getLessonName());
                lesson.persistLesson(lesson);
                tfquestion.setText("");
                tfanswer.setText("");
                setVisible(false);
                new AddQuestion(username, lesson.getLessonName());
            }
        }
        else if(ae.getSource()==back){
            tfquestion.setText("");
            tfanswer.setText("");
            setVisible(false);
            new EditContentMenu(username,lesson.getLessonName());
        }
    }

    public JPanel setPanelLeft(JPanel panel){
        int maxNumQuestions = lesson.getLargestNumQuestions(lesson.getSections());
        panel = Login.utilities.styleAndConfigurePanel(maxNumQuestions,new Color(230, 230, 250),panel);
        Section sectionToShow = lesson.getSections().get(numSectionToShow-1);
        JLabel title = new JLabel("Available questions");
        title= Login.utilities.styleAndConfigureLabel(50,30,600,40,title,"title");
        panel.add(title);
        JLabel sectionName = new JLabel("Section "+numSectionToShow+": "+sectionToShow.getSectionName());
        sectionName= Login.utilities.styleAndConfigureLabel(60,30,600,40,sectionName,"bold");
        panel.add(sectionName);
        System.out.println("num questions:"+sectionToShow.getNumQuestions());
        for(int i=0;i<sectionToShow.getNumQuestions();i++){
            JLabel labelQuestion = new JLabel();
            JLabel labelAnswer = new JLabel();
            labelQuestion.setText((i+1)+". "+sectionToShow.getQuestions().get(i));
            labelQuestion=Login.utilities.styleAndConfigureLabel(100,100+(i*60),400,20,labelQuestion,"bold");
            panel.add(labelQuestion);
            labelAnswer.setText(sectionToShow.getAnswers().get(i));
            labelAnswer=Login.utilities.styleAndConfigureLabel(120,120+(i*60),400,20,labelAnswer,"normal");
            panel.add(labelAnswer);
            if(i==sectionToShow.getNumQuestions()-1){
                panel = setArrows(i,panel);
            }
        }

        return panel;
    }

    public JPanel setPanelRight(JPanel panel){
        panel = Login.utilities.styleAndConfigurePanel(lesson.getAllQuestions().size(),Color.WHITE,panel);

        JLabel title = new JLabel("Add question");
        title=Login.utilities.styleAndConfigureLabel(200,30,200,40,title,"title");
        panel.add(title);

        JLabel numSection = new JLabel("Select the number of the section to add the question ");
        numSection = Login.utilities.styleAndConfigureLabel(50,100,400,30, numSection,"bold");
        panel.add(numSection);

        comboBox = new JComboBox();
        comboBox = Login.utilities.styleAndSetComboBox(50,140,50,25,lesson.getNumSections(),comboBox);
        panel.add(comboBox);

        JLabel question = new JLabel("Enter the question: ");
        question = Login.utilities.styleAndConfigureLabel(50,200,300,30, question,"bold");
        panel.add(question);

        tfquestion = new JTextField();
        tfquestion = Login.utilities.styleAndConfigureTextField(50,240,450,25,tfquestion);
        panel.add(tfquestion);

        JLabel answer = new JLabel("Enter the answer: ");
        answer = Login.utilities.styleAndConfigureLabel(50,290,300,30, answer,"bold");
        panel.add(answer);

        tfanswer = new JTextField();
        tfanswer = Login.utilities.styleAndConfigureTextField(50,330,450,25,tfanswer);
        panel.add(tfanswer);

        submit = new JButton("Submit");
        submit = Login.utilities.styleAndConfigureButton(150,400,120,25,submit,this);
        panel.add(submit);

        back = new JButton("Back");
        back = Login.utilities.styleAndConfigureButton(330,400,120,25,back,this);
        panel.add(back);

        return panel;
    }

    public void checkIfUserFilledInfo(){
        errorString = "";
        if(tfquestion.getText()==null||tfquestion.getText().length()<=0){
            errorString="ERROR! Question cannot be empty!";
            if(tfanswer.getText() == null||tfanswer.getText().length()<=0){
                errorString="ERROR! Question nor answer are entered!";
            } else {
                tfanswer.getText();
            }
            filledFields=false;
        }
        if(tfanswer.getText() == null||tfanswer.getText().length()<=0){
            filledFields=false;
            errorString="ERROR! Answer cannot be empty!";
        } else {
            tfanswer.getText();
        }
    }
    public JPanel setArrows(int i, JPanel panel){
        ImageIcon image1 = new ImageIcon("src/icons/arrowPrevious.jpg");
        arrowLeft=new JButton();
        arrowLeft.setIcon(image1);
        arrowLeft.setLocation(70,150+(i*60));
        arrowLeft.setSize(20,10);
        arrowLeft.addActionListener(this);
        panel.add(arrowLeft);
        ImageIcon image2 = new ImageIcon("src/icons/arrowNext.jpg");
        arrowRight=new JButton();
        arrowRight.setIcon(image2);
        arrowRight.setLocation(250,150+(i*60));
        arrowRight.setSize(20,10);
        arrowRight.addActionListener(this);
        panel.add(arrowRight);
        return panel;
    }
}
