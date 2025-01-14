package org.jeecg.modules.jixiaoPersonPrice.convert;


import org.jeecg.modules.jixiaoPersonPrice.dto.JixiaoPersonPriceAddDTO;
import org.jeecg.modules.jixiaoPersonPrice.entity.JixiaoPersonPrice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;

import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,imports ={ CollectionUtils.class, Collectors.class})
public interface JixiaoPersonPriceConvert {

    JixiaoPersonPriceConvert INSTANCE = Mappers.getMapper(JixiaoPersonPriceConvert.class);

    @Mapping(target = "jixiaoPrice",source = "dto.lhJifen")
    @Mapping(target = "jixiaoPerson",source = "dto.name")
    JixiaoPersonPrice convert(JixiaoPersonPriceAddDTO dto);

}
