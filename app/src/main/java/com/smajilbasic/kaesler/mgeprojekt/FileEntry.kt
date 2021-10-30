package com.smajilbasic.kaesler.mgeprojekt

class FileEntry {
    private var firstNumber: String? = null
    private var secondNumber: String? = null
    private var result: String? = null
    private var operator: Char? = null

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

    fun getFirstNumber() : String?{
        return this.firstNumber
    }
    fun setFirstNumber(number : String? ){
        this.firstNumber = number
    }
    fun getSecondNumber() : String?{
        return this.secondNumber
    }
    fun setSecondNumber(number : String? ){
        this.secondNumber = number
    }
    fun getResult() : String?{
        return this.result
    }
    fun setResult(number : String? ){
        this.result = number
    }
    fun getOperator() : Char?{
        return this.operator;
    }
    fun setOperator(operator : Char? ){
        this.operator = operator
    }

}