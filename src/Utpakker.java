import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Utpakker {
    ArrayList<Node> tre = new ArrayList<Node>();

    public static void main(String[] args) {
        Utpakker ut = new Utpakker();
        //ut.utpakk("src/komprimert", "src/dekomprimert");
        System.out.println("Hallo");
    }

    public Utpakker() {
    }

    public void lesFrekvenstabell(String filnavnFrekvensTabell){
        ArrayList<Node> noder = new ArrayList<Node>();
        Innpakker innpakker = new Innpakker();
        try {


            BufferedReader br = new BufferedReader(new FileReader(filnavnFrekvensTabell));
            StringTokenizer st = new StringTokenizer(br.readLine());
            while (st.hasMoreTokens()) {
                int bokstav = Integer.parseInt(st.nextToken());
                int frekvens = Integer.parseInt(st.nextToken());
                if (frekvens > 0) {
                    Node node = new Node(bokstav);
                    node.verdi = frekvens;
                    noder.add(node);
                }
            }
            tre = innpakker.huffman(noder);

            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static boolean utpakk(String filinn, String filut) {
        byte[] data;
        int index = 0;
        int mengde;

        try (
                BufferedReader br = new BufferedReader(new FileReader(filinn));
                Scanner scanner = new Scanner(new File(filinn));
                DataInputStream innfil = new DataInputStream(new BufferedInputStream(new FileInputStream(filinn)));
                DataOutputStream utfil = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filut)));
        ) {
            //Leser inn ASCIItabellen
            String text = scanner.useDelimiter("\\A").next();
            mengde = text.length();
            data = new byte[mengde];

            innfil.readFully(data, index, mengde);



        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}


