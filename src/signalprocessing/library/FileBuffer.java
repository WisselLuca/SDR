package signalprocessing.library;

import signalpocessing.model.Complex;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by  on 11/05/15.
 */
public class FileBuffer {
    public static List<Complex> readComplexFromFile(String path){
        File fileFromPath = new File(path);
        BufferedReader in;
        List<String> rows = new LinkedList<>();
        List<Complex> outputComplex = new LinkedList<>();
        if (fileFromPath.isDirectory())
            return readComplexFromDirectory(fileFromPath);
        if (fileFromPath.getName().charAt(0) == '.')
            return outputComplex;
        try {
            in = new BufferedReader(new FileReader(path));
            String currentLine = "";
            while (currentLine != null){
                currentLine = in.readLine();
                rows.add(currentLine);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException c) {
            System.out.println(c);
        }
        finally {
            if(!rows.isEmpty()){
                outputComplex = parseComplexFromList(rows);
            }
        }
        return outputComplex;
    }

    private static List<Complex> readComplexFromDirectory(File directory){
        List<Complex> output = new LinkedList<>();
        for (File fileInDir : directory.listFiles()){
            output.addAll(readComplexFromFile(fileInDir.getPath()));
        }
        return output;
    }

    private static List<Complex> parseComplexFromList(List<String> list){
        List<Complex> output = new LinkedList<>();
        for (String row : list){
            if (row != null&&!row.isEmpty()){
                String[] singleComplex;
                singleComplex = row.split("\t");
                Complex temp = new Complex(new Double(singleComplex[0]),new Double(singleComplex[1]));
                output.add(temp);
            }
        }
        return output;
    }

    public static void main (String[] args){
        List<Complex> lista;
        lista =readComplexFromFile("/Users/Andrea/Downloads/Sequenze_SDR_2015/Sequenza_1");
        System.out.print(lista.size());
    }
}
