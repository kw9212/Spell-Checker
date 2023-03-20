/** SpellChecker
*   Author: Keun Woo Song (ks3651)
*
**/
import java.io.*;
import java.util.*;

public class SpellChecker implements SpellCheckerInterface {
    private HashSet<String> dictionary;
    private FileReader fr;
    private BufferedReader br;

    public SpellChecker(String filename) throws IOException {
        fr = new FileReader(filename);
        br = new BufferedReader(fr);
        dictionary = new HashSet<>();

        String word;

        while ((word = br.readLine()) != null) {
            word = word.toLowerCase();
            String[] words = word.split("\\s+");
            for (int i = 0; i < words.length; i++){
                dictionary.add(words[i].replaceAll("[^a-z0-9]", ""));
            }
        }
    }

    public List<String> getIncorrectWords(String filename) {
        try {
            fr = new FileReader(filename);
            br = new BufferedReader(fr);
            String word;
            List<String> incorrectWords = new ArrayList<>();


            while ((word = br.readLine()) != null) {
                word = word.toLowerCase();
                String[] check = word.split("\\s+");
                // String[] check = word.split("\\s+");
                for (String wo : check) {
                    wo = wo.replaceAll("[^a-z0-9]", "");
                    if (!dictionary.contains(wo) && wo.length()>0) {
                        incorrectWords.add(wo);
                    }
                }
            }
            return incorrectWords;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<String> getSuggestions(String word) {
        HashSet <String> potentialIncorrect = new HashSet<>();
        word = word.toLowerCase();
        word = word.replaceAll("[^a-z]", "");

        if(dictionary.contains(word))
            potentialIncorrect.add(word);

        potentialIncorrect.addAll(addCharacter(word));
        potentialIncorrect.addAll(removeCharacter(word));
        potentialIncorrect.addAll(swapCharacter(word));

        return potentialIncorrect;
    }

    private HashSet<String> addCharacter(String word){
        HashSet<String> result = new HashSet<>();
        StringBuilder resultBuilder = new StringBuilder(word);
        char charCollection[]={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p',
                'q','r','s','t','u','v','w','x','y','z'};

        for (int i=0; i <= resultBuilder.length(); i++) //0h1a2v3e4 = length 4 haven
        {
            for (int j=0; j < charCollection.length; j++)
            {
                resultBuilder.insert(i, charCollection[j]);

                if (dictionary.contains(resultBuilder.toString()))
                    result.add(resultBuilder.toString());
                resultBuilder.deleteCharAt(i);
            }
        }
        return result;
    }

    private HashSet<String> removeCharacter(String word){
        HashSet<String> result = new HashSet<>();
        StringBuilder resultBuilder = new StringBuilder(word);

        for (int i = 0; i < resultBuilder.length(); i ++)
        {
            char temp = resultBuilder.charAt(i);
            resultBuilder.deleteCharAt(i);
            if (dictionary.contains(resultBuilder.toString()))
                result.add(resultBuilder.toString());
            resultBuilder.insert(i, temp);
        }
        return result;
    } // theer temp = t    hheer  hteer
    private HashSet<String> swapCharacter(String word){
        HashSet<String> result = new HashSet<>();
        StringBuilder resultBuilder = new StringBuilder(word);

        for(int i = 0; i < word.length()-1; i++) {
            char temp = resultBuilder.charAt(i);
            resultBuilder.setCharAt(i,resultBuilder.charAt(i+1));
            resultBuilder.setCharAt(i+1,temp);

            if (dictionary.contains(resultBuilder.toString()))
                result.add(resultBuilder.toString());
            resultBuilder.setCharAt(i+1, resultBuilder.charAt(i));
            resultBuilder.setCharAt(i, temp);
        }
        return result;
    }


    public static void main(String[] args) {

        try {
            SpellChecker b = new SpellChecker("/home/codio/workspace/words.txt");
            List<String> list = b.getIncorrectWords("/home/codio/workspace/test.txt");
            for (String word2 : list) {
                System.out.println("Incorrect word: " + word2);
                Set<String> list2 = b.getSuggestions(word2);
                System.out.println("Suggestion(s): " + list2);

            }


        }   catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}