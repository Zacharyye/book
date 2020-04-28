package com.zacharye.book.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 加密工具类
 */
public class RSAUtil {
  private static RSAUtil security = new RSAUtil();

  private RSAUtil() {


  }

  public static void main(String[] args) {
    //security.generateKeyPair();
    try {
      //密钥对
      //String publicKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAp7w6yNZjVIpvkztsGLqeVnQB556v48LNDWq6gdfdAPg9PelZsj6T7lBZ3meQQvrUFXCIdkSe4wH6xcK4YXZb3g4U7OoAQF4v5Q+FWEnSA4Luk1l1v9EeYeSd69yq/2iSB4+VxQQG2xD+JG6UDsOrMoqqW8NPkjB4ZKVhSt3zXKupkQc/bP0E4Xo6ZgiWh6a2STLfy4jK2zb/95YJ8k1L/Tv1yGUXJQMhF30b1v7u8+wvujdYTEX+WvVX+T+o3Ddl5TOTX+gZRSp6AEeE6rJQUlY+pU5YNRYtx4nXxgB6AEAAgU2L8aDfjbxfKcS77uBaMmbJmVhlbGV81HJBnfeLJQIDAQAB";
      //String privateKeyStr = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCnvDrI1mNUim+TO2wYup5WdAHnnq/jws0NarqB190A+D096VmyPpPuUFneZ5BC+tQVcIh2RJ7jAfrFwrhhdlveDhTs6gBAXi/lD4VYSdIDgu6TWXW/0R5h5J3r3Kr/aJIHj5XFBAbbEP4kbpQOw6syiqpbw0+SMHhkpWFK3fNcq6mRBz9s/QThejpmCJaHprZJMt/LiMrbNv/3lgnyTUv9O/XIZRclAyEXfRvW/u7z7C+6N1hMRf5a9Vf5P6jcN2XlM5Nf6BlFKnoAR4TqslBSVj6lTlg1Fi3HidfGAHoAQACBTYvxoN+NvF8pxLvu4FoyZsmZWGVsZXzUckGd94slAgMBAAECggEBAJVvgeO59Ka16u996jEh2bzl/2huHP/KZkrEwkK5oSCjCFSnF41QaRTRKy+UjpbNxrMPHdRYEEHoWpVjhEb4XqI3Y3cqhmzSPNNS/5aHtf4A4TIPanMpCGtodTVT1FhVvnvT9Tef/cefnPuyNF/u/Q+7MEkueS6h1jC8kjBUCuOMa+ZWIx+5rHthacrZl0WiFaWeJoXDDcBuJlKPyVEAcbZnVQs8RBP/yn3u8IGICRCZvNp1iih80VoNQz87Uv2dihOx4EkMhP90RCQOZGL5al38QGiymsbWD6qwg8gl7y/7NSJFCC24E/kThFrAYCx1brWZJ8WhfPPROnISYSzgRuUCgYEA68axo/5X9mh5peiHkxCcQkQOhuudGygTEd5i/nNRcEBGFPJtWI5KCZRPTOMkRjMBEJojk1b06oe5iPkrb6bDFE/GExh4igqHvMTdpTXhK3ijkStyF0kAH0HVLEyUbz2EbUJFuazWRE583mAuS0UzQiQmExQE+Uz2ZgI541dTOi8CgYEAth90pc1fop8fNI0QIPIWQVGPnCEmuewhiYXVqL+XTft4eoCqS21+64cnYPXBMPF8oRYmHs5cNeKBtKEo9zsFOYC3I7Vo3BXx2eaylKix0J8k5cKzXH+/1AU/K8ETTUmiaov92nSrYZA7kpik7UtEABA3t/o3bK2kKI9IP3tyfusCgYAiGDB1+4Cy1DbLJA3+2UcPWhITtpyYYbooOlVsYDalmdXIj6n6RVE1g2nYP3xuXk/IG9ILK2btR0UwJn/+fEn3dPW6qy47H/yy8nDuxBTUmXRluLed4GORIY73AffxAh9VQWEd0X2GkScDbTskjVQ1O076MUnq1xNGewnt8yHI5wKBgGMu0Ez3gszCkbEB3bXDgYFDFM23dCquwTju7QQAizsJ53v3lEaNtCA6s6RnUjYAXZowwMPoKGmkGHUxi2jQ+LBvO82znw602MiNjdXN5UCzWtnR5fJLFEI4NUXz2TovDsetw6rz/N2eOgNFJBSi9759FosYNpT2H7+DAJqp9RnPAoGBAOWuAVvHjfj7UewKTQmx8xcNzao9QLKJCL76ATJtLbtsZ8ap33JFsMLXRym7pxXYPKmaolXP3CKAisQYQ2GWCQuzYS5gTCTSRgV0taRFkmHvnOmJqdot8wxaNgP+Jn5XIZncUBlLxlpNQy6r2DiqW/TzvAjqZZuHjfyWH8YaYHiI";
      //数据组装
      String publicKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAheQWW3StUwH7G1S5PadBBYuYnr42IjA/Y7u9j2qsBeaiW9v11bAzKoKHBrUhu0Mxfk+W6hGf/TjpykDo7pvCWaGMvjYKCnpJ4EnEfszz8V5VeXOLB1lSBAsqABcNIeXZKsK0UGd9meAxXO4AuRTfY+IBnBdmQFpxgbTGOWzFtE34cchRWC7R+dzBIKbUBKQIcTeCPw/WOer4RM7o2CnTT+Ph+92dInu++NpnxdxOE3EhfrKQXcdPZ8GNogJ8iUnjpZsHMA6Yy0Pu6w5gpG4AfDHeYpBnOziVaPZhr7gA1Lf3ODTK1u4nVhsyyFCPl9kTptcR/whS0ahA4Q7r1jJttwIDAQAB";
      String privateKeyStr = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCnvDrI1mNUim+TO2wYup5WdAHnnq/jws0NarqB190A+D096VmyPpPuUFneZ5BC+tQVcIh2RJ7jAfrFwrhhdlveDhTs6gBAXi/lD4VYSdIDgu6TWXW/0R5h5J3r3Kr/aJIHj5XFBAbbEP4kbpQOw6syiqpbw0+SMHhkpWFK3fNcq6mRBz9s/QThejpmCJaHprZJMt/LiMrbNv/3lgnyTUv9O/XIZRclAyEXfRvW/u7z7C+6N1hMRf5a9Vf5P6jcN2XlM5Nf6BlFKnoAR4TqslBSVj6lTlg1Fi3HidfGAHoAQACBTYvxoN+NvF8pxLvu4FoyZsmZWGVsZXzUckGd94slAgMBAAECggEBAJVvgeO59Ka16u996jEh2bzl/2huHP/KZkrEwkK5oSCjCFSnF41QaRTRKy+UjpbNxrMPHdRYEEHoWpVjhEb4XqI3Y3cqhmzSPNNS/5aHtf4A4TIPanMpCGtodTVT1FhVvnvT9Tef/cefnPuyNF/u/Q+7MEkueS6h1jC8kjBUCuOMa+ZWIx+5rHthacrZl0WiFaWeJoXDDcBuJlKPyVEAcbZnVQs8RBP/yn3u8IGICRCZvNp1iih80VoNQz87Uv2dihOx4EkMhP90RCQOZGL5al38QGiymsbWD6qwg8gl7y/7NSJFCC24E/kThFrAYCx1brWZJ8WhfPPROnISYSzgRuUCgYEA68axo/5X9mh5peiHkxCcQkQOhuudGygTEd5i/nNRcEBGFPJtWI5KCZRPTOMkRjMBEJojk1b06oe5iPkrb6bDFE/GExh4igqHvMTdpTXhK3ijkStyF0kAH0HVLEyUbz2EbUJFuazWRE583mAuS0UzQiQmExQE+Uz2ZgI541dTOi8CgYEAth90pc1fop8fNI0QIPIWQVGPnCEmuewhiYXVqL+XTft4eoCqS21+64cnYPXBMPF8oRYmHs5cNeKBtKEo9zsFOYC3I7Vo3BXx2eaylKix0J8k5cKzXH+/1AU/K8ETTUmiaov92nSrYZA7kpik7UtEABA3t/o3bK2kKI9IP3tyfusCgYAiGDB1+4Cy1DbLJA3+2UcPWhITtpyYYbooOlVsYDalmdXIj6n6RVE1g2nYP3xuXk/IG9ILK2btR0UwJn/+fEn3dPW6qy47H/yy8nDuxBTUmXRluLed4GORIY73AffxAh9VQWEd0X2GkScDbTskjVQ1O076MUnq1xNGewnt8yHI5wKBgGMu0Ez3gszCkbEB3bXDgYFDFM23dCquwTju7QQAizsJ53v3lEaNtCA6s6RnUjYAXZowwMPoKGmkGHUxi2jQ+LBvO82znw602MiNjdXN5UCzWtnR5fJLFEI4NUXz2TovDsetw6rz/N2eOgNFJBSi9759FosYNpT2H7+DAJqp9RnPAoGBAOWuAVvHjfj7UewKTQmx8xcNzao9QLKJCL76ATJtLbtsZ8ap33JFsMLXRym7pxXYPKmaolXP3CKAisQYQ2GWCQuzYS5gTCTSRgV0taRFkmHvnOmJqdot8wxaNgP+Jn5XIZncUBlLxlpNQy6r2DiqW/TzvAjqZZuHjfyWH8YaYHiI";
      JSONObject object = new JSONObject();
      object.put("tranCode","CSZ02");
      object.put("phone","17326722301");
      object.put("orgId","TEST");
      object.put("timestamp",(System.currentTimeMillis() / 1000) + "");
      System.out.println(object.toJSONString());
      //加密
      String encryptStr = security.encrypt(publicKeyStr, object.toJSONString());
      System.out.println(encryptStr);
      //解密
//      String decryptStr = security.decrypt(privateKeyStr,encryptStr);
//      System.out.println(decryptStr);
      //签名
      String sign = security.sign(SignatureSuite.SHA256,object.toJSONString(),privateKeyStr);
      System.out.println(sign);
      //验证签名
//      boolean flag = security.verifySign(SignatureSuite.SHA256, object.toJSONString().getBytes(), Base64.decodeBase64(sign), publicKeyStr);
//      System.out.println(flag);
      System.out.println("encryptBody=" + URLEncoder.encode(encryptStr) + "&sign=" + URLEncoder.encode(sign));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static RSAUtil getInstance() {
    return security;
  }

  private static KeyPairGenerator keyPairGenerator;

  {
    try {
      keyPairGenerator = KeyPairGenerator.getInstance("RSA");
      keyPairGenerator.initialize(2048);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }

  public void generateKeyPair() {
    KeyPair keyPair = keyPairGenerator.generateKeyPair();
    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
    System.out.println("PUBLIC-" + new String(Base64.encodeBase64(publicKey.getEncoded())));
    System.out.println("PRIVATE-" + new String(Base64.encodeBase64(privateKey.getEncoded())));
  }

  public static enum SignatureSuite {
    SHA1("SHA1WithRSA"),
    SHA256("SHA256WithRSA");

    private String suite;

    SignatureSuite(String suite) {
      this.suite = suite;
    }

    public String val() {
      return suite;
    }
  }


  private KeyFactory getKeyFactory() {
    try {
      return KeyFactory.getInstance("RSA");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  /**
   * 加密
   */
  public String encrypt(String rsaPublicKey, String src) {
    X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(rsaPublicKey));
    KeyFactory keyFactory;
    try {
      keyFactory = this.getKeyFactory();
      PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
      Cipher cipher = Cipher.getInstance("RSA");
      cipher.init(Cipher.ENCRYPT_MODE, publicKey);
      byte[] result = cipher.doFinal(src.getBytes());
      return new String(Base64.encodeBase64(result));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 解密
   */
  public String decrypt(String rsaPrivateKey, String ciphertxt) {
    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.decodeBase64(rsaPrivateKey));
    KeyFactory keyFactory;
    try {
      keyFactory = this.getKeyFactory();
      PrivateKey privateKey = keyFactory.generatePrivate(spec);
      Cipher cipher = Cipher.getInstance("RSA");
      cipher.init(Cipher.DECRYPT_MODE, privateKey);
      byte[] result = cipher.doFinal(Base64.decodeBase64(ciphertxt));
      return new String(result);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 签名
   */
  public String sign(SignatureSuite suite, String src, String privateKeyStr) {
    try {
      return Base64.encodeBase64String(this.sign(suite, src.getBytes("UTF-8"), privateKeyStr));
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("不支持的编码");
    }
  }

  public byte[] sign(SignatureSuite signatureSuite, byte[] srcBuf, String privateKeyStr) {
    Signature signature = null;
    try {
      signature = Signature.getInstance(signatureSuite.val());
      PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyStr));
      PrivateKey privateKey = this.getKeyFactory().generatePrivate(keySpec);

      signature.initSign(privateKey);
      signature.update(srcBuf);

      return signature.sign();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      throw new RuntimeException("NoSuchAlgorithm");
    } catch (InvalidKeySpecException e) {
      e.printStackTrace();
      throw new RuntimeException("InvalidKeySpec");
    } catch (InvalidKeyException e) {
      e.printStackTrace();
      throw new RuntimeException("InvalidKey");
    } catch (SignatureException e) {
      e.printStackTrace();
      throw new RuntimeException("签名失败");
    }
  }

  /**
   * 验签
   */
  public boolean verifySign(SignatureSuite suite, byte[] srcBuf, byte[] sign, String publicKeyStr) {
    Signature signature = null;
    try {
      signature = Signature.getInstance(suite.val());
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyStr));
      PublicKey publicKey = getKeyFactory().generatePublic(keySpec);

      signature.initVerify(publicKey);
      signature.update(srcBuf);

      return signature.verify(sign);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("No Such Method");
    } catch (InvalidKeySpecException e) {
      throw new RuntimeException("Invalid KeySpec");
    } catch (InvalidKeyException e) {
      throw new RuntimeException("Invalid Key");
    } catch (SignatureException e) {
      throw new RuntimeException("Signature Error");
    }
  }
}
