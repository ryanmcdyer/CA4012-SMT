import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class Bleu {
  public static double bleuScore(LinkedList<String> ref, String out) {

    double allPrecisions = precisionForAllNgrams(ref, out);

    String[] rArr = out.split(" ");

    //String[] rArr = ref.split(" ");
    String[] oArr = out.split(" ");

    double brevPenalty = 1.0;

    if(rArr.length > oArr.length){
      brevPenalty = (double) oArr.length/(double) rArr.length;
    }

    System.out.println("Brev: " + brevPenalty);

    return brevPenalty * Math.sqrt(Math.sqrt(allPrecisions));
  }

  private static double precisionForAllNgrams(LinkedList<String> refs, String out) {
    //HashMap<Integer, LinkedList<String>> refGrams = new HashMap<Integer, LinkedList<String>>;

    LinkedList<HashMap <String, LinkedList<String>>> refGrams = new LinkedList<>();


    for(String s : refs) {
      refGrams.add(NGram.getNgrams(s));
    }

    HashMap<Integer, LinkedList<String>> outGrams = NGram.getNgrams(out);


    double[] precision = {1.0, 1.0, 1.0, 1.0};
    LinkedList<String> tmpRef = new LinkedList<>();
    LinkedList<String> tmpOut = new LinkedList<>();
    double count, length, maxCount;

    for(int i = 1; i <= Collections.max(outGrams.keySet()) + 1; i++) {

      tmpRef = refGrams.get(i-1);
      tmpOut = outGrams.get(i-1);

      count = 0;
      length = (double) tmpOut.size();

      maxCount = 999999.0;
      for(String s : tmpOut) {
        maxCount = Collections.frequency(tmpRef, s);
        if(tmpRef.contains(s)) {
          count++;
        }
      }

      if(count > maxCount)
        count = maxCount;

      System.out.println("precision[" + (i-1) + "] is : " + (count/length));

      precision[i-1] = count/length;
    }

    return precision[0] * precision[1] * precision[2] * precision[3];
  }

  public static void main(String[] args) {

    LinkedList<String> refs = new LinkedList<>();
    String out = "";

    BufferedReader br;
    String line = "";
    try {
        br = new BufferedReader(new FileReader("refs.txt"));
        line = br.readLine();
        while (line != null) {
          refs.add(line);
          line = br.readLine();
        }
        refs.add(line);

        br.close();
    } catch (Exception e) {
      ;
    }
    line = "";

    try {
        br = new BufferedReader(new FileReader("out.txt"));
        out = br.readLine();
        br.close();
    } catch (Exception e) {
      ;
    }

/*
    String ref = "The gunman was shot dead by the police .";
    String out = "The gunman was shot dead by police .";*/
    System.out.println(bleuScore(refs, out));
  }
}
