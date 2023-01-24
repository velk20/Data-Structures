import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static int  maxEnergy(int[] ener)
    {
        int  answer = 0;
        // Write your code here
        if (ener.length == 0) {
            return answer;
        }
        int maxProduct = 0;
        int firstNumberIndex = 0;
        int secondNumberIndex = 0;

        for (int i = 0; i < ener.length; i++) {
            for (int j = 0; j < ener.length; j++) {
                if (i == j) {
                    continue;
                }
                int currentProduct = ener[i] * ener[j];
                if (currentProduct > maxProduct) {
                    firstNumberIndex = i;
                    secondNumberIndex = j;
                    maxProduct = currentProduct;
                }
            }
        }

        answer = ener[firstNumberIndex] + ener[secondNumberIndex];
        return answer;
    }

    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        //input for ener
        int ener_size = in.nextInt();
        int ener[] = new int[ener_size];
        for(int idx = 0; idx < ener_size; idx++)
        {
            ener[idx] = in.nextInt();
        }

        int result = maxEnergy(ener);
        System.out.print(result);

    }
}