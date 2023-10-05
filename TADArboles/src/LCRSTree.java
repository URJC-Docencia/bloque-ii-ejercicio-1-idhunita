import material.Position;

import java.util.*;


/**
 * An implementation of the NAryTree interface using left-child, right-sibling representation.
 *
 * @param <E> the type of elements stored in the tree
 */
public class LCRSTree<E> implements NAryTree<E> {

    private LCRSnode<E>arbol;
    private int size;

    public LCRSTree(LCRSnode<E> arbol) {
        this.arbol = arbol;
        this.size = 0;
    }

    public LCRSTree() {
        this.arbol = new LCRSnode<E>(null, null, null, null);
        this.size = 0;
    }

    private class LCRSnode<T> implements Position<T>{

        private T element;
        private LCRSnode<T> parent;
        private LCRSnode<T> child;
        private LCRSnode<T> sibling;

        public LCRSnode(T element, LCRSnode<T> parent, LCRSnode<T> child, LCRSnode<T> sibling) {
            this.element = element;
            this.parent = parent;
            this.child = child;
            this.sibling = sibling;
        }

        public void setElement(T element) {
            this.element = element;
        }

        public void setParent(LCRSnode<T> parent) {
            this.parent = parent;
        }

        public void setChild(LCRSnode<T> child) {
            this.child = child;
        }

        public void setSibling(LCRSnode<T> sibling) {
            this.sibling = sibling;
        }

        @Override
        public T getElement() {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            return this.element;
        }

        public LCRSnode<T> getParent() {
            return parent;
        }

        public LCRSnode<T> getChild() {
            return child;
        }

        public LCRSnode<T> getSibling() {
            return sibling;
        }

        public boolean vacio(){
            return (this.element == null) && (this.parent == null) && (this.child == null) && (this.sibling == null);
        }

        public Iterable<? extends Position<T>> devuelveHijos(){
            LinkedHashSet<LCRSnode<T>> setHijos = new LinkedHashSet<LCRSnode<T>>();
            LCRSnode<T> hijo = this.getChild();
            if (hijo != null) {
                setHijos.add(hijo);
                while (hijo.getSibling() != null) {
                    hijo = hijo.getSibling();
                    setHijos.add(hijo);
                }
            }
            return setHijos;
        }

        public Collection<LCRSnode<T>> devuelveHijosCollection(){
            LinkedHashSet<LCRSnode<T>> setHijos = new LinkedHashSet<LCRSnode<T>>();
            LCRSnode<T> hijo = this.getChild();
            if (hijo != null) {
                setHijos.add(hijo);
                while (hijo.getSibling() != null) {
                    hijo = hijo.getSibling();
                    setHijos.add(hijo);
                }
            }
            return setHijos;
        }

        public Collection<Position<T>> iteratorSet(){

            LCRSnode<T> nodo = this;
            LinkedList<LCRSnode<T>> hijosSinRecorrer = new LinkedList<LCRSnode<T>>();
            LinkedHashSet<Position<T>> setHijos = new LinkedHashSet<Position<T>>();
            Collection<LCRSnode<T>> hijos = nodo.devuelveHijosCollection();
            hijosSinRecorrer.addAll(hijos);
            setHijos.add(nodo);
            setHijos.addAll(hijos);
            while (hijosSinRecorrer.size() > 0){
                LCRSnode<T> hijoActual = hijosSinRecorrer.poll();
                hijos = hijoActual.devuelveHijosCollection();
                hijosSinRecorrer.addAll(hijos);
                setHijos.addAll(hijos);
            }
            return setHijos;
        }

        public int descendientesNum(){

            LCRSnode<T> nodo = this;
            LinkedList<LCRSnode<T>> hijosSinRecorrer = new LinkedList<LCRSnode<T>>();
            int numHijos = 0;
            Collection<LCRSnode<T>> hijos = nodo.devuelveHijosCollection();
            numHijos += hijos.size();
            hijosSinRecorrer.addAll(hijos);
            while (hijosSinRecorrer.size() > 0){
                LCRSnode<T> hijoActual = hijosSinRecorrer.poll();
                hijos = hijoActual.devuelveHijosCollection();
                hijosSinRecorrer.addAll(hijos);
                numHijos += hijos.size();
            }
            return numHijos;
        }

    }

    private LCRSnode<E> checkPosition(Position<E> p) {
        if (p == null || !(p instanceof LCRSnode)) {
            throw new RuntimeException("The position is invalid");
        }
        return (LCRSnode<E>) p;
    }


    @Override
    public Position<E> addRoot(E e) {
        //throw new UnsupportedOperationException("Not supported yet.");
        if (this.arbol.vacio()) {
            LCRSnode<E> raiz = new LCRSnode<E>(e, null, null, null);
            this.arbol = raiz;
            this.size = 1;
            return raiz;
        }
        return null;
    }



