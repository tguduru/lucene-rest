package org.tguduru.lucene.rest.resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Guduru, Thirupathi Reddy
 * @modified 1/28/16
 */
@RestController
@RequestMapping(value = "/{id}/data")
public class OptionalPathResource {
    @RequestMapping(method = RequestMethod.GET)
    public String getData(@PathVariable(value = "id") final String id) {
        if(id != null)
        return "Data for " + id;
        else
            return "Data Without Id";
    }
}
