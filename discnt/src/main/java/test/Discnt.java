package test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.List;

public class Discnt {


    public static void main(String[] args) throws IOException {
        if (args.length < 2){
            args = new String[]{"discnt.in", "discnt.out"};
        }
        List<String> lines = Files.readAllLines(new File(args[0]).toPath());

        int[] prices = countPrices(lines.get(0));
        Integer discount = Integer.parseInt(lines.get(1));

        BigDecimal total = new BigDecimal(0);
        Integer leftIndex = 0;
        Integer rightIndex = 1000;
        int purchase = 0;
        while (leftIndex <= rightIndex){
            if (purchase < 2){
                if (prices[leftIndex] > 0 ){
                    total = total.add(new BigDecimal(leftIndex));
                    purchase++;
                    prices[leftIndex]--;
                }
                if (prices[leftIndex] == 0){
                    leftIndex++;
                }

            } else {
                if (prices[rightIndex] > 0 ){
                    total = total.add(new BigDecimal(rightIndex).multiply(new BigDecimal(100 - discount).divide(new BigDecimal(100), 10, BigDecimal.ROUND_HALF_UP)));
                    purchase = 0;
                    prices[rightIndex]--;
                }
                if (prices[rightIndex] == 0){
                    rightIndex--;
                }

            }
        }

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        df.setGroupingUsed(false);
        Files.write(new File(args[1]).toPath(), df.format(total).getBytes());
    }

    private static int[] countPrices(String s) {
        int[] prices = new int[1001];
        for(String stringPrice : s.split(" ")){
            ++prices[Integer.parseInt(stringPrice)];
        }
        return prices;
    }


}
