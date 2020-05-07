package org.dualstudents.writer;

import org.dualstudents.model.Candidate;
import org.dualstudents.model.Student;
import org.springframework.batch.item.ItemWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CandidateWriter implements ItemWriter<Candidate> {

    private FileWriter rejects;
    private FileWriter students;

    public CandidateWriter() {
        try {
            rejects = new FileWriter("./results/Rejects.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            students = new FileWriter("./results/students.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(List<? extends Candidate> list) throws Exception {
        for (Candidate can : list) {
            if (can instanceof Student) {
                writeStudent((Student) can);
            } else {
                writeReject(can);
            }
        }
        rejects.flush();
        students.flush();
    }

    private void writeStudent(Student can) {
        String text = "We are happy to announce that " + can.getName() + " from " + can.getHome() + " will be joining Starfleet Academy as memeber of " + can.getClassName() + ".\n";
        try {
            students.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeReject(Candidate can) {
        String text = "Candidate " + can.getName() + " has failed his exam and will never be a student at Starfleet Academy. All of " + can.getHome() + " shall be ashamed.\n";
        try {
            rejects.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

