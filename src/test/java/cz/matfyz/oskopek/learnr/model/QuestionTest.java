/*
 * #%L
 * Learnr
 * %%
 * Copyright (C) 2014 Ondrej Skopek
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */
package cz.matfyz.oskopek.learnr.model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
        q2.setWeight(4);
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
        q2.setWeight(4);
        assertEquals(q1.hashCode(), q2.hashCode());
        q1.setText("NamE");
        assertNotEquals(q1.hashCode(), q2.hashCode());
    }

    @Test
    public void compareToTest() {
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
        assertEquals(q1.compareTo(q2), q2.compareTo(q1));
        assertEquals(0, q1.compareTo(q2));
        q2.setWeight(4);
        assertEquals(-1, q2.compareTo(q1));
        assertEquals(+1, q1.compareTo(q2));
        q1.setText("NamE");
        assertEquals(-1, q2.compareTo(q1));
        assertEquals(+1, q1.compareTo(q2));
    }

}
