import java.io.*; 
import java.util.*; 
public class pass2 { 
 public static void main(String[] args) throws IOException { 
 String str, tokens[], temp_arr[]; 
 int temp_num = 0, flag = 0; 
 
 ArrayList<String[]> mdt = new ArrayList<String[]>(); 
 ArrayList<String[]> mnt = new ArrayList<String[]>(); 
 ArrayList<String[]> ala = new ArrayList<String[]>(); 
 
 File input = new File("C:\\Users\\Rashmita Raut\\Desktop\\Macroprocessor lab\\input.txt"); 
 File output = new File("C:\\Users\\Rashmita Raut\\Desktop\\Macroprocessor lab\\expanded"); 
 File filemnt = new File("C:\\Users\\Rashmita Raut\\Desktop\\Macroprocessor lab\\MNT.txt"); 
 File filemdt = new File("C:\\Users\\Rashmita Raut\\Desktop\\Macroprocessor lab\\MDT.txt"); 
 File fileala = new File("C:\\Users\\Rashmita Raut\\Desktop\\Macroprocessor lab\\ALA.txt"); 
 
 FileWriter fw = new FileWriter("expanded"); 
 BufferedWriter bw = new BufferedWriter(fw); 
 
 // fill arraylists with file contents 
 // mnt 
 Scanner fileReader = new Scanner(filemnt); 
 while(fileReader.hasNextLine()) { 
 str = fileReader.nextLine(); 
 tokens = str.split("[ \\n\\t,]"); 
 
 mnt.add(new String[] {tokens[0], tokens[1], 
tokens[2]}); 
 } 
 fileReader.close(); 
 
 // mdt 
 fileReader = new Scanner(filemdt); 
 while(fileReader.hasNextLine()) { 
 str = fileReader.nextLine(); 
 tokens = str.split("[ \\n\\t,]"); 
 
 mdt.add(new String[] {"", "", "", ""}); 
 for(int i = 0; i < tokens.length; i++) { 
 mdt.get(temp_num)[i] = tokens[i]; 
 } 
 temp_num++; 
 } 
 fileReader.close(); 
 
 // ala 
 fileReader = new Scanner(fileala); 
 while(fileReader.hasNextLine()) { 
 str = fileReader.nextLine(); 
 tokens = str.split("[ \\n\\t,]"); 
 
 ala.add(new String[] {tokens[0], tokens[1], 
tokens[2]}); 
 } 
 fileReader.close(); 
 
 // replace the formal arguments in mdt with actual arguments from ala 
 for(int h = 0; h < ala.size(); h++) { 
 for(int i = 1; i < mdt.size(); i++) { 
 temp_arr = mdt.get(i); 
 for(int j = 0; j < temp_arr.length; j++) { 
 if(temp_arr[j].contentEquals(ala.get(h)[0])) 
{ 
 mdt.get(i)[j] = ala.get(h)[2]; 
 } 
 } 
 } 
 } 
 
 // expand macro calls 
 fileReader = new Scanner(input); 
 while(fileReader.hasNextLine()) { 
 str = fileReader.nextLine(); 
 tokens = str.split("[ \\n\\t,]"); 
 
 for(int i = 0; i < mnt.size(); i++) { 
 if(str.contains(mnt.get(i)[1])) { 
 temp_num = Integer.parseInt(mnt.get(i)[2]); 
 
 while(!mdt.get(temp_num)[1].contentEquals("MEND")) { 
 for(int k = 1; k < 
mdt.get(temp_num).length; k++) { 
 bw.write(mdt.get(temp_num)[k] + " "); 
 } 
 temp_num++; 
 bw.write("\n"); 
 } 
 flag = 1; 
 i += mnt.size(); // skips the rest of the loop 
 } 
 else { 
 flag = 0; 
 } 
// System.out.println("set flag " + flag); 
 } 
 
 if(flag == 0) { 
// System.out.println("check flag " + flag); 
 bw.write(str); 
 bw.write("\n"); 
 } 
 } 
 bw.close(); 
 fileReader.close(); 
 
 fileReader = new Scanner(output); 
 while(fileReader.hasNextLine()) { S
 str = fileReader.nextLine();
 System.out.println(str);
 }
 fileReader.close();
} }