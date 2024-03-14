import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class pass2 {
    public static void main(String[] args) {
        String inputFilePath = "C:\\Users\\Rashmita Raut\\Desktop\\Macroprocessor lab\\input.txt";
        String outputFilePath = "output.txt";
        String mntFilePath = "MNT.txt";
        String mdtFilePath = "MDT.txt";
        String alaFilePath = "ALA.txt";

        Map<String, Integer> alaMap = new HashMap<>();
        Map<Integer, List<String>> mdtMap = new HashMap<>();
        Map<String, Integer> mntMap = new HashMap<>();

        try {
            // Reading ALA Table
            BufferedReader alaReader = new BufferedReader(new FileReader(alaFilePath));
            String alaLine;
            while ((alaLine = alaReader.readLine()) != null) {
                String[] parts = alaLine.split("\\s+");
                alaMap.put(parts[1], Integer.parseInt(parts[0]));
            }
            alaReader.close();

            // Reading MDT Table
            BufferedReader mdtReader = new BufferedReader(new FileReader(mdtFilePath));
            String mdtLine;
            int mdtIndex = 1;
            while ((mdtLine = mdtReader.readLine()) != null) {
                String[] parts = mdtLine.split("\\s+");
                mdtMap.put(mdtIndex, List.of(parts));
                mdtIndex++;
            }
            mdtReader.close();

            // Reading MNT Table
            BufferedReader mntReader = new BufferedReader(new FileReader(mntFilePath));
            String mntLine;
            while ((mntLine = mntReader.readLine()) != null) {
                String[] parts = mntLine.split("\\s+");
                mntMap.put(parts[1], Integer.parseInt(parts[0]));
            }
            mntReader.close();

            // Perform macro expansion
            BufferedReader inputReader = new BufferedReader(new FileReader(inputFilePath));
            BufferedWriter outputWriter = new BufferedWriter(new FileWriter(outputFilePath));
            String line;
            while ((line = inputReader.readLine()) != null) {
                if (mntMap.containsKey(line.trim())) {
                    int mntIndex = mntMap.get(line.trim());
                    List<String> expandedMacro = mdtMap.get(mntIndex);
                    for (String macroLine : expandedMacro) {
                        if (macroLine.startsWith("&")) {
                            int argIndex = alaMap.get(macroLine);
                            String arg = line.split("\\s+")[argIndex];
                            outputWriter.write(arg + " ");
                        } else {
                            outputWriter.write(macroLine + " ");
                        }
                    }
                    outputWriter.newLine();
                } else {
                    outputWriter.write(line);
                    outputWriter.newLine();
                }
            }
            inputReader.close();
            outputWriter.close();

            System.out.println("Macro expansion completed. Output written to " + outputFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
