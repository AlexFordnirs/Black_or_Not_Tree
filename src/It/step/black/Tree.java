package It.step.black;

import java.util.ArrayList;

public class Tree {
    private static Node treeRoot = null;
    private static ArrayList<String> temp = new ArrayList<>();

    static Node find(char value){
        if (value == treeRoot.value) return treeRoot;
        Node compareTo = treeRoot;
        int compareRes = 1;
        while (compareRes != 0) {
            compareRes = Character.compare(compareTo.value, value);
            if (compareRes < 0) {
                if (compareTo.leftNode == null) return null;
                compareTo = compareTo.leftNode;
            }
            if (compareRes > 0) {
                if (compareTo.rightNode == null) return null;
                compareTo = compareTo.rightNode;
            }
        }
        return compareTo;
    }

    static void addNode(char value) throws Exception {
        Node newNode = new Node();
        newNode.value = value;
        if (treeRoot == null) { // дерево пустое
            newNode.isRed = false;
            treeRoot = newNode;
        } else { // дерево не пустое
            if (find(value) != null) throw new Exception("Такой нод уже добавлен");

            Node compareTo = treeRoot;
            while (true) {
                int compareRes = Character.compare(compareTo.value, value);
                if (compareRes < 0) {
                    if (compareTo.leftNode == null) {
                        newNode.parent = compareTo;
                        compareTo.leftNode = newNode;
                        break;
                    }
                    compareTo = compareTo.leftNode;
                }
                if (compareRes > 0) {
                    if (compareTo.rightNode == null) {
                        newNode.parent = compareTo;
                        compareTo.rightNode = newNode;
                        break;
                    }
                    compareTo = compareTo.rightNode;
                }
            }
            rebalance(newNode);
        }
    }

    static void removeNode(char value) {
        Node element = find(value);
        if (element == null) return;
        if (element.rightNode != null && element.leftNode != null) { // есть оба сына
            Node next = element.rightNode;
            while(next.leftNode != null) {
                next = next.leftNode;
            }

            if (next == element.rightNode) {
                element.rightNode = null;
            }
            else {
                next.parent.leftNode = null;
            }
            next.parent = null;
            element.value = next.value;
            rebalance(element);
            return;
        }
        // левый есть, правого нет
        if (element.leftNode != null) {
            if (element == treeRoot) {
                treeRoot = element.leftNode;
                treeRoot.parent = null;
            }
            if (element.parent.leftNode == element) {
                element.parent.leftNode = element.leftNode;
            }
            element.leftNode.parent = element.parent;
            return;
        }
        // правый есть, другого нет
        if (element.rightNode != null) {
            if (element == treeRoot) {
                treeRoot = element.rightNode;
                treeRoot.parent = null;
            }
            if (element.parent.rightNode == element) {
                element.parent.rightNode = element.rightNode;
            }
            element.rightNode.parent = element.parent;
            return;
        }
        if (element == treeRoot) {
            treeRoot = null;
            return;
        }
        if (element.parent.rightNode == element)
            element.parent.rightNode = null;
        if (element.parent.leftNode == element)
            element.parent.leftNode = null;
    }

    private static void rebalance(Node currentNode) {
        while (currentNode.parent != null && currentNode.parent.isRed) {
            if (currentNode.parent == currentNode.parent.parent.leftNode) {
                Node uncle = currentNode.parent.parent.rightNode;
                if (uncle != null && uncle.isRed) {
                    currentNode.parent.isRed = false;
                    uncle.isRed = false;
                    currentNode.parent.parent.isRed = true;
                    currentNode = currentNode.parent.parent;
                }
                else {
                    if (currentNode == currentNode.parent.rightNode) {
                        currentNode = currentNode.parent;
                        rotateLeft(currentNode);
                    }
                    currentNode.parent.isRed = false;
                    if (currentNode.parent.parent != null) {
                        currentNode.parent.parent.isRed = true;
                        rotateRight(currentNode.parent.parent);
                    }
                }
            }
            else {
                Node uncle = currentNode.parent.parent.leftNode;
                if (uncle != null && uncle.isRed) {
                    currentNode.parent.isRed = false;
                    uncle.isRed = false;
                    currentNode.parent.parent.isRed = true;
                    currentNode = currentNode.parent.parent;
                }
                else {
                    if (currentNode == currentNode.parent.rightNode) {
                        currentNode = currentNode.parent;
                        rotateLeft(currentNode);
                    }
                    currentNode.parent.isRed = false;
                    if (currentNode.parent.parent != null) {
                        currentNode.parent.parent.isRed = true;
                        rotateRight(currentNode.parent.parent);
                    }
                }
            }
        }
        treeRoot.isRed = false;
    }

    static void removeAll() { treeRoot = null; }

    private static void rotateLeft(Node x) {
        Node y = x.rightNode;
        if (treeRoot == x) {
            treeRoot = y;
        }
        if (x.parent != null) {
            if (x.parent.leftNode == x) {
                x.parent.leftNode = y;
            }
            if (x.parent.rightNode == x) {
                x.parent.rightNode = y;
            }
        }
        y.parent = x.parent;
        x.parent = y;
        x.rightNode = y.leftNode;
        if (x.rightNode != null) x.rightNode.parent = x;
        y.leftNode = x;

    }

    private static void rotateRight(Node x){
        Node y = x.leftNode;
        if (treeRoot == x) {
            treeRoot = y;
        }
        if (x.parent != null) {
            if (x.parent.leftNode == x) {
                x.parent.leftNode = y;
            }
            if (x.parent.rightNode == x) {
                x.parent.rightNode = y;
            }
        }
        y.parent = x.parent;
        x.parent = y;
        x.leftNode = y.rightNode;
        if (x.leftNode != null) x.leftNode.parent = x;
        y.rightNode = x;
    }

    static String serialize() {
        if (treeRoot == null) return "Элементов нет";
        StringBuilder result = new StringBuilder();
        walkTree(treeRoot);
        for (String item : temp)
            result.append(item).append(" \n");
        temp.clear();
        return result.toString();
    }

    private static void walkTree(Node start) {
        if (start.leftNode != null) {
            walkTree(start.leftNode);
        }
        if (start.rightNode != null) {
            walkTree(start.rightNode);
        }
        temp.add(start.toString());
    }
}
