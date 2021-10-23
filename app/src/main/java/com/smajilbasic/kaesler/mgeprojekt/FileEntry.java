package com.smajilbasic.kaesler.mgeprojekt;

public class FileEntry {

    private String firstNumber;
    private String secondNumber;
    private String result;
    private char operator;

    public FileEntry() {
    }

    public FileEntry(String firstNumber, String secondNumber, String result, char operator) {
        setFirstNumber(firstNumber);
        setSecondNumber(secondNumber);
        setResult(result);
        setOperator(operator);
    }

    @Override
    public String toString() {
        return  firstNumber +" "+ operator +" "+ secondNumber + '=' + result +"\n";
    }

    public String getFirstNumber() {
        return firstNumber;
    }

    public void setFirstNumber(String firstNumber) {
        this.firstNumber = firstNumber;
    }

    public String getSecondNumber() {
        return secondNumber;
    }

    public void setSecondNumber(String secondNumber) {
        this.secondNumber = secondNumber;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public char getOperator() {
        return operator;
    }

    public void setOperator(char operator) {
        this.operator = operator;
    }
}
