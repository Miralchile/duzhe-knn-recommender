package com.duzheStudy.modules.video.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlgResDTO {

    private List<MovieDTO> recommendations;

}
