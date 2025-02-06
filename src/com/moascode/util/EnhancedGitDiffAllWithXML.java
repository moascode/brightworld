package com.moascode.util;

import java.io.*;
import java.util.*;

public class EnhancedGitDiffAllWithXML {

    public static void main(String[] args) {
        String diffFile = "C:\\dev docu\\Backlog\\79183 TVA wildfly migration - Rollback interface changes\\diff\\adsales-commit-2024-10-02.patch";  // Update with the path to your Git diff file
        String outputFile = "C:\\dev docu\\Backlog\\79183 TVA wildfly migration - Rollback interface changes\\diff\\adsales-commit-2024-10-02.xml"; // Output file for the enhanced diff in XML format
        List<String> differences = parseAndCompareDiff(diffFile);
        writeDifferencesToFile(differences, outputFile);
        System.out.println("Enhanced diff written to: " + outputFile);
    }

    public static List<String> parseAndCompareDiff(String diffFilePath) {
        Map<String, List<String>> addedLinesMap = new HashMap<>();
        Map<String, List<String>> removedLinesMap = new HashMap<>();
        Map<String, List<String>> reshuffledLinesMap = new HashMap<>();
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

        // Now process each file to find reshuffled lines
        List<String> finalDiff = new ArrayList<>();
        finalDiff.add("<diffs>");

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

            // Start XML segment for the file
            finalDiff.add("  <file name=\"" + file + "\">");

            // Add new lines or indicate no changes
            if (!addedLines.isEmpty()) {
                finalDiff.add("    <newlyAdded>");
                for (String added : addedLines) {
                    finalDiff.add("      <line>" + escapeXml(added) + "</line>");
                }
                finalDiff.add("    </newlyAdded>");
            }

            if (!removedLines.isEmpty()) {
                finalDiff.add("    <missing>");
                for (String removed : removedLines) {
                    finalDiff.add("      <line>" + escapeXml(removed) + "</line>");
                }
                finalDiff.add("    </missing>");
            }

            // Indicate no changes made if both lists are empty
            if (addedLines.isEmpty() && removedLines.isEmpty()) {
                finalDiff.add("    <noChanges>No changes made.</noChanges>");
            }

            // End XML segment for the file
            finalDiff.add("  </file>");
        }

        finalDiff.add("</diffs>");
        return finalDiff;
    }

    public static String escapeXml(String text) {
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
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

