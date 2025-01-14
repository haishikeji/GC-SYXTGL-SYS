package org.jeecg.modules.largeScreen.convert;


import org.jeecg.modules.itdmDataNow.entity.ItdmWenxiangNow;
import org.jeecg.modules.largeScreen.vo.ItdmWenxiangNowWSVO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;

import java.util.stream.Collectors;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,imports ={ CollectionUtils.class, Collectors.class})
public interface ItdmWenxiangNowConvert {

    ItdmWenxiangNowConvert INSTANCE = Mappers.getMapper(ItdmWenxiangNowConvert.class);

    ItdmWenxiangNowWSVO convert(ItdmWenxiangNow convert);
}
