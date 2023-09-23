import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger countOf3 = new AtomicInteger();
    public static AtomicInteger countOf4 = new AtomicInteger();
    public static AtomicInteger countOf5 = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread myThread1 = new Thread(() -> {
            for (String str : texts) {
                if (isStringPalindrom(str) && !isStringContainsChar(str)) {
                    counter(str);
                }
            }
        });
        myThread1.start();


        Thread myThread2 = new Thread(() -> {
            for (String str : texts) {
                if (isStringContainsChar(str)) {
                    counter(str);
                }
            }
        });
        myThread2.start();


        Thread myThread3 = new Thread(() -> {
            for (String str : texts) {
                if (isCharOrder(str)) {
                    counter(str);
                }
            }
        });
        myThread3.start();
        myThread1.join();
        myThread2.join();
        myThread3.join();

        System.out.printf("Красивых слов с длиной 3 : %d шт \n", countOf3.get());
        System.out.printf("Красивых слов с длиной 4 : %d шт \n", countOf4.get());
        System.out.printf("Красивых слов с длиной 5 : %d шт \n", countOf5.get());
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isStringPalindrom(String str) {
        if (str.equals(new StringBuilder(str).reverse().toString())) {
            return true;
        }
        return false;
    }

    public static boolean isStringContainsChar(String str) {
        char c = str.charAt(0);
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != c) {
                return false;
            }
        }
        return true;
    }

    public static boolean isCharOrder(String str) {
        for (int i = 0; i < str.length() - 1; i++) {
            if (str.charAt(i) > str.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public static void counter(String str) {
        switch (str.length()) {
            case (3):
                countOf3.getAndIncrement();
                break;
            case (4):
                countOf4.getAndIncrement();
                break;
            case (5):
                countOf5.getAndIncrement();
                break;
            default:
                break;
        }
    }
}