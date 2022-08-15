package com.khasanov.said;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Main {

    public static Map<Integer, String> sort(Map<Integer, String> rowFound, boolean isStr){
        Map<Integer, String> sortedMap;
        if (isStr){
            sortedMap =
                    rowFound.entrySet().stream()
                            .sorted(Entry.comparingByValue())
                            .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
                                    (e1, e2) -> e1, LinkedHashMap::new));
        }
        else {
            sortedMap =
                    rowFound.entrySet().stream()
                            .sorted(
                                    Comparator.comparing(e -> Integer.valueOf(e.getValue()))).collect(Collectors.toMap(Entry::getKey, Entry::getValue,
                                    (e1, e2) -> e1, LinkedHashMap::new));
        }
        return sortedMap;
    }


    public static void main(String[] args) throws IOException {
        String filename = "C:\\Users\\Amate\\Downloads\\airports.csv";

        CSV_Reader reader = new CSV_Reader();
        reader.read(filename);

        int arg = Integer.parseInt(args[0])-1;
        reader.prepare(arg);
        boolean isStr = arg > 0 && arg < 6 || arg > 9;

        // Чтение строки
        Scanner sc = new Scanner(System.in);
        String query;
        System.out.print("Введите строку: ");

        while (!Objects.equals(query = sc.nextLine(), "!quit")){
            char first;

            if (isStr) {
                query = "\"" + query.toLowerCase();
                first = query.charAt(1);
            }
            else {
                query = query;
                first = query.charAt(0);
            }

            long time = System.currentTimeMillis();

            //  Поиск совпадений
            Map<Integer, String> rowFound = new HashMap<>();
            for (int i:reader.index.get(first)) {
                String strI = reader.table[i][arg];
                if (strI.toLowerCase().startsWith(query)){
                    rowFound.put(i, strI);
                }
            }

            // Сортировка
            Map<Integer, String> sortedMap = sort(rowFound, isStr);


            // Вывод результата поиска
            for(Map.Entry<Integer, String> entry : sortedMap.entrySet()) {
                Integer key = entry.getKey();
                System.out.print(entry.getValue());
                System.out.println(Arrays.toString(reader.table[key]));
            }

            System.out.println("Строк: " + sortedMap.size());
            System.out.println("Время, затраченное на поиск: "+(System.currentTimeMillis() - time)+" мс");
            System.out.print("Введите строку: ");
        }

    }


}



