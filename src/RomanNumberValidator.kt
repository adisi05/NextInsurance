enum class Digit(val value: Int){
    ONE(1), FIVE(5)
}

enum class Scale(val value: Int){
    ONES(1), TENS(10), HUNDREDS(100);
    companion object {
        fun max(): Scale = HUNDREDS
        fun nextScale(scale:Scale): Scale {
            for (enum in Scale.values()) {
                if(enum.value == scale.value*10){
                    return enum
                }
            }
            return scale
        }
    }
}

enum class RomanLetter(val letter: Char, val scale: Scale, val digit: Digit) {
    I('I', Scale.ONES, Digit.ONE),
    V('V', Scale.ONES, Digit.FIVE),
    X('X', Scale.TENS, Digit.ONE),
    L('L', Scale.TENS, Digit.FIVE),
    C('C', Scale.HUNDREDS, Digit.ONE);
    companion object {
        fun contains(c: Char): Boolean = RomanLetter.values().any{it.letter == c}
    }
}

fun  isRomanNumber(str: String): Boolean{
    val strTrimmed = str.trim()
    if (strTrimmed.isEmpty()){
        return false
    }

    val allCharsAreRoman = strTrimmed.all{c -> RomanLetter.contains(c) }
    if(!allCharsAreRoman){
        return false
    }

    var index = 0

    // try to catch hundreds
    index = collectDigitsOfScale(strTrimmed, index, Scale.HUNDREDS)

    // try to catch tens
    index = collectDigitsOfScale(strTrimmed, index, Scale.TENS)

    // try to catch ones
    index = collectDigitsOfScale(strTrimmed, index, Scale.ONES)

    return index == strTrimmed.length
}

fun collectDigitsOfScale(str: String, index: Int, scale: Scale): Int{
    // check that the next letter is of the wanted scale
    val hasNext = hasLetterInIndexOfScale(str, index, scale)
    if(!hasNext){
        return index
    }
    val firstLetter = romanLetterInIndex(str, index)

    var res: Pair<Boolean,Int>
    fun Pair<Boolean, Int>.succeeded():Boolean {return this.first}
    fun Pair<Boolean, Int>.nextIndex():Int {return this.second}


    when(firstLetter.digit){
        Digit.FIVE -> {
            //collect up to three ones
            res = collectOnesOfScaleUpToAmount(str, index+1, scale, 3)
            return res.nextIndex()
        }

        Digit.ONE -> {
            //collect one of higher scale
            res = collectOneOfHigherScale(str, index+1, scale)
            if (res.succeeded()){
                return res.nextIndex()
            }
            //collect 5 of same scale
            res = collectFiveOfScale(str, index+1, scale)
            if (res.succeeded()){
                return res.nextIndex()
            }

            //collect up to two more ones
            res = collectOnesOfScaleUpToAmount(str, index+1, scale, 2)
            return res.nextIndex()
        }
    }
}

private fun romanLetterInIndex(str: String, nextIndex: Int) = RomanLetter.valueOf("${str[nextIndex]}")

fun hasLetterInIndexOfScale(str: String, index: Int, scale: Scale): Boolean{
    // check that index is in the range
    if (!hasLetterInIndex(index, str)) return false

    // check that the next letter is of the wanted scale
    val firstLetter = romanLetterInIndex(str, index)
    if(firstLetter.scale == scale ){
        return true
    }
    return false
}

private fun hasLetterInIndex(index: Int, str: String): Boolean {
    if (0 <= index && index < str.length) {
        return true
    }
    return false
}

fun collectOnesOfScaleUpToAmount(str: String, index: Int, scale: Scale, maxAmount: Int): Pair<Boolean, Int> {
    return collectDigitsOfScaleUpToAmount(str, index, scale, maxAmount, Digit.ONE)
}

fun collectFiveOfScale(str: String, index: Int, scale: Scale): Pair<Boolean, Int> {
    return collectDigitsOfScaleUpToAmount(str, index, scale, 1, Digit.FIVE)
}

fun collectOneOfHigherScale(str: String, index: Int, scale: Scale): Pair<Boolean, Int> {
    if(scale == Scale.max()){
        return Pair(false,index)
    }
    val nextScale = Scale.nextScale(scale)
    return collectOnesOfScaleUpToAmount(str, index, nextScale, 1)
}

fun collectDigitsOfScaleUpToAmount(str: String, index: Int, scale: Scale, maxAmount: Int, wantedDigit: Digit): Pair<Boolean, Int> {
    var succeeded = false
    var collected = 0
    var nextIndex = index
    var hasNext = hasLetterInIndexOfScale(str,nextIndex,scale)
    while(hasNext && collected < maxAmount){
        val letter = romanLetterInIndex(str, nextIndex)
        if (letter.digit == wantedDigit){
            succeeded = true
            collected ++
            nextIndex ++
            hasNext = hasLetterInIndexOfScale(str,nextIndex,scale)
        }
        else {
            break
        }
    }
    return Pair(succeeded,nextIndex)
}