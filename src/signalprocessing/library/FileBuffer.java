package signalprocessing.library;

import signalprocessing.model.*;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Andrea on 11/05/15.
 */
public class FileBuffer {


    //Metodo principale per il parsing di Segnali da un file, può prendere come input sia cartelle che file singoli, il
    //risultato sarà una lista che rispecchia la gerarchia delle cartelle es: Root/cartella1/output1,output2,output3 ->
    //->List-List-Signal
    //          |-Signal
    //          |-Signal
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

    // metodo di utility per il parsing di un file (caso base), ignora i file di sistema derivati dal Filesystem
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

    //metodo di utility per convertire le righe del file in List<Complex>
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


    //Metodo main per il test dei metodi, contiene un timer per visualizzare il tempo impiegato
    public static void main (String[] args) {
        long timeStart = System.currentTimeMillis();
        List<?> lista;
        lista = readComplexFromPath("/Users/Luca/Downloads/Sequenze_SDR_2015/Sequenza_3");
        System.out.println(lista.size());
        List<Complex> temp = (List<Complex>) lista.get(2);
        Signal s = new Signal(temp);
        System.out.println(s.size());
        Detector detector = new Detector();
        Noise n = new Noise(detector.calcolaSNR(s),s.size());
        System.out.println("SNR: "+detector.calcolaSNR(s));
        System.out.println("Detection percentage: "+Hipotesi1.detectionPercentage(s));
        long timerEnd = System.currentTimeMillis();
        System.out.println("Executed in: "+ ((timerEnd-timeStart)/1000)+ " secondi");
        }
    }
