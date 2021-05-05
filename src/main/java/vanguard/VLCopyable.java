package vanguard;

import java.util.Arrays;

public interface VLCopyable<TYPE>{

    long FLAG_REFERENCE = 0xFFFFFFFFFFFFFFFDL;
    long FLAG_DUPLICATE = 0xFFFFFFFFFFFFFFFEL;
    long FLAG_CUSTOM = 0xFFFFFFFFFFFFFFFFL;

    void copy(TYPE src, long flags);
    TYPE duplicate(long flags);

    final class Helper{

        private Helper(){}

        public static void throwMissingDefaultFlags(){
            throwMissingFlags(new String[]{
                    "FLAG_REFERENCE",
                    "FLAG_DUPLICATE"
            });
        }

        public static void throwMissingAllFlags(){
            throwMissingFlags(new String[]{
                    "FLAG_REFERENCE",
                    "FLAG_DUPLICATE",
                    "FLAG_CUSTOM"
            });
        }

        private static void throwMissingFlags(String[] missing){
            throw new RuntimeException("Missing flags for copy operation, possible options".concat(Arrays.toString(missing)));
        };

        public static void throwUnsupportedFlag(String flag){
            throw new RuntimeException("Unsupported flag for this object[".concat(flag).concat("]"));
        }

        public static void throwMissingSubFlags(String mainflag, String... subflags){
            throw new RuntimeException("Missing sub-flag for the main flag[".concat(mainflag).concat("]. possible sub-flags").concat(Arrays.toString(subflags)));
        };
    }
}
