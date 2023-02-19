
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Mono {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String ALPHANUM  = ALPHABET + DIGITS;

    private static final int MAX_SEED = 10000;

    private static final int MIN_SEED = 50;

    private Map<Character, Character> encryptionMap;
    private Map<Character, Character> decryptionMap;

    public Mono(int seed) {
        encryptionMap = new HashMap<>();
        decryptionMap = new HashMap<>();
        generateMapping(seed);
    }
    private void generateMapping(int seed) {
        Random rand = new Random(seed);
        List<Character> alphanumericList = new ArrayList<>(ALPHANUM.length());
        for (char c : ALPHANUM.toCharArray()) {
            alphanumericList.add(c);
        }
        Collections.shuffle(alphanumericList, rand);
        for (int i = 0; i < ALPHANUM.length(); i++) {
            encryptionMap.put(ALPHANUM.charAt(i), alphanumericList.get(i));
            decryptionMap.put(alphanumericList.get(i), ALPHANUM.charAt(i));
        }
    }

    private String decryptText(String ciphertext) {
        StringBuilder sb = new StringBuilder();
        for (char c : ciphertext.toCharArray()) {
            sb.append(decryptionMap.get(c));
        }
        System.out.println("Decryption Mapping:"+decryptionMap.toString());
        return sb.toString();
    }

    private String encryptText(String plaintext) {
        StringBuilder sb = new StringBuilder();
        for (char c : plaintext.toCharArray()) {
            sb.append(encryptionMap.get(c));
        }
        System.out.println("Encryption Mapping:"+encryptionMap.toString());
        return sb.toString();
    }



    public static void main(String[] args) {

        try {

            if (args.length != 4 ) {

                System.err.println("Error: Incorrect number of arguments. Program accepts 4 arguments.");
                System.out.println("Usage: java Mono <inputfile> <outputfile> <seed> 1/0");
                System.exit(0);
            }

        String inputFile = args[0];
        String outputFile = args[1];

        int seed = Integer.parseInt(args[2]);
        int action = Integer.parseInt(args[3]);

        if(seed<=MAX_SEED && seed>=MIN_SEED){
            Mono  mono = new Mono(seed);



            BufferedReader reader;
            BufferedWriter writer;
            if(inputFile.contains(".txt")) {
                 reader = new BufferedReader(new FileReader(inputFile));
            }else{
                 reader = new BufferedReader(new FileReader(inputFile+".txt"));
            }
            if(outputFile.contains(".txt")){
                writer = new BufferedWriter(new FileWriter(outputFile));
            }else {
                writer = new BufferedWriter(new FileWriter(outputFile+".txt"));
            }

            String line;
            while ((line = reader.readLine()) != null) {
                if (action == 1) {
                    if(mono!=null){
                    line = mono.encryptText(line);}
                } else if (action == 0) {
                    line = mono.decryptText(line);
                } else{
                    System.out.println("ERROR: Please provide valid action 1/0 for encryption/decryption in input. input should be in format - <inputfile> <outputfile> <seed> 1/0. Exiting..");
                    writer.write("ERROR: Please provide valid action 1/0 for encryption/decryption in input. input should be in format - <inputfile> <outputfile> <seed> 1/0. Exiting..");
                    writer.close();
                    System.exit(1);
                }
                writer.write(line);
            }
            reader.close();
            writer.close();
        }else{
            System.out.println("ERROR: Please provide seed value between "+MIN_SEED+" to "+MAX_SEED+" in input. input should be in format - <inputfile> <outputfile> <seed> 1/0. Exiting..");
            System.exit(1);
        }
        } catch (IOException e) {
            System.out.println("Error reading/writing file: " + e.getMessage());
        } catch (NumberFormatException e){
            System.out.println("Error converting number " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        }
    }
}
