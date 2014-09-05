package test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ImportTest {
	
	public JsonFactory jfactory = new JsonFactory();
	public ObjectMapper m = new ObjectMapper();
	public Integer count = 0;
	public JsonNode jn;
	
	ImportTest() throws JsonParseException, IOException {
		jn = m.readTree(new File("0.4.1.json"));
		
	}
	
	Stack<String> type = new Stack<String>();
	public String parseSchema(TreeNode node, String key) throws IOException {
		if (node.isObject()) {
			type.push(key);
			//System.out.println("Found an object");
			Iterator<String> fields = node.fieldNames();
			while (fields.hasNext()) {
				String field = fields.next();
				key = parseSchema(node.get(field),field);
			}	
		} else if (node.isValueNode()) {
			type.push(key);
			//type = "";
			//System.out.println(key + " : "+node.toString());
		} else if (node.isArray()) {
			type.push(key);
			
			
		}
		System.out.println(type.toString());
		type.pop();
		return key;
	}
	



	public static void main(String[] args) throws JsonProcessingException, IOException {
	
		ImportTest test = new ImportTest(); 
		
		test.parseSchema(test.jn,"start");
	}
}
