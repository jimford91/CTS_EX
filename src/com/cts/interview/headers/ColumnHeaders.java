package com.cts.interview.headers;

/**
 * Created by James on 22/02/2018.
 */
public class ColumnHeaders {

    /**
     * Evaluates the column number provided and returns the Excel column letters used for that column number.
     *
     * @param colNum the column number
     * @return the letters to used for that Excel column
     */
    public String evaluateColumnNumber(int colNum) {

        if (colNum < 1) {
            return null;
        }


        StringBuilder builder = new StringBuilder();
        int remainingNum = colNum;

        while (true) {

            int remainder = remainingNum % 26;
            remainingNum = remainingNum / 26;

            if (remainder == 0 && remainingNum == 0) {
                break;
            }

            if (remainder == 0) {
                builder.insert(0, "Z");
                remainingNum--;
            } else {
                builder.insert(0, (char) (remainder + 64));
            }
          }
        return builder.toString();
    }


}
