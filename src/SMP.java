import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
    
    static int prefSize;
    
    //ArrayList <SMP_Person>Men;
    ArrayList <SMP_Person>Women;

    public static void main(String[] args) {
        ArrayList <SMP_Person>Men = new ArrayList();
        ArrayList <SMP_Person>Women = new ArrayList();
        
        ArrayList <SMP_Person>OptGroup = new ArrayList();
        ArrayList <SMP_Person>PesGroup = new ArrayList();

        ArgHandler(args, Men, Women);

        if(OP == OptimalPreference.MEN_OPTIMAL)
        {
            OptGroup = Men;
            PesGroup = Women;
        }
        else
        {
            OptGroup = Women;
            PesGroup = Men;
        }
        
        System.out.println(OptGroup.get(0).ProposedList.size());
        System.out.println(Men.get(0).ProposedList.size());
        GSAlgo(OptGroup, PesGroup);
        
        OptGroup.forEach((n) -> System.out.println("("+(n.Index+1)+","+(n.MatchedPartnerIndex+1)+")"));
        // TODO Format Output Response
        // TODO Print Output Response
    }

    private static void GSAlgo(ArrayList<SMP_Person> optGroup, ArrayList<SMP_Person> pesGroup) {
        Stack <SMP_Person> FreeOptPersonStack = new Stack();

        for(int i = 0; i < prefSize; i++)
        {
            FreeOptPersonStack.push(optGroup.get(i));
        }
        while(!FreeOptPersonStack.empty())
        {
            SMP_Person PrefPerson = FreeOptPersonStack.pop();

            for(int i = 0; i < prefSize; i++)
            {
                int PesPersonIndex = PrefPerson.PreferenceList.get(i) - 1;
                SMP_Person PesPerson = pesGroup.get(PesPersonIndex);
                int ProposedTo = PrefPerson.ProposedList.get(PesPersonIndex);
                if(0 == ProposedTo)
                {
                    int PesPersonMatchedIndex = PesPerson.MatchedPartnerIndex;//0 based
                    if(PesPersonMatchedIndex == -1)
                    {
                        //The other person is free!
                        //Match
                        pesGroup.get(PesPersonIndex).MatchedPartnerIndex = PrefPerson.Index;
                        optGroup.get(PrefPerson.Index).MatchedPartnerIndex = PesPerson.Index;
                        optGroup.get(PrefPerson.Index).ProposedList.set(PesPersonIndex, 1);
                        break;
                    }
                    else if(PesPerson.PreferenceList.indexOf(PesPersonMatchedIndex + 1) > PesPerson.PreferenceList.indexOf(PrefPerson.Index + 1))
                    {
                        pesGroup.get(PesPersonIndex).MatchedPartnerIndex = PrefPerson.Index;
                        optGroup.get(PrefPerson.Index).MatchedPartnerIndex = PesPerson.Index;
                        optGroup.get(PrefPerson.Index).ProposedList.set(PesPersonIndex, 1);
                        optGroup.get(PesPersonMatchedIndex).MatchedPartnerIndex = -1;
                        FreeOptPersonStack.push(optGroup.get(PesPersonMatchedIndex));
                        break;
                    }
                }
            }
        }
    }

    private static void ArgHandler(String[] args, ArrayList<SMP_Person> men, ArrayList<SMP_Person> women2) {
        
        int startingMenIndex = 1;
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
            prefSize = Integer.parseInt(csvRecords.get(0).get(0));
            for(int i = 0; i < prefSize; i++)
            {
                ArrayList MenPrefList = new <Integer>ArrayList();
                ArrayList WomPrefList = new <Integer>ArrayList();
                ArrayList ProposedList = new <Integer>ArrayList();
                System.out.println(csvRecords.get(startingMenIndex + i));
                for(int j = 0; j < prefSize; j++)
                {
                    MenPrefList.add(j, Integer.parseInt(csvRecords.get(startingMenIndex + i).get(j)));
                    WomPrefList.add(j, Integer.parseInt(csvRecords.get(startingMenIndex + prefSize + i).get(j)));
                    ProposedList.add(0);
                }
                men.add(i, new SMP_Person(MenPrefList, ProposedList, i));
                women2.add(i, new SMP_Person(WomPrefList, ProposedList, i));
            }
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
