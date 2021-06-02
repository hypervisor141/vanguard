package hypervisor.vanguard.variable;

import hypervisor.vanguard.utils.VLCopyable;
import hypervisor.vanguard.utils.VLLoggable;

public interface VLVTypeRunnable extends VLLoggable, VLCopyable<VLVTypeRunnable> {

    void initialize(float from, float to, int cycles);
    void initialize(float from, float to, float changerate);
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
