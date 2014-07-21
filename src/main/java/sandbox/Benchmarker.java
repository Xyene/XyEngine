package sandbox;

import tk.ivybits.engine.scene.model.ModelIO;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Benchmarker {
    public static void main(String[] args) throws IOException {
        int tests = 100;
        System.out.println("Running " + tests + " test cases.");
        String[][] data = {
                {"OBJ", "serenity.obj"}
        };
        for(String[] testCase : data) {
            long total = 0;
            File in = new File(testCase[1]);
            for (int test = 0; test != tests; test++) {
                long start = System.nanoTime();
                ModelIO.read(in);
                long end = System.nanoTime();
                total += end - start;
            }
            long average = total / tests;
            System.out.println("\t" + testCase[0] + " took an average of " + TimeUnit.NANOSECONDS.toMillis(average) + " milliseconds to load file.");
        }
    }
}
