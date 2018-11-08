public class LesFrekvensTest {
    Utpakker utpakker = new Utpakker();


    public static void main(String[] args) {
        LesFrekvensTest lesFrekvensTest = new LesFrekvensTest();
        lesFrekvensTest.utpakker.lesFrekvenstabell("src/frekvens.txt");

    }

    /*
    innpakining:
tegn: 110, ant: 1, kode: 0101
tegn: 103, ant: 1, kode: 0100
tegn: 32, ant: 1, kode: 0011
tegn: 98, ant: 1, kode: 0010
tegn: 101, ant: 1, kode: 0001
tegn: 115, ant: 1, kode: 0000
tegn: 121, ant: 2, kode: 10
tegn: 108, ant: 2, kode: 0111
tegn: 105, ant: 2, kode: 0110
tegn: 99, ant: 4, kode: 11

utpakning:
tegn: 32, ant: 1, kode: 0101
tegn: 98, ant: 1, kode: 0100
tegn: 101, ant: 1, kode: 0011
tegn: 103, ant: 1, kode: 0010
tegn: 110, ant: 1, kode: 0001
tegn: 115, ant: 1, kode: 0000
tegn: 105, ant: 2, kode: 10
tegn: 108, ant: 2, kode: 0111
tegn: 121, ant: 2, kode: 0110
tegn: 99, ant: 4, kode: 11
     */
}
