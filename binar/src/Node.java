public class Node {
    private int value;
    private Node parent;
    private Node left;
    private Node right;

    public Node(int value) {
        this.value = value;
        this.parent = null;
        this.left = null;
        this.right = null;
    }
    public void setParent(Node parent) {
        this.parent = parent;
    }
    public Node getParent() {
        return parent;
    }
    public int getValue() {
        return value;
    }
    public Node getLeft() {
        return left;
    }
    public Node getRight() {
        return right;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public void setLeft(Node left) {
        this.left = left;
    }
    public void setRight(Node right) {
        this.right = right;
    }
}

