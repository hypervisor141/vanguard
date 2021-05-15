package vanguard;

public interface VLLoggableType{

    void log(VLLog log, Object data);

    final class Helper{

        public String getString(VLLoggableType target, Object data){
            VLLog log = new VLLog(0);
            target.log(log, data);

            return log.get().toString();
        }
    }
}
