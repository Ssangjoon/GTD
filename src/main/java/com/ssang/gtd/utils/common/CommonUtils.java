package com.ssang.gtd.utils.common;

import com.ssang.gtd.utils.VO.ResponseSystemVO;
import com.ssang.gtd.utils.VO.ResponseVO;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CommonUtils {
    public static String sha256(String msg)  throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");

        md.update(msg.getBytes());

        return CommonUtils.byteToHexString(md.digest());

    }

    public static String byteToHexString(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for(byte b : data) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static String setPassword(int length) {
        int index = 0;
        char[] charArr = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a',
                'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
                'w', 'x', 'y', 'z', '!', '@', '#' };
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            index = (int) (charArr.length * Math.random());
            sb.append(charArr[index]);
        }
        return sb.toString();
    }

    public String base64Encode(String str){
        String str64 =str;
        if(null != str64 && !"".equals(str64)){
            Base64.Encoder encoder = Base64.getEncoder();
            byte[] bytes = str64.getBytes(StandardCharsets.UTF_8);
            str64 = encoder.encodeToString(bytes);
        }
        return str64;
    }
    public String base64Decode(String str64){
        Base64.Decoder decoder = Base64.getDecoder();
        String returnStr;
        if(null == str64 || "".equals(str64)){
            returnStr = "";
        }else {
            returnStr = new String(decoder.decode(str64),StandardCharsets.UTF_8);
        }
        return returnStr;
    }
    public Object makeExceptionDataByAjax(String errMsg, String errCd){
        ResponseVO responseVo = new ResponseVO();
        ResponseSystemVO _system_ = new ResponseSystemVO();
        if(errCd != null && errCd.isEmpty()==false){
            _system_.setResultcode(errCd);
        }
        else{
            _system_.setResultcode("9999");
        }
        if(errMsg == null){
            errMsg = "";
        }
        _system_.setResultmsg(errMsg);
        //responseVo.set_system_(_system_);
        //responseVo.set_data_("");
        return responseVo;
    }
    public Object readFileObject(File file) throws IOException, ClassNotFoundException{
        InputStream in = null;
        try{
            in = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(in);
            return ois.readObject();
        } finally {
            if(in!= null){
                in.close();
            }
        }
    }
    public void writeFileObject(Object object, File file)throws IOException {
        OutputStream out = null;
        ObjectOutputStream oos = null;
        try{
            out = new FileOutputStream(file);
            oos = new ObjectOutputStream(out);

            oos.writeObject(object);
        }finally{
            oos.close();
            out.close();
        }
    }
}
