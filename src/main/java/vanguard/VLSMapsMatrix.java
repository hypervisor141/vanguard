package vanguard;

public class VLSMapsMatrix{

    public static final class Var extends VLSyncMap<VLVTypeVariable, VLVMatrix>{

        public int rowindex;
        public int columnindex;

        public Var(VLVMatrix target, int rowindex, int columnindex){
            super(target);

            this.rowindex = rowindex;
            this.columnindex = columnindex;
        }

        @Override
        public void sync(VLVTypeVariable source){
            target.getColumn(rowindex, columnindex).set(source.get());
        }
    }
}
