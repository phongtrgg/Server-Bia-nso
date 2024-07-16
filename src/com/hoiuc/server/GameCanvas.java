package com.hoiuc.server;
import com.hoiuc.io.Message;
import com.hoiuc.io.SQLManager;
import com.hoiuc.io.Util;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
public class GameCanvas {
    public static void addInfoDlg(Session session, String s) {
        Message msg = null;
        try {
            msg = Service.messageNotMap((byte)(-86));
            msg.writer().writeUTF(s);
            msg.writer().flush();
            session.sendMessage(msg);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (msg != null) {
                msg.cleanup();
            }
        }
    }

    public static void startOKDlg(Session session, String info) {
        Message msg = null;
        try {
            msg = new Message(-26);
            msg.writer().writeUTF(info);
            msg.writer().flush();
            session.sendMessage(msg);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (msg != null) {
                msg.cleanup();
            }
        }
    }

    public static void addEffect(Session session, byte b, int vId, short id, int timelive, int miliSecondWait, boolean isHead) {
        Message msg = null;
        try {
            if(session != null) {
                msg = new Message(125);
                msg.writer().writeByte(0);
                msg.writer().writeByte(b);
                if (b == 1) {
                    msg.writer().writeByte(vId);
                }
                else {
                    msg.writer().writeInt(vId);
                }
                msg.writer().writeShort(id);
                msg.writer().writeInt(timelive);
                msg.writer().writeByte(miliSecondWait);
                msg.writer().writeByte(isHead ? 1 : 0);
                msg.writer().flush();
                session.sendMessage(msg);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (msg != null) {
                msg.cleanup();
            }
        }
    }

    public static void getImgEffect(Session session, short id) {
        Message msg = null;
        try {
            if(session != null) {
                //System.out.println("Lấy ảnh " + id);
                byte[] ab = GameSrc.loadFile("res/Effect/x" + session.zoomLevel + "/ImgEffect/ImgEffect " + id + ".png" ).toByteArray();
                if (ab != null) {
                    msg = new Message(125);
                    msg.writer().writeByte(1);
                    msg.writer().writeByte(id);
                    msg.writer().writeInt(ab.length);
                    msg.writer().write(ab);
                    msg.writer().flush();
                    session.sendMessage(msg);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (msg != null) {
                msg.cleanup();
            }
        }
    }

    public static void getDataEffect(Session session, short id) {
        Message msg = null;
        try {
            if(session != null) {
                //System.out.println("Lấy data " + id);
                //byte[] ab = GameSrc.loadFile("res/Effect/x" + session.zoomLevel + "/DataEffect/" + id).toByteArray();
                ResultSet rs = SQLManager.stat.executeQuery("SELECT * FROM `effdata` WHERE `id` = " + id + ";");
                byte[] ab;
                rs.last();
                if (rs.getRow() == 0) {
                    ab = GameSrc.loadFile("res/Effect/x" + session.zoomLevel + "/DataEffect/" + id).toByteArray();
                } else {
                    rs.beforeFirst();
                    ab = Getdata(rs,id); 
                }
                if (ab != null) {
                    if(id==21){
                        ab[6]=127;
                        ab[8]=127;
                        ab[9]=127;
                        ab[11]=127;
                        ab[12]=127;
                        ab[13]=127;
                        ab[14]=127;
                        ab[18]=127;
                        ab[19]=127;
                        ab[22]=127;
                        ab[23]=127;
                        ab[24]=127;
                        ab[29]=-60;
                        ab[31]=70;
                        ab[37]=-60;
                        ab[39]=70;
                        ab[45]=-60;
                        ab[47]=70;
                        ab[53]=-60;
                        ab[55]=70;
                        ab[59]=127;

                        msg = new Message(125);
                        msg.writer().write(ab);
                        msg.writer().flush();
                        session.sendMessage(msg);
                    }
                    else {
                        ab[1] = (byte) id;
                        msg = new Message(125);
                        msg.writer().write(ab);
                        msg.writer().flush();
                        session.sendMessage(msg);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (msg != null) {
                msg.cleanup();
            }
        }
    }
    
    public static byte[] Getdata(ResultSet rs, short id) {
        
        try {
            rs = SQLManager.stat.executeQuery("SELECT * FROM `effdata` WHERE `id` = " + id + ";");
            while (rs.next()) {

                JSONObject jsob = (JSONObject) JSONValue.parse(rs.getString("frames"));
                jsob = (JSONObject) jsob.get("frames");
                JSONObject jsob2 = jsob;
                String name = Getname(jsob);
                if (Integer.parseInt(rs.getString("type")) != jsob2.size()) {
                    break;
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(baos);
                dos.writeByte(2);
                dos.writeByte(id);
                dos.writeByte(0);
                dos.writeByte(-43);
                dos.writeByte(jsob2.size());
                
                for (int i = 0; i < jsob2.size(); i++) {
                    jsob = (JSONObject) jsob2.get((name + i + ".png"));
                    jsob = (JSONObject) jsob.get("frame");
                    dos.writeByte(i);
                    dos.writeByte(Integer.parseInt(jsob.get("x").toString()));
                    dos.writeByte(Integer.parseInt(jsob.get("y").toString()));
                    dos.writeByte(Integer.parseInt(jsob.get("w").toString()));
                    dos.writeByte(Integer.parseInt(jsob.get("h").toString()));
                }

                byte dx = (byte) Integer.parseInt(rs.getString("dx"));
                byte dy = (byte) Integer.parseInt(rs.getString("dy"));
                byte fr = (byte) Integer.parseInt(rs.getString("front"));
                dos.writeShort(jsob2.size());
                for (int i = 0; i < jsob2.size(); i++) {
                    dos.writeByte(1);
                    dos.writeShort(dx);
                    dos.writeShort(dy);
                    dos.writeByte(i);
                    dos.writeByte(0);
                    dos.writeByte(fr);
                }
                
                //dos.writeByte((byte) (jsob2.size()*4));
                dos.writeByte((byte) 12);
                for (int i1 = 0; i1 < jsob2.size(); i1++) {
                    for (int i11 = 0; i11 < 2; i11++) {
                    dos.writeShort((short) i1);
                }
                }
                
                
                
                /*dos.writeByte(15);
                dos.writeShort(0);
                dos.writeShort(0);
                dos.writeShort(0);
                dos.writeShort(1);
                dos.writeShort(1);
                dos.writeShort(1);
                dos.writeShort(3);
                dos.writeShort(3);
                dos.writeShort(3);
                dos.writeShort(2);
                dos.writeShort(2);
                dos.writeShort(2);
                dos.writeShort(1);
                dos.writeShort(1);
                dos.writeShort(0);*/
                
                dos.flush();
                dos.close();
                
                byte[] ab2 = baos.toByteArray();
                return ab2;
            }
            System.out.println("load datasql id - " + id);
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return new byte[1];
    }

    public static String Getname(JSONObject s) {
        String s2 = "";
        int i = 0;
        while (!s2.contains(".png")) {
            s2 += s.toJSONString().charAt(i);
            i++;
        }
        return s2.substring(2, s2.length() - 5);

    }
}
