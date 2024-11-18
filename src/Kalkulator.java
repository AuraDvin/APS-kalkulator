import java.util.Scanner;

public class Kalkulator {
    static Sequence<Stack<String>> skladi = new ArrayDeque<>();
    static final int mainStack = 0;
    static final int stackCount = 42;
    static final String[] validCommands = new String[]{"echo",
            "pop", "dup", "dup2", "swap", // glavni sklad
            "char", "even", "odd", "!", "len", // Zamenja vrh
            "<>", "<", "<=", "==", ">", ">=", "+", "-", "*", "/", "%", ".", "rnd", // zamenja 2 na vrh
            "then", "else", /*"?...",*/ // pogojno izvajanje
            "print", "clear", "run", "loop", "fun", "move", "reverse" // vrh -> index sklada
    };

    static int selectSklad() throws Exception {
        return Integer.parseInt(skladi.get(mainStack).top());
    }


    public static void main(String[] args) throws Exception {
        for (int i = 0; i < stackCount; i++) {
            skladi.add(new ArrayDeque<>());
        }
//        Scanner sc = new Scanner(System.in);
//        final String[] line = sc.nextLine().split(" ");
        final String[] line = new String[args.length];
        System.arraycopy(args, 0, line, 0, args.length); // za testiranje

        int selectedStack = 0;
        for (String s : line) {
            try {
                int num = Integer.parseInt(s);
                System.out.println("num = " + num);
            } catch (Exception e) { // Not a number
                // command
//                if (!Arrays.stream(validCommands).anyMatch(a->a.equals(s))){
//                    if (s.charAt(0) != '?'){
//                        throw new Exception("Invalid command" + s);
//                    }
//                    // pogojno izvajanje
//                }
                System.out.println("not a num");
            }
        }

    }
}

class ArrayDeque<T> implements Stack<T>, Sequence<T> {
    private static final int DEFAULT_CAPACITY = 64;
    @SuppressWarnings("unchecked")
    private final T[] polje = (T[]) (new Object[DEFAULT_CAPACITY]);
    private int front = -1;
    private int back = -1;

    public String toString() {
        if (isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder("[ ");
        for (int i = back; i != front; i++, i %= DEFAULT_CAPACITY) {
            sb.append(polje[i]);
            sb.append(", ");
        }
        sb.append(polje[front]);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean isEmpty() {
        return front < 0;
    }

    @Override
    public boolean isFull() {
        return ((front + 1) % DEFAULT_CAPACITY) == back;
    }

    @Override
    public int size() {
        if (isEmpty()) return 0;
        if (isFull()) return DEFAULT_CAPACITY;
        if (back <= front) {
            return front - back + 1;
        } else {
            return DEFAULT_CAPACITY - back + front + 1;
        }
    }

    @Override
    public T top() throws CollectionException {
        if (isEmpty()) throw new CollectionException(Collection.ERR_MSG_EMPTY);
        return polje[front];
    }

    @Override
    public void push(T x) throws CollectionException {
        if (isFull()) throw new CollectionException(Collection.ERR_MSG_FULL);
        if (isEmpty()) back = 0;
        polje[++front] = x;
    }

    @Override
    public T pop() throws CollectionException {
        T x = top(); // throws exception
        polje[front] = null; // preprečujemo postopanje
        if (front == back) { // Last element -> will be empty next
            front = back = -1;
        } else {
            front--;
            if (front < 0) front += DEFAULT_CAPACITY; // To the end of the list
        }
        return x;
    }


    public T back() throws CollectionException {
        if (isEmpty()) throw new CollectionException(Collection.ERR_MSG_EMPTY);
        return polje[back];
    }

    @Override
    public T get(int i) throws CollectionException {
        if (isEmpty()) throw new CollectionException(Collection.ERR_MSG_EMPTY);
        final int realIndex = (i + back) % DEFAULT_CAPACITY; // polje[back] -> 0. element
        if (i < 0 || realIndex > front) throw new CollectionException(Collection.ERR_MSG_INDEX);
        return polje[realIndex];
    }

    @Override
    public void add(T x) throws CollectionException {
        push(x);
    }
}

class CollectionException extends Exception {
    public CollectionException(String msg) {
        super(msg);
    }
}

interface Collection {
    static final String ERR_MSG_EMPTY = "Collection is empty.";
    static final String ERR_MSG_FULL = "Collection is full.";
    static final String ERR_MSG_INDEX = "Index is out of range";

    boolean isEmpty();

    boolean isFull();

    int size();

    String toString();
}

interface Stack<T> extends Collection {
    T top() throws CollectionException;

    void push(T x) throws CollectionException;

    T pop() throws CollectionException;
}

interface Sequence<T> extends Collection {
    static final String ERR_MSG_INDEX = "Wrong index in sequence.";

    T get(int i) throws CollectionException;

    void add(T x) throws CollectionException;
}