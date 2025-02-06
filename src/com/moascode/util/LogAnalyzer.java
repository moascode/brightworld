package com.moascode.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class LogAnalyzer {

    private static final String PATTERN_REQUEST_STRING = "Request: ([A-Za-z]+).*corId=([A-Za-z0-9-]+)\\).*END \\[.*-> ([0-9:.]+)";
    private static final Pattern PATTERN = Pattern.compile(PATTERN_REQUEST_STRING);

    private static final String DATA_RETRIEVAL_CONTAINS = "data retrieval total";
    private static final String DATA_RETRIEVAL_PATTERN_STRING = ".*corId=([A-Za-z0-9-]+).*data retrieval total: ([0-9:.]+)";
    private static final Pattern DATA_RETRIEVAL_PATTERN = Pattern.compile(DATA_RETRIEVAL_PATTERN_STRING);

    // Pattern to match files like file.log, file.log.1, file.log.2, etc.
    private static final Pattern LOG_FILE_PATTERN = Pattern.compile("^server\\.log(?:\\.\\d+)?$");

    private static final String OUTPUT_FILE_HEADER = "FileName,corId,Request,TimeTaken,DataRetrievalTotal";

    private static final String INPUT_PATH = "C:\\dev docu\\Backlog\\87130 Analyze Performance Issue on BookingServer [TB]\\20241205-JBS101-S4M-3867"; // Input file or directory
    private static final String OUTPUT_FILE = "C:\\dev docu\\Backlog\\87130 Analyze Performance Issue on BookingServer [TB]\\20241205-JBS101-S4M-3867\\analysis\\loganalysis2.csv"; // Single CSV output file

    public static void main(String[] args) {
        try {
            File inputFile = new File(INPUT_PATH);
            File outputFile = new File(OUTPUT_FILE);

            if (outputFile.getParentFile() != null && !outputFile.getParentFile().exists()) {
                if (!outputFile.getParentFile().mkdirs()) {
                    throw new IOException("Failed to create output directory: " + outputFile.getParent());
                }
            }

            List<String[]> allLogData = new ArrayList<>();
            Map<String, Long> requestSums = new HashMap<>();

            if (inputFile.isDirectory()) {
                for (File file : Objects.requireNonNull(inputFile.listFiles((dir, name) -> LOG_FILE_PATTERN.matcher(name).matches()))) {
                    System.out.println("Processing file: " + file.getName());
                    allLogData.addAll(readLogFile(file, requestSums));
                }
            } else {
                System.out.println("Processing file: " + inputFile.getName());
                allLogData.addAll(readLogFile(inputFile, requestSums));
            }

            // Sort all data by timeTaken from longest to shortest
            allLogData.sort((a, b) -> compareTimeTaken(a[3], b[3]));

            // Write all data to a single CSV file
            writeCSVFile(allLogData, requestSums, outputFile);

            System.out.println("Processing complete. Combined CSV file saved at: " + OUTPUT_FILE);
        } catch (IOException e) {
            System.err.println("Error processing files: " + e.getMessage());
        }
    }

    private static List<String[]> readLogFile(File file, Map<String, Long> requestSums) throws IOException {
        List<String> logLines = Files.readAllLines(file.toPath(), StandardCharsets.ISO_8859_1);
        List<String[]> result = new ArrayList<>();

        if (logLines.isEmpty()) {
            System.out.println("Skipping empty file: " + file.getName());
        }

        for (String line : logLines) {
            // Skip empty lines
            if (line.trim().isEmpty()) {
                continue;
            }

            Matcher matcher = PATTERN.matcher(line);
            if (matcher.find()) {
                String requestType = matcher.group(1);
                String corId = matcher.group(2);
                String timeTaken = matcher.group(3);
                result.add(new String[]{file.getName(), corId, requestType, timeTaken});
            }

            // Check for "data retrieval total" with corId
            if (line.contains(DATA_RETRIEVAL_CONTAINS)) {
                Matcher dataRetrievalMatcher = DATA_RETRIEVAL_PATTERN.matcher(line);
                if (dataRetrievalMatcher.find()) {
                    String corId = dataRetrievalMatcher.group(1);
                    String retrievalTime = dataRetrievalMatcher.group(2);
                    long retrievalMillis = parseTimeToMillis(retrievalTime);

                    //requestSums.merge(corId, retrievalMillis, Long::sum);
                    requestSums.put(corId, retrievalMillis);
                }
            }
        }

        return result;
    }

    private static void writeCSVFile(List<String[]> data, Map<String, Long> requestSums, File outputFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            // Write headers
            writer.write(OUTPUT_FILE_HEADER);
            writer.newLine();

            // Write data with the summed data retrieval totals
            for (String[] row : data) {
                String corId = row[1];
                // Get the accumulated data retrieval time for this corId
                Long retrievalTotalMillis = requestSums.getOrDefault(corId, 0L);
                String retrievalTotalFormatted = formatMillisToTime(retrievalTotalMillis);
                writer.write(String.join(",", row) + "," + retrievalTotalFormatted);
                writer.newLine();
            }
        }
    }

    private static int compareTimeTaken(String time1, String time2) {
        return parseTimeToMillis(time2).compareTo(parseTimeToMillis(time1)); // Sort from longest to shortest
    }

    private static Long parseTimeToMillis(String time) {
        String[] parts = time.split(":");
        long hours = Long.parseLong(parts[0]) * 3600000;
        long minutes = Long.parseLong(parts[1]) * 60000;
        long seconds = (long) (Double.parseDouble(parts[2]) * 1000);
        return hours + minutes + seconds;
    }

    private static String formatMillisToTime(long millis) {
        long hours = millis / 3600000;
        long minutes = (millis % 3600000) / 60000;
        long seconds = (millis % 60000) / 1000;
        long milliseconds = millis % 1000;
        return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds);
    }
}
