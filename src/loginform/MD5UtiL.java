/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loginform;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author ACER
 */
public class MD5UtiL {
    public static String md5(String input) {
    try {
        // Tạo đối tượng MessageDigest sử dụng thuật toán MD5
        MessageDigest md = MessageDigest.getInstance("MD5");
        
        // Băm chuỗi đầu vào và lưu vào mảng byte
        byte[] messageDigest = md.digest(input.getBytes());
        
        // Chuyển đổi mảng byte thành chuỗi hexadecimal
        StringBuilder sb = new StringBuilder();
        for (byte b : messageDigest) {
            sb.append(String.format("%02x", b));
        }
        
        // Trả về chuỗi hash
        return sb.toString();
    } catch (NoSuchAlgorithmException e) {
        // Ném ra ngoại lệ nếu thuật toán MD5 không được hỗ trợ
        throw new RuntimeException(e);
    }
}

}
