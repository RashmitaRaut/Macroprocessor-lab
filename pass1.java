import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class pass1 {
    static class MNT {
        int mnt_index;
        String macro_name;
        int mdt_index;
        int ala_mnt_index;

        MNT(int mnt_index, String macro_name, int mdt_index, int ala_mnt_index) {
            this.mnt_index = mnt_index;
            this.macro_name = macro_name;
            this.mdt_index = mdt_index;
            this.ala_mnt_index = ala_mnt_index;
        }
    }

    static class MDT {
        int mdt_index;
        String instr;

        MDT(int mdt_index, String instr) {
            this.mdt_index = mdt_index;
            this.instr = instr;
        }
    }

    static class ALA {
        int mnt_index;
        int ala_index;
        String arg;

        ALA(int mnt_index, int ala_index, String arg) {
            this.mnt_index = mnt_index;
            this.ala_index = ala_index;
            this.arg = arg;
        }
    }

    public static void main(String[] args) {
        String filePath = "C:\\Users\\Rashmita Raut\\Desktop\\Macroprocessor lab\\input.txt";
        String mdtFilePath = "MDT.txt";
        String mntFilePath = "MNT.txt";
        String alaFilePath = "ALA.txt";

        List<MDT> mdt = new ArrayList<>();
        List<MNT> mnt = new ArrayList<>();
        List<ALA> ala = new ArrayList<>();
        int mdt_pointer = 1;
        int mnt_pointer = 1;
        int ala_pointer = 0;
        int ala_mnt_index = 0;

        try {
            BufferedWriter mdtWriter = new BufferedWriter(new FileWriter(mdtFilePath));
            BufferedWriter mntWriter = new BufferedWriter(new FileWriter(mntFilePath));
            BufferedWriter alaWriter = new BufferedWriter(new FileWriter(alaFilePath));

            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            boolean macro = false;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("MACRO")) {
                    macro = true;
                    ala_mnt_index = ala_pointer; // to print ala index in mnt table
                    line = reader.readLine();

                    String[] parts = line.split("\\s+");

                    MNT mntEntry = new MNT(mnt_pointer, parts[0], mdt_pointer, ala_mnt_index);
                    mnt.add(mntEntry);
                    mntWriter.write(mntEntry.mnt_index + "     " + mntEntry.macro_name + "     " + mntEntry.mdt_index +  "\n");
                    mnt_pointer++;
                    for (int i = 1; i < parts.length; i++) {
                        ALA alaEntry = new ALA(mnt_pointer - 1, i, parts[i]);
                        ala.add(alaEntry);
                        alaWriter.write(alaEntry.ala_index + "          " + alaEntry.arg.replaceAll(",", "") + "\n");                        
                        ala_pointer++;
                    }

                    MDT mdtEntry = new MDT(mdt_pointer, line);
                    mdt.add(mdtEntry);
                    mdtWriter.write(mdtEntry.mdt_index + "     " + mdtEntry.instr.replaceAll(",", "") + "\n");
                    mdt_pointer++;

                } else if (line.startsWith("MEND")) {
                    MDT mdtEntry = new MDT(mdt_pointer, line);
                    mdt.add(mdtEntry);
                    mdtWriter.write(mdtEntry.mdt_index + "     " + mdtEntry.instr.replaceAll(",", "") + "\n");
                    mdt_pointer++;
                    macro = false;

                } else if (macro) {
                    String[] parts = line.split("\\s+");
                    for (int j = 0; j < parts.length; j++) {
                        for (ALA alaEntry : ala) {
                            if (parts[j].equals("#" + alaEntry.ala_index) && alaEntry.mnt_index == mnt_pointer) {
                                parts[j] = alaEntry.arg;
                                break;
                            }
                        }
                    }
                    MDT mdtEntry = new MDT(mdt_pointer, String.join(" ", parts));
                    mdt.add(mdtEntry);
                    mdtWriter.write(mdtEntry.mdt_index + "     " + mdtEntry.instr.replaceAll(",", "") + "\n");
                    mdt_pointer++;
                } else {
                    System.out.println(line.replaceAll(",", ""));
                }
            }

            mdtWriter.close();
            mntWriter.close();
            alaWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}