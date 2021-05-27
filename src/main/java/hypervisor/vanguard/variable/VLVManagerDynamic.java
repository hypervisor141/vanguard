package hypervisor.vanguard.variable;

import hypervisor.vanguard.utils.VLCopyable;
import hypervisor.vanguard.sync.VLSyncType;
import hypervisor.vanguard.list.VLListType;

public class VLVManagerDynamic<ENTRY extends VLVTypeManager<?>> extends VLVManager<ENTRY>{

    protected VLListType<ENTRY> dynamicentries;

    public VLVManagerDynamic(int capacity, int resizer, int entrysize){
        super(capacity, resizer);
        dynamicentries = new VLListType<>(entrysize, 0);
    }

    public VLVManagerDynamic(int capacity, int resizer, int entrysize, VLSyncType<VLVManager<ENTRY>> syncer){
        super(capacity, resizer, syncer);
        dynamicentries = new VLListType<>(entrysize, 0);
    }

    public VLVManagerDynamic(VLVManagerDynamic<ENTRY> src, long flags){
        copy(src, flags);
    }

    protected VLVManagerDynamic(){

    }

    public int addEntry(ENTRY entry){
        dynamicentries.add(entry);
        return entry.size() - 1;
    }

    public void addEntry(int index, ENTRY entry){
        dynamicentries.add(index, entry);
    }

    public ENTRY getEntry(int index){
        return dynamicentries.get(index);
    }

    public void removeEntry(int index){
        dynamicentries.remove(index);
    }

    public int activateEntry(int index){
        add(dynamicentries.get(index));
        return size() - 1;
    }

    public int activateLastEntry(){
        return activateEntry(dynamicentries.size() - 1);
    }

    public void deactivateEntry(int rootindex){
        remove(rootindex);
    }

    public void deactivateEntry(ENTRY entry){
        get().remove(entry);
    }

    public VLListType<ENTRY> getEntries(){
        return dynamicentries;
    }

    public int sizeEntries(){
        return dynamicentries.size();
    }

    @Override
    public void copy(VLVTypeRunnable src, long flags){
        super.copy(src, flags);

        VLVManagerDynamic<ENTRY> target = (VLVManagerDynamic<ENTRY>)src;

        if((flags & VLCopyable.FLAG_REFERENCE) == VLCopyable.FLAG_REFERENCE){
            dynamicentries = target.dynamicentries;

        }else if((flags & VLCopyable.FLAG_DUPLICATE) == VLCopyable.FLAG_DUPLICATE){
            dynamicentries = target.dynamicentries.duplicate(VLCopyable.FLAG_DUPLICATE);

        }else if((flags & VLCopyable.FLAG_CUSTOM) == VLCopyable.FLAG_CUSTOM){
            if((flags & FLAG_FORCE_REFERENCE_ENTRIES) == FLAG_FORCE_REFERENCE_ENTRIES){
                dynamicentries = target.dynamicentries.duplicate(VLCopyable.FLAG_CUSTOM | VLListType.FLAG_FORCE_REFERENCE_ARRAY);

            }else if((flags & FLAG_FORCE_DUPLICATE_ENTRIES) == FLAG_FORCE_DUPLICATE_ENTRIES){
                dynamicentries = target.dynamicentries.duplicate(VLCopyable.FLAG_CUSTOM | VLListType.FLAG_FORCE_DUPLICATE_ARRAY);

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
