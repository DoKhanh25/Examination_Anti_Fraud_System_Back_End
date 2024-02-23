package com.dean.server.service;

import com.dean.server.dto.*;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;

@Service
public class AccountService {
    public static final int COLUMN_INDEX_USERNAME = 1;
    public static final int COLUMN_INDEX_PASSWORD = 2;
    public static final int COLUMN_INDEX_NAME = 3;



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private  JwtUtil jwtUtil;

    Logger logger = LoggerFactory.getLogger(AccountService.class);


    public UserDTO findUserByUserName(String username){
        UserDTO userDTO = new UserDTO();
        UserEntity userEntity = this.userRepository.findByUsername(username);

        userDTO.setId(userEntity.getId());
        userDTO.setName(userEntity.getName());
        userDTO.setRole(userDTO.getRole());
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setPassword(userEntity.getPassword());

        return userDTO;
    }

    public ResponseEntity<?> register(RegisterDTO registerDTO){

        ResultDTO resultDTO = new ResultDTO();

        if(registerDTO.getUsername().isEmpty() ||
        registerDTO.getName().isEmpty() ||
        registerDTO.getPassword().isEmpty()){
            resultDTO.setErrorCode("-1");
            resultDTO.setData(null);
            resultDTO.setMessage("empty");
            return new ResponseEntity<>(resultDTO, HttpStatus.OK);

        }

        if(this.userRepository.findByUsername(registerDTO.getUsername()) != null ){
            resultDTO.setErrorCode("-1");
            resultDTO.setMessage("username is already taken");
            return new ResponseEntity<>(resultDTO, HttpStatus.OK);
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setName(registerDTO.getName());
        userEntity.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        userEntity.setUsername(registerDTO.getUsername());
        userEntity.setRole((short) 1);

        userRepository.save(userEntity);



        resultDTO.setErrorCode("0");
        resultDTO.setData(null);
        resultDTO.setMessage("success");

        return new ResponseEntity<>(resultDTO, HttpStatus.OK);

    }

    public ResponseEntity<?> login(LoginDTO loginDTO){
        ResultDTO resultDTO = new ResultDTO();
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();


        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserEntity userEntity = userRepository.findByUsername(authentication.getName());

        if(userEntity != null){
            String token = this.jwtUtil.generateToken(userEntity.getUsername(), Arrays.asList(userEntity.getRole().toString()));

            loginResponseDTO.setUsername(userEntity.getUsername());
            loginResponseDTO.setRole(userEntity.getRole().toString());
            loginResponseDTO.setAuthToken(token);
            loginResponseDTO.setMsv(userEntity.getMsv());

            resultDTO.setErrorCode("0");
            resultDTO.setData(loginResponseDTO);

            return new ResponseEntity<>(resultDTO, HttpStatus.OK);
        } else {
            resultDTO.setErrorCode("-1");
            resultDTO.setMessage("login fail");

            return new ResponseEntity<>(resultDTO, HttpStatus.OK);
        }
    }

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
                logger.info(String.valueOf(row.getRowNum()));
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
                        default:
                            break;
                    }

                }
                UserEntity userEntity = new UserEntity();
                userEntity.setName(registerDTO.getName());
                userEntity.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
                userEntity.setUsername(registerDTO.getUsername());
                userEntity.setRole((short) 1);

                logger.info(registerDTO.getUsername());

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
                logger.info(ex.getMessage());
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



}
