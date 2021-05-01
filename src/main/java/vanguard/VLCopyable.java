package vanguard;

public interface VLCopyable<TYPE>{

    static final int DEPTH_MIN = 0;
    static final int DEPTH_MAX = 1000;

    void copy(TYPE src, int depth);
    TYPE duplicate(int depth);
}
