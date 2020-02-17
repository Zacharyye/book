package com.zacharye.book;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.logging.Logger;
import java.util.regex.Pattern;

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

    @Test
    public void testIPPattern() {
//        String ip = "172.16.254.1";
//        String ip = "2001:0db8:85a3:0:0:8A2E:0370:7334";
        String ip = "256.256.256.256";
        try {
            System.out.println((InetAddress.getByName(ip) instanceof Inet6Address) ? "IPv6" : "Ipv4");
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Neither");
        }
    }

    @Test
    public void testIPPatternWithRegx () {
        String ip = "2001:0db8:85a3:0:0:8A2E:0370:7334";
        String chunkIPv4 = "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])";
        Pattern patternIPv4 = Pattern.compile("^(" + chunkIPv4+ "\\.){3}" + chunkIPv4 + "$");
        String chunkIPv6 = "([0-9a-fA-F]{1,4})";
        Pattern patternIPv6 = Pattern.compile("^(" + chunkIPv6 + "\\:){7}" + chunkIPv6 + "$");
        if (patternIPv4.matcher(ip).matches()) System.out.println("IPv4");
        if (patternIPv6.matcher(ip).matches()) System.out.println("IPv6");
        if(!patternIPv4.matcher(ip).matches() && !patternIPv6.matcher(ip).matches()) System.out.println("Neither");
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

class MyHashSet {
    private Bucket[] bucketArray;
}

class Bucket {
    private LinkedList<Integer> container;

    public Bucket() {
        container = new LinkedList<>();
    }

    public void insert(Integer key) {
        int index = this.container.indexOf(key);
        if (index == -1) {
            this.container.addFirst(key);
        }
    }

    public void delete(Integer key) {
        this.container.remove(key);
    }

    public boolean exists(Integer key) {
        int index = this.container.indexOf(key);
        return (index != -1);
    }
}

class MyHashSetVB {
    private BucketVB[]  bucketArray;
    private int keyRange;

    public MyHashSetVB() {
        this.keyRange = 769;
        this.bucketArray = new BucketVB[this.keyRange];
        for (int i = 0; i < this.keyRange; ++i) {
            this.bucketArray[i] = new BucketVB();
        }
    }

    protected int _hash(int key) {
        return (key % this.keyRange);
    }

    public void add(int key) {
        int bucketIndex = this._hash(key);
        this.bucketArray[bucketIndex].insert(key);
    }

    public void remove(int key) {
        int bucketIndex = this._hash(key);
        this.bucketArray[bucketIndex].delete(key);
    }

    public boolean contains(int key) {
        int bucketIndex = this._hash(key);
        return this.bucketArray[bucketIndex].exists(key);
    }
}

class BucketVB {
    private BSTree tree;

    public BucketVB () {
        tree = new BSTree();
    }

    public void insert(Integer key) {
        this.tree.root = this.tree.insertIntoBST(this.tree.root, key);
    }

    public void delete (Integer key) {
        this.tree.root = this.tree.deleteNode(this.tree.root, key);
    }

    public boolean exists(Integer key) {
        TreeNode node = this.tree.searchBST(this.tree.root, key);
        return (node != null);
    }

}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode (int x) {
        val = x;
    }
}

class BSTree {
    TreeNode root = null;

    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null || val == root.val) {
            return root;
        }
        return val < root.val ? searchBST(root.left, val) : searchBST(root.right, val);
    }

    public TreeNode insertIntoBST (TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }
        if (val > root.val) {
            root.right = insertIntoBST(root.right, val);
        } else if(val == root.val) {
            return root;
        } else {
            root.left = insertIntoBST(root.left, val);
        }
        return root;
    }

    public int successor(TreeNode root) {
        root = root.right;
        while (root.left != null) {
            root = root.left;
        }
        return root.val;
    }

    public int predecessor(TreeNode root) {
        root = root.left;
        while (root.right != null) {
            root = root.right;
        }
        return root.val;
    }

    public TreeNode deleteNode (TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (key > root.val) {
            root.right = deleteNode(root.right, key);
        } else if (key < root.val) {
            root.left = deleteNode(root.left, key);
        } else {
            if (root.left == null && root.right == null) {
                return null;
            } else if (root.right != null) {
                root.val = successor(root);
                root.right = deleteNode(root.right, root.val);
            } else {
                root.val = predecessor(root);
                root.left = deleteNode(root.left, root.val);
            }
        }
        return root;
    }
}
