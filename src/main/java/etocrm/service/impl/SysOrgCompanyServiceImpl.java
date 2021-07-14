package etocrm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.etocrm.convert.SysOrgCompanyNoticeConvert;
import org.etocrm.database.enums.ResponseEnum;
import org.etocrm.database.exception.MyException;
import org.etocrm.database.util.BasePage;
import org.etocrm.database.util.PageBounds;
import org.etocrm.database.util.QueryConditionUtil;
import org.etocrm.database.util.SysUserRedisVO;
import org.etocrm.entity.*;
import org.etocrm.enums.BusinessEnum;
import org.etocrm.enums.OtherSystemUrlTypeEnum;
import org.etocrm.exception.UamException;
import org.etocrm.model.company.*;
import org.etocrm.repository.*;
import org.etocrm.service.SysOrgBrandsService;
import org.etocrm.service.SysOrgCompanyBrandsService;
import org.etocrm.service.SysOrgCompanyService;
import org.etocrm.utils.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * <p>
 * 机构公司信息 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-01-23
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SysOrgCompanyServiceImpl implements SysOrgCompanyService {
    @Value("${companyCallBackUrl}")
    private String callBackUrl;

    @Resource
    private SysOrgCompanyRepository sysOrgCompanyRepository;

    @Resource
    private SysOrgCompanyBrandsService sysOrgCompanyBrandsService;

    @Autowired
    private SysOrgCompanyBrandsRepository sysOrgCompanyBrandsRepository;

    @Resource
    private SysOrgBrandsService sysOrgBrandsService;

    @Resource
    private SysOrgBrandsRepository sysOrgBrandsRepository;

    @Autowired
    private SecurityUtil securityUtil;

    private CopyOnWriteArrayList<ImportCompanyFailVO> list = Lists.newCopyOnWriteArrayList();

    @Autowired
    private UpLoadUtil upLoadUtil;

    @Autowired
    private ImportHistoryRepository importHistoryRepository;

    @Autowired
    private ImportCompanyFailRepository importCompanyFailRepository;

    @Autowired
    private ImportTemplateRepository importTemplateRepository;

    @Autowired
    private OtherSystemNoticeUtil otherSystemNoticeUtil;

    @Autowired
    private BrandsWechatInfoRepository brandsWechatInfoRepository;

    @Autowired
    private SysOrgRepository sysOrgRepository;

    @Autowired
    private MachineRoomRepository machineRoomRepository;

    @Resource
    private SysIndustryRepository sysIndustryRepository;

    @Resource
    SysOrgAuthRepository sysOrgAuthRepository;


    @Autowired
    private SysOrgCompanyNoticeConvert convert;


    @Override
    public Long saveSysOrgCompany(SysOrgCompanySaveVO sysOrgCompany) throws MyException {
        SysUserRedisVO loginUser = securityUtil.getCurrentLoginUser();
        Integer count = sysOrgCompanyRepository.countByOrgIdAndName(loginUser.getUamOrgId(), sysOrgCompany.getName());
        if (count > 0) {
            throw new MyException(ResponseEnum.FAILD.getCode(), "当前机构下，公司名称重复");
        }
        SysOrgCompany sysOrgCompany1 = new SysOrgCompany();
        BeanUtil.copyProperties(sysOrgCompany, sysOrgCompany1);
        if (BusinessEnum.COMPANY_AUTO.getCode().equals(sysOrgCompany.getCodeType())) {
            sysOrgCompany1.setCode(System.currentTimeMillis() + "");
        }
        sysOrgCompany1.setOrgId(loginUser.getUamOrgId());
        sysOrgCompanyRepository.save(sysOrgCompany1);
        //关联品牌ids处理
        SysOrgCompanyBrands vo = new SysOrgCompanyBrands();
        vo.setBrandsId(sysOrgCompany.getBrandId());
        vo.setCompanyId(sysOrgCompany1.getId());
        vo.setOrgId(loginUser.getUamOrgId());
        vo.setMiniAppId(sysOrgCompany.getMiniAppId());
        vo.setServiceId(sysOrgCompany.getServiceAppId());
        sysOrgCompanyBrandsRepository.save(vo);
        //通知
        companyAddOrUpdateNotice(sysOrgCompany.getBrandId(),
                sysOrgCompany.getMiniAppId(),
                sysOrgCompany.getServiceAppId(),
                sysOrgCompany1, OtherSystemUrlTypeEnum.SUBSIDIARY_ADD_REDIRECT);
        return sysOrgCompany1.getId();
    }

    /**
     * 公司新增通知
     *
     * @param brandId                品牌id
     * @param miniAppId              小程序id、
     * @param serviceAppId           公帐号id
     * @param sysOrgCompany          公司实体类
     * @param otherSystemUrlTypeEnum 通知地址类型
     * @throws MyException
     */
    private void companyAddOrUpdateNotice(Long brandId, Long miniAppId, Long serviceAppId, SysOrgCompany sysOrgCompany, OtherSystemUrlTypeEnum otherSystemUrlTypeEnum) throws MyException {
        SysOrgCompanyNoticeVO sysOrgCompanyNoticeVO = convert.doToVo(sysOrgCompany);
        Optional<SysOrgBrands> byId = sysOrgBrandsRepository.findById(brandId);
        if (byId.isPresent()) {
            SysOrgBrands sysOrgBrands = byId.get();
            //将crmBrandId 赋值未 uamBrandId
            sysOrgCompanyNoticeVO.setCrmBrandId(sysOrgBrands.getId());
            sysOrgCompanyNoticeVO.setUamBrandId(sysOrgBrands.getId());
            sysOrgCompanyNoticeVO.setBrandName(sysOrgBrands.getName());
        }
        //通知
        //crmOrgId uamBrandId crmBrandId appId  miniAppName serviceAppName
        //根据 小程序、公众号id 查询名称
        if (null != miniAppId) {
            Optional<BrandsWechatInfo> miniAppOptional = brandsWechatInfoRepository.findById(miniAppId);
            if (miniAppOptional.isPresent()) {
                sysOrgCompanyNoticeVO.setMiniAppId(miniAppOptional.get().getAppid());
                sysOrgCompanyNoticeVO.setMiniAppName(miniAppOptional.get().getWechatName());
            }
        }
        if (null != serviceAppId) {
            Optional<BrandsWechatInfo> serviceAppOptional = brandsWechatInfoRepository.findById(serviceAppId);
            if (serviceAppOptional.isPresent()) {
                sysOrgCompanyNoticeVO.setServiceAppId(serviceAppOptional.get().getAppid());
                sysOrgCompanyNoticeVO.setServiceAppName(serviceAppOptional.get().getWechatName());
            }
        }
        Optional<SysOrg> sysOrgOptional = sysOrgRepository.findById(sysOrgCompanyNoticeVO.getUamOrgId());
        Long machineId = null;
        if (sysOrgOptional.isPresent()) {
            machineId = sysOrgOptional.get().getMachineId();
            Optional<MachineRoom> byId1 = machineRoomRepository.findById(machineId);
            if (byId1.isPresent()) {
                MachineRoom machineRoom = byId1.get();
                sysOrgCompanyNoticeVO.setMachineId(machineRoom.getId());
                sysOrgCompanyNoticeVO.setMachineCode(machineRoom.getCode());
                sysOrgCompanyNoticeVO.setMachineName(machineRoom.getRoomName());
            }
            //行业
            SysOrgIndustry byOrgId = sysOrgAuthRepository.findByOrgId(sysOrgOptional.get().getId());
            sysOrgCompanyNoticeVO.setIndustryId(byOrgId.getId());
            Optional<SysIndustry> byId2 = sysIndustryRepository.findById(byOrgId.getId());
            if (byId2.isPresent()) {
                sysOrgCompanyNoticeVO.setIndustryName(byId2.get().getName());
            }
        }
        //设置回调地址
        sysOrgCompanyNoticeVO.setCallbackUrl(callBackUrl);
        List<SysOrgCompanyNoticeVO> list = new ArrayList<>();
        list.add(sysOrgCompanyNoticeVO);
        String dataJsonStr = JSON.toJSONString(list, SerializerFeature.WriteMapNullValue);
        otherSystemNoticeUtil.restTemplate(sysOrgCompanyNoticeVO.getUamOrgId(), machineId, dataJsonStr, otherSystemUrlTypeEnum);
    }

    @Override
    public Long updateByPk(SysOrgCompanyUpdateVO sysOrgCompany) throws MyException {
        Integer count = sysOrgCompanyRepository.countByNameAndIdNot(sysOrgCompany.getName(), sysOrgCompany.getId());
        if (count > 0) {
            throw new MyException(ResponseEnum.FAILD.getCode(), "当前机构下,公司名称重复");
        }
        SysOrgCompany sysOrgCompany1 = new SysOrgCompany();
        BeanUtil.copyProperties(sysOrgCompany, sysOrgCompany1);
        SysOrgCompany update = sysOrgCompanyRepository.update(sysOrgCompany1);
        //关联品牌ids处理
        SysUserRedisVO loginUser = securityUtil.getCurrentLoginUser();
        //删除关联
        List<SysOrgCompanyBrands> byOrgId = sysOrgCompanyBrandsRepository.findByCompanyId(sysOrgCompany.getId());
        sysOrgCompanyBrandsRepository.deleteAll(byOrgId);
        //新增关联
        SysOrgCompanyBrands sysOrgCompanyBrands = new SysOrgCompanyBrands();
        sysOrgCompanyBrands.setBrandsId(sysOrgCompany.getBrandId());
        sysOrgCompanyBrands.setOrgId(loginUser.getUamOrgId());
        sysOrgCompanyBrands.setCompanyId(sysOrgCompany.getId());
        sysOrgCompanyBrands.setMiniAppId(sysOrgCompany.getMiniAppId());
        sysOrgCompanyBrands.setServiceId(sysOrgCompany.getServiceId());
        sysOrgCompanyBrandsRepository.save(sysOrgCompanyBrands);

        //更新通知
        companyAddOrUpdateNotice(sysOrgCompany.getBrandId(),
                sysOrgCompany.getMiniAppId(),
                sysOrgCompany.getServiceId(), update,
                OtherSystemUrlTypeEnum.SUBSIDIARY_UPDATE_NOTICE_REDIRECT);
        return sysOrgCompany1.getId();
    }

    @Override
    public Long updateStatus(UpdateCompanyStatusVO pk) throws MyException {
        SysOrgCompany sysOrgCompany1 = new SysOrgCompany();
        BeanUtil.copyProperties(pk, sysOrgCompany1);
        sysOrgCompanyRepository.update(sysOrgCompany1);
        //todo 关联品牌ids处理
        return sysOrgCompany1.getId();
    }

    @Override
    public BasePage<SysOrgCompanyVO> getCompanyList(Integer curPage, Integer size, SysCompanyPageListVO v) {
        SysUserRedisVO loginUser = securityUtil.getCurrentLoginUser();


        Page<SysOrgCompany> sysParamIPage = sysOrgCompanyRepository.findAll((Specification<SysOrgCompany>) (root, query, cb) -> {
                    //集合 用于封装查询条件
                    List<Predicate> list = new ArrayList<>();

                    if (null != v.getStatus()) {
                        Predicate status = cb.equal(root.get("status").as(Integer.class), v.getStatus());
                        list.add(status);
                    }
                    if (null != v.getOpenShop()) {
                        Predicate openShop = cb.equal(root.get("openShop").as(Integer.class), v.getOpenShop());
                        list.add(openShop);
                    }
                    Predicate orgId = cb.equal(root.get("orgId").as(Integer.class), loginUser.getUamOrgId());
                    list.add(orgId);
                    return cb.and(list.toArray(new Predicate[0]));
                }
                , PageRequest.of(curPage, size,
                        Sort.by(Sort.Direction.DESC, "updatedTime")));
        BasePage basePage = new BasePage<>(sysParamIPage);
        List<SysOrgCompany> records = (List<SysOrgCompany>) basePage.getRecords();
        List<SysOrgCompanyVO> transformation = this.trandsFormListVO(records);
        basePage.setRecords(transformation);
        return basePage;
    }

    private List<SysOrgCompanyVO> trandsFormListVO(List<SysOrgCompany> records) {
        List<SysOrgCompanyVO> res = Lists.newArrayList();
        if (CollectionUtil.isNotEmpty(records)) {
            records.forEach(
                    item -> {
                        SysOrgCompanyVO sysOrgCompanyVO = new SysOrgCompanyVO();
                        BeanUtil.copyProperties(item, sysOrgCompanyVO);
                        res.add(sysOrgCompanyVO);
                    });
        }
        return res;
    }

    @Override
    public Long importCompany(MultipartFile file) {

        List<ImportCompanyVO> list;
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("公司类型 2-分公司 3-经销商*", "type");
        map.put("公司名称*", "name");
        map.put("公司ID", "companyId");
        map.put("联系人", "contacts");
        map.put("联系方式", "contactsPhone");
        map.put("上属公司ID", "parentId");
        map.put("商家编号", "code");
        map.put("是否开通线上店铺\n" +
                "1-开通  2-不开通", "openShop");
        map.put("关联品牌ID \n" +
                "多个品牌英文逗号分割", "brandsId");
        try {
            list = ExcelUtil.excelToList(file, ImportCompanyVO.class, map);
        } catch (Exception e) {
            log.error("调用工具类异常", e);
            throw new UamException(ResponseEnum.FAILD.getCode(), "调用工具类异常");
        }
        if (list.size() == 0) {
            throw new UamException(ResponseEnum.FAILD.getCode(), "模板内容不能为空");
        }
        if (list.size() > 2000) {
            throw new UamException(ResponseEnum.FAILD.getCode(), "上传失败,您的Excel超过2000行了,请重新上传");
        }
        CopyOnWriteArrayList<ImportCompanyVO> cowList = new CopyOnWriteArrayList(list.toArray());
        String importBatch = UUID.randomUUID().toString();
        Integer totalCount = cowList.size();
        String rowId = "";
        for (ImportCompanyVO model : cowList) {
            rowId = UUID.randomUUID().toString();
            // 数据检查
            if (StringUtils.isBlank(model.getType())) {
                addFailData(model, "公司类型不能为空", importBatch, "partnerName", rowId);
                cowList.remove(model);
            }
            if (StringUtils.isBlank(model.getName())) {
                addFailData(model, "公司名称不能为空", importBatch, "name", rowId);
                cowList.remove(model);
            }

            if (StringUtils.isNotBlank(model.getParentId()) && !CheckUtils.isNumeric(model.getParentId())) {
                addFailData(model, "上属公司Id必须是数字", importBatch, "parentId", rowId);
                cowList.remove(model);
            }

            if (StringUtils.isNotBlank(model.getOpenShop()) && (!CheckUtils.isNumeric(model.getOpenShop()) || Long.valueOf(model.getOpenShop()) < 0 || Long.valueOf(model.getOpenShop()) > 2)) {
                addFailData(model, "店铺类型错误", importBatch, "openShop", rowId);
                cowList.remove(model);
            }

            insertDb();
        }


        if (cowList.size() > 0) {
            SysUserRedisVO loginUser = securityUtil.getCurrentLoginUser();
            //入正确数据
            for (ImportCompanyVO model : cowList) {
                SysOrgCompany sysOrgCompany = new SysOrgCompany();
                BeanUtil.copyProperties(model, sysOrgCompany);
                sysOrgCompany.setCodeType(2);
                sysOrgCompany.setOrgId(loginUser.getUamOrgId());
                SysOrgCompany save = sysOrgCompanyRepository.save(sysOrgCompany);
                String brandsId = model.getBrandsId();
                if (StringUtils.isNotBlank(brandsId)) {
                    String[] split = brandsId.split(",");
                    for (String s : split) {
                        //构建公司和品牌的关系
                        SysOrgCompanyBrands sysOrgCompanyBrands = new SysOrgCompanyBrands();
                        sysOrgCompanyBrands.setOrgId(loginUser.getUamOrgId());
                        sysOrgCompanyBrands.setCompanyId(save.getId());
                        sysOrgCompanyBrands.setBrandsId(Long.valueOf(s));
                        sysOrgCompanyBrandsRepository.save(sysOrgCompanyBrands);
                    }
                }
            }
        }

        // 错误文件上传
        // 查找这一批次的失败条数
        List<ImportCompanyFail> failList = importCompanyFailRepository.findByImportBatch(importBatch);
        JSONObject byByte = null;
        if (CollectionUtil.isNotEmpty(failList)) {

            // 读取文件,读取模板的表头
            List<String> readList = new ArrayList<>();
            try {
                // List<ImportTemplate> all = importTemplateRepository.findAll();
                //  readList = ExcelUtil.readExcel(all.get(0).getTemplateUrl(),all.get(0).getTemplateName());
                for (String heaeder : map.keySet()) {
                    readList.add(heaeder);
                }
            } catch (Exception e) {
                log.error("读取文件异常", e);
                throw new UamException(ResponseEnum.FAILD.getCode(), "读取文件异常");
            }
            //   List<String> attentionList = Lists.newLinkedList();
            // title.add(readList.get(0));
            //List<String> titleList = ListUtils.getDiffrent2(readList, attentionList);
            List<String> titleList = new ArrayList<>();
            for (String header : readList) {
                titleList.add(header);
            }
            titleList.add("错误描述");

            // 写入文件
            try {
                String errorName = String.valueOf(System.currentTimeMillis());
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                WritableWorkbook book = Workbook.createWorkbook(bos);
                WritableSheet sheet = book.createSheet(errorName, 0);

                // 设置单元格样式
                sheet.getSettings().setDefaultColumnWidth(20);
                WritableFont font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);//字体类型,大小,是否加粗
                WritableCellFormat wcf = new WritableCellFormat(font);
                wcf.setBorder(Border.ALL, BorderLineStyle.THIN);//显示哪一个位置的边框,边框是样式粗边框还是细边框
                wcf.setAlignment(Alignment.LEFT);

                WritableFont redFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED);
                WritableCellFormat redWcf = new WritableCellFormat(redFont);
                redWcf.setBorder(Border.ALL, BorderLineStyle.THIN);
                redWcf.setAlignment(Alignment.LEFT);

                // 第一列第一行设置注意事项
                //sheet.addCell(new Label(0, 0, attentionList.get(0), wcf));
                // 合并单元格(开始列下标，开始行下标，结束列下标，结束行下标)
                //  sheet.mergeCells(0, 0, titleList.size() - 1, 0);
                // 第一行单元格设置高度
                //  sheet.setRowView(0, 1600);

                // 第一行循环设置表头
                for (int i = 0; i < titleList.size() - 1; i++) {
                    sheet.addCell(new Label(i, 0, titleList.get(i), wcf));
                }
                // 第二行表头的最后一列设置为错误描述,字体标红
                sheet.addCell(new Label(titleList.size() - 1, 0, titleList.get(titleList.size() - 1), redWcf));

                // 因为excel模板中第一行是注意事项,第二行是表头,所以业务数据从第三行开始,而for循环从0开始是第一次,所以此处为2
                int row = 1;

                // 从第3行开始循环给每一列设置文案
                for (int i = 0; i < failList.size(); i++) {
                    sheet.addCell(new Label(0, i + row, failList.get(i).getType(), wcf));
                    sheet.addCell(new Label(1, i + row, failList.get(i).getName(), wcf));
                    sheet.addCell(new Label(2, i + row, failList.get(i).getCompanyId(), wcf));
                    sheet.addCell(new Label(3, i + row, failList.get(i).getContacts(), wcf));
                    sheet.addCell(new Label(4, i + row, failList.get(i).getContactsPhone(), wcf));
                    sheet.addCell(new Label(5, i + row, failList.get(i).getParentId(), wcf));
                    sheet.addCell(new Label(6, i + row, failList.get(i).getCode(), wcf));
                    sheet.addCell(new Label(7, i + row, failList.get(i).getOpenShop(), wcf));
                    sheet.addCell(new Label(8, i + row, failList.get(i).getBrandsId(), wcf));
                    sheet.addCell(new Label(9, i + row, failList.get(i).getErrorMsg(), redWcf));
                }

                book.write();
                book.close();

                byte[] bytes = bos.toByteArray();

                byByte = upLoadUtil.getUpLoadJsonByByte(errorName + ".csv", bytes);

            } catch (Exception ex) {
                log.error("异常", ex);
                throw new UamException(ResponseEnum.FAILD.getCode(), "写入文件异常");
            }
        }

        // 新增导入历史表
        ImportHistory history = new ImportHistory();

        history.setFileName(file.getOriginalFilename());
        history.setImportBatch(importBatch);
        history.setSuccessNumber(cowList.size());
        history.setFailNumber(totalCount - cowList.size());

        if (null != byByte) {
            Object data = byByte.get("data");
            if (data != null) {
                JSONArray objects = JSONArray.parseArray(JSONObject.toJSONString(data));
                JSONObject jsonObject = JSONObject.parseObject(objects.get(0).toString());
                String ossUrl = jsonObject.getString("ossUrl");
                history.setErrorFileUrl(ossUrl);
            }
        }
        ImportHistory save = importHistoryRepository.save(history);
        log.info("新增导入历史表成功");
        return save.getId();
    }

    @Override
    public ImportHistoryVO importErrorCompany(Long id) {

        Optional<ImportHistory> byId = importHistoryRepository.findById(id);
        if (byId.isPresent()) {
            ImportHistory importHistory = byId.get();
            ImportHistoryVO importHistoryVO = new ImportHistoryVO();
            BeanUtil.copyProperties(importHistory, importHistoryVO);
            return importHistoryVO;
        }
        return null;
    }

    @Override
    public ImportTemplate importTemplate() {

        List<ImportTemplate> all = importTemplateRepository.findAll();
        if (CollectionUtil.isNotEmpty(all)) {
            return all.get(0);
        }
        return null;
    }

    @Override
    public SysOrgCompanyUpdateVO detailByPk(Long pk) {
        SysOrgCompanyUpdateVO vo = new SysOrgCompanyUpdateVO();
        Optional<SysOrgCompany> byId = sysOrgCompanyRepository.findById(pk);
        if (byId.isPresent()) {
            SysOrgCompany sysOrgCompany = byId.get();
            BeanUtils.copyProperties(sysOrgCompany, vo);
            List<SysOrgCompanyBrands> relationList = sysOrgCompanyBrandsService.findByCompanyId(sysOrgCompany.getId());
            if (CollectionUtil.isNotEmpty(relationList)) {
                SysOrgCompanyBrands companyBrands = relationList.get(0);
                vo.setBrandId(companyBrands.getBrandsId());
                vo.setMiniAppId(companyBrands.getMiniAppId());
                vo.setServiceId(companyBrands.getServiceId());
            }
        }
        return vo;
    }

    @Override
    public BasePage<SysOrgCompany> list(Integer curPage, Integer size, SysOrgCompany sysOrgCompany) {
        PageBounds pageBounds = new PageBounds(curPage, size);
        Page<SysOrgCompany> sysOrgCompanys = sysOrgCompanyRepository.findAll((r, q, c) -> {
            return (new QueryConditionUtil()).where(sysOrgCompany, r, c);
        }, PageRequest.of(pageBounds.getOffset(), pageBounds.getLimit()));
        return new BasePage(sysOrgCompanys);
    }

    @Override
    public List<SysOrgCompanyTreeVO> findAll(Integer status, Long id,Integer openShop) throws MyException {
        SysUserRedisVO loginUser = securityUtil.getCurrentLoginUser();
        List<SysOrgCompanyTreeVO> list = new ArrayList<>();
        List<SysOrgCompany> sysOrgCompany = null;
        if(openShop!=null) {
            sysOrgCompany = sysOrgCompanyRepository.findByOrgIdAndStatusAndOpenShop(loginUser.getUamOrgId(),1,openShop);
        }else  if(status != null) {
            sysOrgCompany = sysOrgCompanyRepository.findByOrgIdAndStatus(loginUser.getUamOrgId(), status);

            //自己和自己的子集过滤掉
            sysOrgCompany = sysOrgCompany.stream().filter(item -> item.getId() != id).collect(Collectors.toList());
        } else {
            sysOrgCompany = sysOrgCompanyRepository.findByOrgId(loginUser.getUamOrgId());
        }
        if (!sysOrgCompany.isEmpty()) {
            List<SysOrgCompanyTreeVO> collect = sysOrgCompany.stream().map(s -> {
                SysOrgCompanyTreeVO sysOrderPayDetail = new SysOrgCompanyTreeVO();
                BeanUtil.copyProperties(s, sysOrderPayDetail);
                return sysOrderPayDetail;
            }).collect(Collectors.toList());
            List<SysOrgCompanyTreeVO> tree = this.getTree(collect, 0L);
            return tree;
        }
        return list;
    }


    private List<SysOrgCompanyTreeVO> getTree(List<SysOrgCompanyTreeVO> sysOrgCompanies, Long parentId) throws MyException {
        try {
            List<SysOrgCompanyTreeVO> childTree = getChildTree(sysOrgCompanies, parentId);
            for (SysOrgCompanyTreeVO sysOrgCompany : childTree) {
                //线下门店
                sysOrgCompany.setOfflineShopNumber(this.getShopCount(sysOrgCompany.getId()));
                //关联品牌
                this.getCompanyBrandsName(sysOrgCompany);
                sysOrgCompany.setChildren(getTree(sysOrgCompanies, sysOrgCompany.getId()));
            }
            return childTree;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MyException(ResponseEnum.FAILD.getCode(), "公司查询错误");
        }
    }

    private List<SysOrgCompanyTreeVO> getChildTree(List<SysOrgCompanyTreeVO> list, Long id) {
        List<SysOrgCompanyTreeVO> childTree = new ArrayList<>();
        for (SysOrgCompanyTreeVO dept : list) {
            if (dept.getParentId().equals(id)) {
                childTree.add(dept);
            }
        }
        return childTree;
    }

    /**
     * @param companyTreeVO
     * @return
     */
    private void getCompanyBrandsName(SysOrgCompanyTreeVO companyTreeVO) {
        List<SysOrgCompanyBrands> sysOrgCompanyBrands = sysOrgCompanyBrandsService.findByCompanyId(companyTreeVO.getId());

        for (SysOrgCompanyBrands sysOrgCompanyBrand : sysOrgCompanyBrands) {
            SysOrgBrands sysOrgBrands = sysOrgBrandsService.detailByPk(sysOrgCompanyBrand.getBrandsId());
            if (sysOrgBrands != null) {
                companyTreeVO.setRelatedBrands(sysOrgBrands.getName());
                companyTreeVO.setCrmBrandId(sysOrgBrands.getCrmBrandId());
            }
            companyTreeVO.setUamBrandId(sysOrgCompanyBrand.getBrandsId());
        }
    }

    /**
     * @param companyId
     * @return
     */
    private Integer getShopCount(Long companyId) {
        //todo 计算线下门店数据
        return 0;
    }


    public void addFailData(ImportCompanyVO model, String msg, String importBatch, String field, String rowId) {
        ImportCompanyFailVO fail = new ImportCompanyFailVO();
        BeanUtils.copyProperties(model, fail);
        fail.setImportBatch(importBatch);
        fail.setErrorMsg(msg);
        fail.setErrorField(field);
        fail.setRowId(rowId);
        list.add(fail);
    }

    public void insertDb() {
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                for (int j = i + 1; j < list.size(); j++) {
                    // 如果第一行的标识和第二行的标识相同
                    if (list.get(i).getRowId().equals(list.get(j).getRowId())) {

                        // 得到第一行的错误描述和错误字段
                        StringBuffer sb = new StringBuffer(list.get(i).getErrorMsg());
                        StringBuffer sb2 = new StringBuffer(list.get(i).getErrorField());

                        // 得到第二行的错误描述和错误字段
                        ImportCompanyFailVO fail = list.get(j);
                        String errorMsg = fail.getErrorMsg();
                        String errorField = fail.getErrorField();

                        // 在一行的错误描述和错误字段后追加第二行的错误描述和错误字段,逗号分隔
                        sb.append(",").append(errorMsg);
                        sb2.append(",").append(errorField);

                        // 赋值
                        list.get(i).setErrorMsg(sb.toString());
                        list.get(i).setErrorField(sb2.toString());

                        // 删除第二行
                        list.remove(j);
                        j--;
                    }
                }
            }

            for (ImportCompanyFailVO item : list) {
                ImportCompanyFail fail = new ImportCompanyFail();
                //入库失败记录库
                BeanUtil.copyProperties(item, fail);
                importCompanyFailRepository.save(fail);
            }
            // 很重要的一步:每次新增完后,清空集合
            list.clear();
        }


    }

}
