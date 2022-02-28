package hanoi.model;

public enum Rod {
    LEFT(0), MIDDLE(1), RIGHT(2);

    public int getIndex() {
        return index;
    }

    private final int index;

    private Rod(int index) {
        this.index = index;
    }
}
