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
            BufferedReader br = new BufferedReader(new FileReader(filinn));
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
            for(int b = 0; b < noder2.size(); b++){
                if(noder2.get(b).kode != null) {
                    System.out.println("tegn:" + noder2.get(b).tegn + ", verdi: " + noder2.get(b).verdi + ", kode: " + noder2.get(b).kode);
                }
            }

            //Legger løvnodene (de med koder) inn i en liste
            ArrayList<Node> løv = new ArrayList<>();
            for(Node n : noder2){
                if(n.kode != null) løv.add(n);
            }

            //Skrive koder til tegn i riktig rekkefølge
            int[] tegn = new int[løv.size()];
            String[] koder = new String[løv.size()];
            for(int i = 0; i < løv.size(); i++){
                koder[i] = løv.get(i).kode;
                tegn[i] = løv.get(i).tegn;
                System.out.println("kodekoder:" + koder[i]);
            }

            //Komprimere bytes
            boolean[] bits = new boolean[text.length()*4];
            int bitIndex = 0;
            for(int i = 0; i < text.length(); i++){
                String kode = "";
                for(int j = 0; j < tegn.length; j++){
                    if(text.charAt(i) == tegn[j]){
                        kode = koder[j];
                    }
                }
                //System.out.println("booleanKoder:" + kode);
                for(int j = 0; j < kode.length(); j++){
                    System.out.println("bKoder:" + kode.charAt(j));
                    if(kode.charAt(j) == '0'){
                        bits[bitIndex++] = false;
                    }else{
                        bits[bitIndex++] = true;
                    }
                }
            }

            BitSet set = new BitSet(bits.length);
            for (int i = 0; i < bits.length; i++) {
                if (bits[i]) {
                    set.set(i);
                }
            }
            for(int i = 0; i < set.length(); i++){
                System.out.println("Bit: " + set.get(i));
            }

            byte[] myBytes = set.toByteArray();
            for(byte b: myBytes){
                String res = "Byte:";
                res += b < 0 ? " " + Integer.toString((b*-1+127), 2) : " " + Integer.toString(b, 2);
                System.out.println(res);
            }


            //parse string til long
            long[] longKoder = new long[koder.length];
            for(int i = 0; i < koder.length; i++){
                longKoder[i] = Long.parseLong(koder[i]);
            }
            long[] rekkefølge = new long[text.length()];
            for(int i = 0; i < text.length(); i++){
                long kode = 0;
                for(int j = 0; j < longKoder.length; j++){
                    if(text.charAt(i) == tegn[j]){
                        kode = longKoder[j];
                    }
                }
                rekkefølge[i] = kode;
                //System.out.println("longkoder:" + kode);
            }

            //Skriv ut koder
            byte[] utData = new byte[rekkefølge.length];
            for(int i = 0; i < rekkefølge.length; i++){
                utData[i] = (byte) rekkefølge[i];
            }

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

        //Lager binærtre
        boolean ok = true;
        ArrayList<Node> klone = (ArrayList<Node>) noder.clone();
        int tegn = -1;
        while(ok){
            ArrayList<Node> nivå = new ArrayList<>();
            for(int i = 0; i < klone.size(); i+=2){
                Node forelder = new Node(tegn--);
                if(i+1 > klone.size() - 1){ //kan ikke slå sammen 2 da det bare er 1
                    nivå.add(klone.get(i));
                }else{
                    forelder.verdi = klone.get(i).verdi + klone.get(i+1).verdi; //Legger sammen de 2 største
                    klone.get(i).forelder = forelder;
                    klone.get(i+1).forelder = forelder;
                    forelder.venstre = klone.get(i);
                    forelder.høyre = klone.get(i+1);

                    nivå.add(forelder);
                    noder.add(forelder);
                }
            }
            klone = (ArrayList<Node>) nivå.clone();

            /*
            System.out.println("\n*NYTT NIVÅ*");
            for(Node n : klone){
                System.out.println(n);
            }*/
            if(klone.size() <= 1){
                ok = false;
            }
        }
        Collections.sort(noder);

        //Finner root og bruker finnKode for å lage koder i løvnodene
        Node root = noder.get(noder.size() - 1);
        root.finnKode("");

        return noder;
    }

    public void getFrequencies(byte[] data, String file) {
        int[] freqs = new int[256];
        for(byte b : data) {
            System.out.println(b);
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
