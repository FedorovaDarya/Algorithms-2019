@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson5

import java.util.*

/**
 * Эйлеров цикл.
 * Средняя
 *
 * Дан граф (получатель). Найти по нему любой Эйлеров цикл.
 * Если в графе нет Эйлеровых циклов, вернуть пустой список.
 * Соседние дуги в списке-результате должны быть инцидентны друг другу,
 * а первая дуга в списке инцидентна последней.
 * Длина списка, если он не пуст, должна быть равна количеству дуг в графе.
 * Веса дуг никак не учитываются.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Вариант ответа: A, E, J, K, D, C, H, G, B, C, I, F, B, A
 *
 * Справка: Эйлеров цикл -- это цикл, проходящий через все рёбра
 * связного графа ровно по одному разу
 */

////////////////////////////////////////////////////
///время О(vertices.size + edges.size) = O(V + E)
// ресурсы O(Vertices(queue) + list(verticies) + result(Edges) = O(V + E))
////////////////////////////////////////////////////
fun Graph.findEulerLoop(): List<Graph.Edge> {
    if (this.vertices.isEmpty()
        || this.edges.isEmpty()
    )
        return emptyList()
    for (v in vertices)
        if (getNeighbors(v).size % 2 != 0)
            return emptyList()

    val listOfAnswer = mutableListOf<Graph.Edge>()
    val edges = mutableSetOf<Graph.Edge>()
    val theEulerLoop = ArrayDeque<Graph.Vertex>()
    val verticesStack = Stack<Graph.Vertex>()
    verticesStack.add(this.vertices.first())
    edges.addAll(this.edges)

    while (verticesStack.isNotEmpty()) {
        val temp = verticesStack.last()
        for (v in this.vertices)
            if (edges.contains(getConnection(temp, v))) {
                verticesStack.push(v)
                edges.remove(getConnection(temp, v))
                break
            }
        if (temp == verticesStack.last()) {
            verticesStack.pop()
            theEulerLoop.add(temp)
        }
    }
    for (i in 0 until theEulerLoop.size - 1)
        getConnection(theEulerLoop.poll(), theEulerLoop.first)?.let { listOfAnswer.add(it) }
    return listOfAnswer
}

/**
 * Минимальное остовное дерево.
 * Средняя
 *
 * Дан граф (получатель). Найти по нему минимальное остовное дерево.
 * Если есть несколько минимальных остовных деревьев с одинаковым числом дуг,
 * вернуть любое из них. Веса дуг не учитывать.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Ответ:
 *
 *      G    H
 *      |    |
 * A -- B -- C -- D
 * |    |    |
 * E    F    I
 * |
 * J ------------ K
 */
fun Graph.minimumSpanningTree(): Graph {
    TODO()
}

/**
 * Максимальное независимое множество вершин в графе без циклов.
 * Сложная
 *
 * Дан граф без циклов (получатель), например
 *
 *      G -- H -- J
 *      |
 * A -- B -- D
 * |         |
 * C -- F    I
 * |
 * E
 *
 * Найти в нём самое большое независимое множество вершин и вернуть его.
 * Никакая пара вершин в независимом множестве не должна быть связана ребром.
 *
 * Если самых больших множеств несколько, приоритет имеет то из них,
 * в котором вершины расположены раньше во множестве this.vertices (начиная с первых).
 *
 * В данном случае ответ (A, E, F, D, G, J)
 *
 * Если на входе граф с циклами, бросить IllegalArgumentException
 *
 * Эта задача может быть зачтена за пятый и шестой урок одновременно
 */
fun Graph.largestIndependentVertexSet(): Set<Graph.Vertex> {
    TODO()
}

/**
 * Наидлиннейший простой путь.
 * Сложная
 *
 * Дан граф (получатель). Найти в нём простой путь, включающий максимальное количество рёбер.
 * Простым считается путь, вершины в котором не повторяются.
 * Если таких путей несколько, вернуть любой из них.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Ответ: A, E, J, K, D, C, H, G, B, F, I
 */


fun Graph.longestSimplePath(): Path {
    TODO()
}
