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
        if((flags & FLAG_MAX_DEPTH) == FLAG_MAX_DEPTH){
            array = src.array.clone();

        }else if((flags & FLAG_MINIMAL) == FLAG_MINIMAL){
            this.array = src.array;

        }else{
            throw new RuntimeException("Invalid flags : " + flags);
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