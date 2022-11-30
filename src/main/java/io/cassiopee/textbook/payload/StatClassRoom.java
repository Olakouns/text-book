package io.cassiopee.textbook.payload;

public class StatClassRoom {
    private long nbrTotalProgramming;
    private long nbrTotalProgramLoad;
    private long nbrTotalProEnd;

    public StatClassRoom() {
    }

    public StatClassRoom(long nbrTotalProgramming, long nbrTotalProgramLoad, long nbrTotalProEnd) {
        this.nbrTotalProgramming = nbrTotalProgramming;
        this.nbrTotalProgramLoad = nbrTotalProgramLoad;
        this.nbrTotalProEnd = nbrTotalProEnd;
    }

    public long getNbrTotalProgramming() {
        return nbrTotalProgramming;
    }

    public void setNbrTotalProgramming(long nbrTotalProgramming) {
        this.nbrTotalProgramming = nbrTotalProgramming;
    }

    public long getNbrTotalProgramLoad() {
        return nbrTotalProgramLoad;
    }

    public void setNbrTotalProgramLoad(long nbrTotalProgramLoad) {
        this.nbrTotalProgramLoad = nbrTotalProgramLoad;
    }

    public long getNbrTotalProEnd() {
        return nbrTotalProEnd;
    }

    public void setNbrTotalProEnd(long nbrTotalProEnd) {
        this.nbrTotalProEnd = nbrTotalProEnd;
    }

    @Override
    public String toString() {
        return "StatClassRoom{" +
                "nbrTotalProgramming=" + nbrTotalProgramming +
                ", nbrTotalProgramLoad=" + nbrTotalProgramLoad +
                ", nbrTotalProEnd=" + nbrTotalProEnd +
                '}';
    }
}
