package wordcount;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
//主函数countMain
public class countMain{
	public static void main(String[] args) throws IOException{
		WordCount WC=new WordCount();
		String inputFile="file.c";
		String stopFile="stopList.txt";
		String outputFile="result.txt";
		
		for(int i=0;i<args.length;i++){	
			if(args[i].endsWith(".c"))
				inputFile=args[i];
			if(args[i].equals("-e")){
				WordCount.isStop=true;
				if((i!=(args.length-1))&&args[i+1].endsWith(".txt"))
					stopFile=args[i+1];
			}
				
			if(args[i].equals("-o"))
				if(i!=(args.length-1))
					outputFile=args[i+1];
		}
		
		boolean flag=false;
		for(int i=0;i<args.length;i++){
			if(args[i].equals("-s")) 
				flag=true;	
		}
		
		if(flag){
			for(int i=0;i<args.length;i++){
				if((args[i].indexOf("*")!=-1)){
					WordCount.endString=args[i].substring(args[i].lastIndexOf("*")+1, args[i].length());
					WordCount.getPath=args[i].substring(0,args[i].lastIndexOf("*"));
				   if(WordCount.getPath.equals(""))
					   WordCount.getPath=".";
				}
			}
			File dir = new File(WordCount.getPath); 
			List<File> files = WordCount.getFile(dir); //获取所有的.c文件
			 for (File file : files) { 
	         WordCount ccount=new WordCount();
	         String filePath = file.getAbsolutePath();
	         ccount.wc(filePath,stopFile);
	        ccount.Command(args, filePath, outputFile, ccount);
		}   
		}
		else{
			WC.wc(inputFile,stopFile);
			WC.Command(args, inputFile, outputFile, WC);
		}
	}
}
