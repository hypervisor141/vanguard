package vanguard;

public class VLVManagerDynamic<ENTRY extends VLVTypeManager> extends VLVManager<ENTRY>{

    protected VLListType<ENTRY> entries;

    public VLVManagerDynamic(int capacity, int resizer, int entrysize){
        super(capacity, resizer);
        entries = new VLListType<>(entrysize, 0);
    }

    public VLVManagerDynamic(int capacity, int resizer, int entrysize, VLSyncType<VLVManager<ENTRY>> syncer){
        super(capacity, resizer, syncer);
        entries = new VLListType<>(entrysize, 0);
    }

    public int activateEntry(int index){
        add(entries.get(index));
        return size() - 1;
    }

    public void deactivateEntry(int rootindex){
        remove(rootindex);
    }

    public VLListType<ENTRY> entries(){
        return entries;
    }
}
