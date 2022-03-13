package converter

import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

fun main() {
    do {
        println("Enter two numbers in format: {source base} {target base} (To quit type /exit)")
        val selected = readLine()!!
        if (selected != "/exit") {
            val (sourceBase, targetBase) = selected.split(" ").map{ it.toInt()}
            subMenu(sourceBase, targetBase)
        }
    } while (selected != "/exit")
}

fun subMenu(sourceBase: Int, targetBase: Int) {
    do {
        println("Enter number in base $sourceBase to convert to base $targetBase (To go back type /back)")
        val selected = readLine()!!
        if (selected != "/back") {
            val arr = selected.split(".").toTypedArray()
            var res: String
            if (arr.size == 1)
                res = convertFromDecimal(convertToDecimal(arr[0].reversed(), sourceBase), BigInteger("$targetBase"))
            else {
                res = if (arr[0] == "0") "0"
                else
                    convertFromDecimal(convertToDecimal(arr[0].reversed(), sourceBase), BigInteger("$targetBase"))
                res += ".${convertFractionalFromDecimal(convertFractionalToDecimal(arr[1], sourceBase), targetBase)}"
            }
            println("Conversion result: $res")
        }
    } while (selected != "/back")
}

fun convertFromDecimal(num: BigInteger, base: BigInteger): String {
    var res = ""
    var n = num
    while (n >= base) {
        res += remToHex((n % base).toInt(), base.toInt())
        n /= base
    }
    if (n != BigInteger.ZERO) res += remToHex((n % base).toInt(), base.toInt())
    return res.reversed()
}

fun remToHex(rem: Int, base: Int): Char {
    var res = "$rem".first()
    if (base in 10..36 && rem in 10..36) {
        res = 'A' + (rem - 10)
    }
    return res
}

fun convertToDecimal(num: String, base: Int): BigInteger {
    var sum = BigInteger.ZERO
    for (i in num.indices) {
        val n = if (base == 16 && num[i].uppercaseChar() in 'A'..'F')
            remHexToDecimal(num[i])
        else
            Character.getNumericValue(num[i])
        sum += BigInteger("$n") * base.toBigInteger().pow(i)
    }
    return sum
}

fun remHexToDecimal(c: Char) = if (c in '0'..'9') Character.getNumericValue(c) else c.uppercaseChar() - 'A' + 10

fun convertFractionalToDecimal(f: String, base: Int): BigDecimal {
    if (base == 10) return BigDecimal("0.$f")
    if (f == "0") return BigDecimal.ZERO
    var sum = BigDecimal.ZERO
    val bdSourceBase = BigDecimal(base)
    for (i in f.indices) {
        val a = remHexToDecimal(f[i].uppercaseChar()).toBigDecimal().setScale(15, RoundingMode.HALF_UP)
        sum += a / bdSourceBase.pow(i+1)
    }
    return sum
}

fun convertFractionalFromDecimal(f: BigDecimal, base: Int): String {
    if (f == BigDecimal.ZERO) return "00000"
    if (base == 10) return "${f.setScale(5, RoundingMode.HALF_UP)}".replace("0.", "")
    var res = ""
    var frac = f
    val bdTargetBase = BigDecimal(base)
    for (i in 1..5) {
        val x = frac * bdTargetBase
        val d = x.toInt()
        val r = x - d.toBigDecimal()
        res += "${remToHex(d, base)}"
        frac = r
        if (r == BigDecimal.ZERO) break
    }
    return res
}
