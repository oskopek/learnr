package cz.matfyz.oskopek.learnr.tools;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import cz.matfyz.oskopek.learnr.model.Answer;
import cz.matfyz.oskopek.learnr.model.Dataset;
import cz.matfyz.oskopek.learnr.model.Limits;
import cz.matfyz.oskopek.learnr.model.Question;
import cz.matfyz.oskopek.learnr.model.Statistics;

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

/**
 * Created by oskopek on 11/29/14.
 */
public class DatasetIO {

    // TODO create nonthrowing wrapper functions and privatize these
    // TODO Dataset limits aren't getting saved/exported

    public static void saveDataset(Dataset dataset, String filename) throws IOException {
        File outFile = new File(filename);
        outFile.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(outFile);

        XStream xStream = new XStream();
        xStream.toXML(dataset, fileOutputStream);

        fileOutputStream.close();
    }

    public static Dataset openDataset(String filename) throws IOException {
        XStream xstream = new XStream();
        return (Dataset) xstream.fromXML(new File(filename));
    }

    public static void exportJSONDataset(Dataset dataset, String filename) throws IOException {
        File outFile = new File(filename);
        outFile.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(outFile);

        XStream xStream = new XStream(new JsonHierarchicalStreamDriver());
        xStream.toXML(dataset, fileOutputStream);

        fileOutputStream.close();
    }

    public static Dataset importJSONDataset(String filename) throws IOException {
        XStream xstream = new XStream(new JettisonMappedXmlDriver());
        return (Dataset) xstream.fromXML(new File(filename));
    }

    public static void exportTXTDataset(Dataset dataset, String filename) throws IOException {
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
        pw.printf("PREAMBLE:\n");
        pw.printf("Name: %s", dataset.getName());
        pw.printf("Description: %s\n", dataset.getDescription());
        pw.printf("Author: %s\n", dataset.getAuthor());
        pw.printf("CreatedDate: %d\n", dataset.getCreatedDate());
        pw.printf("Limits: %d/%d/%d\n", dataset.getLimits().getDaily(), dataset.getLimits().getSession(), dataset.getLimits().getSessionTimeout());
        pw.printf("QUESTIONS:\n");
        for (Question q : dataset.getQuestionList()) {
            pw.printf("%s; %s; %s", q.getName(), q.getDescription(), q.getAnswerCheckType()); //skips statistics
            for (Answer a : q.getAnswerList()) {
                pw.printf("; %s", a.getValue());
            }
            pw.printf("\n");
        }
    }

    public static Dataset importTXTDataset(String filename) throws IOException {
        Dataset dataset = new Dataset();
        BufferedReader br = new BufferedReader(new FileReader(filename));

        br.readLine(); // PREAMBLE
        dataset.setName(br.readLine());
        dataset.setDescription(br.readLine());
        dataset.setAuthor(br.readLine());
        dataset.setCreatedDate(Long.parseLong(br.readLine().split(" ")[1]));
        String[] limitsStr = br.readLine().split("/");
        Limits limits = new Limits();
        limits.setDaily(Integer.parseInt(limitsStr[0].split(" ")[1]));
        limits.setSession(Integer.parseInt(limitsStr[1]));
        limits.setSessionTimeout(Integer.parseInt(limitsStr[2]));

        String buffer;
        br.readLine(); // QUESTIONS
        List<Question> questionList = new ArrayList<>();
        while ((buffer = br.readLine()) != null) {
            String[] split = buffer.split(";");
            Question q = new Question();
            q.setName(split[0].trim());
            q.setDescription(split[1].trim());
            q.setAnswerCheckType(Question.AnswerCheckType.valueOf(split[2].trim()));
            q.setStatistics(new Statistics());

            List<Answer> answerList = new ArrayList<>();
            for(int i = 3; i < split.length; i++) {
                Answer answer = new Answer();
                answer.setValue(split[i].trim());
                answerList.add(answer);
            }
            q.setAnswerList(answerList);

            questionList.add(q);
        }
        dataset.setQuestionList(questionList);

        br.close();
        return dataset;
    }

}
