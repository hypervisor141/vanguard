package vanguard.sync;

import vanguard.utils.VLCopyable;

public interface VLSyncType<SOURCE> extends VLCopyable<VLSyncType<SOURCE>> {

    void sync(SOURCE source);
}
