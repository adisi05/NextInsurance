import com.nhaarman.expect.*
import org.junit.jupiter.api.Test


internal class RomanNumberValidatorKtTest{

    @Test
    fun checkEmpty() {
        expect(isRomanNumber("")).notToHold()
        expect(isRomanNumber(" ")).notToHold()
        expect(isRomanNumber("      ")).notToHold()
        expect(isRomanNumber("  \n")).notToHold()
    }

    @Test
    fun checkDifferentChars() {
        expect(isRomanNumber("1")).notToHold()
        expect(isRomanNumber("123")).notToHold()
        expect(isRomanNumber("A")).notToHold()
        expect(isRomanNumber("c")).notToHold()
        expect(isRomanNumber("x")).notToHold()
        expect(isRomanNumber("AbG")).notToHold()
        expect(isRomanNumber("1A@%a")).notToHold()
    }

    @Test
    fun checkMixedChars() {
        expect(isRomanNumber(" C")).toHold()
        expect(isRomanNumber(" I    ")).toHold()
        expect(isRomanNumber("Cc")).notToHold()
        expect(isRomanNumber("iI")).notToHold()
        expect(isRomanNumber("I5")).notToHold()
        expect(isRomanNumber("4CX")).notToHold()
        expect(isRomanNumber("XXX6")).notToHold()
        expect(isRomanNumber("XBX")).notToHold()
    }

    @Test
    fun checkRomanSuccessSingleChar() {
        expect(isRomanNumber("I")).toHold()
        expect(isRomanNumber("V")).toHold()
        expect(isRomanNumber("X")).toHold()
        expect(isRomanNumber("L")).toHold()
        expect(isRomanNumber("C")).toHold()
    }

    @Test
    fun checkRomanSuccessSingleCharManyTimes() {
        expect(isRomanNumber("II")).toHold()
        expect(isRomanNumber("III")).toHold()
        expect(isRomanNumber("XX")).toHold()
        expect(isRomanNumber("XXX")).toHold()
        expect(isRomanNumber("CC")).toHold()
        expect(isRomanNumber("CCC")).toHold()
    }

    @Test
    fun checkRomanSuccessBiggerBeforeLesser() {
        expect(isRomanNumber("VI")).toHold()
        expect(isRomanNumber("XI")).toHold()
        expect(isRomanNumber("LX")).toHold()
        expect(isRomanNumber("CL")).toHold()
        expect(isRomanNumber("CI")).toHold()
        expect(isRomanNumber("CXX")).toHold()
        expect(isRomanNumber("LXXI")).toHold()
        expect(isRomanNumber("CCCLXXXVIII")).toHold()
    }

    @Test
    fun checkRomanSuccessLesserBeforeBigger() {
        expect(isRomanNumber("IV")).toHold()
        expect(isRomanNumber("IX")).toHold()
        expect(isRomanNumber("XL")).toHold()
        expect(isRomanNumber("XC")).toHold()
    }

    @Test
    fun checkRomanSuccessCombinedCases() {
        expect(isRomanNumber("XIX")).toHold()
        expect(isRomanNumber("CCXL")).toHold()
        expect(isRomanNumber("LXXXIX")).toHold()
        expect(isRomanNumber("CCLXXIV")).toHold()
    }

    @Test
    fun checkRomanFailureLesserBeforeBigger() {
        expect(isRomanNumber("IVI")).notToHold()
        expect(isRomanNumber("XCX")).notToHold()
        expect(isRomanNumber("VX")).notToHold()
        expect(isRomanNumber("IIX")).notToHold()
        expect(isRomanNumber("LC")).notToHold()
        expect(isRomanNumber("XXC")).notToHold()
        expect(isRomanNumber("IC")).notToHold()
        expect(isRomanNumber("CLIIV")).notToHold()
    }

    @Test
    fun checkRomanFailureMoreThanThreeInRow() {
        expect(isRomanNumber("IIII")).notToHold()
        expect(isRomanNumber("XXXX")).notToHold()
        expect(isRomanNumber("CCCC")).notToHold()
        expect(isRomanNumber("VIIII")).notToHold()
        expect(isRomanNumber("XIIII")).notToHold()
    }

    @Test
    fun checkRomanFailureTooManyVL() {
        expect(isRomanNumber("VV")).notToHold()
        expect(isRomanNumber("LL")).notToHold()
        expect(isRomanNumber("XVVI")).notToHold()
        expect(isRomanNumber("CLL")).notToHold()
    }
}