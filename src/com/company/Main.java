package com.company;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

public class Main {

    static class Node implements Comparable<Node> {
        final float sum;
        String code;

        void buildCode(String code) {
            this.code = code;
        }

        public Node(float sum) {
            this.sum = sum;
        }

        @Override
        public int compareTo(Node o) {
            return Float.compare(sum, o.sum);
        }
    }

    static class InternalNode extends Node{
        Node left;
        Node right;

        @Override
        void buildCode(String code) {
            super.buildCode(code);
            left.buildCode(code + "0");
            right.buildCode(code + "1");
        }

        public InternalNode(Node left, Node right) {
            super(left.sum + right.sum);
            this.left = left;
            this.right = right;
        }
    }

    static class LearfNode extends Node {
        String symbol;

        @Override
        void buildCode(String code) {
            super.buildCode(code);
            System.out.println(symbol + ": " + code);
        }

        public LearfNode(String symbol, float count) {
            super(count);
            this.symbol = symbol;
        }
    }

    public static void main(String[] args) throws IOException {
	// write your code here
        //file();
        hafman_Single();
    }

    public static void file() throws IOException {
        Random random = new Random();
        int i = 0;
        int ran = 0;
        File Dir = new File("resource\\file");
        if (!Dir.exists()) {
            try {
                Dir.mkdir();
            } catch (SecurityException var10) {

            }
        }
        File F1 = new File("resource\\file", "F1.txt");
        File F2 = new File("resource\\file", "F2.txt");
        do {
            ran = random.nextInt(3);
            switch (ran) {
                case 0:
                    try (FileWriter writer = new FileWriter(F1, true)) {
                        writer.append("a");
                        writer.flush();
                    } catch (IOException ex) {

                        System.out.println(ex.getMessage());
                    }
                    break;
                case 1:
                    try (FileWriter writer = new FileWriter(F1, true)) {
                        writer.append("b");
                        writer.flush();
                    } catch (IOException ex) {

                        System.out.println(ex.getMessage());
                    }
                    break;
                case 2:
                    try (FileWriter writer = new FileWriter(F1, true)) {
                        writer.append("c");
                        writer.flush();
                    } catch (IOException ex) {

                        System.out.println(ex.getMessage());
                    }
                    break;
                default:
                    break;
            }
        } while (F1.length() < 10240);

        do {
            ran = random.nextInt(100);
            if (ran <= 19) {
                try (FileWriter writer = new FileWriter(F2, true)) {
                    writer.append("a");
                    writer.flush();
                } catch (IOException ex) {

                    System.out.println(ex.getMessage());
                }
            } else if (ran <= 39) {
                try (FileWriter writer = new FileWriter(F2, true)) {
                    writer.append("b");
                    writer.flush();
                } catch (IOException ex) {

                    System.out.println(ex.getMessage());
                }
            } else {
                try (FileWriter writer = new FileWriter(F2, true)) {
                    writer.append("c");
                    writer.flush();
                } catch (IOException ex) {

                    System.out.println(ex.getMessage());
                }
            }
        } while (F2.length() < 10240);
    }
    private static void  hafman_Single() throws IOException {
        FileReader reader_F1 = new FileReader("resource\\file\\F1.txt");
        FileReader reader_F2 = new FileReader("resource\\file\\F2.txt");
        FileReader reader_F3 = new FileReader("resource\\file\\F3.txt");
        int ch;
        int all_F1 = 0, all_F2 = 0, all_F3 = 0;
        int a_F1 = 0, b_F1 = 0, c_F1 = 0;
        int a_F2 = 0, b_F2 = 0, c_F2 = 0;
        float freq_a_F1 = 0, freq_b_F1 = 0, freq_c_F1 = 0;
        float freq_a_F2 = 0, freq_b_F2 = 0, freq_c_F2 = 0;
        HashMap<String, Float> map = new HashMap<>();
        HashMap <String, Float> map_prob_F1 = new HashMap<>(); //Вероятности
        HashMap <String, Float> map_prob_F2 = new HashMap<>(); //Вероятности
        HashMap <String, Float> map_prob_F3 = new HashMap<>(); //Вероятности

        //Подсчет вероятностей
        while((ch = reader_F1.read()) != -1) {
            all_F1++;
            switch ((char)ch) {
                case 'a':
                    a_F1++;
                    break;
                case 'b':
                    b_F1++;
                    break;
                case 'c':
                    c_F1++;
                    break;
                default:
                    break;
            }
        }
        freq_a_F1 = (float)a_F1 / all_F1;
        freq_b_F1 = (float)b_F1 / all_F1;
        freq_c_F1 = (float)c_F1 / all_F1;
        map_prob_F1.put("a", freq_a_F1);
        map_prob_F1.put("b", freq_b_F1);
        map_prob_F1.put("c", freq_c_F1);

        while((ch =reader_F2.read()) != -1) {
            all_F2++;
            switch ((char)ch) {
                case 'a':
                    a_F2++;
                    break;
                case 'b':
                    b_F2++;
                    break;
                case 'c':
                    c_F2++;
                    break;
                default:
                    break;
            }
        }
        freq_a_F2 = (float)a_F2 / all_F2;
        freq_b_F2 = (float)b_F2 / all_F2;
        freq_c_F2 = (float)c_F2 / all_F2;
        map_prob_F2.put("a", freq_a_F2);
        map_prob_F2.put("b", freq_b_F2);
        map_prob_F2.put("c", freq_c_F2);

        while((ch = reader_F3.read()) != -1) {
            String str = String.valueOf((char)ch);
            str = str.toLowerCase();
            str = str.replaceAll("[^а-яё]", "");
            if (str.isEmpty()) {
                continue;
            }
            if (!map.containsKey(str)) {
                map.put(str, 1.0f);
            } else {
                float i = map.get(str) + 1;
                map.put(str, i);
            }
        }
        for(Map.Entry entry: map.entrySet()) {
            all_F3 += (Float)entry.getValue();
        }

        for(Map.Entry entry: map.entrySet()) {
            map_prob_F3.put(entry.getKey().toString(), (Float)entry.getValue() / all_F3);
        }


        Map <String, Node> stringMode = new HashMap<>();
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        for (Map.Entry<String, Float> entry: map_prob_F3.entrySet() ) {
            LearfNode node = new LearfNode(entry.getKey(), entry.getValue());
            stringMode.put(entry.getKey(), node);
            priorityQueue.add(node);
        }
        float sum = 0;
        while (priorityQueue.size() > 1) {
            Node first = priorityQueue.poll();
            Node second = priorityQueue.poll();
            InternalNode node = new InternalNode(first, second);
            sum += node.sum;
            priorityQueue.add(node);
        }

        Node root = priorityQueue.poll();
        assert root != null;
        root.buildCode("");
        File F1 = new File("resource\\file", "F3_exmpl.txt");
        FileWriter writer_F1 = new FileWriter("resource\\file\\F3_exmpl.txt");
        reader_F1 = new FileReader("resource\\file\\F3.txt");
        while((ch = reader_F1.read()) != -1) {
            if (map_prob_F3.containsKey(String.valueOf((char)ch))) {
                writer_F1.write(stringMode.get(String.valueOf((char) ch)).code);
            }
        }
        writer_F1.flush();
        writer_F1.close();

    }

}
