package net.freifunk.api.dm4.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import de.deepamehta.core.service.PluginService;

public interface ImportService extends PluginService {
	
	void importSchema(String json) throws JsonParseException, JsonMappingException, IOException;
	
	void importData(String json);
}
