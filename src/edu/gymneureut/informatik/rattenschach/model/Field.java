package edu.gymneureut.informatik.rattenschach.model;

public class Field {
    private int file;
    private int rank;
    private boolean color;

    public Field(int file, int rank) {
        if (!isValid(file, rank)) {
            throw new IllegalArgumentException();
        }
        this.file = file;
        this.rank = rank;
        updateColor();
    }

    public static boolean isValid(int file, int rank) {
        return !(file < 1 && file > 8 && rank < 1 && rank > 8);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Field && (this.hashCode() == o.hashCode());
    }

    @Override
    public int hashCode() {
        int result = file + 8 * (rank - 1);
        return result;
    }

    private void updateColor() {
        this.color = (file + rank % 2) == 0; //zero is black
    }

    public int getFile() {
        return file;
    }

    public void setFile(int file) {
        this.file = file;
        updateColor();
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
        updateColor();
    }

    public String getName() {
        return new Rank().getName(rank) + new File().getName(file);
    }


    public static class File {
        public static final int ONE = 1;
        public static final int TWO = 2;
        public static final int THREE = 3;
        public static final int FOUR = 4;
        public static final int FIVE = 5;
        public static final int SIX = 6;
        public static final int SEVEN = 7;
        public static final int EIGHT = 8;

        public static String getName(int input) {
            switch (input) {
                case 1:
                    return "1";
                case 2:
                    return "2";
                case 3:
                    return "3";
                case 4:
                    return "4";
                case 5:
                    return "5";
                case 6:
                    return "6";
                case 7:
                    return "7";
                case 8:
                    return "8";
                default:
                    return "ERROR";
            }
        }
    }

    public static class Rank {
        public static final int A = 1;
        public static final int B = 2;
        public static final int C = 3;
        public static final int D = 4;
        public static final int E = 5;
        public static final int F = 6;
        public static final int G = 7;
        public static final int H = 8;

        public static String getName(int input) {
            switch (input) {
                case 1:
                    return "A";
                case 2:
                    return "B";
                case 3:
                    return "C";
                case 4:
                    return "D";
                case 5:
                    return "E";
                case 6:
                    return "F";
                case 7:
                    return "G";
                case 8:
                    return "H";
                default:
                    return "ERROR";
            }
        }
    }
}
