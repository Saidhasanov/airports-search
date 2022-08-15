package com.khasanov.said;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CSV_Reader {
    private int rows = 7184;
    private int columns = 14;
    public String[][] table;
    public Map<Character, List<Integer>> index = new HashMap<Character, List<Integer>>();

    public void getSize(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (this.columns == 0) {
                    this.columns = line.split(",").length;
                }
                this.rows++;
            }
        }
    }

    // Чтение CSV
    public void read(String filename) throws IOException {
        //getSize(filename);
        table = new String[this.rows][this.columns];

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            for (int i = 0; i < this.rows; i++) {
                table[i] = br.readLine().split(",");
            }
        }
    }

    // Подготовка данных
    public void prepare(int col){

        // Колонки со строками
        if (col > 0 && col < 6 || col > 9){
            for (int i = 0; i < this.rows; i++){
                char c = table[i][col].toLowerCase().charAt(1);
                try {
                    index.get(c).add(i);
                }
                catch (NullPointerException ex){
                    List<Integer> list = new ArrayList<>();
                    list.add(i);
                    index.put(c, list);
                }
            }
        }
        else {
            for (int i = 0; i < this.rows; i++){
                char c = table[i][col].charAt(0);
                try {
                    index.get(c).add(i);
                }
                catch (NullPointerException ex){
                    List<Integer> list = new ArrayList<>();
                    list.add(i);
                    index.put(c, list);
                }
            }
        }

    }

}
