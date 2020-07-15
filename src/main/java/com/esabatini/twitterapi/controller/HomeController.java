/**
 * 
 */
package com.esabatini.twitterapi.controller;

import io.swagger.annotations.Api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author es
 *
 */
@Api(tags = {"Home Controller manages API DOCs."})
@Controller
@RequestMapping("/")
public class HomeController {

    @RequestMapping(method = RequestMethod.GET)
    public String getDocs() {
        return "redirect:swagger-ui.html";
    }
}
