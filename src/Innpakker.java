import java.io.*;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Scanner;

public class Innpakker {
    //http://www.iie.ntnu.no/fag/_alg/kompr/opg12.pdf
    //Bruker Huffman
    public static void main(String[] args){
        Innpakker inn = new Innpakker();

        inn.innpakk("src/testfile.txt", "src/komprimert.txt", "src/frekvens.txt");
    }

    public Innpakker(){ }

    public boolean innpakk(String filinn, String filut, String filfrekvens){
        byte[] data;
        int index = 0;
        int mengde;
        try(
            Scanner scanner = new Scanner( new File(filinn) );
            DataInputStream innfil = new DataInputStream(new BufferedInputStream(new FileInputStream(filinn)));
            DataOutputStream komprimert = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filut)));
        ){
            //Leser hele filen til string
            String text = scanner.useDelimiter("\\A").next();
            mengde = text.length();
            data = new byte[mengde];

            //Leser hele filen til bytes
            innfil.readFully(data, index, mengde);

            //Lager frekvenstabell av bytes og skriver til fil
            getFrequencies(data, filfrekvens);

            //Gjør klar til huffman
            ArrayList<Node> noder = new ArrayList<>();
            for(int i = 0; i < data.length; i++){
                //System.out.println(data[i]);
                Node n = new Node(data[i]);
                if(!noder.contains(n)){
                    noder.add(n);
                }
                else{
                    noder.get(noder.indexOf(n)).verdi++;
                }
            }
            //Lager binærtre og koder ut fra det!
            ArrayList<Node> noder2 = huffman(noder);

            //Legger løvnodene (de med koder) inn i en liste
            ArrayList<Node> løv = new ArrayList<>();
            for(Node n : noder2){
                if(n.kode != null) løv.add(n);
            }
            for(Node n : løv){
                System.out.println("tegn:" + n.tegn + ", verdi: " + n.verdi + ", kode: " + n.kode);
            }

            //Skrive koder til tegn i riktig rekkefølge
            int[] tegn = new int[løv.size()];
            String[] koder = new String[løv.size()];
            for(int i = 0; i < løv.size(); i++){
                koder[i] = løv.get(i).kode;
                tegn[i] = løv.get(i).tegn;
            }

            //Komprimere bytes
            boolean[] bits = new boolean[text.length()*16];
            int bitIndex = 0;
            for(int i = 0; i < text.length(); i++){
                String kode = "";
                for(int j = 0; j < tegn.length; j++){
                    if(text.charAt(i) == tegn[j]){
                        kode = koder[j];
                        break;
                    }
                }
                //System.out.println("booleanKoder:" + kode);
                for(int j = 0; j < kode.length(); j++){
                    if(kode.charAt(j) == '0'){
                        bits[bitIndex++] = false;
                    }else{
                        bits[bitIndex++] = true;
                    }
                }
            }

            BitSet set = new BitSet();
            for (int i = 0; i < bitIndex; i++) {
                if (bits[i]) {
                    set.set(i);
                }
            }

            byte[] myBytes = set.toByteArray();
            for(byte b: myBytes){
                String res = "Byte: ";
                res += b < 0 ? new StringBuffer("" + Integer.toString((b+256), 2)).reverse().toString() : new StringBuffer("" + Integer.toString(b, 2)).reverse().toString();
                System.out.println(res);
            }

            //Skriv ut koder
            byte[] utData = myBytes;
            System.out.println("Bytes: " + utData.length);
            index = 0;
            komprimert.write(utData, index, utData.length);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Node> huffman(ArrayList<Node> noder){
        Collections.sort(noder);

        /*System.out.println("\n*NODER*");
        for(Node n : noder){
            System.out.println(n);
        }*/

        /* Lager binærtre!
         * Lager en klone-tabell som vi kan legge til nye noder og ta ut noder vi allerede har vært hos.
         * Går så lenge klone har flere elementer enn 1 (når den har 1, er det rotnoden).
         * 1. Lager forelder med tegn=max int verdi
         * 2. Tar ut 2 noder. Listen er sortert slik at de 2 øverste nodene er de minste.
         * 3. Setter foreldrens verdi til summen av barnenodene
         * 4. Setter barnenodenes forelder til forelderen
         * 5. Setter forelderens høyre og venstre til barnenodene
         * 6. Legger til forelder i klone-lista, slik at vi kan lage foreldrenode til den og (hvis det trengs).
         * 7. Legger til forelder i noder-lista, da vi trenger hele treet i node-lista når vi skal finne rotnode (kanskje ikke nødvendig)
         * 8. Sorterer klone-lista da vi trenger å ha de minste nodene (av alle) øverst for neste runde av while-løkke.
         */
        ArrayList<Node> klone = (ArrayList<Node>) noder.clone();
        while(klone.size() > 1){
            Node forelder = new Node(Integer.MAX_VALUE);

            Node a = klone.get(0);
            klone.remove(0);
            Node b = klone.get(0);
            klone.remove(0);

            forelder.verdi = a.verdi + b.verdi; //Legger sammen de 2 minste
            a.forelder = forelder;
            b.forelder = forelder;
            forelder.venstre = a;
            forelder.høyre = b;

            klone.add(forelder);
            noder.add(forelder);
            Collections.sort(klone);
        }
        Collections.sort(noder);

        //Finner root og bruker finnKode for å lage koder i løvnodene
        Node root = noder.get(noder.size() - 1);
        root.finnKode("");

        return noder;
    }

    /*
    Tar inn en byte tabel og returnerer en int tabell med lengde 256 hvor verdien i hver index er frekvensen en bokstav
    med ascii-verdi lik indexen dukker opp i teksten.
     */
    public void getFrequencies(byte[] data, String file) {
        int[] freqs = new int[256];
        for(byte b : data) {
            //System.out.println(b);
            if(b >= 0)
                freqs[b]++;
            else
                freqs[b*-1+127]++;
        }

        try(
                PrintWriter pr = new PrintWriter(new FileWriter(file));
        ){
            String res = "";
            for(int i : freqs)
                res += i+",";
            res = res.substring(0, res.length()-1);
            pr.write(res);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
