package simple.course;

import java.util.SplittableRandom;

public class Phrase {
    private int id;
    private String text;
    private String date;
    private String teacher;
    private String lesson;
    private int author_id;

    public Phrase(int id, String text, String date, String teacher, String lesson, int author_id){
        this.id = id;
        this.text = text;
        this.date = date;
        this.teacher = teacher;
        this.lesson = lesson;
        this.author_id = author_id;
    }

    public Phrase(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }
}


