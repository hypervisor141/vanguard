package vanguard;

public class VLSMapsArray{

    public static class Var extends VLSyncMap<VLVTypeVariable, VLArray<Float, ?>>{

        private int index;

        public Var(VLArray<Float, ?> target, int index){
            super(target);
            this.index = index;
        }

        @Override
        public void sync(VLVTypeVariable source){
            target.set(index, source.get());
        }
    }

    public static class Matrix extends VLSyncMap<VLVMatrix, VLArray<Float, ?>>{

        public int offset;
        public int rowindex;

        public Matrix(VLArray<Float, ?> target, int rowindex, int offset){
            super(target);

            this.offset = offset;
            this.rowindex = rowindex;
        }

        @Override
        public void sync(VLVMatrix source){
            VLListType<VLVTypeVariable> row = source.getRow(rowindex);
            int index = offset;

            for (int i = 0; i < row.size(); i++) {
                target.set(index++, row.get(i).get());
            }
        }
    }
}
