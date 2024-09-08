import java.util.Scanner;

public class RoundRobinScheduling {
    static class Process {
        int id;             // process id
        int arrivalTime;    // arrival time
        int burstTime;      // burst time
        int completionTime; // completion time
        int turnaroundTime; // turnaround time
        int waitingTime;    // waiting time
    
        // Constructor to initialize the process
        public Process(int id, int arrivalTime, int burstTime) {
            this.id = id;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
        }
    }

    // Method to calculate completion times
    public static void calculateTimes(Process[] processes, int n, int quantum) {
        int[] remainingTime = new int[n];
        for (int i = 0; i < n; i++) {
            remainingTime[i] = processes[i].burstTime;
        }

        int currentTime = 0;
        boolean allDone = false;

        while (!allDone) {
            allDone = true;
            for (int i = 0; i < n; i++) {
                if (remainingTime[i] > 0) {
                    allDone = false;
                    if (remainingTime[i] > quantum) {
                        currentTime += quantum;
                        remainingTime[i] -= quantum;
                    } else {
                        currentTime += remainingTime[i];
                        processes[i].completionTime = currentTime;
                        remainingTime[i] = 0;
                    }
                }
            }
        }
    }

    // Method to calculate turnaround time
    public static void calculateTurnaroundTime(Process[] processes, int n) {
        for (int i = 0; i < n; i++) {
            processes[i].turnaroundTime = processes[i].completionTime - processes[i].arrivalTime;
        }
    }

    // Method to calculate waiting time
    public static void calculateWaitingTime(Process[] processes, int n) {
        for (int i = 0; i < n; i++) {
            processes[i].waitingTime = processes[i].turnaroundTime - processes[i].burstTime;
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

        System.out.print("Enter the time quantum: ");
        int quantum = sc.nextInt();

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

        // Calculate times
        calculateTimes(processes, n, quantum);
        calculateTurnaroundTime(processes, n);
        calculateWaitingTime(processes, n);

        // Print the result table
        System.out.println("\nRound Robin Scheduling Results:");
        printTable(processes, n);

        sc.close();
    }
}
