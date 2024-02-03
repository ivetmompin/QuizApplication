package GeneralFeatures;

import java.util.List;

public class Section {
    private String sectionName;
    private int numQuestions;
    private final List<String> questions;
    private final List<String> answers;

    public Section (String sectionName, List<String> questions, List<String> answers){
        this.sectionName=sectionName;
        this.questions=questions;
        this.answers = answers;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public int getNumQuestions() {
        return numQuestions;
    }

    public void setNumQuestions(int numQuestions) {
        this.numQuestions = numQuestions;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public List<String> getAnswers() {
        return answers;
    }

}
