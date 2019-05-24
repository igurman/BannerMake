/*
Генератор размещений из N по M без повторений с перемещениями и без
        Permutation p = new Permutation(9, 4, false);
        ArrayList result = p.build();
        System.out.println(result.size());
        p.Print();

        Возвращает ArrayList<int[]> result;
 */
package com.gurman;

import java.util.ArrayList;

class Permutation {
    private int n;
    private int[] a;
    private int m;
    private int[] b;
    private ArrayList<int[]> result = new ArrayList<>();
    private boolean isMeniat;

    Permutation(int numbers, int size, boolean full) {
        isMeniat = full;
        n = numbers;
        m = size;
        a = new int[n];
        b = new int[m];
        for (int i = 0; i < n; i++){
            a[i] = i + 1;
        }
        for (int i = 0; i < m; i++){
            b[i] = i + 1;
        }
    }

    ArrayList<int[]> build(){
        perestanovka(m, b);
        if (n >= m) {
            while (NextSet2(a, n, m)){
                for(int i = 0; i < m; i++){
                    b[i] = a[i];
                }
                perestanovka(m, b);
            }
        }
        return result;
    }

    private void perestanovka(int n, int[] a) {
        int[] res = new int[n];
        for(int i = 0; i < n; i++){
            res[i] = a[i];
        }
        result.add(res.clone());
        if(isMeniat){
            while (NextSet(a, n)){
                for(int i = 0; i < n; i++){
                    res[i] = a[i];
                }
                result.add(res.clone());
            }
        }
    }

    private void swap(int[] a, int i, int j) {
        int s = a[i];
        a[i] = a[j];
        a[j] = s;
    }

    private boolean NextSet(int[] a, int n) {
        int j = n - 2;
        while (j != -1 && a[j] >= a[j + 1]) j--;
        if (j == -1)
            return false; // больше перестановок нет
        int k = n - 1;
        while (a[j] >= a[k]) k--;
        swap(a, j, k);
        int l = j + 1, r = n - 1; // сортируем оставшуюся часть последовательности
        while (l < r)
            swap(a, l++, r--);
        return true;
    }

    private boolean NextSet2(int[] a, int n, int m) {
        for (int i = m - 1; i >= 0; --i)
            if (a[i] < n - m + i + 1) {
                ++a[i];
                for (int j = i + 1; j < m; ++j)
                    a[j] = a[j - 1] + 1;
                return true;
            }
        return false;
    }

    void Print() {
        for (Object aResult : result) {
            int[] a = (int[]) aResult;
            for (int anA : a) {
                System.out.print(anA);
            }
            System.out.println("");
        }
    }

}
