package client;
public class Main {
    public static void main(String[] args) {
        System.out.println(test()); // 输出结果为2
    }

    public static int test() {
        try {
            return 1;
        } finally {
            System.out.println("执行finally块"); // 会在返回前执行
        }
    }
}