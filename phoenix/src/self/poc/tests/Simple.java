package self.poc.tests;



public class Simple {

    SimpleCounter counter = new SimpleCounter();

    public static void main(String[] argv) {
        Simple simple = new Simple();
        simple.countFast(1000);
        simple.countSlow(1000);
        JustTest.publicStaticFinal();
    }

    private void countSlow(int value) {
        (new SimpleCounter()).count(value, 5);
    }

    public void countFast(int value) {
        counter.count(value, 0);
    }

}
