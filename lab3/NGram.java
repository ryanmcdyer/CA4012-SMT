import java.util.*;

public class NGram {

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
        for(int j = i; j < i + n; j++) {//j=2, 012, 123, 234
          tmpStr += sArr[j] + " ";
        }
        ll.add(tmpStr.trim());
        tmpStr = "";
      }

      output.put(n-1, ll);
      n++;
    }

    return output;
  }

  public static void main(String[] args) {
    HashMap<Integer, LinkedList<String>> map = getNgrams("cat sat on the mat");

    for(int i : map.keySet()) {
      System.out.println(i);
      for(String s : map.get(i)) {
        System.out.print(s + " || ");
      }
      System.out.println();
    }
  }
}
