package vanguard;

public class VLLog{

    private StringBuilder builder = new StringBuilder();
    private final String[] tags;

    public VLLog(String[] tags){
        this.tags = tags;
    }

    public VLLog(String firsttag, int tagsize){
        tags = new String[tagsize];
        tags[0] = firsttag;
    }

    public VLLog(int tagsize){
        this.tags = new String[tagsize];
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

    public void tag(int index, String tag){
        this.tags[index] = tag;
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
        printTags();
        System.out.println(text);
    }

    public void printError(String text){
        printTagsError();
        System.err.println(text);
    }

    public void printTag(String tag){
        System.out.print("[");
        System.out.print(tag);
        System.out.print("]");
    }

    public void printTags(){
        int size = tags.length;

        for(int i = 0; i < size; i++){
            printTag(tags[i]);
            System.out.print(" ");
        }
    }

    public void printTagError(String tag){
        System.err.print("[");
        System.err.print(tag);
        System.err.print("]");
    }

    public void printTagsError(){
        int size = tags.length;

        for(int i = 0; i < size; i++){
            printTagError(tags[i]);
            System.err.print(" ");
        }
    }

    public void release(){
        builder = null;
    }
}
