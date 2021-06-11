package hypervisor.vanguard.variable;

import hypervisor.vanguard.list.VLListType;
import hypervisor.vanguard.sync.VLSyncType;
import hypervisor.vanguard.utils.VLCopyable;

public class VLVManagerDynamic<ENTRY extends VLVTypeRunner> extends VLVManager<ENTRY>{

    protected VLListType<Entry<ENTRY>> dynamicentries;

    public VLVManagerDynamic(int capacity, int resizer, int entrycapacity, int entryresizer){
        super(capacity, resizer);
        dynamicentries = new VLListType<>(entrycapacity, resizer);
    }

    public VLVManagerDynamic(int capacity, int resizer, int entrycapacity, int entryresizer, VLSyncType<VLVManager<ENTRY>> syncer){
        super(capacity, resizer, syncer);
        dynamicentries = new VLListType<>(entrycapacity, entryresizer);
    }

    public VLVManagerDynamic(VLVManagerDynamic<ENTRY> src, long flags){
        copy(src, flags);
    }

    protected VLVManagerDynamic(){

    }

    public int addEntry(ENTRY entry){
        dynamicentries.add(new Entry<>(entry));
        return entries.size() - 1;
    }

    public void addEntry(int index, ENTRY entry){
        dynamicentries.add(index, new Entry<>(entry));
    }

    public ENTRY getEntry(int index){
        return dynamicentries.get(index).entry;
    }

    public void removeEntry(int index){
        dynamicentries.remove(index);
    }

    public int indexOfEntry(VLVTypeRunner target){
        int size = dynamicentries.size();

        for(int i = 0; i < size; i++){
            if(dynamicentries.get(i).entry.equals(target)){
                return i;
            }
        }

        return -1;
    }

    public int activateEntry(int index){
        Entry<ENTRY> target = dynamicentries.get(index);

        if(!target.active){
            add(target.entry);
            target.active = true;

            return size() - 1;

        }else{
            return -1;
        }
    }

    public int activateLastEntry(){
        return activateEntry(dynamicentries.size() - 1);
    }

    public void deactivateEntry(int index){
        Entry<ENTRY> target = dynamicentries.get(index);
        target.active = false;
        get().remove(target.entry);
    }

    public boolean isEntryActive(int index){
        return dynamicentries.get(index).active;
    }

    public VLListType<Entry<ENTRY>> getEntries(){
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

    public static final class Entry<TYPE extends VLVTypeRunner>{

        public TYPE entry;
        public boolean active;

        protected Entry(TYPE entry){
            this.entry = entry;
            active = false;
        }
    }
}
