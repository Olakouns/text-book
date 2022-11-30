package io.cassiopee.textbook.payload;

public class StatFirst {
    private long nbrTeacher;
    private long nbrField;
    private long nbrSubject;

    public StatFirst(long nbrTeacher, long nbrField, long nbrSubject) {
        this.nbrTeacher = nbrTeacher;
        this.nbrField = nbrField;
        this.nbrSubject = nbrSubject;
    }

    public long getNbrTeacher() {
        return nbrTeacher;
    }

    public void setNbrTeacher(long nbrTeacher) {
        this.nbrTeacher = nbrTeacher;
    }

    public long getNbrField() {
        return nbrField;
    }

    public void setNbrField(long nbrField) {
        this.nbrField = nbrField;
    }

    public long getNbrSubject() {
        return nbrSubject;
    }

    public void setNbrSubject(long nbrSubject) {
        this.nbrSubject = nbrSubject;
    }

    @Override
    public String toString() {
        return "StatFirst{" +
                "nbrTeacher=" + nbrTeacher +
                ", nbrField=" + nbrField +
                ", nbrSubject=" + nbrSubject +
                '}';
    }
}
