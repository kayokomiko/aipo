package com.aimluck.eip.cayenne.om.portlet.auto;

/** Class _EipTMessageRead was generated by Cayenne.
  * It is probably a good idea to avoid changing this class manually, 
  * since it may be overwritten next time code is regenerated. 
  * If you need to make any customizations, please use subclass. 
  */
public class _EipTMessageRead extends org.apache.cayenne.CayenneDataObject {

    public static final String IS_READ_PROPERTY = "isRead";
    public static final String ROOM_ID_PROPERTY = "roomId";
    public static final String USER_ID_PROPERTY = "userId";
    public static final String EIP_TMESSAGE_PROPERTY = "eipTMessage";

    public static final String ID_PK_COLUMN = "ID";

    public void setIsRead(String isRead) {
        writeProperty("isRead", isRead);
    }
    public String getIsRead() {
        return (String)readProperty("isRead");
    }
    
    
    public void setRoomId(Integer roomId) {
        writeProperty("roomId", roomId);
    }
    public Integer getRoomId() {
        return (Integer)readProperty("roomId");
    }
    
    
    public void setUserId(Integer userId) {
        writeProperty("userId", userId);
    }
    public Integer getUserId() {
        return (Integer)readProperty("userId");
    }
    
    
    public void setEipTMessage(com.aimluck.eip.cayenne.om.portlet.EipTMessage eipTMessage) {
        setToOneTarget("eipTMessage", eipTMessage, true);
    }

    public com.aimluck.eip.cayenne.om.portlet.EipTMessage getEipTMessage() {
        return (com.aimluck.eip.cayenne.om.portlet.EipTMessage)readProperty("eipTMessage");
    } 
    
    
}
