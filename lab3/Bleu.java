import java.util.*;

public class Bleu {
  public static double bleuScore(String ref, String out) {

    double allPrecisions = precisionForAllNgrams(ref, out);

    String[] rArr = ref.split(" ");
    String[] oArr = out.split(" ");

    double brevPenalty = 1.0;

    if(rArr.length > oArr.length){
      brevPenalty = (double) oArr.length/(double) rArr.length;
    }

    System.out.println("Brev: " + brevPenalty);

    return brevPenalty * Math.sqrt(Math.sqrt(allPrecisions));
  }

  private static double precisionForAllNgrams(String ref, String out) {
    HashMap<Integer, LinkedList<String>> refGrams = NGram.getNgrams(ref);
    HashMap<Integer, LinkedList<String>> outGrams = NGram.getNgrams(out);


    double[] precision = {1.0, 1.0, 1.0, 1.0};
    LinkedList<String> tmpRef = new LinkedList<>();
    LinkedList<String> tmpOut = new LinkedList<>();
    double count, length;

    for(int i = 1; i <= Collections.max(outGrams.keySet()) + 1; i++) {

      tmpRef = refGrams.get(i-1);
      tmpOut = outGrams.get(i-1);

      count = 0;
      length = (double) tmpOut.size();

      for(String s : tmpOut) {
        if(tmpRef.contains(s)) {
          count++;
        }
      }

      System.out.println("precision[" + (i-1) + "] is : " + (count/length));

      precision[i-1] = count/length;
    }

    return precision[0] * precision[1] * precision[2] * precision[3];
  }

  public static void main(String[] args) {
    String ref = "The gunman was shot dead by the police .";
    String out = "The gunman was shot dead by police .";
    System.out.println(bleuScore(ref, out));
  }
}
