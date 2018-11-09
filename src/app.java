public class app {
    public static void main(String[] args){
        Innpakker inn = new Innpakker();
        inn.innpakk("src/filer/opg12.txt", "src/komprimert.txt", "src/frekvens.txt");

        System.out.println("\n*UTPAKKER*\n");

        Utpakker ut = new Utpakker();
        ut.pakkut("src/komprimert.txt", "src/dekomprimert.txt", "src/frekvens.txt");
    }
}
