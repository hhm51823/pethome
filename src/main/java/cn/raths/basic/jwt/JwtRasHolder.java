package cn.raths.basic.jwt;

/**
* @Description: 用于spring容器加载完成后存储JWT公钥私钥名称的类
* @Author: Mr.She
* @Version: 1.0
* @Date:  2022/7/3 11:11
*/
public enum JwtRasHolder {
    INSTANCE;
    private byte[] jwtRsaPubData;
    private byte[] jwtRsaPrivateData;

    public byte[] getJwtRsaPubData() {
        return jwtRsaPubData;
    }

    public void setJwtRsaPubData(byte[] jwtRsaPubData) {
        this.jwtRsaPubData = jwtRsaPubData;
    }

    public byte[] getJwtRsaPrivateData() {
        return jwtRsaPrivateData;
    }

    public void setJwtRsaPrivateData(byte[] jwtRsaPrivateData) {
        this.jwtRsaPrivateData = jwtRsaPrivateData;
    }
}