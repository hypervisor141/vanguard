package hypervisor.vanguard.variable;

import java.util.Random;

public interface VLVTypeRunnerUtils{

    Random RANDOM = new Random();

    void randomizeCycles(int cyclesmin, int cyclesmax, boolean maintaindirection, boolean excludeinactive);
    void randomizeChangeRates(float ratemin, float ratemax, boolean maintaindirection, boolean excludeinactive);
    void randomizeDelays(int delaymin, int delaymax, boolean offsetdelay, boolean excludeinactive);
}
