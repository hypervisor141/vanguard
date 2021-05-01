package vanguard;

public interface VLVTypeRunnable extends VLStringify, VLCopyable<VLVTypeRunnable> {

    void initialize(int cycles);
    void initialize(float changerate);
    void initializeFixedDirection(int cycles);
    void initializeFixedDirection(float changerate);
    void activate();
    void deactivate();
    int next();
    void fastForward(int count);
    void chain(int cycles, float to);
    void reverse();
    void reset();
    void finish();
    float length();
    boolean active();
}
