package co.com.lafemmeapp.lafemmeapp;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    @Test
    public void test1() {
        System.out.print(minimumNumberOfTrips(100, new int[]{70, 10, 20}));
    }

    static int minimumNumberOfTrips(int tripMaxWeight, int[] packagesWeight) {
        packagesWeight = Arrays.stream(packagesWeight).sorted().toArray();
        int n = 0;
        boolean hasBreak = false;
        int lastJ = 0;
        for (int i = 0; i < packagesWeight.length; i++) {
            for (int j = i + 1; j < packagesWeight.length - 1; i++) {
                if (packagesWeight[i] + packagesWeight[j] <= tripMaxWeight) {
                    n++;
                    hasBreak = true;
                    lastJ = j;
                    break;
                }
            }
            if (hasBreak) {
                final int outJ = lastJ;
                final int outI = i;
                lastJ = 0;
                hasBreak = false;
                final int[] preArray = packagesWeight;
                packagesWeight = Arrays.stream(preArray).filter(w -> w != preArray[outJ] && w != preArray[outI])
                        .toArray();
            }

        }

        return n++;

    }


}