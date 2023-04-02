package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model;

import java.util.List;

public class SymptomSearch {
    private List<String> results;

    public List<String> getResults(){
        return results;
    }
    public void setResults(List<String> results){
        this.results = results;
    }
}
