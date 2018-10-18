import java.util.ArrayList;

public class SMP_Person {
    ArrayList <Integer>PreferenceList;
    ArrayList <Integer>ProposedList;
    int MatchedPartnerIndex;//-1 if unmatched
    int Index;
    
    SMP_Person(ArrayList <Integer>PreferenceListArg, ArrayList <Integer> ProposedListArg, int i)
    {
        PreferenceList = PreferenceListArg;
        ProposedList = ProposedListArg;
        MatchedPartnerIndex = -1;
        Index = i;
    }
}
