import java.util.Iterator;

class Lenkeliste<T> implements Liste<T> {
    // Variabler som tar vare paa start-node og stoerrelse
    protected Node start = null;
    protected int stoerrelse = 0;

    // Klasse for noder som lenkes sammen
    class Node {
        Node neste = null;
        T data;

        Node(T x) {
          data = x;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    // Klasse for aa kunne lage Iterator-objekt til Lenkeliste
    class LenkelisteIterator implements Iterator<T> {
        int indeks = 0;

        @Override
        public boolean hasNext() {
            return (indeks < stoerrelse());
        }

        @Override
        public T next() {
            return hent(indeks++);
        }

        @Override
        public void remove() {
            fjern();
        } 
    }

    // Returerer ett nytt Iterator-objekt
    @Override
    public Iterator<T> iterator() {
        return new LenkelisteIterator();
    }

    // Hjelpemetode som returnerer noden en plass foran pos
    private Node finnNode(int pos) {
        Node nodeForan = start;
        for (int i = 0; i < pos - 1; i++) {
            nodeForan = nodeForan.neste;
        }
        return nodeForan;
    }

    // Hjelpemetode for aa sjekke om indeksen er gyldig
    private void sjekkIndeks(int pos) throws UgyldigListeIndeks {
        if (pos < 0 || pos >= stoerrelse) {
            throw new UgyldigListeIndeks(pos);
        }
    }

    // Returnerer antall elementer i lista
    public int stoerrelse() {
        return stoerrelse;
    }

    // Legger til element paa slutten av lista
    public void leggTil(T x) {
        Node nyNode = new Node(x);
        if (start == null) {
            start = nyNode;
        } else {
            finnNode(stoerrelse).neste = nyNode;
        }
        stoerrelse++;
    }

    // Legger inn element paa gitt indeks
    public void leggTil(int pos, T x) throws UgyldigListeIndeks {
        // Kan legge til element paa indeks == stoerrelse, skal ikke kastes unntak
        if (pos != stoerrelse) {
            sjekkIndeks(pos);
        }
        Node nyNode = new Node(x);
        if (pos == 0) {
            nyNode.neste = start;
            start = nyNode;
        } else {
            Node nodeForan = finnNode(pos);
            nyNode.neste = nodeForan.neste;
            nodeForan.neste = nyNode;
        }
        stoerrelse ++;
    }

    // Setter element paa gitt indeks, overskriver det som var der
    public void sett(int pos, T x) throws UgyldigListeIndeks {
        sjekkIndeks(pos);
        finnNode(pos + 1).data = x;
    }

    // Returnerer element paa gitt indeks
    public T hent(int pos) throws UgyldigListeIndeks {
        sjekkIndeks(pos);
        return finnNode(pos + 1).data;
    }

    // Fjerner og returnerer foerste element i listen
    public T fjern() throws UgyldigListeIndeks {
        if (start == null) {
            sjekkIndeks(-1);
        }
        Node slettDenne = start;
        start = start.neste;
        stoerrelse --;
        return slettDenne.data;
    }

    // Fjerner og returnerer element paa gitt indeks
    public T fjern(int pos) throws UgyldigListeIndeks {
        sjekkIndeks(pos);
        Node slettDenne = start;
        if (pos == 0) {
            start = start.neste;
        } else {
            Node nodeForan = finnNode(pos);
            slettDenne = nodeForan.neste;
            nodeForan.neste = slettDenne.neste;
        }
        stoerrelse --;
        return slettDenne.data;
    }

    // * Spoersmaal: Burde denne heller vaere en metode i Rute/Labyrint??
    // Har puttet den her siden da fungerer den for alle lister,
    // uavhengig av hvordan type de inneholder

    // Lager og returner en identisk liste som self, med det nye elementet lagt til
    public Lenkeliste<T> kopierListe(T x) {
        Lenkeliste<T> ny = new Lenkeliste<T>();
        Node denne = start;
        while (denne != null) {
            ny.leggTil(denne.data);
            denne = denne.neste;
        }
        ny.leggTil(x);
        return ny;
    }

    public boolean finnesILista(T x) {
        Node denne = start;
        while (denne != null) {
            if (denne.data == x) {
                return true;
            }
            denne = denne.neste;
        }
        return false;
    }

    // String-representasjon paa formen "((T x).toString) -> ((T x).toString)"
    @Override
    public String toString() {
        if (start == null) {
            return "";
        }
        String str = "";
        Node denne = start;
        while (denne != null) {
            str += denne.toString();
            if (denne.neste != null) {
                str += " --> ";
            }
            denne = denne.neste;
        }
        return str;
    }
}