package com.zacharye.book;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.logging.Logger;

public class LeeCodeTest {

    /**
     * Find Minimun in Rotated Sorted Array II
     * Created by IntelliJ IDEA.
     * @author: zachary
     * @Created: 2019-11-12 16:12
     */
    @Test
    public void testRotatedSortedArray () {
        int[] nums = {4,5,6,7,0,1,2};
        int low = 0, high = nums.length -1;
        while (low < high) {
            int pivot = low + (high - low) / 2;
            if (nums[pivot] < nums[high])
                high = pivot;
            else if (nums[pivot] > nums[high])
                low = pivot + 1;
            else
                high -= 1;
        }
        System.out.println(nums[low]);
    }

    @Test
    public void loggerRateLimiter () {


    }

    @Test
    public void testReverseString () {
        String str = "hello";
        char[] chars = str.toCharArray();
        helper(chars,0,chars.length - 1);
    }

    @Test
    public void testReverseString2 () {
        String str = "hello";
        char[] chars = str.toCharArray();
        int left = 0, right =  chars.length - 1;
        while (left < right) {
            char tmp = chars[left];
            chars[left++] = chars[right];
            chars[right--] = tmp;
        }
        for (char c : chars) {
            System.out.println(c);
        }
    }

    @Test
    public void tetFlattenDoublyLinkedList () {

    }

    private void helper (char[] s, int left, int right) {
        if (left >= right) return;
        char tmp = s[left];
        s[left++] = s[right];
        s[right--] = tmp;

        helper(s,left,right);
    }
}

class Pair<U,V> {
    public U first;
    public V second;

    public Pair (U first, V second) {
        this.first = first;
        this.second = second;
    }
}

class MyLogger {
    private LinkedList<Pair<String, Integer>> msgQueue;
    private HashSet<String> msgSet;

    public MyLogger () {
        msgQueue = new LinkedList<>();
        msgSet = new HashSet<>();
    }

    public boolean shouldPrintMessage (int timestamp, String message) {
        while (msgQueue.size() > 0) {
            Pair<String, Integer> head = msgQueue.getFirst();
            if (timestamp - head.second >= 10) {
                msgQueue.removeFirst();
                msgSet.remove(head.first);
            } else {
                break;
            }
        }

        if (!msgSet.contains(message)) {
            Pair<String, Integer> newEntry = new Pair<String, Integer>(message, timestamp);
            msgQueue.addLast(newEntry);
            msgSet.add(message);
            return true;
        } else {
            return false;
        }
    }

}
