package cz.matfyz.oskopek.learnr.tools;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import cz.matfyz.oskopek.learnr.model.Dataset;
import cz.matfyz.oskopek.learnr.model.Limits;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by oskopek on 11/29/14.
 */
public class DatasetExporter {

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
        XStream xstream = new XStream(new JsonHierarchicalStreamDriver());
        return (Dataset) xstream.fromXML(new File(filename));
    }

    public static void exportTXTDataset(Dataset dataset, String filename) throws IOException {

    }

    public static Dataset importTXTDataset(String filename) throws IOException {
        Dataset dataset = new Dataset();
        BufferedReader br = new BufferedReader(new FileReader(filename));

        String dummy;
        br.readLine(); // PREAMBLE
        dataset.setName(br.readLine());
        dataset.setDescription(br.readLine());
        dataset.setAuthor(br.readLine());
        dataset.setCreatedDate(Long.parseLong(br.readLine()));
        String[] limitsStr = br.readLine().split("/");
        Limits limits = new Limits();
        limits.setDaily(Integer.parseInt(limitsStr[0]));
        limits.setSession(Integer.parseInt(limitsStr[1]));
        limits.setSessionTimeout(Integer.parseInt(limitsStr[2]));
        br.readLine(); // QUESTIONS
        


        br.close();
        return dataset;
    }

}
