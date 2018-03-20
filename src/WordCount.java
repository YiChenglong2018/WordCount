package wordcount;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WordCount {
	private int lineNum;   //�ļ�������  
	private int wordNum;   //���ʸ���
	private int charNum;   //�ַ�����
	private int codeNum;   //��������
	private int noteNum;   //ע������
	private int spaceNum;  //��������
	static public String endString=".c";  //�趨��ȡ�ļ�����
	static public String getPath=".";     //��ȡ�ļ�·������
	static public boolean isStop=false;   //ͣ�ôʱ�����жϲ���

/*��������������*/
	public void Command(String[] args,String inputFile,String outputFile,WordCount WC)throws IOException{
		String outPut="";
		inputFile=inputFile.substring(inputFile.lastIndexOf("\\")+1, inputFile.length());
		for(int i=0;i<args.length;i++){
			if(args[i].equals("-c"))
				outPut=outPut+inputFile+",�ַ�����:" + charNum+"\r\n";//-c��Ӧ�ַ�����ͳ��
		}
		for(int i=0;i<args.length;i++){
			if(args[i].equals("-w"))
				outPut=outPut+inputFile+",���ʸ���:" + wordNum+"\r\n";//-w��Ӧ�ַ�����ͳ��
		}
		for(int i=0;i<args.length;i++){
			if(args[i].equals("-l"))
				outPut=outPut+inputFile+",�ļ�������:" + lineNum+"\r\n";//-l��Ӧ�ַ�����ͳ�ƣ�����Ϊ�������ܲ���
		}
		for(int i=0;i<args.length;i++){
			if(args[i].equals("-a"))
				outPut=outPut+inputFile+",��������:"+codeNum+",��������:"+spaceNum+",ע������:"+noteNum+"\r\n";//-a��Ӧ�����С����С�ע����ͳ�ƣ�����Ϊ��չ���ܲ���
		}
		System.out.println(outPut);
		/*����Ӧ�����ȡ�ļ�ͳ�ƽ����¼���Ѵ��ڵ�result.txt*/
		 File file =new File("result.txt");
		 BufferedWriter result = new BufferedWriter(new FileWriter(file,true));
		 result.write(outPut+"\r\n");
		 result.flush(); // ���������е����ݴ��봴�����ļ�xxx.txt֮��
		 result.close(); // �ر��ļ�
		/*������-o���ȡ�ļ�ͳ�ƽ����¼���´�����xxx.txt֮��*/		
		File writename = new File(outputFile); 
    	writename.createNewFile(); // �������ļ�  
        BufferedWriter _output = new BufferedWriter(new FileWriter(writename,true));
		for(int i=0;i<args.length;i++){
			if(args[i].equals("-o")){
	            _output.write(outPut+"\r\n");
			}	
		}
		_output.flush(); // ���������е����ݴ��봴�����ļ�xxx.txt֮��
        _output.close(); // �ر��ļ�
	}

/*ʵ��ͳ�ƹ��ܷ�����*/
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
        	buff=lineString.split(",| ");      //��ȡ����,��ո� �󣬽�����ֵ
        	int notSpace=0;
 /*�����ȡ�ļ����ݣ���������Ӧ�жϣ������ڴʷ���������*/    	
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

/*�����ļ�����*/	
		public static List<File> getFile(File dir) {  
	        List<File> files = new ArrayList<File>();
	        File[] subs = dir.listFiles(); //��ȡ��ǰ�ļ��µ������ļ����ļ���
	        for (File file : subs) {  
	            if (file.isFile() && file.getName().endsWith(endString)) {  
	                files.add(file);
	            } else if (file.isDirectory())  
	                files.addAll(getFile(file)); //����ȡ��Ŀ¼���ͶԵ�ǰĿ¼�ݹ��ȡ  
	        }  
	        return files;  
	    }   

/*�ж϶�ȡ�����Ƿ���ͣ�ñ��ڣ�����ȡ�����Ƿ�Ϊͣ�ô�*/
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