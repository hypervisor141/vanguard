package vanguard.variable;

import vanguard.list.VLListType;

public interface VLVTypeManager<ENTRY extends VLVTypeRunner> extends VLVTypeRunner{

    void add(ENTRY entry);
    ENTRY set(int index, ENTRY entry);
    void remove(int index);
    ENTRY get(int index);
    VLListType<ENTRY> get();
    void nullify();
    void clear();
    void purge();
    int size();
}
