package javaL;
import java.util.Arrays;

public class arrays {
    public static void main(String[] args) {
        Integer[] arr = new Integer[3];
        arr[0] = 3; arr[1] = 2; arr[2] = 4;

        Arrays.sort(arr, (Integer i1, Integer i2) -> Integer.compare(i2, i1)); //custom comparator

        System.out.println(arr[0]);
        System.out.println(arr[1]);
        System.out.println(arr[2]);
    }
}
