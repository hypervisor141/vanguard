package hypervisor.vanguard.list.arraybacked;

import hypervisor.vanguard.list.VLList;
import hypervisor.vanguard.utils.VLCopyable;

public abstract class VLListArrayBacked<TYPE> extends VLList<TYPE> {

    public VLListArrayBacked(int currentsize, int resizeoverhead){
        super(currentsize, resizeoverhead);
    }

    protected VLListArrayBacked(){

    }

    @Override
    public void remove(int index, int count){
        checkOperableRange(index, count);

        if(index + count == vsize){
            nullify(index, count);

        }else{
            int endpoint = index + count;

            TYPE array = backend();
            System.arraycopy(array, endpoint, array, index, vsize - endpoint);
            nullify(vsize - count, vsize);
        }

        vsize -= count;
    }

    @Override
    public void copy(VLList<TYPE> src, long flags){
        if((flags & FLAG_REFERENCE) == FLAG_REFERENCE){
            backend = src.backend();

        }else if((flags & FLAG_DUPLICATE) == FLAG_DUPLICATE){
            System.arraycopy(src.backend(), 0, backend, 0, realSize());

        }else{
            VLCopyable.Helper.throwMissingDefaultFlags();
        }

        resizeoverhead = src.resizeOverhead();
        vsize = src.size();
    }
}
