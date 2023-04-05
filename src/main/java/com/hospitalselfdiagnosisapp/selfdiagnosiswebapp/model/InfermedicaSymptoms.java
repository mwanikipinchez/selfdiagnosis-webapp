package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class InfermedicaSymptoms {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long keyId;
    private String id;
    private String name;
    private String common_name;
    private String sex_filter;
    private String category;
    private String seriousness;
//    private String extras;
//    private String children;
//    private String image_url;
//    private String image_source;
//    private String parent_id;
//    private  String parent_relation;


    public InfermedicaSymptoms(String id, String name, String common_name, String sex_filter, String category, String seriousness) {
        this.id = id;
        this.name = name;
        this.common_name = common_name;
        this.sex_filter = sex_filter;
        this.category = category;
        this.seriousness = seriousness;

//        this.children = children;
//        this.image_url = image_url;
//        this.image_source = image_source;
//        this.parent_id = parent_id;
//        this.parent_relation = parent_relation;
    }
}
