package com.group26.todoList.utils;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class FileDateUtil {


	
	
	private String BasePath = "D:\\task.json";

	/**
	 * 写入文件数据
	 * @param
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	public void writeDate(String content) throws IOException {
		
		File file = new File(BasePath);
		if(!file.exists()) {
          file.createNewFile();
		}
		//打开一个FileChannel
	    FileOutputStream writer = new FileOutputStream(BasePath);
	    FileChannel channel = writer.getChannel();
	    //将数据存储到ByteBuffer中
	    ByteBuffer buff = ByteBuffer.wrap(content.getBytes(StandardCharsets.UTF_8));
	    //通过通道写入数据
	    channel.write(buff); 
	    writer.close();
	     
	}
	
	/**
	 * 读取文件数据
	 * @param
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */

	public String readDate() throws IOException {
		
		File file = new File(BasePath);
		if(!file.exists()) {
          file.createNewFile();
		}
        
		RandomAccessFile reader = new RandomAccessFile(BasePath, "r");
		FileChannel channel = reader.getChannel();

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int bufferSize = 1024;
		if (bufferSize > channel.size()) {
			bufferSize = (int) channel.size();
		}
		ByteBuffer buff = ByteBuffer.allocate(bufferSize);
	
		while (channel.read(buff) > 0) {
			out.write(buff.array(), 0, buff.position());
		    buff.clear();
		}
		         
		String fileContent = new String(out.toByteArray(), StandardCharsets.UTF_8);
		reader.close();

	    return fileContent;
	}
}
