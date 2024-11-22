import java.util.Scanner;

@SuppressWarnings("ALL")
public class Naloga1 {
    static Sequence<Stack<String>> skladi;
    static final int mainStack = 0;
    static final int stackCount = 42;
    static boolean pogoj = false;

    static void init() throws CollectionException{
        skladi = new ArrayDeque<>();
        for (int i = 0; i < stackCount; i++) {
            skladi.add(new ArrayDeque<>());
        }
    }

    public static void main(String[] args) throws Exception {
        String niz;
        Scanner sc = new Scanner(System.in);
        do {
            init();
            if (!sc.hasNextLine()) break;
            niz = sc.nextLine();
            final String[] line = niz.split("\\s+");
            runLine(line);
        } while (sc.hasNextLine());
    }

    static void runLine(String[] line) throws CollectionException {
        int selected = 0;
        for (int i = 0; i < line.length; i++) {
            String s = line[i];
            try {
                int num = Integer.parseInt(s);
                skladi.get(selected).push(s);
            } catch (Exception e) { // Not a number
                if (s.charAt(0) == '?') {
                    s = s.substring(1);
                    if (!pogoj) continue;
                }
                if (s.equals("fun")) {
                    int sklad = selectSklad();
                    int num = selectSklad();
                    fun(sklad, i + 1, num, line);
                    i += num; // preskoči naprej
                    continue;
                }
                if (!handleUkaz(s, i, line)){
                    skladi.get(selected).push(s);
                }
            }
        }
    }

    static boolean handleUkaz(String ukaz, int index, String[] line) throws CollectionException {
        switch (ukaz) {
            case "echo":
                echo();
                return true;
            case "pop":
                pop();
                return true;
            case "dup":
                dup();
                return true;
            case "dup2":
                dup2();
                return true;
            case "swap":
                swap();
                return true;
            case "char":
                _char();
                return true;
            case "even":
                evenodd(true);
                return true;
            case "odd":
                evenodd(false);
                return true;
            case "!":
                faktorial();
                return true;
            case "len":
                len();
                return true;
            case "<>":
                primerjaj(0);
                return true;
            case "==":
                primerjaj(1);
                return true;
            case ">":
                primerjaj(2);
                return true;
            case ">=":
                primerjaj(3);
                return true;
            case "<":
                primerjaj(4);
                return true;
            case "<=":
                primerjaj(5);
                return true;
            case "+":
                aritmetika(0);
                return true;
            case "-":
                aritmetika(1);
                return true;
            case "*":
                aritmetika(2);
                return true;
            case "/":
                aritmetika(3);
                return true;
            case "%":
                aritmetika(4);
                return true;
            case ".":
                aritmetika(5);
                return true;
            case "rnd":
                aritmetika(6);
                return true;
            case "then":
                pogoj = !skladi.get(mainStack).pop().equals(Integer.toString(0));
                return true;
            case "else":
                pogoj = !pogoj;
                return true;
            case "print":
                print(selectSklad());
                return true;
            case "reverse":
                reverse(selectSklad());
                return true;
            case "move":
                move(selectSklad(), selectSklad());
                return true;
            case "loop":
                loop(selectSklad(), selectSklad());
                return true;
            case "run":
                run(selectSklad());
                return true;
            case "clear":
                clear(selectSklad());
                return true;
            default:
                return false;
        }
    }

    static void clear(int sklad) throws CollectionException{
        while (!skladi.get(sklad).isEmpty()){
            skladi.get(sklad).pop();
        }
    }

    static void run(int sklad) throws CollectionException {
        String[] line = skladi.get(sklad).toString().split(" ");
        runLine(line);
    }

    static void loop(int sklad, int kolikokrat) throws CollectionException {
        // ivedi sklad ena kolikokrat
        for (int i = 0; i < kolikokrat; i++) {
            // izvedi sklad
            run(sklad);
        }
    }

    static void move(int sklad, int num) throws CollectionException {
        for (int i = 0; i < num; i++) {
            skladi.get(sklad).push(skladi.get(mainStack).pop());
        }
    }

    static void fun(int sklad, int start, int num, String[] commands) throws CollectionException {
        for (int i = 0; i < num; i++) {
            skladi.get(sklad).push(commands[start + i]);
        }
    }

    static void reverse(int sklad) throws CollectionException {
        Sequence<String> tmp = new ArrayDeque<>();
        while (!skladi.get(sklad).isEmpty()) {
            tmp.add(skladi.get(sklad).pop());
        }
        for (int i = 0; i < tmp.size(); i++) {
            skladi.get(sklad).push(tmp.get(i));
        }
    }


    static int selectSklad() throws CollectionException {
        return Integer.parseInt(skladi.get(mainStack).pop());
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
            case 0:
                niz = num1 == num2 ? "0" : "1";
                break; // <>
            case 1:
                niz = num1 == num2 ? "1" : "0";
                break; // ==
            case 2:
                niz = num2 > num1 ? "1" : "0";
                break; // >
            case 3:
                niz = num2 >= num1 ? "1" : "0";
                break; // >=
            case 4:
                niz = num2 < num1 ? "1" : "0";
                break; // <
            case 5:
                niz = num2 <= num1 ? "1" : "0";
                break; // <=
        }
        skladi.get(mainStack).push(niz);
    }

    static void aritmetika(int n) throws CollectionException {
        final int num1 = Integer.parseInt(skladi.get(mainStack).pop());
        final int num2 = Integer.parseInt(skladi.get(mainStack).pop());
        String niz = "";
        switch (n) {
            case 0:
                niz = Integer.toString(num1 + num2);
                break;
            case 1:
                niz = Integer.toString(num2 - num1);
                break;
            case 2:
                niz = Integer.toString(num2 * num1);
                break;
            case 3:
                niz = Integer.toString(num2 / num1);
                break;
            case 4:
                niz = Integer.toString(num2 % num1);
                break;
            case 5:
                niz = num2 + Integer.toString(num1);
                break;
            case 6:
                niz = Integer.toString((int) (Math.random() * (num1 + num2 - 1) + num2));
                break;
        }
        skladi.get(mainStack).push(niz);
    }

    static void print(int sklad) throws CollectionException {
        System.out.println(skladi.get(sklad));
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
        if (isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = back; i != front; i++, i %= DEFAULT_CAPACITY) {
            sb.append(polje[i]);
            sb.append(" ");
        }
        sb.append(polje[front]);
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

    public T shift() throws CollectionException {
        T x = back();
        polje[back] = null;
        ++back;
        back %= DEFAULT_CAPACITY;
        return x;
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