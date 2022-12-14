package com.example.testsplatform.controller;

import com.example.testsplatform.service.TimeTableService;
import com.example.testsplatform.util.MireaParser.ParserXLSX;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TimeTableController {

    @Autowired
    TimeTableService timeTableService;

    @PostMapping("/getTimeTable")
    @ResponseBody
    public Object getTimeTable(@RequestBody String jsonString) throws ParseException {
        JSONObject json = (JSONObject) new JSONParser().parse(jsonString);
//        int k = timeTableService.countGroupByPrefix(json.get("searchLine").toString().split("-")[0],json.get("searchLine").toString().split("-")[2]);
//        int i = Integer.parseInt(json.get("searchLine").toString().split("-")[1]);
//
//        if(i>k){
//            return new Object();
//        }

        return ParserXLSX.groups.get((String) json.get("searchLine")).
                get((Integer) json.get("week")).
                get((Integer) json.get("day"));
    }
}
