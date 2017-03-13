import java.io.*;
import java.util.*;

public class Freq {
  public static void main(String[] args) {
      Console c = System.console();
      if (c == null) {
        System.err.println("No console, exiting.");
        System.exit(1);
      }

      String str;
      String[] sArr;
      HashMap<String, Double> counts = new HashMap<>();
      HashMap<String, Double> scores = new HashMap<>();
      double tmp;
      int count;
      double unigramLangModel;

      //while(true) {
        str = c.readLine("Enter sentence: ");

        sArr = str.split(" ");
/*******************
  Q1
*******************/
        for(String s : sArr) {
          if(counts.containsKey(s)) {
            tmp = counts.get(s);
            counts.put(s, tmp + 1.0);
          } else {
            counts.put(s, 1.0);
          }
        }

        count = sArr.length;
        for(String s : counts.keySet()) {
          tmp = counts.get(s) / count;
          scores.put(s, tmp);
        }


/*******************
  Q2
*******************/
        unigramLangModel = 1.0;
        for(String s : scores.keySet()) {
          System.out.println("Score for " + s + " is: " + scores.get(s));
          unigramLangModel *= scores.get(s);
        }
        System.out.println("Score for Unigram Language Model is: " + unigramLangModel);


/*******************
  Q3
*******************/
        int n = Integer.parseInt(c.readLine("Enter n (for n-grams): "));

        LinkedList<String> nGrams = NGram.getNGram(n, str);

        System.out.println(n + "grams: ");
        for(String s : nGrams) {
          System.out.print(s + " || ");
        }

      //}
  }
}

class NGram {

  public static HashMap<Integer, LinkedList<String>> getNgrams(String s) {
    if(s == null)
      return null;

    HashMap<Integer, LinkedList<String>> output = new HashMap<>();
    String[] sArr = s.split(" ");

    int n = 1;

    LinkedList<String> ll;
    String tmpStr;

    while(n <= 4 && n <= sArr.length) {
      ll = new LinkedList<>();
      tmpStr = "";

      for(int i = 0; i < sArr.length - n + 1 ; i++) {//0 1 2 3 4
        for(int j = i; j < i + n; j++) {//j=2 : 012, 123, 234 etc
          tmpStr += sArr[j].toLowerCase() + " ";
        }
        ll.add(tmpStr.trim());
        tmpStr = "";
      }

      output.put(n-1, ll);
      n++;
    }

    return output;
  }

  public static LinkedList<String> getNGram(int n, String s) {
    HashMap<Integer, LinkedList<String>> map = getNgrams(s);
    return map.get(n-1);
  }
}
