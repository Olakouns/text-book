package io.cassiopee.textbook.payload;

public class StatProgram {
    private long nbrTimeDone;
    private long nbrSeance;

    public StatProgram() {
        this.nbrTimeDone = 0;
        this.nbrSeance = 0;
    }

    public StatProgram(long nbrTimeDone, long nbrSeance) {
        this.nbrTimeDone = nbrTimeDone;
        this.nbrSeance = nbrSeance;
    }

    public long getNbrTimeDone() {
        return nbrTimeDone;
    }

    public void setNbrTimeDone(long nbrTimeDone) {
        this.nbrTimeDone = nbrTimeDone;
    }

    public long getNbrSeance() {
        return nbrSeance;
    }

    public void setNbrSeance(long nbrSeance) {
        this.nbrSeance = nbrSeance;
    }
}
