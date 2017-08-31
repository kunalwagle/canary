package controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StreamController {
	
	@RequestMapping(path="/stream", method=RequestMethod.GET)
	public void getStream() {
		
	}
	
}
