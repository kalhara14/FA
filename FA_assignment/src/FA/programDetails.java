package FA;

public class programDetails {

    // variables
    private int programID;
    private int[][] spaceTime;  // int[k][0] = time int[k][1] = space
    private int memoryLocation;
    private int noOfK = 0; // no of instances
    private int startTime;
    private int[] possibleMemoryLocs; // array to store memory location id
    private int numberOfMemoryLocs; // number of possible memory locations
    private int endTime;

    //constructor
    public programDetails(int id,int k,int l)
    {
        programID = id;
        spaceTime = new int[k][2]; // 2D array to save instance parameters
        noOfK = k; // no of instances
        memoryLocation = 0;
        numberOfMemoryLocs = 0;
        possibleMemoryLocs = new int[l];
        startTime = -1;
    }

    // function to store instances
    public void setSpaceTime(int k,int time,int space)
    {
        spaceTime[k][0] = time;
        spaceTime[k][1] = space;
    }

    //selection sort to sort time in space time array
    public void sortSpaceTimeArray()
    {
        for (int i = 0; i < noOfK - 1; i++)
        {
            int index = i;
            for (int j = i + 1; j < noOfK; j++){
                if (spaceTime[j][0] < spaceTime[index][0]){
                    index = j; //searching for lowest time
                }
            }
            int smallerTime = spaceTime[index][0];
            int tempSpace = spaceTime[index][1];
            spaceTime[index][0] = spaceTime[i][0];
            spaceTime[index][1] = spaceTime[i][1];
            spaceTime[i][0] = smallerTime;
            spaceTime[i][1] = tempSpace;
        }
    }
    public void setPossibleLoc(int l)
    {
        possibleMemoryLocs[numberOfMemoryLocs++] = l;

    }

    public int getNumberOfMemoryLocs()
    {
        return numberOfMemoryLocs;
    }

    public int getPossibleLoc(int l)
    {
        return possibleMemoryLocs[l];
    }



    public int getSpace()
    {
        return spaceTime[0][1];
    }

    public int getTime()
    {
        return spaceTime[0][0];
    }

    public void setMemoryLoc(int loc)
    {
        memoryLocation = loc;
    }

    public void setStartTime(int time)
    {
        endTime = time;
        startTime = endTime - getTime();
    }

    public int getEndTime()
    {
        return  endTime;
    }





    public void displayDetails()
    {
        programID = programID + 1;
        System.out.println("Program "+programID+" runs in region "+memoryLocation+" from "+ startTime+" to "+endTime);

    }



}
