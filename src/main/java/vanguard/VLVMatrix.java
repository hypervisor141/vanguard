package vanguard;

public class VLVMatrix implements VLStringify{

    protected VLListType<VLListType<VLVTypeVariable>> matrix;

    public VLVMatrix(int capacity, int resizer){
        matrix = new VLListType<>(capacity, resizer);
    }

    public void addRow(int initialcapacity, int resizercount){
        matrix.add(new VLListType<VLVTypeVariable>(initialcapacity, resizercount));
    }

    public void addRow(int index, int initialcapacity, int resizercount){
        matrix.add(index, new VLListType<VLVTypeVariable>(initialcapacity, resizercount));
    }

    public void addColumn(int index, VLVTypeVariable element){
        matrix.get(index).add(element);
    }

    public void addColumn(int index, int columnindex, VLVTypeVariable element){
        matrix.get(index).add(columnindex, element);
    }

    public void setRow(int index, int initialcapacity, int resizercount){
        matrix.set(index, new VLListType<>(initialcapacity, resizercount));
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
    public void stringify(StringBuilder src, Object hint){
        boolean verbose = hint != null && (boolean) hint;

        src.append("type[");
        src.append(getClass().getSimpleName());
        src.append("] sizeRows[");
        src.append(sizeRows());
        src.append("] content[");

        if(verbose){
            src.append("\n");
        }

        int size = matrix.size();
        int size2 = 0;

        VLListType<VLVTypeVariable> row;

        for(int i = 0; i < size; i++){
            row = matrix.get(i);
            size2 = row.size();

            src.append("row[");
            src.append(i);
            src.append("] size[");
            src.append(size2);
            src.append(verbose ? "] columns[\n" : "] columns[");

            for(int i2 = 0; i2 < size2; i2++){
                row.get(i2).stringify(src, verbose);

                if(i2 != row.size() - 1){
                    src.append(verbose ? ",\n" : ", ");
                }
            }

            src.append(verbose ? "]\n" : "] ");
        }

        src.append("] ");
    }
}
