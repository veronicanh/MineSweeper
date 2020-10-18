interface Liste<T> extends Iterable<T> {
    // Returnerer antall elementer i lista
    public int stoerrelse();
    // Legger til element paa slutten av lista
    public void leggTil(T x);
    // Legger inn element paa gitt indeks
    public void leggTil(int pos, T x);
    // Setter element paa gitt indeks, overskriver det som var der
    public void sett(int pos, T x);
    // Returnerer element paa gitt indeks
    public T hent(int pos);
    // Fjerner og returnerer foerste element i listen
    public T fjern();
    // Fjerner og returnerer element paa gitt indeks
    public T fjern(int pos);
}
