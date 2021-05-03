package vanguard;

public interface VLCopyable<TYPE>{

    long FLAG_SHALLOW_COPY = 0x1L;
    long FLAG_DEEP_COPY = 0xFFFFFFFFFFFFFFFFL;

    void copy(TYPE src, long flags);
    TYPE duplicate(long flags);
}
