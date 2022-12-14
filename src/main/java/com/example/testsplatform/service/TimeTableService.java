package com.example.testsplatform.service;

import com.example.testsplatform.util.MireaParser.ParserXLSX;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;


@Service
public class TimeTableService {

    public int countGroupByPrefix(String prefix, String year){
        int count=0;
        Set<String> setKeys = ParserXLSX.groups.keySet();
        for (String k: setKeys) {
            if(k.startsWith(prefix) && k.endsWith(year)){
                count++;
            }
        }
        return count;
    }
}
