package cz.matfyz.oskopek.learnr.tools;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
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
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

/**
 * Created by oskopek on 11/29/14.
 */
public class DatasetIO {

    final static private Logger LOGGER = LoggerFactory.getLogger(DatasetIO.class);

    public static void exportXMLDataset(Dataset dataset, String filename) throws IOException {
        LOGGER.debug("Export dataset to XML: \'{}\'", filename);
        File outFile = new File(filename);
        outFile.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(outFile);

        XStream xStream = new XStream();
        xStream.toXML(dataset, fileOutputStream);

        fileOutputStream.close();
    }

    public static Dataset importXMLDataset(String filename) throws IOException {
        LOGGER.debug("Import dataset from XML: \'{}\'", filename);
        XStream xstream = new XStream();
        return (Dataset) xstream.fromXML(new File(filename));
    }

    @Deprecated
    public static void exportJSONDataset(Dataset dataset, String filename) throws IOException {
        LOGGER.debug("Export dataset to JSON: \'{}\'", filename);
        File outFile = new File(filename);
        outFile.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(outFile);

        XStream xStream = new XStream(new JettisonMappedXmlDriver());
        xStream.toXML(dataset, fileOutputStream);

        fileOutputStream.close();
    }

    @Deprecated
    public static Dataset importJSONDataset(String filename) throws IOException {
        LOGGER.debug("Import dataset from JSON: \'{}\'", filename);
        XStream xstream = new XStream(new JettisonMappedXmlDriver());
        return (Dataset) xstream.fromXML(new File(filename));
    }

    public static void exportTXTDataset(Dataset dataset, String filename) throws IOException {
        LOGGER.debug("Export dataset to TXT: \'{}\'", filename);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
        pw.printf("PREAMBLE:\n");
        pw.printf("Name: %s\n", dataset.getName());
        pw.printf("Description: %s\n", dataset.getDescription());
        pw.printf("Author: %s\n", dataset.getAuthor());
        pw.printf("CreatedDate: %d\n", dataset.getCreatedDate());
        pw.printf("RepetitionCoef: %d\n", dataset.getQuestionSet().size()+dataset.getFinishedSet().size());
        pw.printf("Limits: %d/%d/%d\n", dataset.getLimits().getDaily(), dataset.getLimits().getSession(), dataset.getLimits().getSessionTimeout());
        pw.printf("QUESTIONS:\n");
        for (Question q : dataset.getQuestionSet()) {
            pw.printf("%s; %s; %s", q.getName(), q.getDescription(), q.getAnswerCheckType()); //skips statistics
            for (Answer a : q.getAnswerList()) {
                pw.printf("; %s", a.getValue());
            }
            pw.printf("\n");
        }
        for (Question q : dataset.getFinishedSet()) {
            pw.printf("%s; %s; %s", q.getName(), q.getDescription(), q.getAnswerCheckType()); //skips statistics
            for (Answer a : q.getAnswerList()) {
                pw.printf("; %s", a.getValue());
            }
            pw.printf("\n");
        }
        pw.close();
    }

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
        Limits limits = new Limits();
        limits.setDaily(Integer.parseInt(limitsStr[0].split(":")[1].trim()));
        limits.setSession(Integer.parseInt(limitsStr[1]));
        limits.setSessionTimeout(Integer.parseInt(limitsStr[2]));
        dataset.setLimits(limits);

        String buffer;
        br.readLine(); // QUESTIONS
        TreeSet<Question> questionSet = new TreeSet<>();
        while ((buffer = br.readLine()) != null) {
            if (StringUtils.isWhitespace(buffer)) continue;
            String[] split = buffer.split(";");
            Question q = new Question();
            q.setName(split[0].trim());
            q.setDescription(split[1].trim());
            q.setAnswerCheckType(Question.AnswerCheckType.valueOf(split[2].trim()));
            q.setStatistics(new Statistics());
            q.setWeight(repCoef);

            List<Answer> answerList = new ArrayList<>();
            for(int i = 3; i < split.length; i++) {
                Answer answer = new Answer();
                answer.setValue(split[i].trim());
                answerList.add(answer);
            }
            q.setAnswerList(answerList);

            LOGGER.debug("Reading question \'{}\'; weight \'{}\'.", q.getName(), q.getWeight());
            if(!questionSet.add(q)) LOGGER.error("Question \'{}\' already in dataset!", q.getName());
        }
        dataset.setQuestionSet(questionSet);
        dataset.setFinishedSet(new TreeSet<Question>());

        br.close();
        return dataset;
    }

    public static String convertNanosToHMS(long nanoTime) {
        long millis = nanoTime/1_000_000l;
        return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                            TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                            TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }

}
