package ds.project1task3;

import java.io.IOException;

public class ClickerModel {
    public int recordClicker(String currentChoice, String option, int currentCount) throws IOException {
        // Record and count the choice
        if (currentChoice.equals(option)) currentCount++;
        System.out.println(option + ": " + currentCount);
        return currentCount;
    }
}
