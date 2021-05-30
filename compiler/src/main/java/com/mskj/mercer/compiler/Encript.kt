package com.mskj.mercer.compiler

import java.security.MessageDigest

object Encript {

    //十六进制下数字到字符的映射数组
    private val hexDigits =
        arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F")

    /**把inputString加密 */
    fun md5(inputStr: String): String {
        return encodeByMD5(inputStr)
    }

    /**对字符串进行MD5编码 */
    private fun encodeByMD5(originString: String): String {
        return try {
            //创建具有指定算法名称的信息摘要
            val md5 =
                MessageDigest.getInstance("MD5")
            //使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
            val results = md5.digest(originString.toByteArray())
            //将得到的字节数组变成字符串返回
            byteArrayToHexString(results)
        } catch (e: Exception) {
            // e.printStackTrace()
            throw e
        }
    }

    /**
     * 轮换字节数组为十六进制字符串
     * @param b 字节数组
     * @return 十六进制字符串
     */
    private fun byteArrayToHexString(b: ByteArray): String {
        val resultSb = StringBuffer()
        for (i in b.indices) {
            resultSb.append(byteToHexString(b[i]))
        }
        return resultSb.toString()
    }

    //将一个字节转化成十六进制形式的字符串
    private fun byteToHexString(b: Byte): String {
        var n = b.toInt()
        if (n < 0) n = 256 + n
        val d1 = n / 16
        val d2 = n % 16
        return hexDigits[d1] + hexDigits[d2]
    }

}