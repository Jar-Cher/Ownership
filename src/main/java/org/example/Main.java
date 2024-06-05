package org.example;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        OwnerShip ownership = new OwnerShip("src\\test\\resources");
        List<String> test = Arrays.asList(
                "src\\test\\resources\\CodeOwnersExamples\\GitHubExample",
                "src\\test\\resources\\CodeOwnersExamples\\GitHubExample"
        );
        ownership.requestReview(test);
    }
}