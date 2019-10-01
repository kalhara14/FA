package FA;
import java.util.Scanner;
/*
    fixed partition memory management program
    FA assignment 2019 regular intake
    @author: H.H.D Kalhara
    IT number: IT18139440
 */

public class Main {


    public static void main(String[] args) {
        //variables
        memoryPartition[] Memory = new memoryPartition[10]; //maximum no of cases 10
        Scanner sc = new Scanner(System.in);
        int partitions;
        int programs;
        int counter = 0; // no of cases currently saved


        System.out.println("\nMemory management program");
        System.out.println("-----------------------------------------\n");

        //take input line 1
         for(int i =0; i < 10; i++) {
             //process input line 1
             if (sc.hasNextInt()) // validate input
             {
                 partitions = sc.nextInt();
                 programs = sc.nextInt();

                 if (partitions <= 10 && partitions > 0 && programs > 0 && programs <= 50) // validate input range
                 {
                     Memory[i] = new memoryPartition(i, partitions, programs); // creation of a case
                     Memory[i].setPartitionSize();    //take input line 2 and save (memory regions sizes)
                     Memory[i].setRunningTimes();     //take program params memory and space required
                     Memory[i].assignPartition1();    //assign memory partition to each program
                     counter++;   // no of cases saved and processed

                 } else if (partitions == 0 && programs == 0)
                     break;
                 else {
                     System.out.println("invalid inputs.");
                     continue;
                 }


             }
         }
         sc.close();
        for(int i =0; i < counter; i++)
        {
            Memory[i].displayMemoryDetails();      // display output
        }

        System.out.println("-----------------------------------------\n");
        //end of the program
    }
}
