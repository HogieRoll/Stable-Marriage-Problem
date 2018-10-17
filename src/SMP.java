import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class SMP {

    enum OptimalPreference
    {
        MEN_OPTIMAL,
        WOMEN_OPTIMAL
    }
    
    static int expectedArgCount = 2;
    
    static OptimalPreference OP;
    
    ArrayList <SMP_Person>Men;
    ArrayList <SMP_Person>Women;

    public static void main(String[] args) {
        // TODO Parse args for File and M/W Optimal Preference
        ArgHandler(args);
        // TODO Read Input File and Parse into Structure
        //FormatArguments();
        // TODO Use Input File and M/W Optimal Preference to find Stable Matching
        // TODO Format Output Response
        // TODO Print Output Response
    }

    private static void ArgHandler(String[] args) {
        // TODO Auto-generated method stub
        if(args.length != expectedArgCount)
        {
            System.exit(0);
        }
        try{
            // Create an object of filereader 
            // class with CSV file as a parameter. 
            FileReader filereader = new FileReader(args[0]);
            
            // create csvReader object passing 
            // file reader as a parameter 
            //initialize FileReader object
            CSVParser csvFileParser = new CSVParser(filereader, CSVFormat.newFormat(' '));
            //Get a list of CSV file records
            List<CSVRecord> csvRecords = csvFileParser.getRecords();
            
            System.out.println(csvRecords.toString());
            csvFileParser.close();
        }
        catch (Exception e) {
            System.out.println("Error in CsvFileReader !!!");
            e.printStackTrace();
        }

        if(args[1].equals("m")){
            OP = OptimalPreference.MEN_OPTIMAL;
        }
        else if(args[1].equals("w")){
            OP = OptimalPreference.WOMEN_OPTIMAL;
        }
        else{
            System.exit(0);
        }
    }

}
