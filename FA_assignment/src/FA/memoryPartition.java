package FA;
import java.util.Scanner;

public class memoryPartition {

    //variables
    private int memoryID;
    private int noOfPartitions;
    private int noOfPrograms;
    private int maxSize = 0;
    private float AvgTurnAroundTime = 0;
    private int[] partitionSize;

    private programDetails[] Program;
    private Scanner sc1 = new Scanner(System.in);

    //constructor to save input line 1 details
    public memoryPartition(int id, int no, int programs) {
        memoryID = id;
        noOfPartitions = no;
        noOfPrograms = programs;
        partitionSize = new int[noOfPartitions]; // create partitions
        Program = new programDetails[noOfPrograms];     //create program objects
    }

    //take input line 2 and save
    public void setPartitionSize() {
        if (sc1.hasNextInt()) // validate input
        {
            for (int i = 0; i < noOfPartitions; i++) {
                int size = 0;
                size = sc1.nextInt();
                if (size > maxSize) {
                    maxSize = size;
                }
                if (size > 0) {
                    partitionSize[i] = size;
                } else {
                    partitionSize[i] = 0;
                }
            }
        }
    }

    // take program spaces and times
    public void setRunningTimes() {
        for (int i = 0; i < noOfPrograms; i++) {
            if (sc1.hasNextInt()) //validate inputs
            {
                int k = 0; // no of instances
                k = sc1.nextInt();
                if (k <= 10 && k > 0) // validate input
                {
                    Program[i] = new programDetails(i, k, noOfPartitions); // create a program object with program id and no of instances

                    //get values of instances
                    for (int j = 0; j < k; j++) {
                        int space = 0;
                        int time = 0;
                        space = sc1.nextInt();
                        time = sc1.nextInt();
                        if (space <= maxSize && space > 0 && time > 0) //validate input
                        {
                            Program[i].setSpaceTime(j, time, space); // save instance values
                        }
                    }

                }
                // sort the time to get smallest run time of the program
                Program[i].sortSpaceTimeArray();
            }
        }

    }

    // calc Average turn around time
    public void calcAvgTurnAroundTimeOfAProgram() {
        for (int i = 0; i < noOfPrograms; i++) {
            AvgTurnAroundTime = AvgTurnAroundTime + Program[i].getEndTime();
        }
        AvgTurnAroundTime = AvgTurnAroundTime / noOfPrograms;
    }

    // assign memory regions to each program
  /*  public void assignPartition()
    {
        int location = -1; // memory location
        int[] TATime = new int[noOfPartitions]; // turn around time in each partition

        for(int i = 0; i < noOfPartitions; i++)
        {
            TATime[i] = 0;
        }
        int[] array = new int[noOfPrograms];
        //unorganized program array
        for(int j = 0; j < noOfPrograms ; j++)
        {
            array[j] = j;
        }

        //selection sort to sort the order of programs according to run time
        for (int a = 0; a < noOfPrograms - 1; a++)
        {
            int index = a;
            for (int b = a + 1; b < noOfPrograms; b++){
                int c = array[index];
                int d = array[b];
                if (Program[d].getTime() < Program[c].getTime()){
                    index = b; //get the smallest run time program
                }
            }
            int temp = array[index];
            array[index] = array[a];
            array[a] = temp;

        }


        for(int k = 0; k < noOfPrograms; k++) {
            int smallest = 10000;
            int e = array[k]; // get program id from above sorted array
            int temp = -1;
            boolean[] hit = new boolean[noOfPartitions]; // possible memory locations
            int i = 0;

            //check possible memory partitions
            for(i = 0; i < noOfPartitions; i++)
            {
                hit[i] = false;
                if (Program[e].getSpace() <= partitionSize[i]) {
                        if(TATime[i] == 0) { // check whether no program assign to partition
                            hit[i] = true;
                            temp = i;
                            TATime[i] += Program[e].getTime();
                            break;
                        }
                        else {
                            hit[i] = true;
                            TATime[i] += Program[e].getTime();
                        }
                    }

            }
            if(temp != -1) // first program in the partition
            {
                location = temp;
            }
            else{
                // determine least turn around memory partition
                for(int q = 0; q < i; q++)
                {
                    if(hit[q])
                    {
                        if( TATime[q] <= smallest) {
                            smallest = TATime[q];
                            location = q;
                        }
                    }
                }
            }

            for(int m = 0; m < noOfPartitions; m++ )
            {
                if(m != location && hit[m])
                {
                    TATime[m] -= Program[e].getTime(); // rest unused turn around time of other partitions
                }
            }
            // assign memory region to program and start time of the program
            Program[e].setMemoryLoc(location+1);
            Program[e].setStartTime(TATime[location]);
        }

    }

*/

