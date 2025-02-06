package com.moascode.util;

import java.io.*;
import java.util.*;

public class EnhancedGitDiffAll {

    public static void main(String[] args) {
        String diffFile = "C:\\dev docu\\Backlog\\79183 TVA wildfly migration - Rollback interface changes\\diff\\diff-patch-2024-09-27.patch";  // Update with the path to your Git diff
        String outputFile = "C:\\dev docu\\Backlog\\79183 TVA wildfly migration - Rollback interface changes\\diff\\result-diff-2024-09-27.txt"; // Where to write the diff results
        List<String> differences = parseAndCompareDiff(diffFile);
        writeDifferencesToFile(differences, outputFile);
        System.out.println("Enhanced diff written to: " + outputFile);
    }

    public static List<String> parseAndCompareDiff(String diffFilePath) {
        Map<String, List<String>> addedLinesMap = new HashMap<>();
        Map<String, List<String>> removedLinesMap = new HashMap<>();
        Map<String, List<String>> reshuffledLinesMap = new HashMap<>();
        List<String> finalDiff = new ArrayList<>();
        String currentFile = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(diffFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Detect file names
                if (line.startsWith("--- a/")) {
                    currentFile = line.substring(6); // Get the file name (removing "--- a/")
                }
                if (line.startsWith("+++ b/")) {
                    currentFile = line.substring(6); // Get the file name (removing "+++ b/")
                    // Initialize maps for the new file
                    addedLinesMap.put(currentFile, new ArrayList<>());
                    removedLinesMap.put(currentFile, new ArrayList<>());
                    reshuffledLinesMap.put(currentFile, new ArrayList<>());
                }

                // Detect added lines (lines starting with '+', excluding the diff metadata)
                if (line.startsWith("+") && !line.startsWith("+++")) {
                    addedLinesMap.get(currentFile).add(line.substring(1).trim());
                }

                // Detect removed lines (lines starting with '-', excluding the diff metadata)
                if (line.startsWith("-") && !line.startsWith("---")) {
                    removedLinesMap.get(currentFile).add(line.substring(1).trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Now process each file to find reshuffled lines and generate the final diff
        for (String file : addedLinesMap.keySet()) {
            List<String> addedLines = addedLinesMap.get(file);
            List<String> removedLines = removedLinesMap.get(file);

            // Find reshuffled lines (those present in both added and removed lists)
            for (String removed : removedLines) {
                if (addedLines.contains(removed)) {
                    reshuffledLinesMap.get(file).add(removed);
                }
            }

            // Remove reshuffled lines from both lists
            addedLines.removeAll(reshuffledLinesMap.get(file));
            removedLines.removeAll(reshuffledLinesMap.get(file));

            // Add file header to the final diff
            finalDiff.add("\nFile: " + file);

            // Add new lines or indicate no changes
            if (!addedLines.isEmpty()) {
                finalDiff.add("Newly Added Lines:");
                finalDiff.addAll(addedLines);
            }

            if (!removedLines.isEmpty()) {
                finalDiff.add("\nMissing Lines:");
                finalDiff.addAll(removedLines);
            }

            // Indicate no changes made if both lists are empty
            if (addedLines.isEmpty() && removedLines.isEmpty()) {
                finalDiff.add("No changes made.");
            }
        }

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