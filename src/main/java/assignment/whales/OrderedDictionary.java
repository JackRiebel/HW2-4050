package assignment.whales;

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
     * @throws assignment/whales/DictionaryException.java
     */
    @Override
    public WhaleRecord find(DataKey k) throws DictionaryException {
	// Start from root of the tree
	Node current = root;

	// Check if the tree is empty
	if (root.isEmpty()) {
		throw new DictionaryException("There is no record that matches the given key");
	}

	// Use a helper method to search for partial key matches
	WhaleRecord match = findPartialKey(current, k.getWhaleName());
	if (match != null) {
		return match;
	} else {
	    throw new DictionaryException("There is no record that matches the given key");
	}
    }
    // Helper search method
    private WhaleRecord findPartialKey(Node node, String partialKey) {
	if (node == null || node.isEmpty()) {
		return null; // Empty node means no match was found
	}

	String currentName = node.getData().getDataKey().getWhaleName();
	if (currentName.contains(partialName.toLowerCase())) {
		return node.getData(); // Found a match
	}

	// Search left subtree
	WhaleRecord leftMatch = findPartialKey(node.getLeftChild(), partialKey);
	if (leftMatch != null) {
		return leftMatch; // Match found in left subtree
	}

	// If match wasn't found in lest subtree, search right subtree
	return findPartialKey(node.getRightChild(), partialKey);
    }
	

    /**
     * Inserts r into the ordered dictionary. It throws a DictionaryException if
     * a record with the same key as r is already in the dictionary.
     *
     * @param r
     * @throws whales.DictionaryException
     */
    @Override
    public void insert(WhaleRecord r) throws DictionaryException {
        root = insertRec(root, r);
    }

    private Node insertRec(Node current, WhaleRecord r) throws DictionaryException {
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
     * @throws whales.DictionaryException
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
            WhaleRecord smallestValue = findSmallestValue(current.getRightChild());
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

    private WhaleRecord findSmallestValue(Node root) {
        return root.getLeftChild() == null ? root.getData() : findSmallestValue(root.getLeftChild());
    }


    /**
     * Returns the successor of k (the record from the ordered dictionary with
     * smallest key larger than k); it returns null if the given key has no
     * successor. The given key DOES NOT need to be in the dictionary.
     *
     * @param k
     * @return
     * @throws whales.DictionaryException
     */
    @Override
    public WhaleRecord successor(DataKey k) throws DictionaryException {
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
     * @throws whales.DictionaryException
     */
    @Override
    public WhaleRecord predecessor(DataKey k) throws DictionaryException {
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

    private WhaleRecord findLargestValue(Node root) {
        return root.getRightChild() == null ? root.getData() : findLargestValue(root.getRightChild());
    }


    /**
     * Returns the record with smallest key in the ordered dictionary. Returns
     * null if the dictionary is empty.
     *
     * @return
     */
    @Override
    public WhaleRecord smallest() throws DictionaryException {
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
    public WhaleRecord largest() throws DictionaryException {
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
