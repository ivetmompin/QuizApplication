package GeneralFeatures;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Lesson {
    private String lessonName;
    private int numSections;
    private List<Section> sections;
    private boolean lessonExists;

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public int getNumSections() {
        return numSections;
    }

    public void setNumSections(int numSections) {
        this.numSections = numSections;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public Lesson() {}

    public Lesson(String lessonName,int numSections, List<Section> sections){
        this.lessonName=lessonName;
        this.numSections=numSections;
        this.sections=sections;
        this.lessonExists=false;
    }

    public List<Lesson> getLessons(){
        Gson gson = new Gson();
        List<String> lessonsNames = new ArrayList<>();
        List<Lesson> lessons = new ArrayList<>();
        lessonsNames = getLessonsNames(lessonsNames,gson);
        lessons = getLessonsFromJson(lessonsNames,lessons,gson);
        return lessons;
    }

    public List<String> getLessonsNames(List<String> lessonsNames, Gson gson){
        try {
            Reader reader = Files.newBufferedReader(Paths.get("src/Lessons/lessons.json"));
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            lessonsNames = gson.fromJson(reader, type);
        } catch (IOException e) {
            //
        }
        return lessonsNames;
    }

    public void removeLesson(String lessonName){
        List<String> lessonsNames = new ArrayList<>();
        Gson gson = new Gson();
        lessonsNames=getLessonsNames(lessonsNames,gson);
        List<Lesson> lessons = getLessons();
        for(int i=0;i<lessons.size();i++){
            if(Objects.equals(lessons.get(i).getLessonName(),lessonName)){
                lessons.remove(lessons.get(i));
                try {
                    Files.delete(Paths.get("src/Lessons/" + lessonName + ".json"));
                    lessonsNames.remove(lessonName);
                }catch(Exception e){
                    //
                }
            }
        }
        updateFiles(lessons,lessonsNames,gson);
    }

    public void persistLesson(Lesson lesson){
        List<Lesson> lessons = getLessons();
        List<String> lessonsNames = new ArrayList<>();
        Gson gson = new Gson();
        Lesson lessonFound = getLesson(lesson.getLessonName(),gson);
        if(lessonExists) {
            if (!lessonFound.equals(lesson)) {
                lessons.remove(lessonFound);
                lessons.add(lesson);
            }
        }else{
            lessonsNames = getLessonsNames(lessonsNames,gson);
            if(lessonsNames==null){
                lessonsNames=new ArrayList<>();
            }
            lessonsNames.add(lesson.getLessonName());
            lessons.add(lesson);
        }
        updateFiles(lessons,lessonsNames,gson);
    }

    public void updateFiles(List<Lesson> lessons, List<String> lessonsNames,Gson gson){
        updateLessonsNames(lessonsNames,gson);
        for (Lesson lesson : lessons) {
            updateLesson(lesson, gson);
        }
    }

    public void updateLessonsNames(List<String> lessonsNames,Gson gson){
        Path path = Paths.get("src/Lessons/lessons.json");
        try(Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)){
            JsonElement tree = gson.toJsonTree(lessonsNames);
            gson.toJson(tree, writer);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void updateLesson(Lesson lesson,Gson gson){
        Path path = Paths.get("src/Lessons/"+lesson.getLessonName()+".json");
        try(Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)){
            JsonElement tree = gson.toJsonTree(lesson);
            gson.toJson(tree, writer);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public Lesson getLesson(String lessonName,Gson gson) {
        Lesson lesson = new Lesson();
        try {
            Reader reader = Files.newBufferedReader(Paths.get("src/Lessons/"+lessonName+".json"));
            Type type = new TypeToken<Lesson>() {
            }.getType();
            lesson = gson.fromJson(reader, type);
            lessonExists=true;
        } catch (IOException e) {
            lessonExists=false;
        }
        return lesson;
    }

    public List<Lesson> getLessonsFromJson(List<String> lessonsNames,List<Lesson> lessons,Gson gson) {
        if(lessonsNames!=null){
            if (lessonsNames.size() > 0) {
                for (String lessonsName : lessonsNames) {
                    Lesson lesson = getLesson(lessonsName, gson);
                    lessons.add(lesson);
                }
            }
        }
        return lessons;
    }

    public List<String> getAllQuestions() {
        List<String> allQuestions = new ArrayList<>();
        System.out.println("nun sections: "+numSections);
        for(int i=0;i<numSections;i++){
            for(int j=0;j<sections.get(i).getNumQuestions();i++){
                allQuestions.add(sections.get(i).getQuestions().get(j));
            }
        }
        return allQuestions;
    }
    public List<String> getAllAnswers() {
        List<String> allAnswers = new ArrayList<>();
        for(int i=0;i<numSections;i++){
            for(int j=0;j<sections.get(i).getNumQuestions();i++){
                allAnswers.add(sections.get(i).getAnswers().get(j));
            }
        }
        return allAnswers;
    }

    public int getLargestNumQuestions(List<Section> sections){
        int max=0;
        for(int i=0;i<sections.size();i++){
            if(sections.get(i).getNumQuestions()>max){
                max = sections.get(i).getNumQuestions();
            }
        }
        return max;
    }
}