    @Override
    public Position<E> add(E element, Position<E> p) {
        //throw new UnsupportedOperationException("Not supported yet.");
        LCRSnode<E> padre = checkPosition(p);
        LCRSnode<E> hijoNuevo = new LCRSnode<E>(element, padre, null, null);
        if (padre.getChild() == null){
            padre.setChild(hijoNuevo);
        }else{
            LCRSnode<E> ultimoHijo = padre.getChild();
            while(ultimoHijo.getSibling() != null){
                ultimoHijo = ultimoHijo.getSibling();
            }
            ultimoHijo.setSibling(hijoNuevo);
        }
        this.size++;
        return hijoNuevo;
    }

    @Override
    public Position<E> add(E element, Position<E> p, int n) {
        //throw new UnsupportedOperationException("Not supported yet.");
        LCRSnode<E> padre = checkPosition(p);
        LCRSnode<E> hijoNuevo = new LCRSnode<E>(element, padre, null, null);
        if (padre.getChild() == null){
            return add(element, p);
        }else if (n == 0){
            LCRSnode<E> hijos = padre.getChild();
            padre.setChild(hijoNuevo);
            hijoNuevo.setSibling(hijos);
            this.size++;
            return hijoNuevo;
        }else{
            LCRSnode<E> sigHijo = padre.getChild();
            LCRSnode<E> antHijo = padre.getChild();
            int indiceHijo = 0;
            while((sigHijo.getSibling() != null) && (indiceHijo < n)){
                antHijo = sigHijo;
                sigHijo = sigHijo.getSibling();
                indiceHijo++;
            }
            antHijo.setSibling(hijoNuevo);
            hijoNuevo.setSibling(sigHijo);
            this.size++;
            return hijoNuevo;
        }
    }

    @Override
    public void swapElements(Position<E> p1, Position<E> p2) {
        //throw new UnsupportedOperationException("Not supported yet.");
        LCRSnode<E> nodo1 = checkPosition(p1);
        LCRSnode<E> nodo2 = checkPosition(p2);
        E aux = nodo1.getElement();
        nodo1.setElement(nodo2.getElement());
        nodo2.setElement(aux);
    }

    @Override
    public E replace(Position<E> p, E e) {
        //throw new UnsupportedOperationException("Not supported yet.");
        LCRSnode<E> nodo = checkPosition(p);
        E old = nodo.getElement();
        nodo.setElement(e);
        return old;
    }

    @Override
    public void remove(Position<E> p) {
        //throw new UnsupportedOperationException("Not supported yet.");
        LCRSnode<E> borrar = checkPosition(p);
        if (isRoot(borrar)) {
            this.arbol = null;
            this.size = 0;
        }else {
            LCRSnode<E> padre = borrar.getParent();
            LCRSnode<E> primerHijo = padre.getChild();
            LCRSnode<E> sigHijo = primerHijo;
            LCRSnode<E> antHijo = primerHijo;
            while (sigHijo != borrar) {
                antHijo = sigHijo;
                sigHijo = sigHijo.getSibling();
            } // antHijo es el hermano anterior a borrar, sigHijo es borrar
            if (sigHijo.getSibling() == null) { // Si borrar no tiene hermano
                antHijo.setSibling(null);
            } else { // Si borrar tiene algún hermano
                antHijo.setSibling(sigHijo.getSibling());
            }
            this.size -= borrar.descendientesNum() + 1;

            }
        }

    @Override
    public NAryTree<E> subTree(Position<E> v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void attach(Position<E> p, NAryTree<E> t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isEmpty() {
        //throw new UnsupportedOperationException("Not supported yet.");
        return this.arbol.vacio();
    }

    @Override
    public Position<E> root() {
        //throw new UnsupportedOperationException("Not supported yet.");
        return this.arbol;
    }

    @Override
    public Position<E> parent(Position<E> v) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return checkPosition(v).getParent();
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return checkPosition(v).devuelveHijos();
    }

    @Override
    public boolean isInternal(Position<E> v) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return !isLeaf(v);
    }

    @Override
    public boolean isLeaf(Position<E> v) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return checkPosition(v).getChild() == null;
    }

    @Override
    public boolean isRoot(Position<E> v) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return (this.arbol == checkPosition(v));
    }

    @Override
    public Iterator<Position<E>> iterator() {
        //throw new UnsupportedOperationException("Not supported yet.");
        LCRSnode<E> raiz = this.arbol;
        return raiz.iteratorSet().iterator();
    }

    public int size() {
        //throw new UnsupportedOperationException("Not supported yet.");
        return this.size;
    }

}
