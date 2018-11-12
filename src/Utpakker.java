import java.io.*;
import java.util.ArrayList;

public class Utpakker {
    private int antallTegn = 0;

    public static void main(String[] args) {
        Utpakker ut = new Utpakker();
        //ut.pakkut("src/komprimert", );
        ut.pakkut("src/komprimert.txt", "src/dekomprimert.txt", "src/frekvens.txt");

    }


    public ArrayList<Node> lesFrekvenstabell(String filnavnFrekvensTabell){

        ArrayList<Node> noder = new ArrayList<Node>();
        Innpakker innpakker = new Innpakker(); //trenger en innpakker for å få huffmann metode. KUN DETTE VI BRUKER INNPAKKER TIL
        try {



            BufferedReader br = new BufferedReader(new FileReader(filnavnFrekvensTabell));
            String st = (br.readLine());
            String[] tabellLest = st.split(","); //"tabellen" vår er en liste med tall som er skilt med "," Konverteres til en 1D string tabell


            //forberedning til å lage huffmann tre: (lager nodene)
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

            //får et huffmann-tre.
            ArrayList<Node> tre =  innpakker.huffman(noder);

            //en liten system.out.println for så se nodene:
            tre.sort(Node::compareTo);
            for(int i = 0; i < tre.size(); i++){
                if(tre.get(i).kode != null) {
                    Node n = tre.get(i);
                    System.out.println("tegn:" + n.tegn + ", som char: "+ (char) n.tegn  + ", verdi: " + n.verdi + ", kode: " + n.kode);
                }
            }

            return tre;

        }catch (IOException e){
            e.printStackTrace();
            return null;
        }

    }

    public void pakkut(String filinn, String filut, String frekvensfil) {

        //metode kall til å få et huffmanntre:
        ArrayList<Node> tre = lesFrekvenstabell(frekvensfil);


        //fileinputstream leser den komprimerte dataene:
        FileInputStream fileInputStream;
        BufferedWriter bufferedWriter;
        try {
            fileInputStream = new FileInputStream(filinn);

            bufferedWriter = new BufferedWriter(new FileWriter(filut));

            byte[] byteTabell = new byte[(int) new File(filinn).length()]; //denne byte-tabellen skal benyttes til å lagre alle bytene fra den komprimerte byten.



            //fileInputStream.read(byteTabell): leser ut bytes og stapper de inn i byteTabell:
            fileInputStream.read(byteTabell);

//            System.out.println("skriver ut bytsene");

            //string komprimert er alle bytesene som er kombinert til en lang string
            String komprimert = "";
            for(int i  = 0; i < byteTabell.length; i++ ){
                int b = 0;
                if(byteTabell[i] < 0){
                    //plusse på 256 for å ta høyde for negative bytes:
                    b = byteTabell[i] & 0xFF;
                }else{
                    b = byteTabell[i];
                }

                //henter ut int verdien til b:
                String temp = "" + Integer.toString(b, 2);

                //flipper svaret rundt fordi svaret er flippet rundt i innpakkker:
                temp = new StringBuffer(temp).reverse().toString();

                //legger på nuller siden inten som dannes kan være mindre enn 8 siffer:
                for (int g = temp.length(); g <8; g++ ){
                    temp += "0";
                }

                //legger den ene bytes strengen til hovedstrengen:
                komprimert += temp;
//                System.out.println(temp);


            }

//            System.out.println(komprimert);

            //metodekall: resultat er string-teksten som skal skrives ut til fil:
            String resultat = lesAvTre(tre , komprimert);
            bufferedWriter.write(resultat, 0, resultat.length());

            bufferedWriter.close();



        }catch (IOException e){
            e.printStackTrace();
        }

    }

    //Denne metoden tar bit for bit fra bitTabellen og går igjennom treet
    public String lesAvTre(ArrayList<Node> tre, String bitTabell){
        String utText = "";

        //Finner root noden
        Node root = tre.get(tre.size()-1); //litt festlig, compareTo metoden vår setter treet i feil rekkefølge, dermed er rota siste element i lista
//        System.out.println("rotnode verdi: " + root.verdi + ", antall tegn: " + antallTegn);
        Node neste = root; //Setter startnode som rootnoden altså vi starter på toppen av treet
        int teller = 0; //skal sørge for at vi ikke leser flere bits enn vi har tegn.
//        System.out.println("bitTabell.length():" + bitTabell.length());

        //Går igjennom bitmønstrene og sammenligner med treet vårt:
        //for hver 1 eller 0 i bitmønsteret vurderer den hvor den skal gå i treet.
        //hver gang vi havner på en løvnode settes neste node til å være rot node, og tegnet til noden skrives ut.
        for(int i = 0; i < bitTabell.length() +1 ; i++){
            //System.out.println("teller: " + teller + ", antall tegn:" + antallTegn+ ", i: " + i);

            if(teller > antallTegn+1){
//                System.out.println("teller: " + teller + ", antall tegn:" + antallTegn);
                break;
            }

            if(neste.venstre == null || neste.høyre == null){ //Hvis noden er en løvnode
                if(neste.tegn >= 0){
                    utText += ((char) neste.tegn);
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