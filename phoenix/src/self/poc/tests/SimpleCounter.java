package self.poc.tests;


public class SimpleCounter {

    public void count(int value, int delay) {
        for (int i = 0; i < value; i++) {
            try {
                Thread.sleep(delay);
            } catch (Exception e) {
            }
        }
    }

}