    // assign memory regions to each program
    public void assignPartition1() {
        int location = -1; // memory location
        int[] TATime = new int[noOfPartitions]; // turn around time in each partition
        int[] TATime1 = new int[noOfPartitions]; // turn around time after assigning a program
        int[] counter = new int[noOfPartitions]; // counter for possible memory locations array
        int[] counter1 = new int[noOfPartitions]; // counter for memory region array to count no of programs
        int maxLocs = -1; // maximum possible locations
        // possible memory locations array possibleMemoryLoc[number of possibilities][program id]
        int[][] possibleMemoryLoc = new int[noOfPartitions][noOfPrograms];
        // array for programs in a memory region regionProgram[region no][program id]
        int[][] regionProgram = new int[noOfPartitions][noOfPrograms];


        for (int i = 0; i < noOfPartitions; i++) {
            TATime[i] = 0;
            TATime1[i] = 0;
            counter[i] = 0;
            counter1[i] = 0;
        }


        for (int k = 0; k < noOfPrograms; k++) {

            int i = 0;
            int locs; // number of possible locations

            //check possible memory partitions
            for (i = 0; i < noOfPartitions; i++) {

                if (Program[k].getSpace() <= partitionSize[i]) {
                    Program[k].setPossibleLoc(i); // set possible memory location number and increase number of possible locations by 1
                }

            }

            locs = Program[k].getNumberOfMemoryLocs() - 1; // get number of possible locations
            int temp1 = counter[locs]; // counter for possible memory locations array
            possibleMemoryLoc[locs][temp1] = k;  // assign program id to the possible locations array
            counter[locs]++;

            if (maxLocs <= locs) // determine maximum number of possible locations
            {
                maxLocs = locs + 1;
            }

        }
        //sort run time with possible locations
        for (int i = 0; i < maxLocs; i++) {
            //selection sort to sort run times descending order
            for (int j = 0; j < counter[i] - 1; j++) {
                int index = j;
                for (int b = j + 1; b < counter[i]; b++) {
                    int c = possibleMemoryLoc[i][index];
                    int d = possibleMemoryLoc[i][b];
                    if (Program[d].getTime() > Program[c].getTime()) {
                        index = b; //get the largest run time program
                    }
                }
                int temp = possibleMemoryLoc[i][index];
                possibleMemoryLoc[i][index] = possibleMemoryLoc[i][j];
                possibleMemoryLoc[i][j] = temp;

            }
        }
        // get region with minimum turn around time and assign memory region to the program
        for (int i = 0; i < maxLocs; i++) {
            for (int j = 0; j < counter[i]; j++) {
                int programID = possibleMemoryLoc[i][j];
                int memoryLoc;

                // get region with minimum turn around time
                for (int k = 0; k <= i; k++) {
                    memoryLoc = Program[programID].getPossibleLoc(k);
                    if (k == 0) {
                        location = memoryLoc;
                    }
                    if (TATime[memoryLoc] <= TATime[location]) {
                        location = memoryLoc;
                    }
                }

                Program[programID].setMemoryLoc(location + 1); // location assigment
                TATime[location] += Program[programID].getTime(); // increase turn around time after assigning
                regionProgram[location][counter1[location]++] = programID; // enter program to region program array to sort the run time

            }
        }
        //sort the runing order of programs
        for (int i = 0; i < noOfPartitions; i++) {
            //selection sort to sort run times
            for (int j = 0; j < counter1[i] - 1; j++) {
                int index = j;
                for (int b = j + 1; b < counter1[i]; b++) {
                    int c = regionProgram[i][index];
                    int d = regionProgram[i][b];
                    if (Program[d].getTime() < Program[c].getTime()) {
                        index = b; //get the smallest run time program
                    }
                }
                int temp = regionProgram[i][index];
                regionProgram[i][index] = regionProgram[i][j];
                regionProgram[i][j] = temp;

            }

            for (int e = 0; e < counter1[i]; e++) {
                int programID = regionProgram[i][e];
                TATime1[i] += Program[programID].getTime(); // turn around time after ordering used to calc set time of the next program
                Program[programID].setStartTime(TATime1[i]); // assign set start time to programs
            }

        }


    }


    // display case details
    public void displayMemoryDetails() {
        sc1.close();
        int temp = memoryID + 1;
        System.out.println("\nCase" + temp);
        calcAvgTurnAroundTimeOfAProgram(); // calculate average turn around time
        System.out.print("Average turnaround time = ");
        System.out.printf("%.2f", AvgTurnAroundTime);
        System.out.println();

        for (int j = 0; j < noOfPrograms; j++) {
            Program[j].displayDetails(); //display program details
        }
    }

}