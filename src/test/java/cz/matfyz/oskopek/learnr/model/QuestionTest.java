package cz.matfyz.oskopek.learnr.model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by oskopek on 12/23/14.
 */
public class QuestionTest {

    @Test
    public void equalsTest() {
        Question q1 = new Question();
        q1.setText("Name");
        q1.setStatistics(new Statistics());
        q1.setWeight(5);
        q1.setAnswerList(new ArrayList<Answer>());
        Question q2 = new Question();
        q2.setText("Name");
        q2.setStatistics(new Statistics());
        q2.setWeight(5);
        q2.setAnswerList(new ArrayList<Answer>());
        assertEquals(q1, q2);
        q2.getAnswerList().add(new Answer());
        assertNotEquals(q1, q2);
        q1.getAnswerList().add(new Answer());
        assertEquals(q1, q2);
        q1.setText("NamE");
        assertNotEquals(q1, q2);
    }

    @Test
    public void hashCodeTest() {
        Question q1 = new Question();
        q1.setText("Name");
        q1.setStatistics(new Statistics());
        q1.setWeight(5);
        q1.setAnswerList(new ArrayList<Answer>());
        Question q2 = new Question();
        q2.setText("Name");
        q2.setStatistics(new Statistics());
        q2.setWeight(5);
        q2.setAnswerList(new ArrayList<Answer>());
        assertEquals(q1.hashCode(), q2.hashCode());
        q2.getAnswerList().add(new Answer());
        assertNotEquals(q1.hashCode(), q2.hashCode());
    }

}
