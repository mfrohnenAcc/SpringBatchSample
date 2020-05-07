package org.dualstudents.processor;

import org.dualstudents.model.Candidate;
import org.dualstudents.model.Student;
import org.springframework.batch.item.ItemProcessor;

public class CandidateProcessor implements ItemProcessor<Candidate, Candidate> {
    private static final Integer MAX_STUDENTS = 18;
    private Integer currentClass;
    private Integer studentsInCurrentClass;

    public CandidateProcessor() {
        this.currentClass = 1;
        this.studentsInCurrentClass = 0;
    }

    @Override
    public Candidate process(Candidate input) {
        Candidate output;
        if ("passed".equalsIgnoreCase(input.getPassed())) {
            output = new Student(input);
            ((Student) output).setClassName(getClassForStudent());
        } else {
            output = input;
        }
        return output;
    }

    private String getClassForStudent() {
        String currentClass = "Class " + this.currentClass;
        this.studentsInCurrentClass++;
        if (MAX_STUDENTS.equals(this.studentsInCurrentClass)) {
            this.currentClass++;
            this.studentsInCurrentClass = 0;
        }
        return currentClass;
    }
}
