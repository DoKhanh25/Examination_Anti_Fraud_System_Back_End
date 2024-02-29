package com.dean.server.service;

import com.dean.server.dto.RegisterDTO;
import com.dean.server.dto.ResultDTO;
import com.dean.server.entity.UserEntity;
import com.dean.server.repository.UserRepository;
import com.dean.server.security.JwtUtil;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Service
public class AccountService {

    Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;



    public static final int COLUMN_INDEX_USERNAME = 0;
    public static final int COLUMN_INDEX_PASSWORD = 1;
    public static final int COLUMN_INDEX_NAME = 2;
    public static final int COLUMN_INDEX_ROLE = 3;

    public ResponseEntity<?> saveAccountsByExcel(MultipartFile file){

        ResultDTO resultDTO = new ResultDTO();

        OPCPackage pkg = null;
        XSSFWorkbook xlsxbook = null;
        InputStream xlsxContentStream = null;
        try {
            xlsxContentStream = file.getInputStream();
            pkg = OPCPackage.open(xlsxContentStream);
            xlsxbook = new XSSFWorkbook(pkg);

            XSSFSheet sheet = xlsxbook.getSheetAt(0);



            Iterator<Row> itr = sheet.iterator();

            while (itr.hasNext()) {
                Row row = itr.next();
                if(row.getRowNum() == 0){
                    continue;
                }

                Iterator<Cell> cellIterator = row.cellIterator();

                RegisterDTO registerDTO = new RegisterDTO();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    Object cellValue = getCellValue(cell);

                    int columnIndex = cell.getColumnIndex();
                    switch (columnIndex){
                        case COLUMN_INDEX_USERNAME:
                            registerDTO.setUsername((String) cellValue);
                            break;
                        case COLUMN_INDEX_PASSWORD:
                            registerDTO.setPassword((String) cellValue);
                            break;
                        case COLUMN_INDEX_NAME:
                            registerDTO.setName((String) cellValue);
                            break;
                        case COLUMN_INDEX_ROLE:
                            registerDTO.setRole(Short.parseShort(cellValue.toString()));
                        default:
                            break;
                    }

                }
                UserEntity userEntity = new UserEntity();
                userEntity.setName(registerDTO.getName());
                userEntity.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
                userEntity.setUsername(registerDTO.getUsername());
                userEntity.setRole(registerDTO.getRole());

                logger.info("registerDTO: " + registerDTO.toString());

                userRepository.save(userEntity);

            }

            resultDTO.setErrorCode("0");
            resultDTO.setMessage("success");
            resultDTO.setData(null);
            return ResponseEntity.status(HttpStatus.OK).body(resultDTO);
        } catch (Exception e) {
            resultDTO.setErrorCode("-1");
            resultDTO.setMessage("fail");
            resultDTO.setData(null);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(resultDTO);
        } finally {
            try {
                xlsxContentStream.close();
                xlsxbook.close();
            } catch (Exception ex){
            }

        }
    }

    private static Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        Object cellValue = null;
        switch (cellType) {
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case FORMULA:
                Workbook workbook = cell.getSheet().getWorkbook();
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                cellValue = evaluator.evaluate(cell).getNumberValue();
                break;
            case NUMERIC:
                cellValue = cell.getNumericCellValue();
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case _NONE:
            case BLANK:
            case ERROR:
                break;
            default:
                break;
        }

        return cellValue;
    }

    public ResponseEntity<?> getAllStudentAccount(){

        List<String> usernameList = userRepository.getAllUsernameByRole((short) 1);
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setErrorCode("0");

        resultDTO.setData(usernameList);

        return ResponseEntity.status(HttpStatus.OK).body(resultDTO);
    }
}
