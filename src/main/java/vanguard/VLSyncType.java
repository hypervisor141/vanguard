package vanguard;

public interface VLSyncType<SOURCE> extends VLCopyable<VLSyncType<SOURCE>>{

    void sync(SOURCE source);
}
