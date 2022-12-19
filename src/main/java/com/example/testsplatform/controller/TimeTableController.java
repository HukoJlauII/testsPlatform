package com.example.testsplatform.controller;

import com.example.testsplatform.service.TimeTableService;
import com.example.testsplatform.util.MireaParser.ParserXLSX;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.Collections;

@Controller
public class TimeTableController {

    @Autowired
    TimeTableService timeTableService;

    @PostMapping("/getTimeTable")
    @ResponseBody
    public ResponseEntity<Object> getTimeTable(@RequestBody String jsonString) throws ParseException {
        JSONObject json = (JSONObject) new JSONParser().parse(jsonString);

        if (ParserXLSX.groups.containsKey((String) json.get("searchLine")))
        {
            return new ResponseEntity<>(ParserXLSX.groups.get((String) json.get("searchLine")).
                    get((Integer) json.get("week")).
                    get((Integer) json.get("day")), HttpStatus.OK);
        }
        else return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NO_CONTENT);

    }
}
