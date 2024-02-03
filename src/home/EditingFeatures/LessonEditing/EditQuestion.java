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

public class EditQuestion extends JFrame implements ActionListener {

    private String username;
    private Lesson lesson;
    private JButton back,submit;
    private JComboBox comboBoxSection, comboBoxQuestion;
    private JButton arrowLeft, arrowRight, yes, no;
    private JTextField tfquestion,tfanswer;
    private JDialog dialog;
    private JPanel panelLeft,panelRight;
    private boolean filledFields;
    private int largestNum, numSectionToShow;
    private String errorString;

    public EditQuestion(String username,String lessonName){
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

        configureConfirmationDialog();
        add(dialog);

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
        else if(ae.getSource()==comboBoxSection){
            numSectionToShow=comboBoxSection.getSelectedIndex()+1;
            setPanelLeft(panelLeft);
        }
        else if(ae.getSource()==submit) {
            dialog.setVisible(true);
        }else if(ae.getSource()==yes){
            int numSectionToEdit = comboBoxSection.getSelectedIndex();
            int numQuestionToEdit = comboBoxQuestion.getSelectedIndex();
            lesson.getSections().get(numSectionToEdit).getQuestions().set(numQuestionToEdit,tfquestion.getText());
            lesson.getSections().get(numSectionToEdit).getAnswers().set(numQuestionToEdit,tfanswer.getText());
            lesson.removeLesson(lesson.getLessonName());
            lesson.persistLesson(lesson);
        }else if(ae.getSource()==no){
            dialog.setVisible(false);
        }
        else if(ae.getSource()==back){
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

        JLabel title = new JLabel("Edit question");
        title=Login.utilities.styleAndConfigureLabel(200,30,200,40,title,"title");
        panel.add(title);

        JLabel numSection = new JLabel("Select the number of the section to edit the question ");
        numSection = Login.utilities.styleAndConfigureLabel(50,100,400,30, numSection,"bold");
        panel.add(numSection);

        comboBoxSection = new JComboBox();
        comboBoxSection = Login.utilities.styleAndSetComboBox(50,140,50,25,lesson.getNumSections(),comboBoxSection);
        comboBoxSection.addActionListener(this);
        panel.add(comboBoxSection);

        JLabel numQuestion = new JLabel("Select the number of the question to edit");
        numQuestion = Login.utilities.styleAndConfigureLabel(50,200,400,30, numQuestion,"bold");
        panel.add(numQuestion);

        comboBoxQuestion= new JComboBox();
        comboBoxQuestion = Login.utilities.styleAndSetComboBox(50,240,50,25,lesson.getSections().get(comboBoxSection.getSelectedIndex()).getNumQuestions(),comboBoxQuestion);
        panel.add(comboBoxQuestion);

        JLabel question = new JLabel("Enter the question: ");
        question = Login.utilities.styleAndConfigureLabel(50,290,300,30, question,"bold");
        panel.add(question);

        tfquestion = new JTextField();
        tfquestion = Login.utilities.styleAndConfigureTextField(50,330,450,25,tfquestion);
        panel.add(tfquestion);

        JLabel answer = new JLabel("Enter the answer: ");
        answer = Login.utilities.styleAndConfigureLabel(50,380,300,30, answer,"bold");
        panel.add(answer);

        tfanswer = new JTextField();
        tfanswer = Login.utilities.styleAndConfigureTextField(50,420,450,25,tfanswer);
        panel.add(tfanswer);

        submit = new JButton("Submit");
        submit = Login.utilities.styleAndConfigureButton(150,470,120,25,submit,this);
        panel.add(submit);

        back = new JButton("Back");
        back = Login.utilities.styleAndConfigureButton(330,470,120,25,back,this);
        panel.add(back);


        return panel;
    }

    public void configureConfirmationDialog(){
        yes = new JButton("YES");
        no = new JButton("NO");
        dialog = new JDialog(this," ",true);
        dialog = Login.utilities.styleAndConfigureConfirmationDialog(400,300,500,200,dialog,yes,no,this,"Are you sure you want to edit this question?");
    }

    public JPanel setArrows(int i,JPanel panel){
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
