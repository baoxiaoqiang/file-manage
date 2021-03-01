package cn.com.quanyou.ioc.file.manage.service;

import cn.com.quanyou.ioc.file.manage.common.PageInfo;
import cn.com.quanyou.ioc.file.manage.common.ResultInfo;
import cn.com.quanyou.ioc.file.manage.common.enums.ImportFileTypeEnum;
import cn.com.quanyou.ioc.file.manage.common.enums.ResultStatusEnum;
import cn.com.quanyou.ioc.file.manage.facade.IExcelImportService;
import cn.com.quanyou.ioc.file.manage.facade.ISearchService;
import cn.com.quanyou.ioc.file.manage.vo.SearchParamVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: SearchService
 * @date 2019/6/20 13:49
 * @projectName file-manage
 * @description: 查询服务
 */
@Service
@Slf4j
public class SearchService extends ImportBasicService implements ISearchService {

    @Override
    public ResultInfo<PageInfo<Object>, String> search(SearchParamVo searchParamVo, PageInfo<Object> pageInfo) {

        ResultInfo<PageInfo<Object>, String> resultInfo = new ResultInfo<>();
        resultInfo.setData(pageInfo);
        if(searchParamVo == null){
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            resultInfo.setMessage("参数丢失servcie");
            return resultInfo;
        }

        ImportFileTypeEnum dataType = searchParamVo.getSearchType();
        if(dataType == null){
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            resultInfo.setMessage("查询类型有误");
            return resultInfo;
        }

        IExcelImportService importService = super.getServivceByFileImportTypeEnum(dataType);
        if(importService == null){
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            resultInfo.setMessage("内部错误：查询服务丢失有误");
            return resultInfo;
        }
        return importService.search(searchParamVo,pageInfo);
    }
}
