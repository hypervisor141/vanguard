package vanguard;

public class VLSMapsPrims{

    public static class Short extends VLSyncMap<VLVTypeVariable, VLShort>{

        public Short(VLShort target){
            super(target);
        }

        @Override
        public void sync(VLVTypeVariable source){
            target.set((short)source.get());
        }
    }

    public static class Int extends VLSyncMap<VLVTypeVariable, VLInt>{

        public Int(VLInt target){
            super(target);
        }

        @Override
        public void sync(VLVTypeVariable source){
            target.set((int)source.get());
        }
    }

    public static class Long extends VLSyncMap<VLVTypeVariable, VLLong>{

        public Long(VLLong target){
            super(target);
        }

        @Override
        public void sync(VLVTypeVariable source){
            target.set((long)source.get());
        }
    }

    public static class Float extends VLSyncMap<VLVTypeVariable, VLFloat>{

        public Float(VLFloat target){
            super(target);
        }

        @Override
        public void sync(VLVTypeVariable source){
            target.set((short)source.get());
        }
    }

    public static class Double extends VLSyncMap<VLVTypeVariable, VLDouble>{

        public Double(VLDouble target){
            super(target);
        }

        @Override
        public void sync(VLVTypeVariable source){
            target.set((double)source.get());
        }
    }
}
