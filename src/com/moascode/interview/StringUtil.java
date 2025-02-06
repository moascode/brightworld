package com.moascode.interview;

public class StringUtil {
    public static void main (String... args) {
        // Reverse a string
        String str = "Hello, World!";
        System.out.println(reverse(str));

        // Check if a string is a palindrome
        String palindrome = "racecar";
        System.out.println(isPalindrome(palindrome));
    }

    private static String reverse(String str) {
        StringBuilder sb = new StringBuilder(str).reverse();
        StringBuilder another = new StringBuilder();
        for (int i = str.length() - 1; i >= 0; i--) {
            another.append(str.charAt(i));
        }
        return another.toString();
    }

    private static boolean isPalindrome(String str) {
        return str.equals(reverse(str));
    }
}
