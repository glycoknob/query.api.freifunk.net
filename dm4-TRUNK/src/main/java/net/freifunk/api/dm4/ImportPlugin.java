package net.freifunk.api.dm4;

import java.io.IOException;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.freifunk.api.dm4.service.ImportService;
import de.deepamehta.core.osgi.PluginActivator;

@Path("/freifunk/import")
@Consumes("application/json")
public class ImportPlugin extends PluginActivator implements ImportService {

	private Logger log = Logger.getLogger(getClass().getName());
	
	@POST
	@Path("schema")
	public void importSchema(String json) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readValue(json, JsonNode.class);
		log.info("got something");
		
		
	}

	public void importData(String json) {
		// TODO Auto-generated method stub
		
	}

	
}
