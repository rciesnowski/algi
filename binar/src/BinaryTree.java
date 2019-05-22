public class BinaryTree {
    private Node root;
    private int counter;
    private boolean switchIsRight;
    public BinaryTree() {
        this.root = null;
        this.switchIsRight = false;
    }
    static final void spaces(int n){
        for(int i=0; i<n; i++) System.out.print(" ");
    }
    static final int blank(int depth){
        int blank = 1;
        for(int i=1; i<depth; i++) blank = blank*2+1;
        return blank;
    }
    public void insert(int value){
        Node node = new Node(value);
        Node tempRoot = root;
        if(root == null) root = node;
        else while(true) {
            if(node.getValue() < tempRoot.getValue()){
                if(tempRoot.getLeft() == null) {
                    tempRoot.setLeft(node);
                    node.setParent(tempRoot);
                    return;
                }
                else tempRoot = tempRoot.getLeft();
            }
            else if(node.getValue() > tempRoot.getValue()){
                if(tempRoot.getRight() == null) {
                    tempRoot.setRight(node);
                    node.setParent(tempRoot);
                    return;
                }
                else tempRoot = tempRoot.getRight();
            }
            else if(node.getValue() == tempRoot.getValue()){
                if(switchIsRight) {
                    if (tempRoot.getRight() == null) {
                        tempRoot.setRight(node);
                        node.setParent(tempRoot);
                        switchIsRight = false;
                        return;
                    }
                    else tempRoot = tempRoot.getRight();
                }
                else {
                    if (tempRoot.getLeft() == null) {
                        tempRoot.setLeft(node);
                        node.setParent(tempRoot);
                        switchIsRight = true;
                        return;
                    }
                    else tempRoot = tempRoot.getLeft();
                }
            }
        }
    }

    public Node search(int value){return search(value, root);}
    public Node search(int value, Node customRoot){
        while(true){
            if(customRoot == null) return null;
            if(value == customRoot.getValue()) return customRoot;
            else if(value < customRoot.getValue()) customRoot = customRoot.getLeft();
            else if (value > customRoot.getValue()) customRoot = customRoot.getRight();
        }
    }
    public void infosearch(int value){
        if (search(value, root)!=null) System.out.println(value + " found");
        else System.out.println(value + " not found");
    }
    public boolean[] array(int index, int depth){
        boolean array[] = new boolean[depth];
        for(int i = depth-1; i >= 0 ; i--){
            if(index%2 == 1) {
                array[i] = true;
                index--;
            }
            else array[i] = false;
            index=index/2;
        }
        return array;
    }

    public void print(){
        countLevels();
        int depth = counter;
        int start = blank(depth);
        int blank = blank(depth-1);

        for(int level = 0; level <depth; level++) {
            start = blank(depth-level);
            spaces(start);
            for (int index = 0; index < Math.pow(2, level); index++) {
                if (getNode(index, level) != null) System.out.print(getNode(index, level).getValue());
                else System.out.print(" ");
                spaces(blank);
            }
            System.out.println();
            blank = start;
        }
        System.out.println();
    }

    public Node getNode(int index, int depth){
        Node currentNode = root;
        boolean[] direction = array(index, depth);
        for( int j = 0; j < depth; j++){
            if(direction[j]) currentNode = currentNode.getRight();
            else currentNode = currentNode.getLeft();
            if(currentNode == null) return null;
            }
        return currentNode;
    }

    public Node treeMinimum(Node x){
        while (x.getLeft() != null) x= x.getLeft();
        return x;
    }

    public void delete(Node z){
        if(z.getLeft() == null && z.getRight() == null){
            if(z == root) root = null;
            else if (z == z.getParent().getLeft()) z.getParent().setLeft(null);
            else if (z == z.getParent().getRight()) z.getParent().setRight(null);
        }
        else if(z.getLeft() != null && z.getRight() != null){
            Node y = treeMinimum(z.getRight());
            z.setValue(y.getValue());
            delete(y);
        }
        else if( z.getLeft() != null ) {
            z.getLeft().setParent(z.getParent());
            if (z.getParent() == root) root = z.getLeft();
            else if(z == z.getParent().getLeft()) z.getParent().setLeft(z.getLeft());
            else z.getParent().setRight(z.getLeft());
        }
        else {
            z.getRight().setParent(z.getParent());
            if( z == root ) root = z.getRight();
            else if( z == z.getParent().getLeft()) z.getParent().setLeft(z.getRight());
            else z.getParent().setRight(z.getRight());
        }
    }

    public void countLevels(){
        counter = 0;
        countLevels(root, 1);
    }

    public void countLevels(Node currentRoot, int counter){
        if(currentRoot.getRight() != null) countLevels(currentRoot.getRight() , counter+1);
        if(currentRoot.getLeft() != null) countLevels(currentRoot.getLeft(), counter+1);
        if(counter > this.counter) this.counter = counter;
        return;
    }

    public static void main(String[] args) {
        BinaryTree sample = new BinaryTree();
        sample.insert(23);
        sample.insert(15);
        sample.insert(61);
        sample.insert(93);
        sample.insert(3);
        sample.insert(64);
        sample.insert(9);
        sample.insert(2);
        sample.insert(2);
        sample.print();

        sample.infosearch(61);
        sample.infosearch(20);

        sample.delete(sample.search(3));
        sample.delete(sample.search(23));
        sample.infosearch(23);
        sample.print();
    }
}
