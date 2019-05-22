import java.util.ArrayList;

public class brdzwa {
    class Wezel {
        ArrayList<Integer> klucze = new ArrayList<Integer>();
        ArrayList<Wezel> syny = new ArrayList<Wezel>();
        int rozmiar, stopien;

        public Wezel(int st) {
            this.stopien = st;
            klucze.ensureCapacity(2*st-1);
            syny.ensureCapacity(2*st);
            for(int i = 0;i<(2*st);i++) syny.add(null);
            for(int i = 0;i<(2*st-1);i++) klucze.add(null);
        }
        boolean czyLisc(Wezel w) {
            if(syny.get(0)!=null) return false;
            return true;
        }
        public void odczyt() {
            int i;
            for(i=0; i< rozmiar; i++) {
                if((!czyLisc(this))&&(syny.get(i)!=null)) syny.get(i).odczyt();
                System.out.print(klucze.get(i)+" ");
            }
            if((!czyLisc(this))&&(syny.get(i)!=null)) syny.get(i).odczyt();
        }

        public Wezel szuk(int k) {
            int i = 0;
            while(i < rozmiar && k > klucze.get(i)) i++;
            if(klucze.get(i)== k) return this;
            if(czyLisc(this)) return null;
            return syny.get(i).szuk(k);
        }

        public void insertnonfull(int key) {
            int j = rozmiar -1;
            if(czyLisc(this)) {
                while(j>=0&& klucze.get(j)>key) {
                    klucze.set(j+1, klucze.get(j));
                    j--;
                }
                klucze.set(j+1,key);
                rozmiar = rozmiar + 1;
            }
            else {
                while(j>=0&& klucze.get(j)>key) j--;
                if(syny.get(j+1).rozmiar == 2* stopien -1) {
                    rozerw(j + 1, syny.get(j + 1));
                    if (klucze.get(j + 1) < key) j++;
                }
                syny.get(j+1).insertnonfull(key);
            }
        }
        public void rozerw(int i, Wezel y) {
            Wezel z = new Wezel(y.stopien);
            z.rozmiar = stopien -1;
            for(int j = 0; j< stopien -1; j++) z.klucze.set(j,y.klucze.get(j+ stopien));
            if(!czyLisc(y)) for(int j = 0; j < stopien; j++) z.syny.set(j,y.syny.get(j+ stopien));
            y.rozmiar = stopien -1;
            for(int j = rozmiar; j >= i+1; j--) syny.set(j+1, syny.get(j));
            syny.set(i+1,z);
            for (int j = rozmiar - 1; j>=i; j--) klucze.set(j+1, klucze.get(j));
            klucze.set(i,y.klucze.get(stopien -1));
            rozmiar = rozmiar +1;
        }
        public int znajdz(int k) {
            int i = 0;
            while(i< rozmiar && klucze.get(i) < k) i++;
            return i;
        }
        public void usun(int k) {
            int i = znajdz(k);
            if(i< rozmiar && klucze.get(i)==k) {
                if(czyLisc(this)) {
                    klucze.remove(i);
                    rozmiar--;
                    System.out.println("usunieto " + k + "\n");
                }
                else usun2(i);
            }
            else {
                if (czyLisc(this)) {
                    System.out.println("z pustego i salomon nie wyleje\n");
                    return;
                }
                boolean flag;
                flag = (i == rozmiar);
                if(this.syny.get(i).rozmiar < (stopien)) wypełnij(i);
                if(flag && i> rozmiar) syny.get(i-1).usun(k);
                else syny.get(i).usun(k);
            }
        }

        public void usun2(int i) {
            int k = klucze.get(i);
            if(syny.get(i).rozmiar >= stopien) {
                int pred = poprzedni(i);
                klucze.set(i,pred);
                syny.get(i).usun(pred);
            }
            else if(syny.get(i+1).rozmiar >= stopien) {
                int successor = nast(i);
                klucze.set(i,successor);
                syny.get(i+1).usun(successor);
            }
            else {
                zcal(i);
                syny.get(i).usun(k);
            }
        }

        public int poprzedni(int i) {
            Wezel z = syny.get(i);
            while((!czyLisc(z)&&(z.syny.get(z.rozmiar)!=null))) z = z.syny.get(z.rozmiar);
            return z.klucze.get(z.rozmiar -1);
        }

