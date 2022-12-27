package com.github.sqyyy.jcougar;

public enum Slot {
    RowOneSlotOne(0, 0),
    RowOneSlotTwo(0, 1),
    RowOneSlotThree(0, 2),
    RowOneSlotFour(0, 3),
    RowOneSlotFive(0, 4),
    RowOneSlotSix(0, 5),
    RowOneSlotSeven(0, 6),
    RowOneSlotEight(0, 7),
    RowOneSlotNine(0, 8),

    RowTwoSlotOne(1, 0),
    RowTwoSlotTwo(1, 1),
    RowTwoSlotThree(1, 2),
    RowTwoSlotFour(1, 3),
    RowTwoSlotFive(1, 4),
    RowTwoSlotSix(1, 5),
    RowTwoSlotSeven(1, 6),
    RowTwoSlotEight(1, 7),
    RowTwoSlotNine(1, 8),

    RowThreeSlotOne(2, 0),
    RowThreeSlotTwo(2, 1),
    RowThreeSlotThree(2, 2),
    RowThreeSlotFour(2, 3),
    RowThreeSlotFive(2, 4),
    RowThreeSlotSix(2, 5),
    RowThreeSlotSeven(2, 6),
    RowThreeSlotEight(2, 7),
    RowThreeSlotNine(2, 8),

    RowFourSlotOne(3, 0),
    RowFourSlotTwo(3, 1),
    RowFourSlotThree(3, 2),
    RowFourSlotFour(3, 3),
    RowFourSlotFive(3, 4),
    RowFourSlotSix(3, 5),
    RowFourSlotSeven(3, 6),
    RowFourSlotEight(3, 7),
    RowFourSlotNine(3, 8),

    RowFiveSlotOne(4, 0),
    RowFiveSlotTwo(4, 1),
    RowFiveSlotThree(4, 2),
    RowFiveSlotFour(4, 3),
    RowFiveSlotFive(4, 4),
    RowFiveSlotSix(4, 5),
    RowFiveSlotSeven(4, 6),
    RowFiveSlotEight(4, 7),
    RowFiveSlotNine(4, 8),

    RowSixSlotOne(5, 0),
    RowSixSlotTwo(5, 1),
    RowSixSlotThree(5, 2),
    RowSixSlotFour(5, 3),
    RowSixSlotFive(5, 4),
    RowSixSlotSix(5, 5),
    RowSixSlotSeven(5, 6),
    RowSixSlotEight(5, 7),
    RowSixSlotNine(5, 8),

    RowSevenSlotOne(6, 0),
    RowSevenSlotTwo(6, 1),
    RowSevenSlotThree(6, 2),
    RowSevenSlotFour(6, 3),
    RowSevenSlotFive(6, 4),
    RowSevenSlotSix(6, 5),
    RowSevenSlotSeven(6, 6),
    RowSevenSlotEight(6, 7),
    RowSevenSlotNine(6, 8),

    RowEightSlotOne(7, 0),
    RowEightSlotTwo(7, 1),
    RowEightSlotThree(7, 2),
    RowEightSlotFour(7, 3),
    RowEightSlotFive(7, 4),
    RowEightSlotSix(7, 5),
    RowEightSlotSeven(7, 6),
    RowEightSlotEight(7, 7),
    RowEightSlotNine(7, 8),

    RowNineSlotOne(8, 0),
    RowNineSlotTwo(8, 1),
    RowNineSlotThree(8, 2),
    RowNineSlotFour(8, 3),
    RowNineSlotFive(8, 4),
    RowNineSlotSix(8, 5),
    RowNineSlotSeven(8, 6),
    RowNineSlotEight(8, 7),
    RowNineSlotNine(8, 8);

    public final int chestSlot;
    public final int dispenserSlot;
    public final int hopperSlot;
    private final int row;
    private final int column;

    Slot(int row, int column) {
        this.row = row;
        this.column = column;
        this.chestSlot = row * 9 + column;
        this.dispenserSlot = row * 3 + column;
        this.hopperSlot = column;
    }

    public static int getRow(int rowWidth, int slot) {
        return (slot - slot % rowWidth) / rowWidth;
    }

    public static int getColumn(int rowWidth, int slot) {
        return slot % rowWidth;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }
}
