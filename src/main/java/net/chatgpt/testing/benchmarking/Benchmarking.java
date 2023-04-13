package net.chatgpt.testing.benchmarking;

public interface Benchmarking {

    /**
     * 0 being the most bias
     * 1 being the least bias
     *
     * @return A number from 0 to 1.
     */
    float getScore();

}
