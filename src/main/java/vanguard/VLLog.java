package vanguard;

public class VLLog{

    private StringBuilder builder = new StringBuilder();
    private String tag;

    public VLLog(String tag){
        this.tag = tag;
    }

    public VLLog(){

    }

    public void append(Object s){
        builder.append(s);
    }

    public void append(String s){
        builder.append(s);
    }

    public void append(StringBuffer s){
        builder.append(s);
    }

    public void append(CharSequence s){
        builder.append(s);
    }

    public void append(CharSequence s, int start, int end){
        builder.append(s, start, end);
    }

    public void append(char[] s){
        builder.append(s);
    }

    public void append(char[] s, int offset, int length){
        builder.append(s, offset, length);
    }

    public void append(boolean s){
        builder.append(s);
    }

    public void append(char s){
        builder.append(s);
    }

    public void append(int s){
        builder.append(s);
    }

    public void append(long s){
        builder.append(s);
    }

    public void append(float s){
        builder.append(s);
    }

    public void append(double s){
        builder.append(s);
    }

    public StringBuilder get(){
        return builder;
    }

    public void reset(){
        builder = new StringBuilder(builder.capacity());
    }

    public void tag(String tag){
        this.tag = tag;
    }

    public void printInfo(){
        String[] lines = builder.toString().split("\n");
        int size = lines.length;

        for(int i = 0; i < size; i++){
            printInfo(lines[i]);
        }

        reset();
    }

    public void printError(){
        String[] lines = builder.toString().split("\n");
        int size = lines.length;

        for(int i = 0; i < size; i++){
            printError(lines[i]);
        }

        reset();
    }

    public void printInfo(String text){
        System.out.print("[");
        System.out.print(tag);
        System.out.print("] ");
        System.out.println(text);
    }

    public void printError(String text){
        System.err.print("[");
        System.err.print(tag);
        System.err.print("] ");
        System.err.println(text);
    }

    public void release(){
        builder = null;
    }
}
