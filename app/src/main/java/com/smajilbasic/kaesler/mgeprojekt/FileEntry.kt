package com.smajilbasic.kaesler.mgeprojekt

class FileEntry {
    var firstNumber: String? = null
    var secondNumber: String? = null
    var result: String? = null
    var operator = 0.toChar()

    constructor()
    constructor(firstNumber: String?, secondNumber: String?, result: String?, operator: Char) {
        this.firstNumber = firstNumber
        this.secondNumber = secondNumber
        this.result = result
        this.operator = operator
    }

    override fun toString(): String {
        return "$firstNumber $operator $secondNumber=$result\n"
    }
}