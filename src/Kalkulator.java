// import java.util.Scanner;

public class Kalkulator {
    static final Sequence<Stack<String>> skladi = new ArrayDeque<>();
    static final int mainStack = 0;
    static final int stackCount = 42;
//    static final String[] validCommands = new String[]{"echo",
//            "pop", "dup", "dup2", "swap", // glavni sklad
//            "char", "even", "odd", "!", "len", // Zamenja vrh
//            "<>", "<", "<=", "==", ">", ">=", "+", "-", "*", "/", "%", ".", "rnd", // zamenja 2 na vrh
//            "then", "else", /*"?...",*/ // pogojno izvajanje
//            "print", "clear", "run", "loop", "fun", "move", "reverse" // vrh -> index sklada
//    };

    static int selectSklad() throws Exception {
        return Integer.parseInt(skladi.get(mainStack).top());
    }

    static void echo() {
        try {
            System.out.println(skladi.get(mainStack).top());
        } catch (Exception e) {
            System.out.println();
        }
    }

    static void pop() throws CollectionException {
        skladi.get(mainStack).pop();
    }

    static void dup() throws CollectionException {
        skladi.get(mainStack).push(skladi.get(mainStack).top());
    }

    static void dup2() throws CollectionException {
        final String vrh = skladi.get(mainStack).pop();
        final String vrh2 = skladi.get(mainStack).pop();
        skladi.get(mainStack).push(vrh2);
        skladi.get(mainStack).push(vrh);
        skladi.get(mainStack).push(vrh2);
        skladi.get(mainStack).push(vrh);
    }

    static void swap() throws CollectionException {
        final String vrh = skladi.get(mainStack).pop();
        final String vrh2 = skladi.get(mainStack).pop();
        skladi.get(mainStack).push(vrh);
        skladi.get(mainStack).push(vrh2);
    }

    static void _char() throws CollectionException {
        final int code = Integer.parseInt(skladi.get(mainStack).pop());
        skladi.get(mainStack).push(String.valueOf((char) code));
    }

    static void evenodd(boolean even) throws CollectionException {
        final int num = Integer.parseInt(skladi.get(mainStack).pop());
        if (even)
            skladi.get(mainStack).push(num % 2 == 1 ? "0" : "1");
        else {
            skladi.get(mainStack).push(num % 2 + "");
        }
    }

    static void faktorial() throws CollectionException {
        final int num = Integer.parseInt(skladi.get(mainStack).pop());
        int cum = 1;
        for (int i = 2; i <= num; i++) {
            cum *= i;
        }
        skladi.get(mainStack).push("" + cum);
    }

    static void len() throws CollectionException {
        final String niz = skladi.get(mainStack).pop();
        skladi.get(mainStack).push("" + niz.length());
    }

    static void primerjaj(int n) throws CollectionException {
        final int num1 = Integer.parseInt(skladi.get(mainStack).pop());
        final int num2 = Integer.parseInt(skladi.get(mainStack).pop());
        String niz = "null";
        switch (n) {
            case 0 -> niz = num1 == num2 ? "0" : "1"; // <>
            case 1 -> niz = num1 == num2 ? "1" : "0"; // ==
            case 2 -> niz = num2 > num1 ? "1" : "0"; // >
            case 3 -> niz = num2 >= num1 ? "1" : "0"; // >=
            case 4 -> niz = num2 < num1 ? "1" : "0"; // <
            case 5 -> niz = num2 <= num1 ? "1" : "0"; // <=
        }
        skladi.get(mainStack).push(niz);
    }

    static void aritmetika(int n) throws CollectionException{
        final int num1 = Integer.parseInt(skladi.get(mainStack).pop());
        final int num2 = Integer.parseInt(skladi.get(mainStack).pop());
        String niz = "";
        switch (n){
            case 0 -> niz = Integer.toString(num1 + num2);
            case 1 -> niz = Integer.toString(num2 - num1);
            case 2 -> niz = Integer.toString(num2 * num1);
            case 3 -> niz = Integer.toString(num2 / num1);
            case 4 -> niz = Integer.toString(num2 % num1);
            case 5 -> niz = num2 + Integer.toString(num1);
            case 6 -> niz = Integer.toString((int)(Math.random() * (num1 + num2 - 1) + num2));
        }
        skladi.get(mainStack).push(niz);
    }

    static void print(int sklad) throws CollectionException{
        System.out.println(skladi.get(sklad));
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < stackCount; i++) {
            skladi.add(new ArrayDeque<>());
        }
        boolean pogoj = false;
//        Scanner sc = new Scanner(System.in);
//        final String[] line = sc.nextLine().split(" ");
        final String[] line = new String[args.length];
        System.arraycopy(args, 0, line, 0, args.length); // za testiranje

        for (String s : line) {
            try {
                int num = Integer.parseInt(s);
                System.out.println("num = " + num);
            } catch (Exception e) { // Not a number
                if (s.charAt(0) == '?'){
                    s = s.substring(1);
                    if (!pogoj) continue;
                }
                switch (s) {
                    case "echo" -> echo();
                    case "pop" -> pop();
                    case "dup" -> dup();
                    case "dup2" -> dup2();
                    case "swap" -> swap();
                    case "char" -> _char();
                    case "even" -> evenodd(true);
                    case "odd" -> evenodd(false);
                    case "!" -> faktorial();
                    case "len" -> len();
                    case "<>" -> primerjaj(0);
                    case "==" -> primerjaj(1);
                    case ">" -> primerjaj(2);
                    case ">=" -> primerjaj(3);
                    case "<" -> primerjaj(4);
                    case "<=" -> primerjaj(5);
                    case "+" -> aritmetika(0);
                    case "-" -> aritmetika(1);
                    case "*" -> aritmetika(2);
                    case "/" -> aritmetika(3);
                    case "%" -> aritmetika(4);
                    case "." -> aritmetika(5);
                    case "rnd" -> aritmetika(6);
                    case "then" -> pogoj = !skladi.get(mainStack).top().equals("" + 0);
                    case "else" -> pogoj = !pogoj;
                }

            }

        }

    }

}


@SuppressWarnings("unused")
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

@SuppressWarnings("unused")
interface Collection {
    String ERR_MSG_EMPTY = "Collection is empty.";
    String ERR_MSG_FULL = "Collection is full.";
    String ERR_MSG_INDEX = "Index is out of range";

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

@SuppressWarnings("unused")
interface Sequence<T> extends Collection {
    String ERR_MSG_INDEX = "Wrong index in sequence.";

    T get(int i) throws CollectionException;

    void add(T x) throws CollectionException;
}