package test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.List;

public class Ingpok {
    private static Integer leftIndex = 0;
    private static Integer rightIndex = 1000;

    public static void main(String[] args) throws IOException {
        if (args.length < 2){
            args = new String[]{"discnt.in", "discnt.out"};
        }
        List<String> lines = Files.readAllLines(new File(args[0]).toPath());

        int[] prices = countPrices(lines.get(0));
        Integer discount = Integer.parseInt(lines.get(1));

        BigDecimal total = new BigDecimal(0);

        while (leftIndex < rightIndex) {
            for (int i = 0 ; i<2; i++) {
                Integer price = getLeftPrice(prices);
                if (leftIndex > rightIndex){
                    break;
                }
                total = total.add(new BigDecimal(price));

            }
            Integer price = getRightPrice(prices);
            if (leftIndex > rightIndex){
                break;
            }
            total = total.add(new BigDecimal(price).multiply(new BigDecimal(discount).divide(new BigDecimal(100), 10, BigDecimal.ROUND_HALF_UP)));
        }

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        df.setGroupingUsed(false);
        Files.write(new File(args[1]).toPath(), df.format(total).getBytes());
    }

    private static Integer getRightPrice(int[] prices) {
        if (prices[rightIndex] > 0){
            return rightIndex--;
        } else {
            rightIndex--;
            return getRightPrice(prices);
        }
    }
    private static Integer getLeftPrice(int[] prices) {
        if (prices[leftIndex] > 0){
            return leftIndex++;
        } else {
            leftIndex++;
            return getLeftPrice(prices);
        }
    }

    private static int[] countPrices(String s) {
        int[] prices = new int[1001];
        for(String stringPrice : s.split(" ")){
            ++prices[Integer.parseInt(stringPrice)];
        }
        return prices;
    }


}
