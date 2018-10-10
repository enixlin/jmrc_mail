
/**
 * 
 */
package enixlin.jmrc.mail.entity;

/**
 * 
 * 系统用户实体类
 * 
 * @author enixlin
 *
 */
public class User {

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getTypeCode() {
	return typeCode;
    }

    public void setTypeCode(String typeCode) {
	this.typeCode = typeCode;
    }

    public String getParentId() {
	return parentId;
    }

    public void setParentId(String parentId) {
	this.parentId = parentId;
    }

    public String getMobileno() {
	return mobileno;
    }

    public void setMobileno(String mobileno) {
	this.mobileno = mobileno;
    }

    public String getIsLeaf() {
	return isLeaf;
    }

    public void setIsLeaf(String isLeaf) {
	this.isLeaf = isLeaf;
    }

    public String getExpanded() {
	return expanded;
    }

    public void setExpanded(String expanded) {
	this.expanded = expanded;
    }

    public String getEmpcode() {
	return empcode;
    }

    public void setEmpcode(String empcode) {
	this.empcode = empcode;
    }

    public String getImg() {
	return img;
    }

    public void setImg(String img) {
	this.img = img;
    }

    private String id;
    private String name;
    private String typeCode;
    private String parentId;
    private String mobileno;
    private String isLeaf;
    private String expanded;
    private String empcode;
    private String img;
    private String otel;

    public String getOtel() {
	return otel;
    }

    public void setOtel(String otel) {
	this.otel = otel;
    }

    public String toString() {
	return name.replace("\"", "");
    }

}
