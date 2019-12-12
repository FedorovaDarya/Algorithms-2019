@file:Suppress("UNUSED_PARAMETER")

package lesson6

import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.max

/**
 * Наибольшая общая подпоследовательность.
 * Средняя
 *
 * Дано две строки, например "nematode knowledge" и "empty bottle".
 * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
 * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
 * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
 * Если общей подпоследовательности нет, вернуть пустую строку.
 * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
 * При сравнении подстрок, регистр символов *имеет* значение.
 */


//////////////////////////////////////////////////////////
// O(first.length*second.length) по времени и по памяти //
//////////////////////////////////////////////////////////
fun longestCommonSubSequence(first: String, second: String): String {
    var res = ""
    val matrix = fillMatrix(first, second)
    var i = first.length - 1
    var j = second.length - 1
    while (i >= 0 && j >= 0) {
        when {
            first[i] == second[j] -> {
                res += first[i--]
                j--
            }
            matrix[i][j + 1] > matrix[i + 1][j] -> i--
            else -> j--
        }
    }
    return res.reversed()
}

private fun fillMatrix(first: String, second: String): Array<Array<Int>> {
    val m = Array(first.length + 1) { Array(second.length + 1) { 0 } }
    for (i in 0 until first.length - 1)
        for (j in 0 until second.length - 1) {
            if (first[i] == second[j])
                m[i + 1][j + 1] = m[i][j] + 1
            else
                m[i + 1][j + 1] = max(m[i + 1][j], m[i][j + 1])
        }
    return m;
}

/**
 * Наибольшая возрастающая подпоследовательность
 * Сложная
 *
 * Дан список целых чисел, например, [2 8 5 9 12 6].
 * Найти в нём самую длинную возрастающую подпоследовательность.
 * Элементы подпоследовательности не обязаны идти подряд,
 * но должны быть расположены в исходном списке в том же порядке.
 * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
 * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
 * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
 */

//////////////////////////////////////////////////////////
// O(list.length^2) по времени  и O(list.length) памяти //
//////////////////////////////////////////////////////////
fun longestIncreasingSubSequence(list: List<Int>): List<Int> {
    val res = ArrayList<Int>()
    if (list.isNotEmpty()) {
        val curLengths = ArrayList<Int>()
        val prevIndexes = ArrayList<Int>()
        for (i in 0 until list.size) {
            var cur = 0
            var prev = -1
            for (j in 0 until i) {
                if (list[j] < list[i] && curLengths[j] > cur) {
                    cur = curLengths[j]
                    prev = j
                }
            }
            curLengths.add(cur + 1)
            prevIndexes.add(prev)
        }
        var max = curLengths.indexOf(Collections.max(curLengths))
        while (max != -1) {
            res.add(list[max])
            max = prevIndexes[max]
        }
    }
    return res.reversed()
}

/**
 * Самый короткий маршрут на прямоугольном поле.
 * Средняя
 *
 * В файле с именем inputName задано прямоугольное поле:
 *
 * 0 2 3 2 4 1
 * 1 5 3 4 6 2
 * 2 6 2 5 1 3
 * 1 4 3 2 6 2
 * 4 2 3 1 5 0
 *
 * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
 * В каждой клетке записано некоторое натуральное число или нуль.
 * Необходимо попасть из верхней левой клетки в правую нижнюю.
 * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
 * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
 *
 * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
 */
fun shortestPathOnField(inputName: String): Int {
    TODO()
}

// Задачу "Максимальное независимое множество вершин в графе без циклов"
// смотрите в уроке 5