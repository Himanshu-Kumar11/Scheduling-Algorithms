import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class FCFS_Scheduling{

 static class Process {
    int id;
    int arrivalTime;
    int burstTime;
    int completionTime;
    int turnaroundTime;
    int waitingTime;

    public Process(int id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }
}



    // Method to calculate completion, turnaround, and waiting times
    public static void calculateFCFS(Queue<Process> processQueue) {
        int currentTime = 0;

        while (!processQueue.isEmpty()) {
            Process process = processQueue.poll();

            if (currentTime < process.arrivalTime) {
                currentTime = process.arrivalTime;
            }
            currentTime += process.burstTime;
            process.completionTime = currentTime;
            process.turnaroundTime = process.completionTime - process.arrivalTime;
            process.waitingTime = process.turnaroundTime - process.burstTime;
        }
    }

    // Method to print the scheduling table
    public static void printTable(Process[] processes) {
        System.out.println("---------------------------------------------------------");
        System.out.println("| Process | Arrival Time | Burst Time | Completion Time | Turnaround Time | Waiting Time |");
        System.out.println("---------------------------------------------------------");
        for (Process process : processes) {
            System.out.printf("|   %2d    |      %2d      |     %2d     |        %2d        |        %2d        |      %2d      |\n",
                    process.id, process.arrivalTime, process.burstTime, 
                    process.completionTime, process.turnaroundTime, process.waitingTime);
        }
        System.out.println("---------------------------------------------------------");
    }

    // Main method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();

        Queue<Process> processQueue = new LinkedList<>();

        // Input process details
        for (int i = 0; i < n; i++) {
            System.out.println("Process " + (i + 1) + ":");
            System.out.print("   Arrival Time: ");
            int arrivalTime = sc.nextInt();
            System.out.print("   Burst Time: ");
            int burstTime = sc.nextInt();
            processQueue.add(new Process(i + 1, arrivalTime, burstTime));
        }

        // Sort processes by arrival time to simulate FCFS
        Process[] processes = processQueue.toArray(new Process[0]);
        java.util.Arrays.sort(processes, (p1, p2) -> Integer.compare(p1.arrivalTime, p2.arrivalTime));

        // Refill the queue with sorted processes
        processQueue = new LinkedList<>();
        for (Process process : processes) {
            processQueue.add(process);
        }

        // Calculate FCFS times
        calculateFCFS(processQueue);

        // Print the result table
        System.out.println("\nFCFS Scheduling Results:");
        printTable(processes);

        sc.close();
    }
}
