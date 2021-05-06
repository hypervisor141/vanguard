package vanguard;

import java.util.Arrays;

public final class VLArrayShort extends VLArray<Short, short[]>{

    public VLArrayShort(short[] s) {
        super(s);
    }

    public VLArrayShort(int size) {
        super(new short[size]);
    }

    public VLArrayShort(VLArrayShort src, long flags){
        copy(src, flags);
    }

    protected VLArrayShort(){

    }

    @Override
    public void set(int index, Short element) {
        array[index] = element;
    }

    @Override
    public Short get(int index) {
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
    public void copy(VLArray<Short, short[]> src, long flags){
        if((flags & FLAG_REFERENCE) == FLAG_REFERENCE){
            array = src.array;

        }else if((flags & FLAG_DUPLICATE) == FLAG_DUPLICATE){
            array = src.array.clone();

        }else{
            Helper.throwMissingDefaultFlags();
        }
    }

    @Override
    public VLArrayShort duplicate(long flags){
        return new VLArrayShort(this, flags);
    }

    @Override
    public void stringify(StringBuilder src, Object hint){
        super.stringify(src, hint);

        src.append(" content[");
        src.append(Arrays.toString(array));
        src.append("]");
    }
}
