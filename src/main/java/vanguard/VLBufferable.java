package vanguard;

public interface VLBufferable<
        MANAGERTYPE extends VLBufferManagerBase,
        ADDRESSTYPE extends VLBufferAddress<MANAGERTYPE>>{

    void buffer(MANAGERTYPE buffer, int bufferindex);
    void buffer(MANAGERTYPE buffer, int bufferindex, int arrayoffset, int arraycount, int unitoffset, int unitsize, int unitsubcount, int stride);
}