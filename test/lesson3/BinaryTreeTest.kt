package lesson3

import kotlin.test.assertFailsWith
import org.junit.jupiter.api.Tag
import java.util.*
import kotlin.NoSuchElementException
import kotlin.test.*

class BinaryTreeTest {
    private fun testAdd(create: () -> CheckableSortedSet<Int>) {
        val tree = create()
        assertEquals(0, tree.size)
        assertFalse(tree.contains(5))
        tree.add(10)
        tree.add(5)
        tree.add(7)
        tree.add(10)
        assertEquals(3, tree.size)
        assertTrue(tree.contains(5))
        tree.add(3)
        tree.add(1)
        tree.add(3)
        tree.add(4)
        assertEquals(6, tree.size)
        assertFalse(tree.contains(8))
        tree.add(8)
        tree.add(15)
        tree.add(15)
        tree.add(20)
        assertEquals(9, tree.size)
        assertTrue(tree.contains(8))
        assertTrue(tree.checkInvariant())
        assertEquals(1, tree.first())
        assertEquals(20, tree.last())
    }

    @Test
    @Tag("Example")
    fun testAddKotlin() {
        testAdd { createKotlinTree() }
    }

    @Test
    @Tag("Example")
    fun testAddJava() {
        testAdd { createJavaTree() }
    }

    private fun <T : Comparable<T>> createJavaTree(): CheckableSortedSet<T> = BinaryTree()

    private fun <T : Comparable<T>> createKotlinTree(): CheckableSortedSet<T> = KtBinaryTree()

    private fun testRemove(create: () -> CheckableSortedSet<Int>) {
        val random = Random()
        for (iteration in 1..100) {
            val list = mutableListOf<Int>()
            for (i in 1..20) {
                list.add(random.nextInt(100))
            }
            val binarySet = create()
            assertFalse(binarySet.remove(42))
            for (element in list) {
                binarySet += element
            }
            val originalHeight = binarySet.height()
            val toRemove = list[random.nextInt(list.size)]
            val oldSize = binarySet.size
            assertTrue(binarySet.remove(toRemove))
            assertEquals(oldSize - 1, binarySet.size)
            println("Removing $toRemove from $list")
            for (element in list) {
                val inn = element != toRemove
                assertEquals(
                    inn, element in binarySet,
                    "$element should be ${if (inn) "in" else "not in"} tree"
                )
            }
            assertTrue(binarySet.checkInvariant(), "Binary tree invariant is false after tree.remove()")
            assertTrue(
                binarySet.height() <= originalHeight,
                "After removal of $toRemove from $list binary tree height increased"
            )
        }
    }

    private fun myTestRemoveJava(create: () -> CheckableSortedSet<String>) {
        val set = setOf("yty", "iggy", "saturday", "field", "limb", "Fame")  //size = 6
        val binarySet = create()

        assertFalse(binarySet.remove("sunday")) //удаляем из пустого

        val temp = "111"
        binarySet.add(temp)
        assertTrue(binarySet.remove(temp))
        assertEquals(0, binarySet.size)

        for (elem in set)
            binarySet += elem

        val toRemove = "iggy"
        val oldSize = binarySet.size
        assertTrue(binarySet.remove(toRemove))
        assertFalse(binarySet.remove(toRemove))   //элеент точно удалился!
        assertEquals(oldSize - 1, binarySet.size)

        for (elem in set)
            binarySet.remove(elem)

        binarySet.remove("FOREIGN ELEM") //
        binarySet.remove("FOREIGN ELEM") //а вдруг при каждом вызове size--?
        binarySet.remove("FOREIGN ELEM") //
        assertEquals(0, binarySet.size)
    }

    @Test
    @Tag("Normal")
    fun testRemoveKotlin() {
        testRemove { createKotlinTree() }
    }

    @Test
    @Tag("Normal")
    fun testRemoveJava() {
        testRemove { createJavaTree() }
        myTestRemoveJava { createJavaTree() }
    }


