package com.moascode.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ArrayUtil {
    public static void main(String... args) {
        //Find the second largest number in an array
        int[] arr = {1, 2, 3, 4, 5};
        System.out.println(secondLargest(arr));

        //Remove duplicates from an array
        int[] nums = {1, 1, 2, 2, 3, 3, 4, 4};
        System.out.println(removeDuplicates(nums));

        //Remove duplicates from an array
        int[] nums2 = {1, 1, 2, 2, 3, 3, 4, 4};
        System.out.println(removeDuplicates2(nums2));
    }

    private static int secondLargest(int[] arr) {
        // Sort the array and return the second last element
//        Arrays.sort(arr);
//        return arr[arr.length - 2];
        // This is a naive approach
        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;
        for (int j : arr) {
            if (j > largest) {
                secondLargest = largest;
                largest = j;
            } else if (j > secondLargest && j != largest) {
                secondLargest = j;
            }
        }
        return secondLargest;
    }

    public static int removeDuplicates(int[] nums) {
        Set<Integer> uniqElem = new HashSet<>();
        for (int num : nums) {
            uniqElem.add(num);
        }

        int index = 0;
        for(int elem : uniqElem) {
            nums[index++] = elem;
        }

        return uniqElem.size();
    }

    public static int removeDuplicates2(int[] nums) {
        int uniqIndex = 1;
        for (int iterator = 1; iterator < nums.length; iterator++) {
            if (nums[iterator] != nums[iterator - 1]) {
                nums[uniqIndex++] = nums[iterator];
            }
        }
        return uniqIndex;
    }
}
