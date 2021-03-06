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
 * A basic test of the equals, hashCode, and compareTo implementations in {@link cz.matfyz.oskopek.learnr.model.Question}.
 */
public class QuestionTest {

    @Test
    public void equalsTest() {
        Question q1 = new Question("Name", new Statistics(), new ArrayList<Answer>(), 5);
        Question q2 = new Question("Name", new Statistics(), new ArrayList<Answer>(), 5);
        assertEquals(q1, q2);
        q2.setWeight(4);
        assertEquals(q1, q2);
        q1 = new Question("NamE", new Statistics(), new ArrayList<Answer>(), 5);
        assertNotEquals(q1, q2);
    }

    @Test
    public void hashCodeTest() {
        Question q1 = new Question("Name", new Statistics(), new ArrayList<Answer>(), 5);
        Question q2 = new Question("Name", new Statistics(), new ArrayList<Answer>(), 5);
        assertEquals(q1.hashCode(), q2.hashCode());
        q2.setWeight(4);
        assertEquals(q1.hashCode(), q2.hashCode());
        q1 = new Question("NamE", new Statistics(), new ArrayList<Answer>(), 5);
        assertNotEquals(q1.hashCode(), q2.hashCode());
    }

    @Test
    public void compareToTest() {
        Question q1 = new Question("Name", new Statistics(), new ArrayList<Answer>(), 5);
        Question q2 = new Question("Name", new Statistics(), new ArrayList<Answer>(), 5);
        assertEquals(q1.compareTo(q2), q2.compareTo(q1));
        assertEquals(0, q1.compareTo(q2));
        q2.setWeight(4);
        assertEquals(-1, q2.compareTo(q1));
        assertEquals(+1, q1.compareTo(q2));
        q1 = new Question("NamE", new Statistics(), new ArrayList<Answer>(), 5);
        assertEquals(-1, q2.compareTo(q1));
        assertEquals(+1, q1.compareTo(q2));
    }

}