    private fun testIterator(create: () -> CheckableSortedSet<Int>) {
        val random = Random()
        for (iteration in 1..100) {
            val list = mutableListOf<Int>()
            for (i in 1..20) {
                list.add(random.nextInt(100))
            }
            val treeSet = TreeSet<Int>()
            val binarySet = create()
            assertFalse(binarySet.iterator().hasNext(), "Iterator of empty set should not have next element")
            for (element in list) {
                treeSet += element
                binarySet += element
            }
            val treeIt = treeSet.iterator()
            val binaryIt = binarySet.iterator()
            println("Traversing $list")
            while (treeIt.hasNext()) {
                assertEquals(treeIt.next(), binaryIt.next(), "Incorrect iterator state while iterating $treeSet")
            }
            val iterator1 = binarySet.iterator()
            val iterator2 = binarySet.iterator()
            println("Consistency check for hasNext $list")
            // hasNext call should not affect iterator position
            while (iterator1.hasNext()) {
                assertEquals(
                    iterator2.next(), iterator1.next(),
                    "Call of iterator.hasNext() changes its state while iterating $treeSet"
                )
            }
        }
    }

    private fun myTestIterator(create: () -> CheckableSortedSet<Int>) {
        val binarySet = create()
        val list = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        list.shuffle();
        val iterator = binarySet.iterator()
        assertFalse(iterator.hasNext())
        for (i in list)
            binarySet += i
        val SIZE = list.size
        while (iterator.hasNext())
            assertNotNull(iterator.next())
        assertFailsWith<NoSuchElementException> {
            iterator.next();
        }

        val iterator2 = binarySet.iterator()
        assertEquals(iterator2.next(), 1)
        assertEquals(iterator2.next(), 2)
        assertEquals(iterator2.next(), 3)

        assertEquals(binarySet.size, SIZE)
    }

    @Test
    @Tag("Normal")
    fun testIteratorKotlin() {
        testIterator { createKotlinTree() }
    }

    @Test
    @Tag("Normal")
    fun testIteratorJava() {
        testIterator { createJavaTree() }
        myTestIterator { createJavaTree() }
    }

    private fun testIteratorRemove(create: () -> CheckableSortedSet<Int>) {
        val random = Random()
        for (iteration in 1..100) {
            val list = mutableListOf<Int>()
            for (i in 1..20) {
                list.add(random.nextInt(100))
            }
            println("list")
            val treeSet = TreeSet<Int>()
            val binarySet = create()
            for (element in list) {
                treeSet += element
                binarySet += element
            }
            val toRemove = list[random.nextInt(list.size)]
            treeSet.remove(toRemove)
            println("Removing $toRemove from $list")
            val iterator = binarySet.iterator()
            var counter = binarySet.size
            while (iterator.hasNext()) {
                val element = iterator.next()
                counter--
                print("$element ")
                if (element == toRemove) {
                    iterator.remove()
                }
            }
            assertEquals(
                0, counter,
                "Iterator.remove() of $toRemove from $list changed iterator position: " +
                        "we've traversed a total of ${binarySet.size - counter} elements instead of ${binarySet.size}"
            )
            println()
            assertEquals<SortedSet<*>>(treeSet, binarySet, "After removal of $toRemove from $list")
            assertEquals(treeSet.size, binarySet.size, "Size is incorrect after removal of $toRemove from $list")
            for (element in list) {
                val inn = element != toRemove
                assertEquals(
                    inn, element in binarySet,
                    "$element should be ${if (inn) "in" else "not in"} tree"
                )
            }
            assertTrue(binarySet.checkInvariant(), "Binary tree invariant is false after tree.iterator().remove()")
        }
    }

    @Test
    @Tag("Hard")
    fun testIteratorRemoveKotlin() {
        testIteratorRemove { createKotlinTree() }
    }

    private fun myTestIteratorRemove(create: () -> CheckableSortedSet<Int>) {
        val binarySet = create()
        val list = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        list.shuffle()
        val iterator0 = binarySet.iterator()
        assertFailsWith<NoSuchElementException> {
            iterator0.remove();
        }
        for (element in list) {
            binarySet += element
        }
        val iterator = binarySet.iterator()
        assertEquals(binarySet.size, list.size)
        iterator.next()
        iterator.remove()
        assertEquals(2, iterator.next())

        assertEquals(list.size - 1, binarySet.size)
    }

    @Test
    @Tag("Hard")
    fun testIteratorRemoveJava() {
        testIteratorRemove { createJavaTree() }
        myTestIteratorRemove { createJavaTree() }
    }
}