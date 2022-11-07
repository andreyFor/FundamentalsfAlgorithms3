

import java.util.Arrays;

public class IntegerListImpl implements IntegerList {
    private Integer[] arr;
    private int size;

    public IntegerListImpl() {
        arr = new Integer[10];
    }
    public IntegerListImpl(int initSize) {
        arr = new Integer[initSize];
    }
    //делано
    @Override
    public Integer add(Integer item) {
        growIfNeeded();
        validateItem(item);
        arr[size++] = item;
        return item;
    }

    @Override
    public Integer add(int index, Integer item) {
        growIfNeeded();
        validateItem(item);
        validateIndex(index);
        if (index == size) {
            arr[size++] = item;
            return item;
        }
        System.arraycopy(arr, index, arr, index + 1, size - index);
        arr[index] = item;
        size++;
        return item;
    }
//делано
    @Override
    public Integer set(int index, Integer item) {
        validateIndex(index);
        validateItem(item);
        arr[index] = item;
        return item;
    }

    @Override
    public Integer remove(Integer item) {
        validateItem(item);
        int index = indexOf(item);

        return remove(index);
    }

    @Override
    public Integer remove(int index) {
        validateIndex(index);

        Integer item = arr[index];

        if (index != size) {
            System.arraycopy(arr, index + 1, arr, index, size - index);
        }

        size--;
        return item;
    }

    @Override
    public boolean contains(Integer item) {
        Integer[] arrCopy = toArray();
        sort(arrCopy);
        return binarySearch(arrCopy, item);

    }

    @Override
    public int indexOf(Integer item) {
        for (int i = 0; i < size; i++) {
            if (arr[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Integer item) {
        for (int i = size - 1; i >= 0; i--) {
            if (arr[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Integer get(int index) {
        validateIndex(index);
        return arr[index];
    }

    @Override
    public boolean equals(IntegerList otherList) {
        return Arrays.equals(this.toArray(), otherList.toArray());
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public Integer[] toArray() {
        return Arrays.copyOf(arr, size);
    }

    private void validateItem(Integer item) {
        if ( item == null) {
            throw new NullPointerException();
        }
    }

    private void growIfNeeded() {
        if (size == arr.length) {
            grow();
        }
    }

    private void validateIndex(int index) {
        if (index < 0 || index > size) {
            throw new InvalidIndexException();
        }
    }

    private void sort(Integer[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private void quickSort(Integer[] arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
    }

    private static int partition(Integer[] arr, int begin, int end) {
        int pivot = arr[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;

                swapElements(arr, i, j);
            }
        }

        swapElements(arr, i + 1, end);
        return i + 1;
    }

    private static void swapElements(Integer[] arr, int left, int right) {
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
    }

    private static boolean binarySearch(Integer[] arr, Integer item) {
        int min = 0;
        int max = arr.length - 1;

        while (min <= max) {
            int mid = (min + max) / 2;

            if (item == arr[mid]) {
                return true;
            }

            if (item < arr[mid]) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return false;
    }

    private void grow() {
        arr = Arrays.copyOf(arr, size + size/ 2);
    }
}