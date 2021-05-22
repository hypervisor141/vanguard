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

    protected VLVManagerDynamic(){

    }

    public int addEntry(ENTRY entry){
        backingentries.add(entry);
        return entry.size() - 1;
    }

    public void addEntry(int index, ENTRY entry){
        backingentries.add(index, entry);
    }

    public void removeEntry(int index){
        backingentries.remove(index);
    }

    public int activateEntry(int index){
        add(backingentries.get(index));
        return size() - 1;
    }

    public int activateLastEntry(){
        return activateEntry(backingentries.size() - 1);
    }

    public void deactivateEntry(int rootindex){
        remove(rootindex);
    }

    public VLListType<ENTRY> entries(){
        return backingentries;
    }

    public int sizeEntries(){
        return backingentries.size();
    }

    @Override
    public void copy(VLVTypeRunnable src, long flags){
        super.copy(src, flags);

        VLVManagerDynamic<ENTRY> target = (VLVManagerDynamic<ENTRY>)src;

        if((flags & FLAG_REFERENCE) == FLAG_REFERENCE){
            backingentries = target.backingentries;

        }else if((flags & FLAG_DUPLICATE) == FLAG_DUPLICATE){
            backingentries = target.backingentries.duplicate(FLAG_DUPLICATE);

        }else if((flags & FLAG_CUSTOM) == FLAG_CUSTOM){
            if((flags & FLAG_FORCE_REFERENCE_ENTRIES) == FLAG_FORCE_REFERENCE_ENTRIES){
                backingentries = target.backingentries.duplicate(FLAG_CUSTOM | VLListType.FLAG_FORCE_REFERENCE_ARRAY);

            }else if((flags & FLAG_FORCE_DUPLICATE_ENTRIES) == FLAG_FORCE_DUPLICATE_ENTRIES){
                backingentries = target.backingentries.duplicate(FLAG_CUSTOM | VLListType.FLAG_FORCE_DUPLICATE_ARRAY);

            }else{
                VLCopyable.Helper.throwMissingSubFlags("FLAG_CUSTOM", "FLAG_FORCE_REFERENCE_ENTRIES", "FLAG_FORCE_DUPLICATE_ENTRIES");
            }


        }else{
            VLCopyable.Helper.throwMissingAllFlags();
        }
    }

    @Override
    public VLVManagerDynamic<ENTRY> duplicate(long flags){
        return new VLVManagerDynamic<>(this, flags);
    }
}
