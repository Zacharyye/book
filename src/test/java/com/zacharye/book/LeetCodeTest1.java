package com.zacharye.book;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.*;

public class LeetCodeTest1 {
  private List<List<Integer>> output = new ArrayList();
  int n, k;

  @Test
  public void testSPath () {
    System.out.println(simplefyPath("/a/b/c/d/../e"));
  }

  @Test
  public void testTest () {
    for(int i = 0; i < 100; i++) {
      int num = (int) ((Math.random() * 9 + 1) * 100000);
      if (num < 100000) {
        System.out.println(num);
      }
    }
  }

  @Test
  public void testSubset() {
    int[] nums = {1,2,3};
    System.out.println(subsets_B(nums));
  }

  public List<List<Integer>> subsets_B(int[] nums) {
    List<List<Integer>> output = new ArrayList();
    int n = nums.length;

    for (int i = (int) Math.pow(2, n); i < (int) Math.pow(2, n + 1); ++i) {
      String bitmask = Integer.toBinaryString(i).substring(1);
      List<Integer> curr = new ArrayList();
      for (int j = 0; j < n; ++j) {
        if (bitmask.charAt(j) == '1') {
          curr.add(nums[j]);
        }
      }
      output.add(curr);
    }
    return output;
  }

  public void backtrack(int first, ArrayList<Integer> curr, int[] nums) {
    if (curr.size() == k) {
      output.add(new ArrayList(curr));
    }
    for (int i = first; i < n; ++i) {
      curr.add(nums[i]);
      backtrack(i + 1, curr, nums);
      curr.remove(curr.size() - 1);
    }
  }

  public List<List<Integer>> subsets_(int[] nums) {
    n = nums.length;
    for (k = 0; k < n + 1; ++k) {
      backtrack(0, new ArrayList<Integer>(), nums);
    }
    return output;
  }

  public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> output = new ArrayList();
    output.add(new ArrayList<Integer>());

    for (int num : nums) {
      List<List<Integer>> newSubsets = new ArrayList<>();
      for (List<Integer> curr : output) {
        newSubsets.add(new ArrayList<Integer>(curr){{add(num);}});
      }
      for (List<Integer> curr : newSubsets) {
        output.add(curr);
      }
    }
    return output;
  }

  public String simplefyPath (String path) {
    if (path.isEmpty()) {
      return path;
    }

    Stack<String> stack = new Stack<>();
    String[] components = path.split("/");
    for (String directory: components) {
      if (directory.equals(".") || directory.isEmpty()) {
        continue;
      } else if (directory.equals("..")) {
        if (!stack.isEmpty()) {
          stack.pop();
        }
      } else {
        stack.add(directory);
      }
    }

    StringBuilder result = new StringBuilder();
    for (String dir : stack) {
      result.append("/");
      result.append(dir);
    }

    return result.length() > 0 ? result.toString() : "/";
  }

  @Test
  public void testMinSetSize () {
    int[] arr = {3,5,4,3,2,6,2,2,1,9,7,5};
    System.out.println(minSetSize(arr));
  }

  public int minSetSize (int[] arr) {
    Arrays.sort(arr);

    List<Integer> counts = new ArrayList<>();
    int currentRun = 1;
    for (int i = 1; i < arr.length; i++) {
      if (arr[i] == arr[i - 1]) {
        currentRun += 1;
        continue;
      }
      counts.add(currentRun);
      currentRun = 1;
    }
    counts.add(currentRun);

    Collections.sort(counts);
    Collections.reverse(counts);

    int numbersRemovedFromArr = 0;
    int setSize = 0;
    for (int count: counts) {
      numbersRemovedFromArr += count;
      setSize += 1;
      if (numbersRemovedFromArr >= arr.length / 2) {
        break;
      }
    }
    return setSize;
  }

  public List<List<Integer>> shiftGrid (int[][] grid, int k) {
    for (;k > 0; k--) {
      int[][] newGrid = new int[grid.length][grid[0].length];

      for (int row = 0; row < grid.length; row ++) {
        for (int col = 0; col < grid[0].length - 1; col++) {
          newGrid[row][col + 1] = grid[row][col];
        }
      }

      
    }
    return null;
  }
}
