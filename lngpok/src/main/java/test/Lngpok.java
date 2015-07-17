package test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Lngpok {

    public static void main(String[] args) throws IOException {
        if (args.length < 2){
            args = new String[]{"lngpok.in", "lngpok.out"};
        }
        Integer jokerCounter = 0;
        Set<Integer> cards = new TreeSet<>((a,b) -> a-b);
        for (Integer card : Arrays.stream(Files.readAllLines(new File(args[0]).toPath()).get(0).split(" ")).map(s -> Integer.parseInt(s)).collect(Collectors.toList())){
            if (card == 0){
                jokerCounter++;
            } else {
                cards.add(card);
            }
        }
        Iterator<Integer> it = cards.iterator();
        Integer firstElement = it.next();
        Integer first = firstElement;
        Integer last = firstElement;
        Integer longest = 1;
        Integer currentJokerCount = jokerCounter;
        while (it.hasNext()){
            int card = it.next();
            if (card - last <= currentJokerCount + 1){
                currentJokerCount-=(card - last - 1);
                last = card;
            } else {
                first = card;
                last = card;
                currentJokerCount = jokerCounter;
            }
            if (last-first + currentJokerCount + 1 > longest){
                longest = last-first + currentJokerCount +1;
            }
        }

        System.out.println(longest);
        Files.write(new File(args[1]).toPath(), longest.toString().getBytes());
    }




}
