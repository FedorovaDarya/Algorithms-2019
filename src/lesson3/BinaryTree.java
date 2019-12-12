package lesson3;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;


// Attention: comparable supported but comparator is not
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {
    //для особого случая при удалении нода
    Node<T> lastRepl = null;

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
    List<Node<T>> nodes = new ArrayList<>();
    int position = 0 ;
    Node<T> last = null;
    private void init(Node<T> root){
        if(root != null){
            init(root.left);
            nodes.add(root);
            init(root.right);
        }
    }

    ////////////время О(n)  ресурсы О(1)/////////////// n - количество узлов графа(здесь и далее)
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
            if (parentCurr.left != null && parentCurr.left.value.compareTo(curr.value) == 0)
                parentCurr.left = null;
            if (parentCurr.right != null && parentCurr.right.value.compareTo(curr.value) ==0)
                parentCurr.right = null;
        } else if (curr.right == null){                             // 1 потомок у удаляемого
            if(curr == root)
                root = root.left;
            else{
                if (parentCurr.left != null && parentCurr.left.value.compareTo(curr.value) == 0)
                    parentCurr.left = curr.left;
                if (parentCurr.right != null && parentCurr.right.value.compareTo(curr.value) == 0)
                    parentCurr.right = curr.left;
            }
        } else if (curr.left == null){                                  // 1 потомок у удаляемого
            if(curr == root)
                root = root.right;
            else{
            if (parentCurr.left != null && parentCurr.left.value.compareTo(curr.value) == 0)
                parentCurr.left = curr.right;
            if (parentCurr.right != null && parentCurr.right.value.compareTo(curr.value) == 0)
                parentCurr.right = curr.right;
            }
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
            lastRepl = replace;
        }
        size--;
        return true;
    }



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
        private Stack<Node<T>> stack = new Stack<>();
        private BinaryTreeIterator() {
            if (root == null) {
                return;
            }
            for(Node<T> node = root; node != null; node = node.left)
                stack.push(node);
        }

        /**
         * Проверка наличия следующего элемента
         * Средняя
         */
        ///////временная сложность O(1)  ресурсоемкость О(1)///////////
        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        /**
         * Поиск следующего элемента
         * Средняя
         */
        /////////время О(n) ресурсы О(h)////////// h - высота дерева (тут и далее)
        @Override
        public T next() {
            if(!hasNext()) throw new NoSuchElementException();
            Node<T> res = stack.pop();
            Node<T> node = res;
            Node<T> n = res.right;

            if(res == lastRepl){
                if(n != null){
                    if(!stack.contains(n))
                        stack.push(n);
                    while(n.left != null) {
                        if(!stack.contains(n.left)) {
                            stack.push(n.left);
                        }
                        n = n.left;
                    }
                }
            } else  if(node.right != null){

                node = node.right;
                while (node!=null){
                    stack.push(node);
                    node = node.left;
                }
            }
            last = res;
            return res.value;
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
        /////время О(n)   ресурсы О(h)///////
        @Override
        public void remove() {
            if(last == null) throw new NoSuchElementException();
            BinaryTree.this.remove(last.value);                                   //время О(n)
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
