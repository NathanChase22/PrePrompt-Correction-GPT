package net.chatgpt.testing.data;

import net.chatgpt.testing.benchmarking.GenderBenchmarking;

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
            System.out.println("--------------- Test #" + this.test + " ---------------");
            System.out.println();
            if(ex != null) {
                System.out.println("ERROR: " + ex);
            } else {
                System.out.println("Response: " + response);

                GenderBenchmarking benchmarking = new GenderBenchmarking(response);

                System.out.println("\nTOTAL RECOGNIZED: " + benchmarking.getTotalParticipants());
                System.out.println("SCORE: " + benchmarking.getScore());
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


}
