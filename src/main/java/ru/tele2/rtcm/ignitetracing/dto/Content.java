package ru.tele2.rtcm.ignitetracing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Content {

    String contentServiceName;
    Double sumRatedAmount;
}
