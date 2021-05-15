package vanguard;

public interface VLLoggableType{

    void log(VLLog log, Object data);

    final class Helper{

        public String getString(String tag, VLLoggableType target, Object data){
            VLLog log = new VLLog(tag, 1);
            target.log(log, data);

            return log.get().toString();
        }
    }
}
