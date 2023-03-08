package Algorithms;

import java.util.*;

public class KMP {
  public static ArrayList<Integer> kmp(String text, String pattern) {
    ArrayList<Integer> res = new ArrayList<>();
    int pi[] = prefixFunction(pattern);
    int N = text.length(), M = pattern.length();
    int i = 0, j = 0;
    while (i < N) {
      if (text.charAt(i) == pattern.charAt(j)) {
        i++;
        j++;
        if (j == M) {
          res.add(i - M);
          j = pi[j - 1];
        }
      } else {
        if (j == 0) {
          i++;
        } else {
          j = pi[j - 1];
        }
      }
    }
    return res;
  }

  public static int[] prefixFunction(String input) {
    int N = input.length();
    int pi[] = new int[N];

    pi[0] = 0;
    int i = 1;
    int len = 0;

    while (i < N) {
      if (input.charAt(i) == input.charAt(len)) {
        pi[i++] = ++len;
      } else {
        if (len == 0) {
          pi[i++] = 0;
        } else {
          len = pi[len - 1];
        }
      }
    }

    return pi;
  }

  public static void main(String[] args) {
    System.out.println(kmp("ABC ABCDAB ABCDABCDABDE", "ABCDABD"));
  }
}
