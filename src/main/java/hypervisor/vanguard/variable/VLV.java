package hypervisor.vanguard.variable;

import hypervisor.vanguard.utils.VLLog;

public class VLV implements VLVTypeVariable{

    private static final class Static extends VLV{

        private Static(int value){
            super(value);
        }

        @Override
        public VLV duplicate(long flags){
            return this;
        }
    };

    public static final VLV ZERO = new Static(0);
    public static final VLV ONE = new Static(1);
    public static final VLV NEGATIVE_ONE = new Static(-1);

    protected float value;

    public VLV(float value){
        this.value = value;
    }

    public VLV(VLV src, long flags){
        copy(src, flags);
    }

    protected VLV(){

    }

    @Override
    public void initialize(int cycles){

    }

    @Override
    public void initialize(float changerate){

    }

    @Override
    public void initialize(float from, float to, int cycles){

    }

    @Override
    public void initialize(float from, float to, float changerate){

    }

    @Override
    public float convertCyclesToChangeRate(float from, float to, int cycles) {
        return 0;
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
    public int advance(){
        return 0;
    }

    @Override
    public void fastForward(int count){

    }

    @Override
    public void chain(int cycles, float to){

    }

    @Override
    public void chain(float changerate, float to){

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
    public void set(float value){
        this.value = value;
    }

    @Override
    public void changeRate(float changerate){

    }

    @Override
    public float changeRate(){
        return 0F;
    }

    @Override
    public float get(){
        return value;
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
    public boolean isIncreasing(){
        return false;
    }

    @Override
    public boolean reversed(){
        return false;
    }

    @Override
    public void copy(VLVTypeRunnable src, long flags){
        value = ((VLV)src).value;
    }

    @Override
    public VLV duplicate(long flags){
        return new VLV(this, flags);
    }

    @Override
    public void log(VLLog log, Object data){
        log.append("[");
        log.append(getClass().getSimpleName());
        log.append("] value[");
        log.append(get());
        log.append("]");
    }
}
