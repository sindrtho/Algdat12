import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Utpakker {

    public static void main(String[] args) {
        Utpakker ut = new Utpakker();
        //ut.utpakk("src/komprimert", "src/dekomprimert");
        System.out.println("Hallo");
    }

    public Utpakker() {
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


