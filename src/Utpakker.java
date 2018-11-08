import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Utpakker {

    public static void main(String[] args) {
        Utpakker ut = new Utpakker();
        ut.utpakk("src/komprimert", "src/dekomprimert");
    }

    public Utpakker() {
    }

    public static boolean utpakk(String filinn, String filut) {
        int[] frekvens;
        byte[] data;
        int index = 0;
        int index2 = 0;
        int mengde;
        int mengde2;

        try (
                BufferedReader br = new BufferedReader(new FileReader(filinn));
                Scanner scanner = new Scanner(new File(filinn));
                DataInputStream innfil = new DataInputStream(new BufferedInputStream(new FileInputStream(filinn)));
                DataOutputStream utfil = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filut)));
        ) {
            //Leser inn Frekvenstabellen
            String text = scanner.useDelimiter("\\A").next();
            mengde = text.length();
            frekvens = new int[mengde];

            //Leser inn ASCIItabellen
            String text2 = scanner.useDelimiter("\\A").next();
            mengde2 = text2.length();
            data = new byte[mengde2];

            innfil.readFully(data, index, mengde);


        }
    }
}

public class Node implements Comparable<Node> {
    public int tegn;
    public int verdi = 1; //hvor mange
    public String kode;

    public Node forelder;
    public Node venstre;
    public Node høyre;

    public Node(String kode) {
        this.kode = kode;
    }

    public void finnTegn(int tegn) {
        if (!this.kode.equals("") || this.kode != null) {

        } else {

        }
    }
}

