public class LesFrekvensTest {
    Utpakker utpakker = new Utpakker();


    public static void main(String[] args) {
        LesFrekvensTest lesFrekvensTest = new LesFrekvensTest();
        lesFrekvensTest.utpakker.lesFrekvenstabell("src/frekvens.txt");

    }

    /*
    innpakining:
tegn:32, verdi: 1, kode: 0000
tegn:98, verdi: 1, kode: 0001
tegn:101, verdi: 1, kode: 0010
tegn:103, verdi: 1, kode: 0011
tegn:110, verdi: 1, kode: 0100
tegn:115, verdi: 1, kode: 0101
tegn:105, verdi: 2, kode: 0110
tegn:108, verdi: 2, kode: 0111
tegn:121, verdi: 2, kode: 10
tegn:99, verdi: 4, kode: 11

utpakning:
tegn:32, verdi: 1, kode: 0000
tegn:98, verdi: 1, kode: 0001
tegn:101, verdi: 1, kode: 0010
tegn:103, verdi: 1, kode: 0011
tegn:110, verdi: 1, kode: 0100
tegn:115, verdi: 1, kode: 0101
tegn:105, verdi: 2, kode: 0110
tegn:108, verdi: 2, kode: 0111
tegn:121, verdi: 2, kode: 10
tegn:99, verdi: 4, kode: 11
     */
}
