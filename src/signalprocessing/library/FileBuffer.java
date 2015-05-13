package signalprocessing.library;

import signalpocessing.model.Complex;
import signalpocessing.model.Detector;
import signalpocessing.model.Signal;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Andrea on 11/05/15.
 */
public class FileBuffer {

    public static List<List<?>> readComplexFromPath(String path){
        List<List<?>> output = new LinkedList<>();
        File fileFromPath = new File(path);
        if (fileFromPath.isDirectory()){
            for (File current : fileFromPath.listFiles()){
                List<?> temp;
                if (current.isDirectory()) {
                    temp = readComplexFromPath(current.getPath());
                }
                else {
                    temp = readComplexFromFile(current);
                }
                if (temp.size() != 0)
                    output.add(temp);
            }
        }
        else {
            List<Complex> temp = readComplexFromFile(fileFromPath);
            if(temp.size() != 0)
                output.add(temp);
        }
        return output;
    }

    private static List<Complex> readComplexFromFile(File file){
        List<Complex> output = new LinkedList<>();
        if (file.getName().charAt(0) == '.')
            return output;
        BufferedReader in;
        List<String> rows = new LinkedList<>();
        try{
            in = new BufferedReader(new FileReader(file.getPath()));
            String currentLine = "";
            while (currentLine != null){
                currentLine = in.readLine();
                rows.add(currentLine);
            }
        }catch (FileNotFoundException f){
            System.out.println("Il file non e' stato trovato");
        }
        catch (IOException io){
            System.out.println("Si e' verificato un errore nella lettura del file");
        }
        finally {
            if (!rows.isEmpty())
                output = parseComplexFromList(rows);
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
        List<?> lista;
        lista =readComplexFromPath("/Users/Andrea/Downloads/Sequenze_SDR_2015/Sequenza_1");
        System.out.println(lista.size());
        List<Complex> temp = (List<Complex>)lista.get(0);
        Signal s = new Signal(temp);
        System.out.println(s.getValues().length);
        Detector detector = new Detector();
        double[] energyVector = detector.getEnergyVector(s);
//        System.out.println("Vettore di energia");
//        for (double d : energyVector){
//            System.out.println(d);
//        }
        System.out.println(detector.calculateSoglia(s));
    }
}
