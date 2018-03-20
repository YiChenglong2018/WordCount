package wordcount;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WordCount {
	private int lineNum;   //文件总行数  
	private int wordNum;   //单词个数
	private int charNum;   //字符个数
	private int codeNum;   //代码行数
	private int noteNum;   //注释行数
	private int spaceNum;  //空行行数
	static public String endString=".c";  //设定读取文件类型
	static public String getPath=".";     //获取文件路径参数
	static public boolean isStop=false;   //停用词表调用判断参数

/*调用命令输出结果*/
	public void Command(String[] args,String inputFile,String outputFile,WordCount WC)throws IOException{
		String outPut="";
		inputFile=inputFile.substring(inputFile.lastIndexOf("\\")+1, inputFile.length());
		for(int i=0;i<args.length;i++){
			if(args[i].equals("-c"))
				outPut=outPut+inputFile+",字符个数:" + charNum+"\r\n";//-c对应字符个数统计
		}
		for(int i=0;i<args.length;i++){
			if(args[i].equals("-w"))
				outPut=outPut+inputFile+",单词个数:" + wordNum+"\r\n";//-w对应字符个数统计
		}
		for(int i=0;i<args.length;i++){
			if(args[i].equals("-l"))
				outPut=outPut+inputFile+",文件总行数:" + lineNum+"\r\n";//-l对应字符个数统计，以上为基础功能部分
		}
		for(int i=0;i<args.length;i++){
			if(args[i].equals("-a"))
				outPut=outPut+inputFile+",代码行数:"+codeNum+",空行行数:"+spaceNum+",注释行数:"+noteNum+"\r\n";//-a对应代码行、空行、注释行统计，以上为拓展功能部分
		}
		System.out.println(outPut);
		/*将对应命令读取文件统计结果记录到已存在的result.txt*/
		 File file =new File("result.txt");
		 BufferedWriter result = new BufferedWriter(new FileWriter(file,true));
		 result.write(outPut+"\r\n");
		 result.flush(); // 将缓存区中的内容存入创建的文件xxx.txt之中
		 result.close(); // 关闭文件
		/*将输入-o后读取文件统计结果记录到新创建的xxx.txt之中*/		
		File writename = new File(outputFile); 
    	writename.createNewFile(); // 创建新文件  
        BufferedWriter _output = new BufferedWriter(new FileWriter(writename,true));
		for(int i=0;i<args.length;i++){
			if(args[i].equals("-o")){
	            _output.write(outPut+"\r\n");
			}	
		}
		_output.flush(); // 将缓存区中的内容存入创建的文件xxx.txt之中
        _output.close(); // 关闭文件
	}

/*实际统计功能方法体*/
	public void wc(String inputFile,String stopFile) throws IOException{
		String lineString = null; 
        String[] buff;
        String[] buff1 = null;
        boolean flagNode = false;
        String regx = "^//.*";               
        String regxNodeBegin = "\\s*/\\*.*"; 
        String regxNodeEnd = ".*\\*/\\s*";  
        if(isStop){
        	File dirr=new File(stopFile);
            BufferedReader bff = new BufferedReader(new FileReader(dirr)); 
            while((lineString=bff.readLine())!=null){
              buff1=lineString.split(",| ");
            }
            bff.close();
        }
        lineString = null;
        File dir=new File(inputFile);
        BufferedReader bf = new BufferedReader(new FileReader(dir));    
        while((lineString=bf.readLine())!=null){
        	buff=lineString.split(",| ");      //读取到“,与空格” 后，结束赋值
        	int notSpace=0;
 /*逐个读取文件内容，并进行相应判断，类似于词法分析过程*/    	
        	for(int i=0;i<buff.length;i++){
        		if(isStop){
        			if(!buff[i].equals("")&&!inStop(buff[i], buff1)){
            			wordNum++;
            		} 
        		}
        		else{
        			if(!buff[i].equals("")){
            			wordNum++;
            		}  
        		}   		
        	}
        	if(buff.length!=1)
        		lineNum++;
        	for(int i=0;i<lineString.length();i++){
        		if(!lineString.valueOf(i).equals(""))
        			notSpace++;
        	}
        	if(notSpace<=1)
        		spaceNum++;
        	charNum+=(lineString.length()+1);
        	
        	if (lineString.matches(regxNodeBegin) && lineString.matches(regxNodeEnd)) {
                ++noteNum;
                continue;
            }
            if (lineString.matches(regxNodeBegin)&&(!flagNode)) {
                ++noteNum;
                flagNode = true;
                continue;
            } else if (lineString.matches(regxNodeEnd)&&flagNode) {
                ++noteNum;
                flagNode = false;
                continue;
            }else if (lineString.matches(regx)){
                ++noteNum;
                continue;
            }else if(flagNode){
            	++noteNum;
            }
            }
            bf.close();
	}

/*遍历文件内容*/	
		public static List<File> getFile(File dir) {  
	        List<File> files = new ArrayList<File>();
	        File[] subs = dir.listFiles(); //读取当前文件下的所有文件、文件夹
	        for (File file : subs) {  
	            if (file.isFile() && file.getName().endsWith(endString)) {  
	                files.add(file);
	            } else if (file.isDirectory())  
	                files.addAll(getFile(file)); //若读取到目录，就对当前目录递归读取  
	        }  
	        return files;  
	    }   

/*判断读取内容是否在停用表内，即读取单词是否为停用词*/
public static boolean inStop(String str,String[] buffer){
	int count=0;
	for(int i=0;i<buffer.length;i++){
		if(str.equals(buffer[i])){
			count++;
		}
	}
	if(count>0)
		return true;
	else
		return false;
	}
}	