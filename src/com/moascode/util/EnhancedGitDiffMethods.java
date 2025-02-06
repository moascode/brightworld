package com.moascode.util;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class EnhancedGitDiffMethods {

    public static void main(String[] args) {
        String diffFile = "C:\\dev docu\\Backlog\\79183 TVA wildfly migration - Rollback interface changes\\diff\\diff-patch-2024-09-27.patch";  // Update with the path to your Git diff
        String outputFile = "C:\\dev docu\\Backlog\\79183 TVA wildfly migration - Rollback interface changes\\diff\\result-diff-2024-09-27.txt"; // Where to write the diff results
        List<String> differences = parseAndCompareDiff(diffFile);
        writeDifferencesToFile(differences, outputFile);
        System.out.println("Enhanced diff written to: " + outputFile);
    }

    public static List<String> parseAndCompareDiff(String diffFilePath) {
        List<String> addedMethods = new ArrayList<>();
        List<String> removedMethods = new ArrayList<>();
        List<String> reshuffledMethods = new ArrayList<>();
        List<String> finalDiff = new ArrayList<>();

        // Regex pattern to match C# method declarations
        Pattern methodPattern = Pattern.compile("\\+\\s*(public|private|protected|internal)\\s+[\\w<>\\[\\]]+\\s+(\\w+)\\s*\\(.*\\)\\s*\\{");
        Pattern removedMethodPattern = Pattern.compile("-\\s*(public|private|protected|internal)\\s+[\\w<>\\[\\]]+\\s+(\\w+)\\s*\\(.*\\)\\s*\\{");

        try (BufferedReader reader = new BufferedReader(new FileReader(diffFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher addMatcher = methodPattern.matcher(line);
                Matcher removeMatcher = removedMethodPattern.matcher(line);

                // Check for added methods (lines starting with '+')
                if (line.startsWith("+")) {
                    if (addMatcher.find()) {
                        String methodName = addMatcher.group(2);
                        addedMethods.add(methodName);
                    }
                }

                // Check for removed methods (lines starting with '-')
                if (line.startsWith("-")) {
                    if (removeMatcher.find()) {
                        String methodName = removeMatcher.group(2);
                        removedMethods.add(methodName);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Find reshuffled methods (those present in both added and removed lists)
        for (String method : removedMethods) {
            if (addedMethods.contains(method)) {
                reshuffledMethods.add(method);
            }
        }

        // Remove reshuffled methods from both lists
        addedMethods.removeAll(reshuffledMethods);
        removedMethods.removeAll(reshuffledMethods);

        // Build final diff list of changes (methods that are truly added or missing)
        finalDiff.add("Newly Added Methods:");
        finalDiff.addAll(addedMethods);

        finalDiff.add("\nMissing Methods:");
        finalDiff.addAll(removedMethods);

        return finalDiff;
    }

    public static void writeDifferencesToFile(List<String> differences, String outputFilePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            for (String diff : differences) {
                writer.write(diff);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
