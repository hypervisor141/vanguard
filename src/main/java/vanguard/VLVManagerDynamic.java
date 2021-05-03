package vanguard;

public class VLVManagerDynamic<ENTRY extends VLVTypeManager<?>> extends VLVManager<ENTRY>{

    protected VLListType<ENTRY> backingentries;

    public VLVManagerDynamic(int capacity, int resizer, int entrysize){
        super(capacity, resizer);
        backingentries = new VLListType<>(entrysize, 0);
    }

    public VLVManagerDynamic(int capacity, int resizer, int entrysize, VLSyncType<VLVManager<ENTRY>> syncer){
        super(capacity, resizer, syncer);
        backingentries = new VLListType<>(entrysize, 0);
    }

    public VLVManagerDynamic(VLVManagerDynamic<ENTRY> src, long flags){
        copy(src, flags);
    }

    public int activateEntry(int index){
        add(backingentries.get(index));
        return size() - 1;
    }

    public void deactivateEntry(int rootindex){
        remove(rootindex);
    }

    public VLListType<ENTRY> entries(){
        return backingentries;
    }

    @Override
    public void copy(VLVTypeRunnable src, long flags){
        super.copy(src, flags);

        VLVManagerDynamic<ENTRY> target = (VLVManagerDynamic<ENTRY>)src;

        if((flags & FLAG_SHALLOW_COPY) == FLAG_SHALLOW_COPY){
            backingentries = target.backingentries;

        }else{
            VLListType<ENTRY> srcentries = target.backingentries;
            backingentries = new VLListType<>(srcentries.size(), srcentries.resizerCount());
            backingentries.maximizeVirtualSize();

            int size = backingentries.size();

            if((flags & FLAG_SHALLOW_ENTRIES) == FLAG_SHALLOW_ENTRIES){
                for(int i = 0; i < size; i++){
                    backingentries.set(i, srcentries.get(i));
                }

            }else if((flags & FLAG_DEEP_COPY) == FLAG_DEEP_COPY){
                for(int i = 0; i < size; i++){
                    backingentries.set(i, (ENTRY)srcentries.get(i).duplicate(FLAG_DEEP_COPY));
                }

            }else{
                throw new RuntimeException("Invalid depth : " + flags);
            }
        }
    }

    @Override
    public VLVManagerDynamic<ENTRY> duplicate(long flags){
        return new VLVManagerDynamic<>(this, flags);
    }
}
