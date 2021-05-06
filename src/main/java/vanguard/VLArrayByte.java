package vanguard;

import java.util.Arrays;

public class VLArrayByte extends VLArray<Byte, byte[]>{

    public VLArrayByte(byte[] s) {
        super(s);
    }

    public VLArrayByte(int size) {
        super(new byte[size]);
    }

    public VLArrayByte(VLArrayByte src, long flags){
        copy(src, flags);
    }

    protected VLArrayByte(){

    }

    @Override
    public void set(int index, Byte element) {
        array[index] = element;
    }

    @Override
    public Byte get(int index) {
        return array[index];
    }

    @Override
    public void resize(int size) {
        array = VLArrayUtils.resize(array, size);
    }

    @Override
    public int size() {
        return array.length;
    }

    @Override
    public void copy(VLArray<Byte, byte[]> src, long flags){
        if((flags & FLAG_REFERENCE) == FLAG_REFERENCE){
            array = src.array;

        }else if((flags & FLAG_DUPLICATE) == FLAG_DUPLICATE){
            array = src.array.clone();

        }else{
            Helper.throwMissingDefaultFlags();
        }
    }

    @Override
    public VLArrayByte duplicate(long flags){
        return new VLArrayByte(this, flags);
    }

    @Override
    public void stringify(StringBuilder src, Object hint){
        super.stringify(src, hint);

        src.append(" content[");
        src.append(Arrays.toString(array));
        src.append("]");
    }
}