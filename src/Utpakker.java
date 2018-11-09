

import java.io.*;
import java.util.ArrayList;

public class Utpakker {
    private int antallTegn = 0;

    public static void main(String[] args) {
        Utpakker ut = new Utpakker();
        //ut.pakkut("src/komprimert", );
        ut.pakkut("src/komprimert.txt", "src/dekomprimert.txt", "src/frekvens.txt");

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
                    antallTegn += verdi;
                    noder.add(node);
                }
            }
            br.close();

            ArrayList<Node> tre =  innpakker.huffman(noder);
            tre.sort(Node::compareTo);
            for(int i = 0; i < tre.size(); i++){
                if(tre.get(i).kode != null) {
                    System.out.println("tegn:" + tre.get(i).tegn + ", verdi: " + tre.get(i).verdi + ", kode: " + tre.get(i).kode);
                }
            }
            return tre;

        }catch (IOException e){
            e.printStackTrace();
            return null;
        }

    }

    public void pakkut(String filinn, String filut, String frekvensfil) {
        ArrayList<Node> tre = lesFrekvenstabell(frekvensfil);
        FileInputStream fileInputStream;
        BufferedWriter bufferedWriter;
        try {
            fileInputStream = new FileInputStream(filinn);

            bufferedWriter = new BufferedWriter(new FileWriter(filut));

            byte[] byteTabell = new byte[(int) new File(filinn).length()];



            //fileInputStream.read(byteTabell): leser ut bytes og stapper de inn i byteTabell:
            fileInputStream.read(byteTabell);
            System.out.println("skriver ut bytsene");
            String komprimert = "";
            for(int i  = 0; i < byteTabell.length; i++ ){
                int b = 0;
                if(byteTabell[i] < 0){
                    b = byteTabell[i] + 256;
                }else{
                    b = byteTabell[i];
                }

                String temp = "" + Integer.toString(b, 2);
                temp = new StringBuffer(temp).reverse().toString();
                for (int g = temp.length(); g <8; g++ ){
                    temp += "0";
                }
                komprimert += temp;
                System.out.println(temp);


            }
            System.out.println(komprimert);
            ArrayList<Character> resultat = lesAvTre(tre , komprimert);



        }catch (IOException e){
            e.printStackTrace();
        }

    }

    //Denne metoden tar bit for bit fra bitTabellen og går igjennom treet
    public ArrayList<Character> lesAvTre(ArrayList<Node> tre, String bitTabell){
        ArrayList<Character> utText = new ArrayList<Character>();

        //Finner root noden
        Node root = tre.get(tre.size()-1); //litt festlig, compareTo metoden vår setter treet i feil rekkefølge, dermed er rota siste element i lista
        System.out.println("rotnode verdi: " + root.verdi + ", antall tegn: " + antallTegn);
        Node neste = root; //Setter startnode som rootnoden altså vi starter på toppen av treet
        int teller = 0;
        System.out.println("bitTabell.length():" + bitTabell.length());

        //Går igjennom treet
        for(int i = 0; i < bitTabell.length() +1 ; i++){
            //System.out.println("teller: " + teller + ", antall tegn:" + antallTegn+ ", i: " + i);

            if(teller > antallTegn+1){
                System.out.println("teller: " + teller + ", antall tegn:" + antallTegn);
                break;
            }

            if(neste.venstre == null || neste.høyre == null){ //Hvis noden er en løvnode
                if(neste.tegn >= 0){
                    utText.add((char) neste.tegn);
                    char a = (char) neste.tegn;
                    System.out.print(a);
                    neste = root;
                    teller++;
                }
            }
            if(i < bitTabell.length()) {
                if (bitTabell.charAt(i) == '0') {
                    neste = neste.venstre;
                } else if (bitTabell.charAt(i) == '1') {
                    neste = neste.høyre;
                }
            }

        }
        return utText;
    }
}


