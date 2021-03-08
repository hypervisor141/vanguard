package vanguard;

public class VLVManagerRootDynamic extends VLVManagerRoot{

    protected VLListType<VLVTypeManager<? extends VLVTypeRunner>> entries;

    public VLVManagerRootDynamic(int capacity, int resizer, int entrysize){
        super(capacity, resizer);
        entries = new VLListType<>(entrysize, 0);
    }

    public VLVManagerRootDynamic(int capacity, int resizer, int entrysize, VLSyncType<VLVManagerRoot> syncer){
        super(capacity, resizer, syncer);
        entries = new VLListType<>(entrysize, 0);
    }

    public int activate(int index){
        add(entries.get(index));
        return size() - 1;
    }

    public void deactivate(int rootindex){
        remove(rootindex);
    }

    public VLListType<VLVTypeManager<? extends VLVTypeRunner>> entries(){
        return entries;
    }
}
