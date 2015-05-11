package signalprocessing.library;

import signalpocessing.model.Complex;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Andrea on 11/05/15.
 */
public class FileBuffer {
    public static List<Complex> readComplexFromFile(String path){
        BufferedReader in;
        List<String> rows = new LinkedList<>();
        List<Complex> outputComplex = new LinkedList<>();
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
}
