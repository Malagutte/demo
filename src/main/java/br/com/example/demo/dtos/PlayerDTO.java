package br.com.example.demo.dtos;

import static br.com.example.demo.constants.Types.TYPE_EXPERT;
import static br.com.example.demo.constants.Types.TYPE_NOVICE;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PlayerDTO {

    private String name;
    private String type;


    @Override
    public String toString(){
        if (StringUtils.equalsIgnoreCase(type, TYPE_EXPERT)) {

            return String.format("player %s stored in DB", name);

        } else if (StringUtils.equalsIgnoreCase(type, TYPE_NOVICE)) {

            return String.format("player %s sent to Kafka topic", name);

        }else{

            return String.format("player %s did not fit", name);

        }
    }
}
