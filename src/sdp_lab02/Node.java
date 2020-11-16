package sdp_lab02;

public class Node {
    public static void main(String[] args) {
        Node x = new Node(1,null);
        Node y = new Node(2,x);
        System.out.println(y.getNext().getData());
    }
    private int data;
    private Node next;
    public Node(int d, Node nx){
        data = d;
        next = nx;
    }
    public int getData(){ return data;}
    public Node getNext(){return next;}
    public void setData(int d) {data = d;}
    public void setNext(Node n) {next = n;}

}
