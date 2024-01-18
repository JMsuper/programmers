package algorithm;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Combination{
    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3};
        boolean[] visited = new boolean[arr.length];
        int m = 2;

        ArrayList<int[]> combiList = new ArrayList<>();

        Arrays.setAll(arr,i -> i + 1);

        combi(arr, visited,combiList,0,m);
        combiList.forEach(Combination::print);
    }

    public static int factorial(int n){
        int result = 1;
        for(int i = 1; i <= n; i++){
            result *= i;
        }
        return result;
    }

    public static int calculateCombinationCnt(int n, int m){
        return factorial(n) / ( factorial(n - m) * factorial(m) );
    }

    static void combi(int[] arr, boolean[] visited, ArrayList<int[]> combiList, int start, int r) {
        if(r == 0) {
            int[] newCombi = new int[2];
            int cur = 0;
            for(int i = 0; i < arr.length; i++){
                if(visited[i]){
                    newCombi[cur] = arr[i];
                    cur += 1;
                }
            }
            combiList.add(newCombi);
        } else {
            for(int i = start; i < arr.length; i++) {
                visited[i] = true;
                combi(arr, visited, combiList,i + 1, r - 1);
                visited[i] = false;
            }
        }
    }

    static void print(int[] arr){
        for(int i = 0; i < arr.length; i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    static void print(boolean[] arr){
        for(int i = 0; i < arr.length; i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

}