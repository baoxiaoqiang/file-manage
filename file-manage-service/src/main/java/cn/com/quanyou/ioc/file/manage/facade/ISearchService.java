package cn.com.quanyou.ioc.file.manage.facade;

import cn.com.quanyou.ioc.file.manage.common.PageInfo;
import cn.com.quanyou.ioc.file.manage.common.ResultInfo;
import cn.com.quanyou.ioc.file.manage.vo.SearchParamVo;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: ISearchService
 * @date 2019/6/20 13:45
 * @projectName file-manage
 * @description:
 */
public interface ISearchService {

    /**
    * @Description 查询
    * @Author heshiyi@quanyou.com.cn
    * @Date 13:46 2019/6/20
    * @param searchParamVo
    * @param pageInfo
    * @return
    **/
    ResultInfo<PageInfo<Object>,String> search(SearchParamVo searchParamVo, PageInfo<Object> pageInfo);
}
