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

        public static void throwMissingFlags(String[] missing){
            throw new RuntimeException("Missing flags for copy operation, possible options".concat(Arrays.toString(missing)));
        };

        public static void throwCustomCopyNotSupported(long flags){
            throw new RuntimeException("Custom copy not supported, given flags[".concat(String.valueOf(flags)).concat("]"));
        };

        public static void throwUnsupportedFlagForThisObject(String flag){
            throw new RuntimeException("This instance does not support this flag[".concat(flag).concat("]"));
        };

        public static void throwMissingSubFlag(String mainflag, String... subflags){
            throw new RuntimeException("Missing sub-flag for the main flag[".concat(mainflag).concat("]. possible sub-flags").concat(Arrays.toString(subflags)));
        };
    }
}
