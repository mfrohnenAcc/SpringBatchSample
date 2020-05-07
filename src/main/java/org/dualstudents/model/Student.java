package org.dualstudents.model;

public class Student extends Candidate {
    private String className;

    public Student() {
    }

    public Student(Candidate input) {
        this.setID(input.getID());
        this.setName(input.getName());
        this.setHome(input.getHome());
        this.setPassed(input.getPassed());
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
