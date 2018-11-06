package com.yuri.ynweb_kj.dto.content;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yuri.ynweb_kj.pojo.KeCharacterPic;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CharacterStep4Dto {
    private Integer characterId;
    private String backgroundPic4;
    private List<KeCharacterPic> picList;
}
