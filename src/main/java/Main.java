import hypervisor.vanguard.utils.VLLog;
import hypervisor.vanguard.variable.VLV;
import hypervisor.vanguard.variable.VLVManager;
import hypervisor.vanguard.variable.VLVMatrix;

public class Main {

    public static void main(String[] args){
        VLVMatrix mat = new VLVMatrix(5, 0);

        mat.addRow(3, 0);
        mat.addColumn(0, VLV.ZERO);
        mat.addColumn(0, VLV.ONE);
        mat.addColumn(0, VLV.NEGATIVE_ONE);

        System.out.println(mat.getRow(0).get(1).get() + " ");
    }
}
