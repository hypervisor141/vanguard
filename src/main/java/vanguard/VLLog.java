package vanguard;

public class VLLog{

    private StringBuilder builder = new StringBuilder();
    private final VLListType<String> tags;
    private int debugtagsoffset;

    public VLLog(String[] tags){
        this.tags = new VLListType<>(tags, tags.length);
        debugtagsoffset = Integer.MAX_VALUE;
    }

    public VLLog(String[] tags, int debugtagsoffset){
        this.tags = new VLListType<>(tags, tags.length);
        this.debugtagsoffset = debugtagsoffset;
    }

    public VLLog(int tagcapacity){
        tags = new VLListType<>(tagcapacity, tagcapacity);
        debugtagsoffset = Integer.MAX_VALUE;
    }

    public VLLog(int tagcapacity, int debugtagsoffset){
        tags = new VLListType<>(tagcapacity, tagcapacity);
        this.debugtagsoffset = debugtagsoffset;
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

    public void reset(){
        builder = new StringBuilder(builder.capacity());
    }

    public void setDebugTagsOffset(int offset){
        debugtagsoffset = offset;
    }

    public void setDebugTagsOffsetHere(){
        debugtagsoffset = tags.size() - 1;
    }

    public void addTag(String tag){
        tags.add(tag);
    }

    public void addTag(int index, String tag){
        tags.add(index, tag);
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

    public void removeTags(){
        tags.nullify();
        tags.virtualSize(0);
    }

    public void removeDebugTags(){
        if(debugtagsoffset < 0){
            tags.nullify();
            tags.virtualSize(0);

        }else{
            tags.remove(debugtagsoffset, tags.size() - debugtagsoffset);
        }
    }

    public StringBuilder get(){
        return builder;
    }

    public VLListType<String> tags(){
        return tags;
    }

    public int debugTagOffset(){
        return debugtagsoffset;
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

    public void clearLogs(){
        builder = null;
    }
}
