import java.util.PriorityQueue;
import java.util.*;

public class PriorityScheduling {

 static class Process implements Comparable<Process> {
    int id;             // process id
    int arrivalTime;    // arrival time
    int burstTime;      // burst time
    int priority;       // priority of the process
    int completionTime; // completion time
    int turnaroundTime; // turnaround time3
    
    int waitingTime;    // waiting time

    // Constructor to initialize the process
    public Process(int id, int arrivalTime, int burstTime, int priority) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }

    @Override
    public int compareTo(Process other) {
        // Higher priority number means lower priority
        return Integer.compare(this.priority, other.priority);
    }
}




    // Method to calculate completion, turnaround, and waiting times
    public static void calculateTimes(Process[] processes, int n) {
        PriorityQueue<Process> pq = new PriorityQueue<>();
        int currentTime = 0;
        int index = 0;
        int completed = 0;

        while (completed < n) {
            // Add all processes that have arrived by currentTime to the priority queue
            while (index < n && processes[index].arrivalTime <= currentTime) {
                pq.offer(processes[index]);
                index++;
            }

            if (!pq.isEmpty()) {
                Process current = pq.poll();
                currentTime += current.burstTime;
                current.completionTime = currentTime;
                current.turnaroundTime = current.completionTime - current.arrivalTime;
                current.waitingTime = current.turnaroundTime - current.burstTime;
                completed++;
            } else {
                currentTime++;
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
            System.out.print("   Priority (lower number = higher priority): ");
            int priority = sc.nextInt();
            processes[i] = new Process(i + 1, arrivalTime, burstTime, priority); // Add process ID here
        }

        // Calculate times
        calculateTimes(processes, n);

        // Print the result table
        System.out.println("\nPriority Scheduling Results:");
        printTable(processes, n);

        sc.close();
    }
}
