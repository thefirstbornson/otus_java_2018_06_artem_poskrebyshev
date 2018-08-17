package ru.otus.hw05;

class Benchmark {
    private int size = 0;

    @SuppressWarnings("InfiniteLoopStatement")
    void run() {

        System.out.println("Starting the loop");
        while (true) {
            int local = size;
            Object[] array = new Object[local];
            System.out.println("Array of size: " + array.length + " created");

            for (int i = 0; i < local; i++) {
                array[i] = new String(new char[0]);
            }
            System.out.println("Created " + local + " objects.");
            size+=100000;
        }
    }


    public int getSize() {
        return size;
    }


    public void setSize(int size) {
        this.size = size;
    }

}
