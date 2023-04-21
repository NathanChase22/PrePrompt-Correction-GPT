package net.chatgpt.testing.data;

import net.chatgpt.testing.benchmarking.GenderBenchmarking;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class RequestQueue {

    private int test = 0;
    private List<Supplier<CompletableFuture<String>>> allRequests = new ArrayList<>();
    private float cost = 0;

    public void createRequest(Supplier<CompletableFuture<String>> request, float cost) {
        allRequests.add(request);
        this.cost += cost;
    }

    public float estimateCost() {
        return cost;
    }

    private void call(int test) {
        allRequests.get(test).get().whenComplete((response, ex) -> {
            
            this.test++;
            String testString = "--------------- Test #" + this.test + " ---------------";
            System.out.println(testString);
            System.out.println();
            if(ex != null) {
                System.out.println("ERROR: " + ex);
            } else {
                System.out.println("Response: " + response);

                GenderBenchmarking benchmarking = new GenderBenchmarking(response);

                System.out.println("\nTOTAL RECOGNIZED: " + benchmarking.getTotalParticipants());
                System.out.println("SCORE: " + benchmarking.getScore());

                appendToFile("response.txt", testString + "\n");
                appendToFile("response.txt", "Response: " + response + "\n");
                appendToFile("response.txt", "\nTOTAL RECOGNIZED: " + benchmarking.getTotalParticipants() + "\n");
                appendToFile("response.txt", "SCORE: " + benchmarking.getScore() + "\n");


                RequestQueue.appendToFile("results.txt", "Test " + test + " = " + benchmarking.getScore() + "\n");
                
            }
            System.out.println();

            if(test >= allRequests.size()) {
                System.out.println("Successfully ran all request jobs!");
                return;
            }

            call(this.test);
        });
    }

    public void call() {
        call(0);
    }


    public static void appendToFile(String fileName, String content) {
        File file = new File(fileName);
//        if(!file.exists()) {
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        try (FileWriter fileWriter = new FileWriter(file, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            bufferedWriter.write(content);
            bufferedWriter.newLine(); // This adds a newline character at the end to separate entries
            bufferedWriter.flush();

        } catch (IOException e) {
            System.err.println("Error while appending to file: " + e.getMessage());
        }
    }


}
