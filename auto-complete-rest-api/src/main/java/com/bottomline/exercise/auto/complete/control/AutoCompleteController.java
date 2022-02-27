package com.bottomline.exercise.auto.complete.control;

import java.util.List;

import org.eclipse.jdt.core.compiler.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bottomline.exercise.auto.complete.service.AutoCompleteService;

@Controller
public class AutoCompleteController {
	
	private final AutoCompleteService autoCompleteService;
	
	@Autowired
	public AutoCompleteController(AutoCompleteService acs) {
		this.autoCompleteService = acs;
	}

	@GetMapping("/api")
	@ResponseBody
	public List<String> autoComplete(@RequestParam("prefix") String prefix) throws IllegalStateException{
		if(prefix.isEmpty() || prefix == null) {
			throw new IllegalStateException("Invalid input");
		}
		return autoCompleteService.autoCompleteNames(prefix);
	}
	
	@ExceptionHandler
	public String handleInvalidInputException(IllegalStateException e) {
		return e.getMessage();
	}
	
	@RequestMapping(value = "/" ,method = RequestMethod.GET)
	public String home(){
		return "home.jsp";
	}
	
	

}
