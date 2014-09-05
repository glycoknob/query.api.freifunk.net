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
			System.err.print(e.getKey().replaceAll(", ", ".").replaceFirst("start.schema.properties", "freifunk.community").replaceAll("properties.", ""));
			System.err.println(" : " + e.getValue().toString());
		}
		/** output		 
[freifunk.community.state.focus] : [title, type, description, required, items]
[freifunk.community.nodeMaps.items.technicalType] : [title, type, description, enum, default, required]
[freifunk.community.api] : [title, type, description, enum, default, required]
[freifunk.community.location.address.Name] : [title, type, description, required]
[freifunk.community.contact.twitter] : [title, type, pattern, description, required]
[freifunk.community.techDetails.networks] : []
[freifunk.community.feeds.items.type] : [title, type, description, required]
[freifunk.community.state.focus.items] : [type, title, enum]
[freifunk.community.techDetails.updatemode.items] : [type, title, enum]
[freifunk.community.location.lat] : [title, type, description, required]
[freifunk.community.techDetails.networks.ipv6] : [type, title, description, required, items]
[freifunk.community.techDetails.networks.ipv4.items.network] : [type, description, pattern, required]
[freifunk.community.nodeMaps.items. : [url, interval, technicalType, mapType]
[freifunk.community.location.address.Street] : [title, type, description, required]
[freifunk.community.techDetails.firmware.docs] : [title, type, pattern, description, required]
[freifunk.community.contact.identica] : [title, type, pattern, description, required]
[freifunk.community.contact.phone] : [title, type, description, required]
[freifunk.community.techDetails.networks.ipv4.items] : []
[freifunk.community.techDetails.dns.items.nameserver] : [type, title, description, required, items]
[freifunk.community.location] : []
[freifunk.community.techDetails.firmware. : [url, docs, name]
[freifunk.community.timeline.items. : [decription, timestamp]
[freifunk.community.services.items.externalUri] : [title, type, description, required]
[freifunk.community.techDetails.dns.items.domainname] : [type, description, pattern, required, title]
[freifunk.community.nodeMaps] : [type, description, required, items]
[freifunk.community.techDetails.routing] : [title, description, required, type, items]
[freifunk.community.nodeMaps.items] : []
[freifunk.community.techDetails.networks.ipv6.items] : []
[freifunk.community] : [api, name, metacommunity, location, contact, url, timeline, feeds, state, nodeMaps, services, techDetails]
[freifunk.community.location.address. : [Name, Street, Zipcode]
[freifunk.community.timeline.items.decription] : [title, type, description, required]
[freifunk.community.state.lastchange] : [title, type, description, default, required]
[freifunk.community.feeds] : [type, description, required, items]
[freifunk.community.techDetails. : [firmware, dns, networks, routing, legals, updatemode]
[freifunk.community.location.city] : [title, type, description, required]
[freifunk.community.contact] : []
[freifunk.community.techDetails.dns] : [title, descripition, type, items]
[freifunk.community.techDetails.legals] : [title, description, required, type, items]
[freifunk.community.contact.irc] : [title, type, pattern, description, required]
[freifunk.community.techDetails.networks.ipv4] : [type, description, required, items]
[freifunk.community.url] : [title, type, pattern, description, required]
[freifunk.community.feeds.items.category] : [title, type, enum, default, description, required]
[freifunk.community.services.items.internalUri] : [title, type, description, required]
[freifunk.community.metacommunity] : [title, type, description, required]
[freifunk.community.feeds.items.url] : [title, type, description, required]
[freifunk.community.contact.jabber] : [title, type, pattern, description, required]
[freifunk.community.timeline.items.timestamp] : [title, type, description, required]
[freifunk.community.state.description] : [title, type, description, required]
[freifunk.community.location.address] : []
[freifunk.community.contact.webform] : [title, type, description, pattern, required]
[freifunk.community.timeline] : [type, description, required, items]
[freifunk.community.nodeMaps.items.url] : [title, type, description, required]
[freifunk.community.techDetails.updatemode] : [title, type, description, required, items]
[freifunk.community.state] : []
[freifunk.community.location.lon] : [title, type, description, required]
[freifunk.community.services.items.serviceDescription] : [title, type, description, required]
[freifunk.community.techDetails.dns.items. : [domainname, nameserver]
[freifunk.community.techDetails.networks.ipv4.items. : [network]
[freifunk.community.feeds.items.name] : [title, type, description, required]
[freifunk.community.techDetails.networks.ipv6.items.network] : [type, description, pattern, required]
[freifunk.community.techDetails.legals.items] : [type, title, enum]
[freifunk.community.techDetails.firmware.name] : [title, type, description, required]
[freifunk.community.techDetails] : []
[freifunk.community.techDetails.networks.ipv6.items. : [network]
[freifunk.community.techDetails.dns.items] : []
[freifunk.community.name] : [title, type, description, required]
[freifunk.community.timeline.items] : []
[freifunk.community.services] : [type, description, required, items]
[freifunk.community.techDetails.networks. : [ipv6, ipv4]
[freifunk.community.nodeMaps.items.mapType] : [title, type, description, enum, default, required]
[freifunk.community.state.nodes] : [title, type, description, required]
[freifunk.community.services.items. : [serviceName, serviceDescription, externalUri, internalUri]
[freifunk.community.techDetails.dns.items.nameserver.items] : [type, pattern, required]
[freifunk.community.contact.email] : [title, type, pattern, description, required]
[freifunk.community.nodeMaps.items.interval] : [title, type, description, required]
[freifunk.community.location. : [city, country, address, lat, lon]
[freifunk.community.contact.googleplus] : [title, type, description, pattern, required]
[freifunk.community.services.items.serviceName] : [title, type, description, required]
[freifunk.community.state. : [nodes, lastchange, message, description, focus]
[freifunk.community.feeds.items] : []
[freifunk.community.feeds.items. : [name, category, type, url]
[freifunk.community.contact.ml] : [title, type, description, pattern, required]
[start] : [schema]
[freifunk.community.contact.facebook] : [title, type, pattern, description, required]
[freifunk.community.state.message] : [title, type, description, required]
[freifunk.community.techDetails.routing.items] : [type, title, enum]
[freifunk.community.techDetails.firmware.url] : [title, type, pattern, description, required]
[freifunk.community.location.address.Zipcode] : [title, type, description, required]
[start.schema] : []
[freifunk.community.location.country] : [title, type, description, enum, default, required]
[freifunk.community.techDetails.firmware] : []
[freifunk.community.services.items] : []
[freifunk.community.contact. : [email, facebook, identica, irc, jabber, ml, phone, googleplus, twitter, webform
		 */
	}
}
