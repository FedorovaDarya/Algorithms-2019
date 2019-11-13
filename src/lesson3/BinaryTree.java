package lesson3;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.security.auth.login.CredentialException;
import java.util.*;

// Attention: comparable supported but comparator is not
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        }
        else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        }
        else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    public int height() {
        return height(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    private int height(Node<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */

    ////////////время О(n)  ресурсы О(1)///////////////
    @SuppressWarnings("unchecked")
    @Override
    public boolean remove(Object o) {
        T t = (T) o;
        if (!contains(t))
            return false;
        Node<T> parentCurr = root;
        Node<T> curr = root;

        while(curr.value != t){             //ищем заменяемый нод и родитедя.    время О(n)
            parentCurr = curr;
            if(t.compareTo(curr.value) > 0)
                curr = curr.right;
            else
                curr = curr.left;
        }

        if (curr.right == null && curr.left == null){              //удаляемый это лист
            if (parentCurr.left != null && parentCurr.left.value == curr.value)
                parentCurr.left = null;
            if (parentCurr.right != null && parentCurr.right.value == curr.value)
                parentCurr.right = null;
        } else if (curr.right == null){                             // 1 потомок у удаляемого
            if(curr == root)
                root = root.left;
            else{
                if (parentCurr.left != null && parentCurr.left.value == curr.value)
                    parentCurr.left = curr.left;
                if (parentCurr.right != null && parentCurr.right.value == curr.value)
                    parentCurr.right = curr.left;
            }
        } else if (curr.left == null){                            // 1 потомок у удаляемого
                if (parentCurr.left != null && parentCurr.left.value == curr.value)
                    parentCurr.left = curr.right;
                if (parentCurr.right != null && parentCurr.right.value == curr.value)
                    parentCurr.right = curr.right;
        } else {                                                 //2 потомка у удаляемого
            Node<T> replace = curr.right;
            Node<T> parentReplace = curr;

            while(replace.left != null){           //ищем замену и её родителя. время О(h), h - высота
                parentReplace = replace;
                replace = replace.left;
            }

            if(replace.right != null && parentReplace != curr)
                parentReplace.left = replace.right;
            else if(replace.right != null )
                parentReplace.right = replace.right;
            else{
                if(parentReplace.left == replace)
                    parentReplace.left = null;
                if(parentReplace.right == replace)
                    parentReplace.right = null;
            }

            if(replace != curr.right) {
                replace.right = curr.right;
            }

            if(curr == root){
                root = replace;
                replace.left = curr.left;
            } else {
                if(parentCurr.left == curr)
                    parentCurr.left = replace;
                else
                    parentCurr.right = replace;
                replace.left = curr.left;
            }
        }
        size--;
        return true;
    }

//    private Node<T> removeHelper(Node<T> r,Node<T> prev, T s){
//        System.out.println(r.value);
//
//
//        if(r.value.compareTo(s) > 0)
//            r.left = removeHelper(r.left,r, s);
//
//        else if(r.value.compareTo(s) < 0)
//            r.right = removeHelper(r.right,r, s);
//
//        //нашли нужный нод
//        else{
//            Node<T> newNode;
//            if(r.right != null && r.left != null){
//                System.out.println("Here, 2 childs,  at value " + r.value + " deleting the " + s);
//                Node<T> min = findMin(r.right);
//                newNode = new Node<>(min.value);
//                Node<T> newLeft = r.left;
//                Node<T> newRight = (min.value == r.right.value)? r.right.right : r.right;
//                r = newNode;
//                newNode.left = newLeft;
//                newNode.right = newRight;
//                if(s == root.value) {
//                    root = newNode;
//
//                }
//                if(newNode.right != null)
//                    removeHelper(r.right,r,min.value);
//            }
//            else if(r.right != null) {
//                r = r.right;
//                if(root.value == s)
//                    root = r.right;
//            }
//            else if(r.left != null) {
//                r = r.left;
//                if(s == root.value)
//                    root = r.left;
//            }
//            else
//                r = null;
//        }
//        return r;
//    }
//
//    private Node<T> findMin(Node<T> r){
//        if(r.left == null)
//            return r;
//        else
//            return findMin(r.left);
//    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        }
        else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        }
        else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {
        Node<T> last;
    List<Node<T>> nodes;
    int position ;
        private BinaryTreeIterator() {
           nodes = new LinkedList<>();
           init(root);
           position = 0;
           last = null;
        }

        private void init(Node<T> root){
            if(root != null){
                init(root.left);
                nodes.add(root);
                init(root.right);
            }
        }

        /**
         * Проверка наличия следующего элемента
         * Средняя
         */
        ///////временная сложность O(1)  ресурсоемкость О(n)///////////
        @Override
        public boolean hasNext() {
            return position < nodes.size();
        }

        /**
         * Поиск следующего элемента
         * Средняя
         */
        /////////время О(n) ресурсы О(n)//////////
        @Override
        public T next() {
            if(!hasNext()) throw new NoSuchElementException();
            last = nodes.get(position);
            position++;
            return last.value;
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
        /////время О(n)   ресурсы О(n)///////
        @Override
        public void remove() {
            BinaryTree.this.remove(nodes.get(--position).value);  //O(n)
            nodes.remove(position); //O(n)
        }
    }


    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        // TODO
        throw new NotImplementedError();
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}
