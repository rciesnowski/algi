package com.company;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Huffman {
	private String sciezka;
	private SingleChar root;
	private int rozmiarKodu;
	private ArrayList<SingleChar> chars = new ArrayList<>();

	Huffman(String p) throws IOException {
		this.wezPlik(p);
		this.rozmiarKodu(0);
        this.tablicuj();
        this.drzewko();
        this.wypelnijTablice();
        this.drukowanko();
	}

    public void wezPlik(String s) {
        this.sciezka = s;
    }
    public void rozmiarKodu(int rK) {
        this.rozmiarKodu = rK;
    }
    public void tablicuj() throws IOException {
        FileReader fr= new FileReader(this.sciezka());
        int i;
        while((i=fr.read())!=-1) if(!this.znajdzZnak((char) i)) chars.add(new SingleChar((char) i));
        fr.close();
    }
    public String sciezka() {
        return sciezka;
    }
    public boolean znajdzZnak(char c) {
        for (int i = 0; i < chars.size(); i++) if (chars.get(i).znak() == c) {
            chars.get(i).ilosc(chars.get(i).ilosc() + 1);
            return true;
        }
        return false;
    }
    private class SingleChar{
        private int ilosc;
        private char znak;
        private String kod;
        private SingleChar lewy;
        private SingleChar prawy;
        SingleChar(){}
        SingleChar(char c){
            this.ilosc(1);
            this.znak(c);
        }
        public int ilosc() { return ilosc; }
        public void ilosc(int ilosc) { this.ilosc = ilosc; }
        public char znak() { return znak; }
        public void znak(char z) { this.znak = z; }
        public String kod() { return kod; }
        public void kod(String k) { this.kod = k; }
        public SingleChar lewy() { return lewy; }
        public void lewy(SingleChar l) { this.lewy = l; }
        public SingleChar prawy() { return prawy; }
        public void prawy(SingleChar p) { this.prawy = p; }
    }
    public void drzewko() {
        while(this.chars.size()>1) {
            SingleChar sc1=this.najmniejszy();
            chars.remove(sc1);
            SingleChar sc2=this.najmniejszy();
            chars.remove(sc2);
            SingleChar node=new SingleChar();
            node.ilosc(sc1.ilosc()+sc2.ilosc());
            node.lewy(sc1);
            node.prawy(sc2);
            chars.add(node);
        }
        root = this.chars.get(0);
        chars.clear();
        koduj(root,  "");
    }
    public SingleChar najmniejszy() {
        SingleChar sc=this.chars.get(0);
        for(int i=0;i<this.chars.size();i++) if(chars.get(i).ilosc()<sc.ilosc()) sc=chars.get(i);
        return sc;
    }
    private void koduj(SingleChar tmproot, String str) {
        if(tmproot.lewy()==null && tmproot.prawy()==null) {
            tmproot.kod(str);
            return;
        }
        koduj(tmproot.lewy(), str+"0");
        koduj(tmproot.prawy(), str+"1");
    }
    public void wypelnijTablice() {
        wypelnijTablice(root);
        posortuj();
    }
    private void wypelnijTablice(SingleChar tmp) {
        if(tmp.lewy()==null && tmp.prawy()==null) {
            chars.add(tmp);
            this.rozmiarKodu(this.rozmiarKodu()+tmp.ilosc()*tmp.kod().length());
            return;
        }
        wypelnijTablice(tmp.lewy());
        wypelnijTablice(tmp.prawy());
    }
    public int rozmiarKodu() {
        return rozmiarKodu;
    }
    private void posortuj() {
        for(int i=0;i<chars.size()-1;i++) for(int j=i+1;j<chars.size();j++) if(chars.get(j).ilosc()>chars.get(i).ilosc()) {
            SingleChar tmp=chars.get(j);
            chars.set(j, chars.get(i));
            chars.set(i, tmp);
        }
    }
    public void drukowanko() {
        for(int i = 0; i<chars.size();i++) System.out.format("%s\t| %4d wystąpień | kod %8s\n", chars.get(i).znak(), chars.get(i).ilosc(), chars.get(i).kod());
        System.out.println("\ndlugosc tekstu: " + this.root().ilosc() + " | roznych znaków: " + chars.size() + " | długośc kodu: " + this.rozmiarKodu()/8 + " | kompresja: "+(int)this.kompresja()+ "%\n");
    }
    public float kompresja() {
        float m = this.rozmiarKodu();
        float n = this.root().ilosc()*8;
        return  m/n *100;
    }
    public SingleChar root() {
        return root;
    }


	public static void main(String[] args) throws IOException {
		Huffman h = new Huffman("tekst-dlugi.txt");
	}
}