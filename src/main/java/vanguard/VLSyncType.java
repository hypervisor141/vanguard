package vanguard;

public interface VLSyncType<SOURCE> extends VLCopyable<VLSyncType<SOURCE>>{

    long FLAG_SHALLOW_ENTRIES = 0x1L;

    void sync(SOURCE source);
}
