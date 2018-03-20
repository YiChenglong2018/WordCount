package basictest;
import java.io.*;

public class basic {
	
	private static int lineCount; // 代码行数  
	private static int wordCount; //单词个数
	private static int charCount; //字符个数
	
	public basic(int lineCount,int wordCount,int charCount){
		this.lineCount=lineCount;
		this.wordCount=wordCount;
		this.charCount=charCount;
	}
	public static void main(String[] args) throws IOException{
		basic WC=new basic(0,0,0);
		String inputFile="file.c";
		String stopFlie="stopFile.txt";
		String outputFile="result.txt";
		for(int i=0;i<args.length;i++){
			if(args[i].endsWith(".c"))
				inputFile=args[i];
			if(args[i].equals("-e"))
				stopFlie=args[i+1];
			if(args[i].equals("-o"))
				outputFile=args[i+1];
		}
		
		WC.wc(inputFile);
		
		for(int i=0;i<args.length;i++)
		{
			switch(args[i]){
			case "-c":{
				System.out.println("字符个数:" + charCount);
				break;
			}
			case "-w":{
				System.out.println("单词个数:" + wordCount);
				break;
			}
	        case "-l":{
	        	System.out.println("代码行数:" + lineCount);
	        	break;
	        }
	        case "-o":{
	        	String outText="";   
	        	for(int j=0;j<args.length;j++){
	        		if(args[j].equals("-c"))
	        			outText=outText+"\r\n"+"字符个数:" + charCount;
	        		if(args[j].equals("-w"))
	        			outText=outText+"\r\n"+"单词个数:" + wordCount;
	        		if(args[j].equals("-l"))
	        			outText=outText+"\r\n"+"代码行数:" + lineCount;
	        		}
	        	File writename = new File(outputFile);
	        	writename.createNewFile(); // 创建新文件  
	            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
	            out.write(outText);
	            out.flush(); // 将缓存区内容压入文件  
	            out.close(); //关闭文件
	            break;
	        }
			}
		}
	}
    
	public void wc(String inputFile) throws IOException{
		String lineString = null; 
        String[] buffer;
        File dir=new File(inputFile);
        BufferedReader bf = new BufferedReader(new FileReader(dir));
        System.out.println(dir);        
        while((lineString=bf.readLine())!=null){
        	buffer=lineString.split(",| "); //当读取到“ ,与 空格”后，结束赋值
        	for(int i=0;i<buffer.length;i++){
        		if(!buffer[i].equals(""))
        			wordCount++;
        	}
            lineCount++;
        	charCount+=lineString.length();
            }
            bf.close();
	}
}
