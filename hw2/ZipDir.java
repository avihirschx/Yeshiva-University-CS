import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipDir
{
	ArrayList<String> fileList;
	private ArrayList<String> report;
	private String inDir;
	private String outDir;
	private String type;
	private String[] exclude;

	public ZipDir()
	{
		fileList = new ArrayList<>();
		report = new ArrayList<>();
	}

	public static void main(String[] args) throws IOException
	{
		ZipDir zipProgram = new ZipDir();

		//Find values for inDir, outDir, type, and exclude.
		zipProgram.parse(args);

		//Generate a file list from the in Directory, maintaining directory structure.
		zipProgram.generateFileList(new File(zipProgram.getInDir()));

		if (zipProgram.getType().equals("zip")) {
			//Zip the files into a zip file.
			zipProgram.zip(zipProgram.getOutDir().concat("\\" + "out.zip"));
			zipProgram.printReport();	//Print the report.
		}
		else if (zipProgram.getType().equals("jar")) {
			//Zip the files into a jar file.
			zipProgram.zip(zipProgram.getOutDir().concat("\\" + "out.jar"));
			zipProgram.printReport();	//Print the report.
		}
		else {
			//Invalid or missing type.
			System.out.println(zipProgram.getType());
		}
		
	}

	public void parse(String[] arguments)
	{
		for (String arg : arguments) {
			//Parse the arguments for the information we need:
			switch (arg.substring(0, arg.indexOf("="))) {
				case "inDir": this.inDir = arg.substring(6);
					break;
				case "outDir": this.outDir = arg.substring(7);
					break;
				case "type": this.type = arg.substring(5);
					break;
				case "exclude":
					if (arg.substring(8).length() > 0) {
						String excludeString = arg.substring(8);
						this.exclude = excludeString.split(",");
					}
					break;
				//Ignore any other arguments.
				default: break;
			}
		}
	}

	public void generateFileList(File node)
	{
		if (node.isFile()) {
			//Check if this file type is to be excluded.
			if (!checkExclusion(node)) {
				//If not, add the file's relative directory to the file list.
				fileList.add(generateZipEntry(node.getAbsolutePath()));
			}
		}
		if (node.isDirectory()) {
			for (String fileName : node.list()) {		//Check every file "child" in the directory
				File child = new File(node, fileName);	//Create new File reference to each child
				generateFileList(child);				//Recursively call this method to move through entire directory
			}
		}
	}

	private String generateZipEntry(String file)
	{
		return file.substring(inDir.length() + 1); //The entry starts from after indir, to get relative directory only.
	}

	public void zip(String zipFile) throws IOException
	{
		byte[] buffer = new byte[1024];
		try(FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos)) {

			for (String file : this.fileList) {

				//Add this file's relative directory to the report list.
				this.report.add(file);

				//Create new zip entry for this file.
				ZipEntry ze = new ZipEntry(file);
				//Send the zip entry to the zipOutputStream.
				zos.putNextEntry(ze);

				FileInputStream fis = new FileInputStream(inDir + File.separator + file);

				//Write to the zip file.
				int len;
				while ((len = fis.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}
				fis.close();
			}
			zos.closeEntry();
		}
	}

	private boolean checkExclusion(File file)
	{
		boolean isExcluded = false;
			if (this.exclude != null) {
				for (String fileType : this.exclude) {
					if (file.getName().endsWith(fileType)) {
						isExcluded = true;
					}
				}
			}
		return isExcluded;
	}

	public String getInDir()
	{
		return this.inDir;
	}

	public String getOutDir()
	{
		return this.outDir;
	}

	public String getType()
	{
		if (this.type.equals("zip") || this.type.equals("jar")) {
			return this.type;
		}
		return "Please enter what type of file you wish to create: zip or jar.";
	}

	public void printReport() throws IOException
	{
		//create a file "report.txt" in outDir and write the report list to it.
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.outDir.concat("\\" + "report.txt")))) {

			//For every line in the report
			for (String line : this.report) {
				bw.write(line);	//Write the next line to the text file.
				bw.newLine();	//Move to the next line in text file.
			}

		}
	}
}