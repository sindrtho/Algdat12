import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Innpakker {
    //http://www.iie.ntnu.no/fag/_alg/kompr/opg12.pdf
    //Bruker Huffman
    public static void main(String[] args){
        Innpakker inn = new Innpakker();

        inn.innpakk("src/testfile", "src/komprimert.txt");
    }

    public Innpakker(){ }

    public boolean innpakk(String filinn, String filut){
        byte[] data;
        int index = 0;
        int mengde;
        try(
            BufferedReader br = new BufferedReader(new FileReader(filinn));
            Scanner scanner = new Scanner( new File(filinn) );
            DataInputStream innfil = new DataInputStream(new BufferedInputStream(new FileInputStream(filinn)));
            DataOutputStream komprimert = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filut)));
        ){
            String text = scanner.useDelimiter("\\A").next();
            mengde = text.length();
            data = new byte[mengde];

            innfil.readFully(data, index, mengde);

            ArrayList<Node> løv = huffman(data); //Lager binærtre og koder ut fra det!
            String[] koder = new String[løv.size()];
            for(int i = 0; i < løv.size(); i++){
                koder[i] = løv.get(i).kode;
            }
            long[] temp = new long[koder.length];
            for(int i = 0; i < koder.length; i++){
                temp[i] = Long.parseLong(koder[i]);
            }

            int[] tegn = new int[løv.size()];
            for(int i = 0; i < løv.size(); i++){
                tegn[i] = løv.get(i).tegn;
            }

            
            byte[] utData = new byte[temp.length];
            for(int i = 0; i < temp.length; i++){
                utData[i] = (byte) temp[i];
            }

            index = 0;
            komprimert.write(utData, index, utData.length);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private ArrayList<Node> huffman(byte[] data){
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
        Collections.sort(noder);
        System.out.println("\n*NODER*");
        for(Node n : noder){
            System.out.println(n);
        }

        boolean ok = true;
        ArrayList<Node> klone = (ArrayList<Node>) noder.clone();
        int tegn = -1;
        while(ok){
            ArrayList<Node> nivå = new ArrayList<>();
            for(int i = klone.size() - 1; i >= 0;){
                Node forelder = new Node(tegn--);
                if(i+1 > klone.size() - 1){ //kan ikke slå sammen 2 da det bare er 1
                    nivå.add(klone.get(i));
                    i--;
                }else{
                    forelder.verdi = klone.get(i).verdi + klone.get(i+1).verdi; //Legger sammen de 2 største
                    klone.get(i).forelder = forelder;
                    klone.get(i+1).forelder = forelder;
                    forelder.venstre = klone.get(i);
                    forelder.høyre = klone.get(i+1);

                    nivå.add(forelder);
                    noder.add(forelder);
                    i-=2;
                }
            }
            klone = (ArrayList<Node>) nivå.clone();

            System.out.println("\n*NYTT NIVÅ*");
            for(Node n : klone){
                System.out.println(n);
            }
            if(klone.size() <= 1){
                ok = false;
            }
        }
        Collections.sort(noder);

        Node root = noder.get(0);
        root.finnKode("");

        ArrayList<Node> løv = new ArrayList<>();
        for(Node n : noder){
            if(n.kode != null) løv.add(n);
        }

        System.out.println("\n*NODER*");
        for(Node n : løv){
            System.out.println(n);
        }
        return løv;
    }

    public class Node implements Comparable<Node>{
        public int tegn;
        public int verdi = 1; //hvor mange
        public String kode;

        public Node forelder;
        public Node venstre;
        public Node høyre;

        public Node(int tegn){
            this.tegn = tegn;
        }

        public void finnKode(String kode){
            if(this.tegn >= 0){
                this.kode = kode;
            }else{
                if(venstre != null){
                    venstre.finnKode(kode + "0");
                }
                if(høyre != null){
                    høyre.finnKode(kode + "1");
                }
            }
        }

        @Override
        public boolean equals(Object o){
            if(o == this) return true;
            if(!o.getClass().equals(this.getClass())) return false;
            Node n = (Node) o;
            if(n.tegn == this.tegn) return true;
            return false;
        }

        public int compareTo( Node node ){
            if(node == this) return 0;
            if(node.verdi > this.verdi) return 1;
            return -1;
        }

        @Override
        public String toString(){
            return "tegn: " + this.tegn + ", ant: " + this.verdi + ", kode: " + this.kode;
        }
    }
}
