package edu.gymneureut.informatik.rattenschach;

public class Field {
    private int line;
    private int row;
    private boolean color;

    public Field(int line, int row) {
        if (!isValid(line, row)) {
            throw new IllegalArgumentException();
        }
        this.line = line;
        this.row = row;
        updateColor();
    }

    public static boolean isValid(int line, int row) {
        return !(line < 1 && line > 8 && row < 1 && row > 8);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Field && (this.hashCode() == o.hashCode());
    }

    @Override
    public int hashCode() {
        int result = line + 8 * (row - 1);
        return result;
    }

    private void updateColor() {
        this.color = (line + row % 2) == 0; //zero is black
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
        updateColor();
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
        updateColor();
    }

    public String getName() {
        return new Row().getName(row) + new Line().getName(line);
    }


    public static class Line {
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

    public static class Row {
        public static final int A = 1;
        public static final int B = 2;
        public static final int C = 3;
        public static final int D = 4;
        public static final int E = 5;
        public static final int F = 6;
        public static final int G = 7;
        public static final int H = 8;

        public String getName(int input) {
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