        public int nast(int i) {
            Wezel z = syny.get(i+1);
            while((!czyLisc(z)&&(z.syny.get(0)!=null))) z = z.syny.get(0);
            return z.klucze.get(0);
        }
        void wypełnij(int i) {
            if(i!=0 && syny.get(i-1).rozmiar >= stopien) zabierzPoprz(i);
            else if(i!= rozmiar && syny.get(i+1).rozmiar >= stopien) zabierzNast(i);
            else {
                if(i!= rozmiar) zcal(i);
                else zcal(i-1);
            }
        }

        void zabierzPoprz(int i) {
            Wezel child = syny.get(i);
            Wezel sibling = syny.get(i - 1);
            for (int j = child.rozmiar - 1; j >= 0; j--) child.klucze.set(j + 1, klucze.get(j));
            if (!czyLisc(child)) for (int j = child.rozmiar; j >= 0; j--) child.syny.set(j + 1, child.syny.get(j));
            child.klucze.set(0, klucze.get(i - 1));
            if (!czyLisc(child)) child.syny.set(0, sibling.syny.get(sibling.rozmiar));
            klucze.set(i - 1, sibling.klucze.get(sibling.rozmiar - 1));
            child.rozmiar++;
            sibling.rozmiar--;
        }
        void zabierzNast(int i) {
            Wezel child= syny.get(i);
            Wezel sibling= syny.get(i+1);
            child.klucze.set(child.rozmiar, klucze.get(i));
            if(!czyLisc(child)) child.syny.set(child.rozmiar +1,sibling.syny.get(0));
            klucze.set(i,sibling.klucze.get(0));
            for(int j = 1; j<sibling.rozmiar; j++) sibling.klucze.set(j-1,sibling.klucze.get(j));
            if(!czyLisc(sibling)) for(int j = 1; j<=sibling.rozmiar; j++) sibling.syny.set(j-1,sibling.syny.get(j));
            child.rozmiar += 1;
            sibling.rozmiar -= 1;
        }
        void zcal(int i) {
            Wezel syn= syny.get(i);
            Wezel sibling= syny.get(i+1);
            syn.klucze.set(stopien -1, klucze.get(i));
            for (int j = 0; j<sibling.rozmiar; ++j) syn.klucze.set(j+ stopien,sibling.klucze.get(j));
            if(!czyLisc(syn)) for(int j = 0; j<=sibling.rozmiar; ++j) syn.syny.set(j+ stopien,sibling.syny.get(j));
            for(int j = i+1; j< rozmiar; ++j) klucze.set(j-1, klucze.get(j));
            for(int j = i+2; j<= rozmiar; ++j) syny.set(j-1, syny.get(j));
            syn.rozmiar += sibling.rozmiar +1;
            rozmiar--;
            sibling = null;
        }
    }

    public Wezel korz;
    public int stopien;

    public brdzwa(int st) {
        this.stopien = st;
        this.korz = null;
    }
    public void odczyt() {
        if(korz != null) korz.odczyt();
        System.out.println("\n");
    }
    public void szuk(int k) {
        if (korz != null) System.out.println("klucze " + k + "-ki: " + korz.szuk(k).klucze + "\n");
    }
    public void wstaw(int[] l) {
        for(int i:l) wstaw(i);
    }
    public void wstaw(int k) {
        if(korz == null) {
            korz = new Wezel(stopien);
            korz.klucze.set(0,k);
            korz.rozmiar =1;
        }
        else {
            if(korz.rozmiar >= 2* stopien - 1) {
                Wezel s = new Wezel(stopien);
                s.syny.set(0, korz);
                s.rozerw(0, korz);
                int j = 0;
                if(s.klucze.get(0)<k) j++;
                s.syny.get(j).insertnonfull(k);
                korz = s;
                }
            else korz.insertnonfull(k);
        }
    }

    public void usun(int k) {
        if(korz ==null) {
            System.out.println("pusty");
            return;
        }
        korz.usun(k);
        if(korz.rozmiar ==0) {
            if(korz.czyLisc(korz)) korz = null;
            else korz = korz.syny.get(0);
        }
    }

    public static void main(String[] args) {
        brdzwa jablonka = new brdzwa(3);
        int[] l = {12, 53, 29, 19, 30, 17, 10, 3, 6, 2, 65, 24, 16, 5};
        jablonka.wstaw(l);
        jablonka.odczyt();

        jablonka.szuk(3);

        jablonka.usun(1);
        jablonka.usun(16);
        jablonka.odczyt();
    }
}
