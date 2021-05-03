package vanguard;

public interface VLCopyable<TYPE>{

    long FLAG_MINIMAL = 0xFFFFFFFFFFFFFFFEL;
    long FLAG_MAX_DEPTH = 0xFFFFFFFFFFFFFFFFL;

    void copy(TYPE src, long flags);
    TYPE duplicate(long flags);
}
