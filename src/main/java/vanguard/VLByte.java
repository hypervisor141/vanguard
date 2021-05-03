package vanguard;

public class VLByte implements VLPrimitive{

    private byte field;

    public VLByte(byte v){
        field = v;
    }

    public VLByte(VLByte src, long flags){
        copy(src, flags);
    }

    public VLByte(){

    }

    public void set(byte field){
        this.field = field;
    }

    public byte get(){
        return field;
    }

    @Override
    public void copy(VLPrimitive src, long flags){
        field = ((VLByte)src).field;
    }

    @Override
    public VLByte duplicate(long flags){
        return new VLByte(this, flags);
    }

    @Override
    public void stringify(StringBuilder src, Object hint){
        src.append("byte[");
        src.append(field);
        src.append("]");
    }
}