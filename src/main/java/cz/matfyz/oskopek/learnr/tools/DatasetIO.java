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
package cz.matfyz.oskopek.learnr.tools;

import com.thoughtworks.xstream.XStream;
import cz.matfyz.oskopek.learnr.model.Answer;
import cz.matfyz.oskopek.learnr.model.Dataset;
import cz.matfyz.oskopek.learnr.model.Limits;
import cz.matfyz.oskopek.learnr.model.Question;
import cz.matfyz.oskopek.learnr.model.Statistics;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/**
 * A class for static helper methods related to loading/saving and importing/exporting datasets.
 */
public class DatasetIO {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatasetIO.class);

    /**
     * Saves the dataset via XStream.
     * <p/>
     * Used for saving a used dataset. Saves statistics.
     *
     * @param dataset  the dataset to save
     * @param filename the filename into which to save
     * @throws IOException if an error during write occurs
     */
    public static void saveXMLDataset(Dataset dataset, String filename) throws IOException {
        LOGGER.debug("Save dataset to XML: \'{}\'", filename);
        File outFile = new File(filename);
        outFile.createNewFile(); // Ignored return value, checking for overwriting in UI
        FileOutputStream fileOutputStream = new FileOutputStream(outFile);

        XStream xStream = new XStream();
        xStream.toXML(dataset, fileOutputStream);

        fileOutputStream.close();
    }

    /**
     * Loads a dataset via XStream.
     * <p/>
     * Used for loading a used dataset. Loads statistics.
     *
     * @param filename the filename from which to load
     * @return the imported dataset
     */
    public static Dataset loadXMLDataset(String filename) {
        LOGGER.debug("Load dataset from XML: \'{}\'", filename);
        XStream xstream = new XStream();
        return (Dataset) xstream.fromXML(new File(filename));
    }

    /**
     * A manual method of exporting the dataset to a text file.
     * <p/>
     * Used for plain dataset distribution. Does not export statistics of any kind.
     *
     * @param dataset  the dataset to export
     * @param filename the filename into which to export
     * @throws IOException if an error during write occurs
     */
    public static void exportTXTDataset(Dataset dataset, String filename) throws IOException {
        LOGGER.debug("Export dataset to TXT: \'{}\'", filename);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
        pw.printf("PREAMBLE:\n");
        pw.printf("Name: %s\n", dataset.getName());
        pw.printf("Description: %s\n", dataset.getDescription());
        pw.printf("Author: %s\n", dataset.getAuthor());
        pw.printf("CreatedDate: %d\n", dataset.getCreatedDate());
        pw.printf("RepetitionCoef: %d\n", dataset.getQuestionSet().size() + dataset.getFinishedSet().size());
        pw.printf("Limits: %d/%d\n", dataset.getLimits().getDaily(), dataset.getLimits().getSession());
        pw.printf("AnswerCheckType: %s\n", dataset.getAnswerCheckType());
        pw.printf("GoodAnswerPenalty: %d\n", dataset.getGoodAnswerPenalty());
        pw.printf("BadAnswerPenalty: %d\n", dataset.getBadAnswerPenalty());
        pw.printf("QUESTIONS:\n");
        for (Question q : dataset.getQuestionSet()) {
            pw.printf("%s", q.getText()); //skips statistics
            for (Answer a : q.getAnswerList()) {
                pw.printf("; %s", a.getValue());
            }
            pw.printf("\n");
        }
        for (Question q : dataset.getFinishedSet()) {
            pw.printf("%s", q.getText()); //skips statistics
            for (Answer a : q.getAnswerList()) {
                pw.printf("; %s", a.getValue());
            }
            pw.printf("\n");
        }
        pw.close();
    }

    /**
     * A manual method of importing a dataset from a text file.
     * <p/>
     * Used for plain dataset distribution. Does not import statistics of any kind.
     *
     * @param filename the filename from which to import
     * @return the imported dataset
     * @throws IOException if an error during read occurs
     */
    public static Dataset importTXTDataset(String filename) throws IOException {
        LOGGER.debug("Import dataset from TXT: \'{}\'", filename);
        Dataset dataset = new Dataset();
        BufferedReader br = new BufferedReader(new FileReader(filename));

        br.readLine(); // PREAMBLE
        dataset.setName(br.readLine().split(":")[1].trim());
        dataset.setDescription(br.readLine().split(":")[1].trim());
        dataset.setAuthor(br.readLine().split(":")[1].trim());
        dataset.setCreatedDate(Long.parseLong(br.readLine().split(":")[1].trim()));
        int repCoef = Integer.parseInt(br.readLine().split(":")[1].trim());
        String[] limitsStr = br.readLine().split("/");
        Limits limits = new Limits(Integer.parseInt(limitsStr[0].split(":")[1].trim()), Integer.parseInt(limitsStr[1]));
        dataset.setLimits(limits);
        String answerCheckTypeStr = br.readLine().split(":")[1].trim();
        dataset.setAnswerCheckType(Dataset.AnswerCheckType.valueOf(answerCheckTypeStr));
        dataset.setGoodAnswerPenalty(Integer.parseInt(br.readLine().split(":")[1].trim()));
        dataset.setBadAnswerPenalty(Integer.parseInt(br.readLine().split(":")[1].trim()));

        String buffer;
        br.readLine(); // QUESTIONS
        TreeSet<Question> questionSet = new TreeSet<>();
        while ((buffer = br.readLine()) != null) {
            if (StringUtils.isWhitespace(buffer)) continue;
            String[] split = buffer.split(";");
            String text = split[0].trim();

            List<Answer> answerList = new ArrayList<>();
            for (int i = 1; i < split.length; i++) {
                Answer answer = new Answer();
                answer.setValue(split[i].trim());
                answerList.add(answer);
            }
            Question q = new Question(text, new Statistics(), answerList, repCoef);

            LOGGER.debug("Reading question \'{}\'; weight \'{}\'.", q.getText(), q.getWeight());
            if (!questionSet.add(q)) {
                LOGGER.warn("Question \'{}\' already in dataset, adding as an answer.", q.getText());
                Iterator<Question> descIter = questionSet.descendingIterator(); // Descending iterator, because it's probably last
                while (descIter.hasNext()) {
                    Question current = descIter.next();
                    if (current.equals(q)) {
                        current.getAnswerList().addAll(q.getAnswerList());
                        break;
                    }
                }
            }
        }
        dataset.setQuestionSet(questionSet);
        dataset.setFinishedSet(new TreeSet<Question>());

        br.close();
        return dataset;
    }

}
