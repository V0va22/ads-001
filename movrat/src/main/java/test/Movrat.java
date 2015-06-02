package test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Movrat {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(new File("movrat.in").toPath());
        Input input = new Input(lines);
        Integer average = calculateAverage(input);
        Files.write(new File("movrat.out").toPath(), average.toString().getBytes());
    }

    private static Integer calculateAverage(Input input) {
        mergeSort(input.marks);// could be used simply Collections.sort(input.marks)
        Integer sum = 0;
        for (Integer i = input.lowIgnoreCount; i < input.count - input.highIgnoreCount; i++){
            sum += input.marks[i];
        }
        return sum /(input.count - input.lowIgnoreCount - input.highIgnoreCount);
    }

    private static void mergeSort(Integer[] array){
        System.arraycopy(mergeRecursive(array, 0, array.length-1), 0, array, 0, array.length);

    }
    private static Integer[] mergeRecursive(Integer[] array, Integer left, Integer right){
        if (left >= right){
            return new Integer[]{array[left]};
        } else if (right - left == 1 && isLess(array[right], array[left])){ // optimization for arrays with length 2
            return  new Integer[] {array[right], array[left]};
        } else if (right - left == 1){
            return  new Integer[] {array[left], array[right]};
        } else {
            Integer mid = (right + left) / 2;
            Integer[] leftArray = mergeRecursive(array, left, mid);
            Integer[] rightArray = mergeRecursive(array, mid + 1, right);
            return merge(leftArray, rightArray);
        }
    }

    private static Integer[] merge(Integer[] leftArray, Integer[] rightArray) {
        Integer leftIndex = 0;
        Integer rightIndex = 0;
        Integer writePosition = 0;
        Integer[] result = new Integer[leftArray.length + rightArray.length];
        while (writePosition < result.length){
            if (leftIndex >= leftArray.length ||
                    (rightIndex < rightArray.length && isLess(rightArray[rightIndex], leftArray[leftIndex]))){
                result[writePosition++] = rightArray[rightIndex++];
            } else {
                result[writePosition++] = leftArray[leftIndex++];
            }
        }
        return result;
    }

    private static boolean isLess(Integer i, Integer i1) {
        return i < i1;
    }

    private static class Input{

        public final Integer count;
        public final Integer[] marks;
        public final Integer lowIgnoreCount;
        public final Integer highIgnoreCount;

        public Input(List<String> lines) {
            count = Integer.parseInt(lines.get(0));
            marks = Arrays.stream(lines.get(1).split(" ")).map(stringMark -> Integer.parseInt(stringMark.trim())).collect(Collectors.toList()).toArray(new Integer[0]);
            lowIgnoreCount = Integer.parseInt(lines.get(2));
            highIgnoreCount = Integer.parseInt(lines.get(3));

        }

    }

}
