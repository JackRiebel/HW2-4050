package assignment.birds;

public class OrderedDictionary implements OrderedDictionaryADT {

    Node root;

    OrderedDictionary() {
        root = new Node();
    }

    /**
     * Returns the Record object with key k, or it returns null if such a record
     * is not in the dictionary.
     *
     * @param k
     * @return
     * @throws assignment/birds/DictionaryException.java
     */
    @Override
    public BirdRecord find(DataKey k) throws DictionaryException {
        Node current = root;
        int comparison;
        if (root.isEmpty()) {         
            throw new DictionaryException("There is no record matches the given key");
        }

        while (true) {
            comparison = current.getData().getDataKey().compareTo(k);
            if (comparison == 0) { // key found
                return current.getData();
            }
            if (comparison == 1) {
                if (current.getLeftChild() == null) {
                    // Key not found
                    throw new DictionaryException("There is no record matches the given key");
                }
                current = current.getLeftChild();
            } else if (comparison == -1) {
                if (current.getRightChild() == null) {
                    // Key not found
                    throw new DictionaryException("There is no record matches the given key");
                }
                current = current.getRightChild();
            }
        }

    }

    /**
     * Inserts r into the ordered dictionary. It throws a DictionaryException if
     * a record with the same key as r is already in the dictionary.
     *
     * @param r
     * @throws birds.DictionaryException
     */
    @Override
    public void insert(BirdRecord r) throws DictionaryException {
        root = insertRec(root, r);
    }

    private Node insertRec(Node current, BirdRecord r) throws DictionaryException {
        if (current == null || current.isEmpty()) {
            return new Node(r);
        }

        int comparison = current.getData().getDataKey().compareTo(r.getDataKey());
        if (comparison == 0) {
            throw new DictionaryException("A record with the same key already exists.");
        }

        if (comparison > 0) {
            current.setLeftChild(insertRec(current.getLeftChild(), r));
        } else {
            current.setRightChild(insertRec(current.getRightChild(), r));
        }

        return current;
    }


    /**
     * Removes the record with Key k from the dictionary. It throws a
     * DictionaryException if the record is not in the dictionary.
     *
     * @param k
     * @throws birds.DictionaryException
     */
    @Override
    public void remove(DataKey k) throws DictionaryException {
        root = removeRec(root, k);
    }

    private Node removeRec(Node current, DataKey k) throws DictionaryException {
        if (current == null) {
            throw new DictionaryException("No record found with the given key to remove.");
        }

        int comparison = current.getData().getDataKey().compareTo(k);
        if (comparison == 0) {
            // Node with only one child or no child
            if (current.getLeftChild() == null) {
                return current.getRightChild();
            } else if (current.getRightChild() == null) {
                return current.getLeftChild();
            }

            // Node with two children
            BirdRecord smallestValue = findSmallestValue(current.getRightChild());
            current.setData(smallestValue);
            current.setRightChild(removeRec(current.getRightChild(), smallestValue.getDataKey()));
            return current;
        }
        if (comparison > 0) {
            current.setLeftChild(removeRec(current.getLeftChild(), k));
            return current;
        }
        current.setRightChild(removeRec(current.getRightChild(), k));
        return current;
    }

    private BirdRecord findSmallestValue(Node root) {
        return root.getLeftChild() == null ? root.getData() : findSmallestValue(root.getLeftChild());
    }


    /**
     * Returns the successor of k (the record from the ordered dictionary with
     * smallest key larger than k); it returns null if the given key has no
     * successor. The given key DOES NOT need to be in the dictionary.
     *
     * @param k
     * @return
     * @throws birds.DictionaryException
     */
    @Override
    public BirdRecord successor(DataKey k) throws DictionaryException {
        Node current = findNode(root, k);
        if (current == null) {
            throw new DictionaryException("No such key exists.");
        }
        if (current.getRightChild() != null) {
            return findSmallestValue(current.getRightChild());
        }

        Node successor = null;
        Node ancestor = root;
        while (ancestor != current) {
            if (current.getData().getDataKey().compareTo(ancestor.getData().getDataKey()) < 0) {
                successor = ancestor;
                ancestor = ancestor.getLeftChild();
            } else {
                ancestor = ancestor.getRightChild();
            }
        }
        return successor != null ? successor.getData() : null;
    }

    private Node findNode(Node root, DataKey key) {
        if (root == null || root.isEmpty()) {
            return null;
        }
        int comparison = root.getData().getDataKey().compareTo(key);
        if (comparison == 0) {
            return root;
        } else if (comparison > 0) {
            return findNode(root.getLeftChild(), key);
        } else {
            return findNode(root.getRightChild(), key);
        }
    }



    /**
     * Returns the predecessor of k (the record from the ordered dictionary with
     * largest key smaller than k; it returns null if the given key has no
     * predecessor. The given key DOES NOT need to be in the dictionary.
     *
     * @param k
     * @return
     * @throws birds.DictionaryException
     */
    @Override
    public BirdRecord predecessor(DataKey k) throws DictionaryException {
        Node current = findNode(root, k);
        if (current == null) {
            throw new DictionaryException("No such key exists.");
        }
        if (current.getLeftChild() != null) {
            return findLargestValue(current.getLeftChild());
        }

        Node predecessor = null;
        Node ancestor = root;
        while (ancestor != current) {
            if (current.getData().getDataKey().compareTo(ancestor.getData().getDataKey()) > 0) {
                predecessor = ancestor;
                ancestor = ancestor.getRightChild();
            } else {
                ancestor = ancestor.getLeftChild();
            }
        }
        return predecessor != null ? predecessor.getData() : null;
    }

    private BirdRecord findLargestValue(Node root) {
        return root.getRightChild() == null ? root.getData() : findLargestValue(root.getRightChild());
    }


    /**
     * Returns the record with smallest key in the ordered dictionary. Returns
     * null if the dictionary is empty.
     *
     * @return
     */
    @Override
    public BirdRecord smallest() throws DictionaryException {
        if (root == null || root.isEmpty()) {
            throw new DictionaryException("The dictionary is empty.");
        }
        return findSmallestValue(root);
    }

    /*
	 * Returns the record with largest key in the ordered dictionary. Returns
	 * null if the dictionary is empty.
     */
    @Override
    public BirdRecord largest() throws DictionaryException {
        if (root == null || root.isEmpty()) {
            throw new DictionaryException("The dictionary is empty.");
        }
        return findLargestValue(root);
    }
      
    /* Returns true if the dictionary is empty, and true otherwise. */
    @Override
    public boolean isEmpty (){
        return root.isEmpty();
    }
}
