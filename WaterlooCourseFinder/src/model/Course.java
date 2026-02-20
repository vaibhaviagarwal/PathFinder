package model;

import java.util.Collections;
import java.util.List;

public class Course {
    private String subject;
    private String number;
    private String title;
    private List<String> prereq;
    private List<String> coreq;
    private List<String> antireq;

    // Default constructor needed by Gson
    public Course() {}

    // Getters
    public String getSubject() { return subject; }
    public String getNumber() { return number; }
    public String getTitle() { return title; }
    public List<String> getPrereq() { return prereq == null ? Collections.emptyList() : prereq; }
    public List<String> getCoreq() { return coreq == null ? Collections.emptyList() : coreq; }
    public List<String> getAntireq() { return antireq == null ? Collections.emptyList() : antireq; }
    public String getCode() { return (subject == null ? "" : subject) + (number == null ? "" : number); }
}
