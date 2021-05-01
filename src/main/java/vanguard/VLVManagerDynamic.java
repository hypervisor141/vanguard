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

    public VLVManagerDynamic(VLVManagerDynamic<ENTRY> src, int depth){
        copy(src, depth);
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
    public void copy(VLVTypeRunnable src, int depth){
        super.copy(src, depth);

        VLVManagerDynamic<ENTRY> target = (VLVManagerDynamic<ENTRY>)src;

        if(depth == DEPTH_MIN){
            backingentries = target.backingentries;

        }else{
            VLListType<ENTRY> srcentries = target.backingentries;
            backingentries = new VLListType<>(srcentries.size(), srcentries.resizerCount());
            backingentries.maximizeVirtualSize();

            int size = backingentries.size();

            if(depth == DEPTH_SHALLOW_ENTRIES){
                for(int i = 0; i < size; i++){
                    backingentries.set(i, srcentries.get(i));
                }

            }else if(depth == DEPTH_MAX){
                for(int i = 0; i < size; i++){
                    backingentries.set(i, (ENTRY)srcentries.get(i).duplicate(DEPTH_MAX));
                }

            }else{
                throw new RuntimeException("Invalid depth : " + depth);
            }
        }
    }

    @Override
    public VLVManagerDynamic<ENTRY> duplicate(int depth){
        return new VLVManagerDynamic<>(this, depth);
    }
}
