import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

class Process {
    int pid;
    int arrivalTime;
    int burstTime;
    int completionTime;
    int turnaroundTime;
    int waitingTime;

    public Process(int pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }
}

public class SJF_Scheduling {

    public static void findSJF(Process[] proc, int n) {
        // Sort processes by arrival time
        Arrays.sort(proc, Comparator.comparingInt(p -> p.arrivalTime));

        int[] remainingTime = new int[n];
        for (int i = 0; i < n; i++) {
            remainingTime[i] = proc[i].burstTime;
        }

        int currentTime = 0;
        int complete = 0;
        int minBurst = Integer.MAX_VALUE;
        int shortest = -1;
        boolean foundProcess = false;

        while (complete < n) {
            minBurst = Integer.MAX_VALUE;
            foundProcess = false;

            // Find the process with the shortest burst time among the arrived processes
            for (int i = 0; i < n; i++) {
                if (proc[i].arrivalTime <= currentTime && remainingTime[i] < minBurst && remainingTime[i] > 0) {
                    minBurst = remainingTime[i];
                    shortest = i;
                    foundProcess = true;
                }
            }

            // If no process has arrived yet, increment currentTime
            if (!foundProcess) {
                currentTime++;
                continue;
            }

            // Execute the process
            remainingTime[shortest]--;

            // If the process is finished
            if (remainingTime[shortest] == 0) {
                complete++;
                proc[shortest].completionTime = currentTime + 1;
                proc[shortest].turnaroundTime = proc[shortest].completionTime - proc[shortest].arrivalTime;
                proc[shortest].waitingTime = proc[shortest].turnaroundTime - proc[shortest].burstTime;
            }

            // Move to the next time unit
            currentTime++;
        }
    }

    public static void displayProcessDetails(Process[] proc, int n) {
        System.out.println("Process\tArrivalTime\tBurstTime\tCompletionTime\tTurnaroundTime\tWaitingTime");
        for (int i = 0; i < n; i++) {
            System.out.println(proc[i].pid + "\t\t" + proc[i].arrivalTime + "\t\t" + proc[i].burstTime + "\t\t" +
                    proc[i].completionTime + "\t\t" + proc[i].turnaroundTime + "\t\t" + proc[i].waitingTime);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();

        Process[] proc = new Process[n];
        System.out.println("Enter Process Details (ID, Arrival Time, Burst Time):");

        for (int i = 0; i < n; i++) {
            System.out.print("Enter Process " + (i + 1) + ": ");
            int pid = sc.nextInt();
            int arrivalTime = sc.nextInt();
            int burstTime = sc.nextInt();
            proc[i] = new Process(pid, arrivalTime, burstTime);
        }

        findSJF(proc, n);
        displayProcessDetails(proc, n);

        sc.close();
    }
}
