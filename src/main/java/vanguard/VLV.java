package vanguard;

public class VLV implements VLVTypeVariable{

    public static final VLV ZERO = new VLV(0);
    public static final VLV ONE = new VLV(1);
    public static final VLV NEGATIVE_ONE = new VLV(-1);

    protected float value;

    public VLV(float value){
        this.value = value;
    }

    public VLV(){
        value = 0;
    }

    @Override
    public void set(float s){
        value = s;
    }

    @Override
    public float get(){
        return value;
    }

    @Override
    public void changeRate(float s){

    }

    @Override
    public void initialize(int cycles){

    }

    @Override
    public void initialize(float changerate){

    }

    @Override
    public void initializeFixedDirection(int cycles){

    }

    @Override
    public void initializeFixedDirection(float changerate){

    }

    @Override
    public void activate(){

    }

    @Override
    public void deactivate(){

    }

    @Override
    public int next(){
        return 0;
    }

    @Override
    public void fastForward(int count){

    }

    @Override
    public void chain(int cycles, float to){

    }

    @Override
    public void reverse(){

    }

    @Override
    public void reset(){

    }

    @Override
    public void finish(){

    }

    @Override
    public float changeRate(){
        return 0F;
    }

    @Override
    public float length(){
        return 0F;
    }

    @Override
    public boolean active(){
        return false;
    }

    @Override
    public void stringify(StringBuilder src, Object hint){
        src.append("[");
        src.append(getClass().getSimpleName());
        src.append("] value[");
        src.append(get());
        src.append("]");
    }
}
