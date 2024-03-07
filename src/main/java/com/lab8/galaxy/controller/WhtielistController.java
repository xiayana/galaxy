package com.lab8.galaxy.controller;

import com.lab8.galaxy.common.ResponseCode;
import com.lab8.galaxy.entity.ResultData;
import com.lab8.galaxy.entity.Whtielist;
import com.lab8.galaxy.entity.WhtielistCsv;
import com.lab8.galaxy.entity.WhtielistCsvData;
import com.lab8.galaxy.service.WhtielistCsvService;
import com.lab8.galaxy.service.WhtielistService;
import com.lab8.galaxy.utils.WhtielistUtil;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;
import com.opencsv.CSVReader;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 * (Whtielist)表控制层
 *
 * @author xy
 * @since 2024-01-04 18:57:11
 */
@Slf4j
@RestController
@RequestMapping("whtielist")
public class WhtielistController {
    /**
     * 服务对象
     */
    @Resource
    private WhtielistService whtielistService;
    @Resource
    private WhtielistCsvService whtielistCsvService;
    /**
     * 分页查询
     *
     * @param whtielist 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<Whtielist>> queryByPage(Whtielist whtielist, PageRequest pageRequest) {
        return ResponseEntity.ok(this.whtielistService.queryByPage(whtielist, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResultData queryById(@PathVariable("id") Integer id) {
        ResultData resultData = new ResultData();
        resultData.setData(this.whtielistService.queryById(id));
        return resultData;
    }
    @GetMapping("launchById")
    public ResultData launchById( Integer id,String address) {
        log.info("Request arrives whtielist -launchById-");

        ResultData resultData = new ResultData();
        Whtielist whtielist = whtielistService.launchById(id,address);
        resultData.setData(whtielist);
        return resultData;
    }

    @GetMapping("queryByWhitelist")
    public ResultData queryByWhitelist(String address,Integer pid) {
        log.info("Request arrives whtielist -queryByWhitelist-");

        ResultData resultData = new ResultData();
        WhtielistCsv addressResult = whtielistService.queryByWhitelist(address,pid);
      if(addressResult == null){
          resultData.setCode(ResponseCode.NOT_WHITELISTED_USER);
          resultData.setMsg("no whitelist");
          resultData.setData(0);
      }else{
          addressResult.setId(0);
          addressResult.setCreatedAt(null);
          addressResult.setStatus(0);
          addressResult.setPid(0);
          resultData.setData(addressResult);
      }
        return resultData;
    }
    /**
     * 新增数据
     *
     * @param whtielist 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public ResultData add(@RequestBody  Whtielist whtielist) {
        log.info("Request arrives whtielist -add-");

        ResultData resultData = new ResultData();
        if (WhtielistUtil.areAllFieldsEmpty(whtielist)) {
            // 处理所有字段都为空的情况
            log.info("所有数据都为空，本次请求不入库");
            resultData.setData("All fields are empty");
            return resultData;
        }
        resultData.setData(this.whtielistService.insert(whtielist));
        return resultData;
    }

    /**
     * 编辑数据
     *
     * @param whtielist 实体
     * @return 编辑结果
     */
    @PostMapping("/edit")
    public ResultData edit(@RequestBody  Whtielist whtielist) {
        log.info("Request arrives whtielist -edit-");

        ResultData resultData = new ResultData();
        resultData.setData(this.whtielistService.update(whtielist));
        return resultData;
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.whtielistService.deleteById(id));
    }
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/upload")
    public ResultData uploadFile(@RequestParam("file") MultipartFile file ,@RequestParam("pid") Integer pid){
        log.info("Request arrives whtielist -upload-");

        ResultData resultData = new ResultData();
        Integer del = whtielistCsvService.deleteDataWithStatus3(pid,3);
        log.info("del status 3 count :"+del);
        Integer igCache = whtielistCsvService.updateByStatus(pid,4,3);
        log.info("ig status 1 count :"+igCache);
        String fileName = file.getOriginalFilename();
        List<String> data = new ArrayList<>();
        List<WhtielistCsv> list = new ArrayList<>();
        List<WhtielistCsvData> dataList = new ArrayList<>();
        DataFormatter formatter = new DataFormatter(); // 创建一个 DataFormatter 来格式化并获取单元格的文本

        try {
            if (fileName.endsWith(".xlsx") || fileName.endsWith(".xls")) {
                // Process Excel file
                Workbook workbook = WorkbookFactory.create(file.getInputStream());
                Sheet sheet = workbook.getSheetAt(0);
                for (Row row : sheet) {
/*                    Cell cell = row.getCell(0);
                    if (cell != null) {
                        data.add(cell.getStringCellValue().toString());
                    }*/
                    String column1Value = "";
                    Integer column2Value = 0;

                    if (row.getCell(0) != null) {
                        column1Value = formatter.formatCellValue(row.getCell(0)); // 使用 DataFormatter 获取单元格文本
                    }
                    if (row.getCell(1) != null) {
                        String column2Text = formatter.formatCellValue(row.getCell(1)); // 同样处理第二个单元格
                        try {
                            column2Value = Integer.parseInt(column2Text); // 尝试将文本转换为整数
                        } catch (NumberFormatException e) {
                            column2Value = 0; // 如果转换失败，则默认为 0
                        }
                    } dataList.add(new WhtielistCsvData(column1Value, column2Value));
                }
            } else if (fileName.endsWith(".csv")) {
                // Process CSV file
                try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
                    String[] nextLine;
                    while ((nextLine = reader.readNext()) != null) {
                     //   data.add(nextLine[0]); // Assumes single column CSV
                        String column1Value = nextLine[0];
                        Integer column2Value = nextLine.length > 1 ? Integer.parseInt(nextLine[1]) : 0;
                        dataList.add(new WhtielistCsvData(column1Value, column2Value));
                    }
                }
            }
   /*         for (String add : data) {
                list.add(new WhtielistCsv(add, 0,pid,4));
            }*/
            for (WhtielistCsvData data1 : dataList) {
                // 检查 addr 字段是否为空或只包含空白字符
                if (data1.getColumn1() != null && !data1.getColumn1().trim().isEmpty()) {
                    list.add(new WhtielistCsv(data1.getColumn1(), data1.getColumn2(), pid, 4));
                }
            }
           int i =  whtielistCsvService.insertBatch(list);
            log.info("success count :"+i);
        } catch (IOException e) {
            log.error("IO Error: " + e.getMessage());
            resultData.setCode(1);
            resultData.setMsg("数据异常！");
            return resultData;
        } catch (EncryptedDocumentException e) {
            log.error("Encrypted document error: " + e.getMessage());
            resultData.setCode(1);
            resultData.setMsg("数据异常！");
            return resultData;
        }
        catch (CsvException | InvalidFormatException e) {
            log.error("CSV processing error: " + e.getMessage());
            resultData.setCode(1);
            resultData.setMsg("数据异常！");
            return resultData;
        }
        catch (IllegalStateException e) {
            log.error("IllegalState error: " + e.getMessage());
            resultData.setCode(1);
            resultData.setMsg("数据异常！");
            return resultData;
        }
        resultData.setData(data);
        return resultData;
    }

}

