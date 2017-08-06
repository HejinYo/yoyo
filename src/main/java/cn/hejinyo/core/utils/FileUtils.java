package cn.hejinyo.core.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class FileUtils {
    private static final Log LOG = LogFactory.getLog(FileUtils.class);

    /**
     * 把一个文件转化为字节
     *
     * @param file
     * @return byte[]
     * @throws Exception
     */
    public static byte[] getByte(File file) {
        byte[] bytes = null;
        FileInputStream os = null;
        if (file != null) {
            try {
                os = new FileInputStream(file);
                int length = (int) file.length();
                if (length > Integer.MAX_VALUE) {
                    return null;
                }
                bytes = new byte[length];
                int offset = 0;
                int numRead = 0;
                while (offset < bytes.length
                        && (numRead = os.read(bytes, offset, bytes.length
                        - offset)) >= 0) {
                    offset += numRead;
                }
                // 如果得到的字节长度和file实际的长度不一致就可能出错了
                if (offset < bytes.length) {
                    System.out.println("file length is error");
                    return null;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        return bytes;
    }

    /**
     * 此类实现了一个输出流，其中的数据被写入一个 byte 数组。缓冲区会随着数据的不断写入而自动增长。 可使用 toByteArray() 和
     * toString() 获取数据。 关闭 ByteArrayOutputStream 无效。此类中的方法在关闭此流后仍可被调用，而不会产生任何
     * IOException。
     *
     * @param filename 文件路径名称
     * @param buffSize 文件读取流ByteArrayOutputStream， 缓冲区大小
     * @return
     */
    public static ByteArrayOutputStream getOutStreamByte(String filename, int buffSize) {
        LOG.debug("read file:" + filename);
        BufferedInputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(filename));
            out = new ByteArrayOutputStream(buffSize);
            byte[] temp = new byte[buffSize];
            int size = 0;
            while ((size = in.read(temp)) != -1) {
                out.write(temp, 0, size);
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                    LOG.debug("close when read finished");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    LOG.debug("close when write to out finished");
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return out;
    }

    /**
     * 删除指定文件
     *
     * @param filename
     */
    public static void deleteFile(String filename) {
        File tmp = new File(filename);
        boolean flag = false;
        if (tmp.exists()) {
            flag = tmp.delete();
        }
        if (!flag) {
            LOG.error("文件删除失败：" + filename);
        }

    }

    /**
     * 传输文件
     *
     * @param fileName
     * @param prefix
     * @param response
     */
    public static void transferFile(String fileName, String prefix, HttpServletResponse response) {
        LOG.debug("transfer start filename:" + fileName);
        ByteArrayOutputStream os = FileUtils.getOutStreamByte(fileName, 1024 * 32);
        ByteArrayInputStream inStream = null;
        try {
            inStream = new ByteArrayInputStream(os.toByteArray());
            LOG.debug("get instream size :" + inStream.available());
            if (os != null && os.size() > 0) {
                long filelength = os.size();
                // 设置输出的格式
                response.reset();
                response.setContentType("application/x-msdownload");
                response.setContentLength((int) filelength);
                response.setContentType("text/html;charset=UTF-8");
                response.addHeader(
                        "Content-Disposition",
                        "attachment; filename=\""
                                + new String(prefix.getBytes("GBK"),
                                "ISO8859_1") + ".xls\"");
                LOG.debug("init format ok!");
                // 循环取出流中的数据
                byte[] b = new byte[4];
                int len;
                //              os.flush();
                ServletOutputStream out = response.getOutputStream();
                while ((len = inStream.read(b)) != -1) {
                    //                  LOG.debug("write byte: "+Arrays.toString(b));
                    out.write(b, 0, len);
                    //                  response.getWriter().write(b.toString().toCharArray(), 0, len);
                }
                out.flush();
                out.close();
                LOG.debug("out close ok");
                response.flushBuffer();
                LOG.debug("response finished");
            }

        } catch (FileNotFoundException e) {
            LOG.error("要发送的文件不存在");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            LOG.error("编码格式不支持");
            e.printStackTrace();
        } catch (IOException e) {
            LOG.debug("文件流异常");
            e.printStackTrace();
        } finally {
            if (null != inStream) {
                try {
                    inStream.close();
                    LOG.debug("inStream close ok");
                } catch (IOException e) {
                    LOG.error("文件关闭异常");
                    e.printStackTrace();
                }
            }

        }

    }

}
