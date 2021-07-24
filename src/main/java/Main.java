import hypervisor.vanguard.variable.*;

public class Main {

    public static void main(String[] args){
        VLVLimited limited = new VLVLimited(-100, 100, 50, 0, 5, VLVariable.LOOP_RETURN_ONCE, VLVCurved.CURVE_LINEAR);
        limited.activate();

        while(limited.active()){
            limited.next();
            System.out.println(limited.get() + "  ");
        }
    }
}
