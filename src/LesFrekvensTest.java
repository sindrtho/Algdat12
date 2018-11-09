public class LesFrekvensTest {
    Utpakker utpakker = new Utpakker();


    public static void main(String[] args) {
        LesFrekvensTest lesFrekvensTest = new LesFrekvensTest();
        lesFrekvensTest.utpakker.lesFrekvenstabell("src/frekvens.txt");

    }

    /*
    innpakining:
tegn:32, verdi: 1, kode: 1110
tegn:98, verdi: 1, kode: 1111
tegn:101, verdi: 1, kode: 0010
tegn:103, verdi: 1, kode: 0011
tegn:110, verdi: 1, kode: 1100
tegn:115, verdi: 1, kode: 1101
tegn:105, verdi: 2, kode: 010
tegn:108, verdi: 2, kode: 011
tegn:121, verdi: 2, kode: 000
tegn:99, verdi: 4, kode: 10

utpakning:
tegn:32, verdi: 1, kode: 1110
tegn:98, verdi: 1, kode: 1111
tegn:101, verdi: 1, kode: 0010
tegn:103, verdi: 1, kode: 0011
tegn:110, verdi: 1, kode: 1100
tegn:115, verdi: 1, kode: 1101
tegn:105, verdi: 2, kode: 010
tegn:108, verdi: 2, kode: 011
tegn:121, verdi: 2, kode: 000
tegn:99, verdi: 4, kode: 10
     */
}
