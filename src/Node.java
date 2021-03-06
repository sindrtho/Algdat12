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

    /* Finner koder på løvnoder ved å gå fra rotnoden
     * 1. Sjekker om noden ikke er løvnode ved at vi sjekker denne nodens tegn. Foreldrenoder (ikke løvnoder) har tegn = integer.MAX
     * 2. Hvis denne noden ikke er foreldrenode, finn kode på venstre og høyre (om de ikke er null)
     * 3. Når vi finner kode for venstre og høyre, legger vi til 0 eller 1 i koden, for å si hvilken path vi tok :)
     * 1.2 Hvis noden er løvnode, set koden til kode. Koden er sammensatt av 0 og 1.
     */
    public void finnKode(String kode){
        if(this.tegn != Integer.MAX_VALUE){
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
        if(node.verdi < this.verdi){
            return 1;
        }else if(node.verdi == this.verdi){
            if(node.tegn < 0 || this.tegn < 0){
                return 0;
            }else if(node.tegn < this.tegn){
                return  1;
            }else{
                return -1;
            }
        }
        return -1;
    }

    @Override
    public String toString(){
        return "tegn: " + this.tegn + ", ant: " + this.verdi + ", kode: " + this.kode;
    }
}