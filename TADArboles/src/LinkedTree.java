import material.Position;
import org.junit.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This class represents a tree data structure using a linked implementation.
 * It implements the NAryTree interface.
 *
 * @param <E> the type of element stored in the tree
 */
public class LinkedTree<E> implements NAryTree<E> {

    /**
     * This class represents a node in a tree data structure.
     * It implements the Position interface.
     *
     * @param <T> the type of element stored in the node
     */
    private TreeNode<E> arbol;
    private int size;

    private class TreeNode<T> implements Position<T>{

        private T element;
        private TreeNode<T> parent;
        private LinkedList<TreeNode<T>> hijos;

        public TreeNode(TreeNode<T> parent, T element, LinkedList<TreeNode<T>> hijos) {
            this.parent = parent;
            this.element = element;
            this.hijos = hijos;
        }

        public TreeNode(TreeNode<T> parent, T element) {
            this.parent = parent;
            this.element = element;
            this.hijos = new LinkedList<TreeNode<T>>();
        }

        public Position<T> getParent() {
            return parent;
        }

        public List<TreeNode<T>> getHijos() {
            return hijos;
        }

        public void setElement(T element) {
            this.element = element;
        }

        public void setParent(TreeNode<T> parent) {
            this.parent = parent;
        }

        public void setHijos(LinkedList<TreeNode<T>> hijos) {
            this.hijos = hijos;
        }

        public boolean vacio(){
            return (this.element == null) && (this.parent == null) && (this.hijos == null);
        }

        @Override
        public T getElement() {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            return this.element;
        }

    }

    public LinkedTree(TreeNode<E> arbol) {
        this.arbol = arbol;
        this.size = 0;
    }

    /** Check if a given position is valid and return the corresponding TreeNode.
     *
     * @param p The position to check
     * @return The corresponding TreeNode
     * @throws RuntimeException If the position is invalid
     */
    private TreeNode<E> checkPosition(Position<E> p) {
        if (!(p instanceof TreeNode)) {
            throw new RuntimeException("The position is invalid");
        }
        return (TreeNode<E>) p;
    }

    public LinkedTree(){

    }

    @Override
    public Position<E> addRoot(E e) {
        //throw new UnsupportedOperationException("Not supported yet.");
        if (this.isEmpty()){
            TreeNode<E> raiz = new TreeNode<E> (null, e, new LinkedList<TreeNode<E>>());
            this.size = 1;
            this.arbol = raiz;
            return this.arbol;
        }
        throw new RuntimeException("No puedes meter una raíz si ya hay una raíz anterior");
    }

    @Override
    public Position<E> add(E element, Position<E> p) {
        //throw new UnsupportedOperationException("Not supported yet.");
        TreeNode<E> parent = checkPosition(p);
        TreeNode<E> newNode = new TreeNode<E>(parent, element);
        parent.getHijos().add(newNode);
        this.size++;
        return newNode;
    }

    @Override
    public Position<E> add(E element, Position<E> p, int n) {
        //throw new UnsupportedOperationException("Not supported yet.");
        TreeNode<E> parent = checkPosition(p);
        checkPositionOfChildrenList(n, parent);
        TreeNode<E> newNode = new TreeNode<E>(parent, element);
        parent.getHijos().add(newNode);
        this.size++;
        return newNode;
    }


    /**
     * Check if a given position is valid for the children list of a TreeNode.
     *
     * @param n      The position to check
     * @param parent The parent TreeNode
     * @throws RuntimeException If the position is invalid
     */
    private static <E> void checkPositionOfChildrenList(int n, LinkedTree<E>.TreeNode<E> parent) {
        if (n < 0 || n > parent.getHijos().size()) {
            throw new RuntimeException("The position is invalid");
        }
    }


    @Override
    public void swapElements(Position<E> p1, Position<E> p2) {
        //throw new UnsupportedOperationException("Not supported yet.");
        TreeNode<E> node1 = checkPosition(p1);
        TreeNode<E> node2 = checkPosition(p2);
        E aux = node1.getElement();
        node1.setElement(node2.getElement());
        node2.setElement(aux);
    }

    @Override
    public E replace(Position<E> p, E e) {
        //throw new UnsupportedOperationException("Not supported yet.");
        TreeNode<E> node = checkPosition(p);
        E old = node.getElement();
        node.setElement(e);
        return old;
    }

    @Override
    public void remove(Position<E> p) {
        //throw new UnsupportedOperationException("Not supported yet.");
        TreeNode<E> node = checkPosition(p);
        if (node == this.arbol){
            this.arbol = null;
            size = 0;
        }else{
            TreeNode<E> parent = checkPosition(node.getParent());
            List<TreeNode<E>> hijos = parent.getHijos();
            this.size -= computeSize(node);
            hijos.remove(node);
        }
    }

    private int computeSize(TreeNode<E> node){
        int size = 1;
        for(TreeNode<E> child : node.getHijos()){
            size += computeSize(child);
        }
        return size;
    }

    @Override
    public NAryTree<E> subTree(Position<E> v) {
        //throw new UnsupportedOperationException("Not supported yet.");
        TreeNode<E> node = checkPosition(v);
        LinkedTree<E> tree = new LinkedTree<E>();
        tree.arbol = node;
        tree.size = computeSize(node);
        return tree;
    }

    @Override
    public void attach(Position<E> p, NAryTree<E> t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isEmpty() {
        //throw new UnsupportedOperationException("Not supported yet.");
        return (this.size == 0);
    }

    @Override
    public Position<E> root() {
        //throw new UnsupportedOperationException("Not supported yet.");
        return this.arbol;
    }


    @Override
    public Position<E> parent(Position<E> v) {
        throw new UnsupportedOperationException("Not supported yet.");

    }


    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isInternal(Position<E> v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isLeaf(Position<E> v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isRoot(Position<E> v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Iterator<Position<E>> iterator() {
        //throw new UnsupportedOperationException("Not supported yet.");
        // Comporbar si está vacío
        List<Position<E>> positions = new ArrayList<Position<E>>();
        breadthOrder(this.arbol, positions);
        return positions.iterator();
    }

    private void breadthOrder(TreeNode<E> node, List<Position<E>> positions) {

        if (this.arbol != null){
            List<TreeNode<E>> queue = new ArrayList<TreeNode<E>>();
            queue.add(node);
            while (!queue.isEmpty()) {
                TreeNode<E> toExplore = queue.remove(0);
                positions.add(toExplore);
                queue.addAll(node.getHijos());
            }
        }

    }

    public int size() {
        //throw new UnsupportedOperationException("Not supported yet.");
        return this.size;
    }
}
