package com.example.testsplatform.controller;

import com.example.testsplatform.util.MireaParser.ParserXLSX;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TimeTableController {


    @PostMapping("/getTimeTable")
    @ResponseBody
    public Object getTimeTable(@RequestBody String jsonString) throws ParseException {
        JSONObject json = (JSONObject) new JSONParser().parse(jsonString);
        return ParserXLSX.groups.get((String) json.get("searchLine")).
                get((Integer) json.get("week")).
                get((Integer) json.get("day"));
    }
}
