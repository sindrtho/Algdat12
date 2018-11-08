import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Utpakker {

    public static void main(String[] args) {
        Utpakker ut = new Utpakker();
        //ut.pakkut("src/komprimert", "src/dekomprimert");

    }

    public Utpakker() {
    }

    public ArrayList<Node> lesFrekvenstabell(String filnavnFrekvensTabell){
        ArrayList<Node> noder = new ArrayList<Node>();
        Innpakker innpakker = new Innpakker();
        try {


            BufferedReader br = new BufferedReader(new FileReader(filnavnFrekvensTabell));
            String st = (br.readLine());
            String[] tabellLest = st.split(",");

            for(int i = 0; i < tabellLest.length; i++){
                int verdi = Integer.parseInt(tabellLest[i]);
                if(verdi > 0){
                    Node node = new Node(i);
                    node.verdi = verdi;
                    noder.add(node);
                }
            }

            return innpakker.huffman(noder);

            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static boolean pakkut(String filinn, String filut) {
        byte[] data;
        int index = 0;
        int mengde;
        String[] bitTabell;
        final int BITSPRBYTE = 8;

        try (
                BufferedReader br = new BufferedReader(new FileReader(filinn));
                Scanner scanner = new Scanner(new File(filinn));
                DataInputStream innfil = new DataInputStream(new BufferedInputStream(new FileInputStream(filinn)));
                DataOutputStream utfil = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filut)));
        ) {
            //Leser inn ASCIItabellen og gjør om til en bitstring og legger hver bit i hver sin tabellplass (bitTabell)
            String text = scanner.useDelimiter("\\A").next();
            mengde = text.length();
            data = new byte[mengde];

            innfil.readFully(data, index, mengde);

            bitTabell = new String[data.length * BITSPRBYTE];
            int bitkode = 0;
            String bitstreng = "";
            for(int i = 0; i < data.length; i++){
                bitstreng += Integer.toString(data[i],2);
            }
            System.out.println(bitstreng);
            bitTabell = bitstreng.split("");



        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String lesAvTre(bitTabell){

    }
}


