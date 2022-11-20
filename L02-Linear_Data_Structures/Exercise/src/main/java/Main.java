import implementations.DoublyLinkedList;

public class Main {
    public static void main(String[] args) {
        DoublyLinkedList<Integer> numbers = new DoublyLinkedList<>();
        numbers.addFirst(12);
        numbers.addFirst(13);
        numbers.addFirst(14);

        numbers.removeFirst();
    }
}
