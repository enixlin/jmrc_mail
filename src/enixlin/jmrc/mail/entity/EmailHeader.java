
/**
 * 
 */
package enixlin.jmrc.mail.entity;

/**
 * 邮件头部实体类<br>
 * 邮件头部提供了邮件的一些概要信息，主要是用户生成邮件列表
 * @author enixl
 *
 */
public class EmailHeader {
    
    private Long fileId;
    private String filename;
    private String filePath;
    private Long filesize;
    private Long messageId;
    private String senderName;
    private Long tid;
    private String title;
    
    public Long getFileId() {
        return fileId;
    }
    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }
    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public Long getFilesize() {
        return filesize;
    }
    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }
    public Long getMessageId() {
        return messageId;
    }
    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }
    public String getSenderName() {
        return senderName;
    }
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
    public Long getTid() {
        return tid;
    }
    public void setTid(Long tid) {
        this.tid = tid;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    
    

}

