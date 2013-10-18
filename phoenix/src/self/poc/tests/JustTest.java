package self.poc.tests;


public class JustTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        publicStatic();

    }

    public static final void publicStaticFinal() {
        protectedStaticFinal();

    }

    protected static final void protectedStaticFinal() {
        defaultStaticFinal();
    }

    static final void defaultStaticFinal() {
        privateStaticFinal();
    }

    private static final void privateStaticFinal() {
        publicStatic();
    }

    public static void publicStatic() {
        protectedStatic();
    }

    protected static void protectedStatic() {
        defaultStatic();

    }

    static void defaultStatic() {
        privateStatic();
    }

    private static void privateStatic() {
        new JustTest().publicMeth();
    }

    public final void publicFinalMeth() {
        protectedFinalMeth();

    }

    protected final void protectedFinalMeth() {
        defaultFinalMeth();
    }

    final void defaultFinalMeth() {
        privateFinalMeth();

    }

    private void privateFinalMeth() {
        publicMeth();
    }

    public void publicMeth() {
        protectedMeth();

    }

    protected void protectedMeth() {
        defaultMethd();
    }

    void defaultMethd() {
        privateMethd();

    }

    private void privateMethd() {
    }

}
