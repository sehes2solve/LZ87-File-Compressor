import java.util.HashMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.*;
import java.io.*;

public class LZ78 {

    public static Integer Search(HashMap <Integer,String> list  , String msg){
        for (Integer i : list.keySet()) {
            if (list.get(i).equals(msg) ) {
                return i;
            }
        }
        return 0;
    }

    //public static void WriteTags

    public static void Compression(String msg) throws IOException {
        FileWriter TagsFile = new FileWriter("TagsCOMP.txt");
        HashMap <Integer,String> list = new HashMap <Integer,String>();
        list.put(0,"");     //default position
        Integer key = 1;    //next key
        for (Integer i = 0 ; i < msg.length() ; i++){
            String s ="";
            s+= msg.charAt(i);
            while (Search(list, s) != 0 && i != msg.length() - 1 ) {
                i++;
                s += msg.charAt(i);
            }
            if(i != msg.length() - 1){
                System.out.println( Search(list, s.substring(0,s.length()-1)) + " " + msg.charAt(i) );
                TagsFile.write(Search(list, s.substring(0,s.length()-1)).toString()+ " " + msg.charAt(i) + "\n" );
                list.put(key++, s);
            }
            else{
                if(s.length()>1){
                    System.out.println( Search(list, s.substring(0,s.length()-1 ))+ " "+s.charAt(s.length()-1) );
                    TagsFile.write(Search(list, s.substring(0,s.length()-1 ))+ " " + s.charAt(s.length()-1));
                }
                else{
                    if(Search(list, s)==0) {
                        list.put(key, s);
                    }
                    System.out.println( "0 "+s );
                    TagsFile.write("0 " + s);
                }
            }
        }
        System.out.println(list);
        TagsFile.close();
    }
    public static void Decompression() throws IOException {
        ArrayList<SimpleEntry<Integer,String>> tags = new ArrayList<SimpleEntry<Integer,String>>();
        FileWriter outfile = new FileWriter("Message.txt");
        File file = new File ("TagsCOMP.txt");
        Scanner reader = new Scanner(file);
        int idx;String str;SimpleEntry<Integer,String>  pair = new SimpleEntry<Integer,String>(0,"k");
        while(reader.hasNext()){
            idx = reader.nextInt();
            str = reader.next();
            pair = new SimpleEntry<Integer,String>(idx,str);
            tags.add(pair);
        }
        HashMap <Integer,String> list = new HashMap<Integer,String>();
        list.put(0,"");
        Integer key = 1;
        String msg = "",tag = "";
        for (Integer i  = 0;i < tags.size();i++) {
            tag = tags.get(i).getValue();
            if(tag == "NULL")
                tag = "";
            msg = list.get(tags.get(i).getKey()) + tag;
            if(Search(list,msg) == 0){
                list.put(key++,msg);
            }
            System.out.print(msg);
            outfile.write(msg);
        }
        System.out.println("");
        System.out.println(list);
        outfile.close();
    }

    public static void main(String[] args) throws IOException {
        Compression("ABBC");
        Decompression();
    }
}
