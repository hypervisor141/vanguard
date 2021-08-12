import hypervisor.vanguard.buffer.VLBufferFloat;
import hypervisor.vanguard.utils.VLLoggable;

import java.nio.ByteOrder;

public class Main {

    public static void main(String[] args){
        try{
            VLBufferFloat buffer = new VLBufferFloat.Direct(5);
            buffer.adjustPreInitCapacity(10);
            buffer.initialize(ByteOrder.LITTLE_ENDIAN);

            System.out.println(buffer.size() + "  ");

            for(int i = 0; i < 13; i++){
                buffer.put((float)i);
            }

            System.out.println(VLLoggable.Helper.getString("wtf", buffer, null));

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
