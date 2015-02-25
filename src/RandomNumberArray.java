import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class RandomNumberArray {

    private final HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

    public RandomNumberArray(final String numbers) {
        this.parse(numbers.trim());
    }

    public void add(final int number) {
        if (map.containsKey(number)) {
            map.put(number, map.get(number) + 1);
        } else {
            map.put(number, 1);
        }
    }

    public int evenCount() {
        int count = 0;
        for (final int i : map.keySet()) {
            if (i % 2 == 0) {
                count += map.get(i);
            }
        }
        return count;
    }

    public double findAverage() {
        if (map.isEmpty()) {
            return 0;
        }
        double total = 0;
        double numbers = 0;
        for (final int i : map.keySet()) {
            total += map.get(i) * i;
            numbers += map.get(i);
        }
        return total / numbers;
    }

    public int[] findMode() {
        if (map.isEmpty()) {
            return new int[] { 0 };
        }
        final int max = Collections.max(map.values());
        final ArrayList<Integer> modes = new ArrayList<Integer>();
        for (final int i : map.keySet()) {
            if (map.get(i) == max) {
                modes.add(i);
            }
        }
        final int[] result = new int[modes.size() + 1];
        result[0] = max;
        for (int i = 0; i < modes.size(); i++) {
            result[i + 1] = modes.get(i);
        }
        return result;
    }

    public int getAmount(final int number) {
        return map.containsKey(number) ? map.get(number) : -1;
    }

    public ArrayList<Integer> getNumberArray() {
        final ArrayList<Integer> sorted = new ArrayList<Integer>(map.keySet());
        Collections.sort(sorted);
        return sorted;
    }

    private boolean isPrime(final int n) {
        if (n % 2 == 0) {
            return false;
        }
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public int oddCount() {
        int count = 0;
        for (final int i : map.keySet()) {
            if (i % 2 == 1) {
                count += map.get(i);
            }
        }
        return count;
    }

    private void parse(final String numbers) {
        final String replaced = numbers.replaceAll("[^0-9\\s]", "");
        final String[] numArrayString = replaced.split("\\s+");
        for (final String s : numArrayString) {
            Integer num;
            try {
                num = Integer.parseInt(s);
            } catch (final Exception e) {
                continue;
            }
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            } else {
                map.put(num, 1);
            }
        }
    }

    public int primeCount() {
        int count = 0;
        for (final int i : map.keySet()) {
            if (this.isPrime(i)) {
                count += map.get(i);
            }
        }
        return count;
    }

    public int range() {
        return Collections.max(map.keySet()) - Collections.min(map.keySet())
                + 1;
    }

    public void remove(final int number) {
        if (map.containsKey(number)) {
            if (map.get(number) == 1) {
                map.remove(number);
            } else {
                map.put(number, map.get(number) - 1);
            }
        }
    }

    public void removeAll(final double number) {
        if (map.containsKey(number)) {
            map.remove(number);
        }
    }

    public int total() {
        int total = 0;
        for (final int i : map.values()) {
            total += i;
        }
        return total;
    }
}
