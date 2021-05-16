package vanguard;

public class VLLog{

    private StringBuilder builder = new StringBuilder();
    private final VLListType<String> tags;

    public VLLog(String[] tags){
        this.tags = new VLListType<>(tags, tags.length);
    }

    public VLLog(String firsttag, int tagsize){
        tags = new VLListType<>(tagsize, tagsize);
        tags.set(0, firsttag);
    }

    public VLLog(int tagsize){
        tags = new VLListType<>(tagsize, tagsize);
    }

    public VLLog(){
        this.tags = new VLListType<>(0, 5);
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

    public void addTag(String tag){
        tags.add(tag);
    }

    public void setTag(int index, String tag){
        tags.set(index, tag);
    }

    public void removeTag(int index){
        tags.remove(index);
    }

    public void removeLastTag(){
        tags.remove(tags.size() - 1);
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
        int size = tags.size();

        for(int i = 0; i < size; i++){
            printTag(tags.get(i));
            System.out.print(" ");
        }
    }

    public void printTagError(String tag){
        System.err.print("[");
        System.err.print(tag);
        System.err.print("]");
    }

    public void printTagsError(){
        int size = tags.size();

        for(int i = 0; i < size; i++){
            printTagError(tags.get(i));
            System.err.print(" ");
        }
    }

    public void release(){
        builder = null;
    }
}
