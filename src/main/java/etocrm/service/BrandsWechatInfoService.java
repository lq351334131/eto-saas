package etocrm.service;

import org.etocrm.database.exception.MyException;
import org.etocrm.database.util.BasePage;
import org.etocrm.entity.BrandsWechatInfo;
import org.etocrm.model.brands.SysBrandsWechatInfoVO;
import org.etocrm.model.minapp.*;

import java.util.List;

/**
 * <p>
 * 品牌公众号小程序信息 服务类
 * </p>
 *
 * @author admin
 * @since 2021-01-22
 */
public interface BrandsWechatInfoService {


    /**
     * 添加
     */
    Long saveBrandsWechatService(BrandsWechatInfoSaveVO brandsWechatService);

    /**
     * 修改
     */
    Long updateByPk(BrandsWechatInfoUpdateVO brandsWechatService) throws MyException;

    /**
     * 删除
     */
    void updateStatus(UpdateMinAppStatusVO pk) throws MyException;

    /**
     * 详情
     */
    BrandsWechatInfoUpdateVO detailByPk(Long pk);

    /**
     * 全查列表
     *
     * @return
     */
    List<BrandsWechatInfo> findAll(BrandsWechatInfo brandsWechatService);

    /**
     * 分页查询
     */
    BasePage<BrandsWechatListVO> list(Integer curPage, Integer pageSize, BrandsWechatSelectVO brandsWechatService) throws MyException;

    List<BrandWechatInfoVO> getDetailByType(SysBrandsWechatInfoVO vo);

    Long bindBrandsAndMiniApp(BindParam bindParam);

    List<BrandWechatInfoVO> getBindedWechatInfo(Long brandId, String type);

    List<BrandsWechatInfoUpdateVO>  detailByBrandsId(Long id, String type);

    Long dismissBrandsWechatInfo(DissmissBindParam bindParam);

    // List<BrandsWechatInfo> findByBrandsAndType(Long brandsId, String code);
}