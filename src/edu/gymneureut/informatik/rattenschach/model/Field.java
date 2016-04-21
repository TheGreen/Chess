package edu.gymneureut.informatik.rattenschach.model;

/**
 * The <tt>Field</tt> class.
 *
 * @author Jan Christian Gruenhage, Alex Klug
 * @version 0.1
 */
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

    private static boolean isValid(int file, int rank) {
        if (file < 1) {
            return false;
        } else if (file > 8) {
            return false;
        } else if (rank < 1) {
            return false;
        } else return rank <= 8;
    }

    public static Field parseField(String field) {
        switch (field.charAt(0)) {
            case 'A':
                return new Field(1, Integer.parseInt(field.substring(1, 2)));
            case 'B':
                return new Field(2, Integer.parseInt(field.substring(1, 2)));
            case 'C':
                return new Field(3, Integer.parseInt(field.substring(1, 2)));
            case 'D':
                return new Field(4, Integer.parseInt(field.substring(1, 2)));
            case 'E':
                return new Field(5, Integer.parseInt(field.substring(1, 2)));
            case 'F':
                return new Field(6, Integer.parseInt(field.substring(1, 2)));
            case 'G':
                return new Field(7, Integer.parseInt(field.substring(1, 2)));
            case 'H':
                return new Field(8, Integer.parseInt(field.substring(1, 2)));
            default:
                return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Field && (this.hashCode() == o.hashCode());
    }

    @Override
    public int hashCode() {
        return file + 8 * (rank - 1);
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
        return File.getName(file) + Rank.getName(rank);
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
}
