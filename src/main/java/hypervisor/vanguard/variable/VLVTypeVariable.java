package hypervisor.vanguard.variable;

public interface VLVTypeVariable extends VLVTypeRunnable{

    float convertCyclesToChangeRate(float from, float to, int cycles);
    int advance();
    void set(float value);
    void changeRate(float changerate);
    boolean isIncreasing();
    boolean reversed();
    float get();
    float changeRate();
}
