package test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
	public Map<String, List<String>> types = new HashMap<>();

	ImportTest() throws JsonParseException, IOException {
		jn = m.readTree(new File("0.4.1.json"));

	}

	Stack<String> type = new Stack<String>();

	public String parseSchema(TreeNode node, String key) throws IOException {
		if (node.isObject()) {
			type.push(key);
			Iterator<String> fields = node.fieldNames();
			List<String> s = new ArrayList<>();
			while (fields.hasNext()) {

				String field = fields.next();
				if (node.at("/type").toString().contains("object")) {
					// System.out.println("true");
					key = parseSchema(node.get(field), field);

				} else {
					// System.out.println("false");
					// System.out.println(type.toString()+":"+field);
					s.add(field);
					key = parseSchema(node.get(field), field);
				}
			}
			types.put(type.toString(), s);
			type.pop();
		}

		return key;
	}

	public static void main(String[] args) throws JsonProcessingException,
			IOException {

		ImportTest test = new ImportTest();

		test.parseSchema(test.jn, "start");

		for (Entry<String, ?> e : test.types.entrySet()) {
			System.err.print(e.getKey().replaceAll(", ", ".").replaceFirst("start.schema.properties", "freifunk.community"));
			System.err.println(" : " + e.getValue().toString());
		}
		/** output		 
[freifunk.community.state.properties.focus] : [title, type, description, required, items]
[freifunk.community.nodeMaps.items.properties.technicalType] : [title, type, description, enum, default, required]
[freifunk.community.api] : [title, type, description, enum, default, required]
[freifunk.community.location.properties.address.properties.Name] : [title, type, description, required]
[freifunk.community.contact.properties.twitter] : [title, type, pattern, description, required]
[freifunk.community.techDetails.properties.networks] : []
[freifunk.community.feeds.items.properties.type] : [title, type, description, required]
[freifunk.community.state.properties.focus.items] : [type, title, enum]
		 */
	}
}
