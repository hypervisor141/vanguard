package hypervisor.vanguard.variable;

import hypervisor.vanguard.utils.VLCopyable;
import hypervisor.vanguard.utils.VLLog;
import hypervisor.vanguard.utils.VLLoggable;
import hypervisor.vanguard.list.arraybacked.VLListType;

public class VLVMatrix implements VLLoggable, VLCopyable<VLVMatrix> {

    public static final long FLAG_REFERENCE_ENTRIES = 0x1L;
    public static final long FLAG_DUPLICATE_ENTRIES = 0x2L;

    protected VLListType<VLListType<VLVTypeVariable>> matrix;

    public VLVMatrix(int capacity, int resizeoverhead){
        matrix = new VLListType<>(capacity, resizeoverhead);
    }

    public VLVMatrix(VLVMatrix src, long flags){
        copy(src, flags);
    }

    protected VLVMatrix(){

    }

    public void addRow(int capacity, int resizeoverhead){
        matrix.add(new VLListType<VLVTypeVariable>(capacity, resizeoverhead));
    }

    public void addRow(int index, int capacity, int resizeoverhead){
        matrix.add(index, new VLListType<VLVTypeVariable>(capacity, resizeoverhead));
    }

    public void addColumn(int index, VLVTypeVariable element){
        matrix.get(index).add(element);
    }

    public void addColumn(int index, int columnindex, VLVTypeVariable element){
        matrix.get(index).add(columnindex, element);
    }

    public void setRow(int index, int capacity, int resizeoverhead){
        matrix.set(index, new VLListType<>(capacity, resizeoverhead));
    }

    public void setRow(int index, VLListType<VLVTypeVariable> row){
        matrix.set(index, row);
    }

    public void setColumn(int index, int columnindex, VLVTypeVariable value){
        matrix.get(index).set(columnindex, value);
    }

    public void swapRows(int index, int toindex){
        VLListType<VLVTypeVariable> a = matrix.get(index);
        VLListType<VLVTypeVariable> a2 = matrix.get(toindex);

        matrix.set(index, a2);
        matrix.set(toindex, a);
    }

    public void removeRow(int index){
        matrix.remove(index);
    }

    public void removeColumn(int index, int columnindex){
        matrix.get(index).remove(columnindex);
    }

    public VLListType<VLVTypeVariable> getRow(int index){
        return matrix.get(index);
    }

    public VLVTypeVariable getColumn(int index, int columnindex){
        return matrix.get(index).get(columnindex);
    }

    public int sizeRows(){
        return matrix.size();
    }

    public int sizeColumns(int index){
        return matrix.get(index).size();
    }

    @Override
    public void copy(VLVMatrix src, long flags){
        if((flags & FLAG_REFERENCE) == FLAG_REFERENCE){
            matrix = src.matrix;

        }else if((flags & FLAG_DUPLICATE) == FLAG_DUPLICATE){
            matrix = src.matrix.duplicate(FLAG_DUPLICATE);

        }else if((flags & FLAG_CUSTOM) == FLAG_CUSTOM){
            if((flags & FLAG_REFERENCE_ENTRIES) == FLAG_REFERENCE_ENTRIES){
                matrix = src.matrix.duplicate(VLCopyable.FLAG_REFERENCE);

            }else if((flags & FLAG_DUPLICATE_ENTRIES) == FLAG_DUPLICATE_ENTRIES){
                matrix = src.matrix.duplicate(VLCopyable.FLAG_DUPLICATE);

            }else{
                VLCopyable.Helper.throwMissingSubFlags("FLAG_CUSTOM", "FLAG_REFERENCE_ENTRIES", "FLAG_DUPLICATE_ENTRIES");
            }

        }else{
            VLCopyable.Helper.throwMissingAllFlags();
        }
    }

    @Override
    public VLVMatrix duplicate(long flags){
        return new VLVMatrix(this, flags);
    }

    @Override
    public void log(VLLog log, Object data){
        boolean verbose = data != null && (boolean) data;

        log.append("[");
        log.append(getClass().getSimpleName());
        log.append("] countRows[");
        log.append(sizeRows());
        log.append("] content[");

        if(verbose){
            log.append("\n");
        }

        int size = matrix.size();

        for(int i = 0; i < size; i++){
            VLListType<VLVTypeVariable> row = matrix.get(i);
            int size2 = row.size();

            log.append("row[");
            log.append(i);
            log.append("] size[");
            log.append(size2);
            log.append(verbose ? "] columns[\n" : "] columns[");

            for(int i2 = 0; i2 < size2; i2++){
                row.get(i2).log(log, data);

                if(i2 != row.size() - 1){
                    log.append(verbose ? ",\n" : ", ");
                }
            }

            log.append(verbose ? "]\n" : "] ");
        }

        log.append("]");
    }
}
