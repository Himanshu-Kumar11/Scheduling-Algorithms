import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
public class SRTFScheduling {

static class Process {
    int id;             // Process ID
    int arrivalTime;    // Arrival Time
    int burstTime;      // Burst Time
    int completionTime; // Completion Time
    int turnaroundTime; // Turnaround Time
    int waitingTime;    // Waiting Time4
    
    int remainingTime;  // Remaining Time for SRTF

    // Constructor to initialize the process
    public Process(int id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }
}



    // Method to calculate completion, turnaround, and waiting times
    public static void calculateTimes(Process[] processes, int n) {
        int currentTime = 0;
        int completed = 0;
        boolean allDone = false;

        while (completed < n) {
            // Find the process with the shortest remaining time
            int shortestIndex = -1;
            int minRemainingTime = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                if (processes[i].arrivalTime <= currentTime && processes[i].remainingTime > 0) {
                    if (processes[i].remainingTime < minRemainingTime) {
                        minRemainingTime = processes[i].remainingTime;
                        shortestIndex = i;
                    }
                }
            }

            // If no process is found, move the time forward
            if (shortestIndex == -1) {
                currentTime++;
                continue;
            }

            // Process the selected process
            processes[shortestIndex].remainingTime--;
            currentTime++;

            // If the process is completed
            if (processes[shortestIndex].remainingTime == 0) {
                processes[shortestIndex].completionTime = currentTime;
                processes[shortestIndex].turnaroundTime = processes[shortestIndex].completionTime - processes[shortestIndex].arrivalTime;
                processes[shortestIndex].waitingTime = processes[shortestIndex].turnaroundTime - processes[shortestIndex].burstTime;
                completed++;
            }
        }
    }

    // Method to print the scheduling table
    public static void printTable(Process[] processes, int n) {
        System.out.println("---------------------------------------------------------");
        System.out.println("| Process | Arrival Time | Burst Time | Completion Time | Turnaround Time | Waiting Time |");
        System.out.println("---------------------------------------------------------");
        for (int i = 0; i < n; i++) {
            System.out.printf("|   %2d    |      %2d      |     %2d     |        %2d        |        %2d        |      %2d      |\n",
                    processes[i].id, processes[i].arrivalTime, processes[i].burstTime, 
                    processes[i].completionTime, processes[i].turnaroundTime, processes[i].waitingTime);
        }
        System.out.println("---------------------------------------------------------");
    }

    // Main method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();

        Process[] processes = new Process[n];

        // Input process details
        for (int i = 0; i < n; i++) {
            System.out.println("Process " + (i + 1) + ":");
            System.out.print("   Arrival Time: ");
            int arrivalTime = sc.nextInt();
            System.out.print("   Burst Time: ");
            int burstTime = sc.nextInt();
            processes[i] = new Process(i + 1, arrivalTime, burstTime); // Add process ID here
        }

        // Sort processes by arrival time
        Arrays.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));

        // Calculate times
        calculateTimes(processes, n);

        // Print the result table
        System.out.println("\nSRTF Scheduling Results:");
        printTable(processes, n);

        sc.close();
    }
}

