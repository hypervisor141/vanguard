package vanguard;

public interface VLLoggable{

    void log(VLLog log, Object data);

    final class Helper{

        public static String getString(String tag, VLLoggable target, Object data){
            VLLog log = new VLLog(tag, 1);
            target.log(log, data);

            return log.get().toString();
        }
    }
}
