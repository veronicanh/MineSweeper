class UgyldigListeIndeks extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    UgyldigListeIndeks(int indeks) {
        super("Ugyldig indeks: " + indeks);
    }
}
